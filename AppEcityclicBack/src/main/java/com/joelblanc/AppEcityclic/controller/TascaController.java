package com.joelblanc.AppEcityclic.controller;

import com.joelblanc.AppEcityclic.entity.Projecte;
import com.joelblanc.AppEcityclic.entity.Tasca;
import com.joelblanc.AppEcityclic.service.ProjecteService;
import com.joelblanc.AppEcityclic.service.TascaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/empresa")
@CrossOrigin("http://localhost:4200/")
public class TascaController {

    private static final Logger logger = LoggerFactory.getLogger(TascaController.class);
    private final TascaService tascaService;
    private final ProjecteService projecteService;

    // Obtenir totes les tasques d'un projecte
    @GetMapping("/{empresaId}/projecte/{projecteId}/tasca")
    public ResponseEntity<List<Tasca>> getTasquesByProjecteId(@PathVariable Long empresaId, @PathVariable Long projecteId) {
        logger.debug("Obtenint tasques per projecteId: {}", projecteId);
        List<Tasca> tasques = tascaService.getTasquesPerProjecte(projecteId);
        logger.info("Tasques obtingudes: {}", tasques);
        return new ResponseEntity<>(tasques, HttpStatus.OK);
    }

    // Obtenir totes les tasques assignades a un treballador
    @GetMapping("{empresaId}/treballador/{treballadorId}/tasca")
    public ResponseEntity<List<Tasca>> getTasquesByTreballadorId(@PathVariable Long empresaId ,@PathVariable Long treballadorId) {
        logger.debug("Obtenint tasques per treballadorId: {}", treballadorId);
        List<Tasca> tasques = tascaService.getTasquesPerTreballador(treballadorId);
        logger.info("Tasques obtingudes: {}", tasques);
        return new ResponseEntity<>(tasques, HttpStatus.OK);
    }

    // Obtenir una tasqua per ID
    @GetMapping("/{empresaId}/projecte/{projecteId}/tasca/{tascaId}") // Nueva ruta
    public ResponseEntity<Tasca> getTascaById(@PathVariable Long empresaId, @PathVariable Long projecteId, @PathVariable Long tascaId) {
        logger.debug("Obtenint tasca per tascaId: {}", tascaId);
        Optional<Tasca> tasca = tascaService.findById(tascaId);
        logger.info("Tasca obtinguda: {}", tasca);
        if (tasca.isPresent()) {
            logger.info("Tasca trobada: {}", tasca.get());
            return new ResponseEntity<>(tasca.get(), HttpStatus.OK);
        } else {
            logger.warn("Tasca no trobada amb el id: {}", tascaId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear una tasca
    @PostMapping("/{empresaId}/projecte/{projecteId}/tasca") // Nueva ruta
    public ResponseEntity<Tasca> createTasca(@PathVariable Long empresaId, @PathVariable Long projecteId, @RequestBody Tasca tasca) {
        logger.debug("Creant tasca per projecteId: {}", projecteId);
        Optional<Projecte> projecteOpt = projecteService.findById(projecteId);
        if (projecteOpt.isPresent()) {
            tasca.setProjecte(projecteOpt.get());
            Tasca newTasca = tascaService.save(tasca);
            logger.info("Tasca creada: {}", newTasca);
            return new ResponseEntity<>(newTasca, HttpStatus.CREATED);
        } else {
            logger.warn("Projecte no trobat amb el id: {}", projecteId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualitzar una tasca
    @PutMapping("/{empresaId}/projecte/{projecteId}/tasca/{tascaId}")
    public ResponseEntity<Tasca> updateTasca(@PathVariable Long empresaId, @PathVariable Long projecteId, @RequestBody Tasca tasca, @PathVariable Long tascaId) {
        logger.debug("Actualitzant tasca per tascaId: {}", tascaId);
        Optional<Projecte> projecteOpt = projecteService.findById(projecteId);
        if (projecteOpt.isPresent()) {
            tasca.setProjecte(projecteOpt.get());
            logger.info("Projecte trobat: {}", projecteOpt.get());
            try {
                Tasca updatedTasca = tascaService.update(tasca, tascaId);
                logger.info("Tasca actualitzada: {}", updatedTasca);
                return new ResponseEntity<>(updatedTasca, HttpStatus.OK);
            } catch (EntityNotFoundException e){
                logger.warn("Tasca no trobada amb el id: {}", tascaId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e){
                logger.error("Error al actualitzar tasca: {}", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            logger.warn("Projecte no trobat amb el id: {}", projecteId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Borrar una tasca
    @DeleteMapping("/{empresaId}/projecte/{projecteId}/tasca/{id}")
    public ResponseEntity<Void> deleteTasca(@PathVariable Long empresaId, @PathVariable Long projecteId, @PathVariable Long id) {
        logger.debug("Eliminant tasca per id: {}", id);
        try {
            tascaService.delete(id);
            logger.info("Tasca eliminada");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            logger.warn("Tasca no trobada amb el id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al eliminar tasca: {}", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
