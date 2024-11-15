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
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/empresa")
@CrossOrigin("http://localhost:4200/")
public class TascaController {

    private final TascaService tascaService;
    private final ProjecteService projecteService;

    // Obtenir totes les tasques d'un projecte
    @GetMapping("/{empresaId}/projecte/{projecteId}/tasca")
    public ResponseEntity<List<Tasca>> getTasquesByProjecteId(@PathVariable Long empresaId, @PathVariable Long projecteId) {
        List<Tasca> tasques = tascaService.getTasquesPerProjecte(projecteId);
        return new ResponseEntity<>(tasques, HttpStatus.OK);
    }

    // Obtenir totes les tasques assignades a un treballador
    @GetMapping("{empresaId}/treballador/{treballadorId}/tasca")
    public ResponseEntity<List<Tasca>> getTasquesByTreballadorId(@PathVariable Long empresaId ,@PathVariable Long treballadorId) {
        List<Tasca> tasques = tascaService.getTasquesPerTreballador(treballadorId);
        return new ResponseEntity<>(tasques, HttpStatus.OK);
    }

    // Obtenir una tasqua per ID
    @GetMapping("/{empresaId}/projecte/{projecteId}/tasca/{tascaId}") // Nueva ruta
    public ResponseEntity<Tasca> getTascaById(@PathVariable Long empresaId, @PathVariable Long projecteId, @PathVariable Long tascaId) {
        Optional<Tasca> tasca = tascaService.findById(tascaId);
        return tasca.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear una tasca
    @PostMapping("/{empresaId}/projecte/{projecteId}/tasca") // Nueva ruta
    public ResponseEntity<Tasca> createTasca(@PathVariable Long empresaId, @PathVariable Long projecteId, @RequestBody Tasca tasca) {
        Optional<Projecte> projecteOpt = projecteService.findById(projecteId);
        if (projecteOpt.isPresent()) {
            tasca.setProjecte(projecteOpt.get());
            Tasca newTasca = tascaService.save(tasca);
            return new ResponseEntity<>(newTasca, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualitzar una tasca
    @PutMapping("/{empresaId}/projecte/{projecteId}/tasca/{tascaId}")
    public ResponseEntity<Tasca> updateTasca(@PathVariable Long empresaId, @PathVariable Long projecteId, @RequestBody Tasca tasca, @PathVariable Long tascaId) {
        Optional<Projecte> projecteOpt = projecteService.findById(projecteId);
        if (projecteOpt.isPresent()) {
            tasca.setProjecte(projecteOpt.get());
            try {
                Tasca updatedTasca = tascaService.update(tasca, tascaId);
                return new ResponseEntity<>(updatedTasca, HttpStatus.OK);
            } catch (EntityNotFoundException e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Borrar una tasca
    @DeleteMapping("/{empresaId}/projecte/{projecteId}/tasca/{id}")
    public ResponseEntity<Void> deleteTasca(@PathVariable Long empresaId, @PathVariable Long projecteId, @PathVariable Long id) {
        try {
            tascaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
