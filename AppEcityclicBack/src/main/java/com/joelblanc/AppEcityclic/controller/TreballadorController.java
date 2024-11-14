package com.joelblanc.AppEcityclic.controller;

import com.joelblanc.AppEcityclic.entity.Empresa;
import com.joelblanc.AppEcityclic.entity.Treballador;
import com.joelblanc.AppEcityclic.service.TreballadorService;
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
@RequestMapping("/api/empresa/{empresaId}/treballador")
@CrossOrigin(origins = "http://localhost:4200")
public class TreballadorController {

    private static final Logger logger = LoggerFactory.getLogger(TreballadorController.class);
    private final TreballadorService treballadorService;

    // Obtenir tots els treballadors
    @GetMapping
    public ResponseEntity<List<Treballador>> getTreballadorsByEmpresaId(@PathVariable Long empresaId) {
        logger.debug("Buscant treballadors per empresa id: {}", empresaId);
        List<Treballador> treballadors = treballadorService.findByEmpresa_Id(empresaId);
        logger.info("Trobats {} treballadors per empresa id: {}", treballadors.size(), empresaId);
        return new ResponseEntity<>(treballadors, HttpStatus.OK);
    }

    // Obtenir un treballador per ID
    @GetMapping("/{id}")
    public ResponseEntity<Treballador> getTreballadorById(@PathVariable Long id) {
        logger.debug("Buscant treballador per id: {}", id);
        Optional<Treballador> treballador = treballadorService.findById(id);
        if (treballador.isPresent()) {
            logger.info("Treballador trobat: {}", treballador.get());
            return new ResponseEntity<>(treballador.get(), HttpStatus.OK);
        } else {
            logger.error("No s'ha trobat el treballador amb el id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear un treballador
    @PostMapping
    public ResponseEntity<Treballador> crearTreballador(@PathVariable Long empresaId, @RequestBody Treballador treballador) {
        logger.debug("Creant treballador per empresa id: {}", empresaId);
        try {
            // Trobar la empresa per ID
            Empresa empresa = new Empresa();
            empresa.setEmpresaId(empresaId);

            // Establir l'empresa per al treballador
            treballador.setEmpresa(empresa);

            //Guardar el treballador
            Treballador newTreballador = treballadorService.save(treballador);
            return new ResponseEntity<>(newTreballador, HttpStatus.CREATED);
        } catch (Exception e) {
            // Agrega logs o detalles de la excepción aquí
            System.err.println("Error al crear treballador: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualitzar un treballador
    @PutMapping("/{id}")
    public ResponseEntity<Treballador> updateTreballador(@RequestBody Treballador treballador, @PathVariable Long id) {
        logger.debug("Actualitzant treballador amb id: {}", id);
        try {
            Treballador updatedTreballador = treballadorService.update(treballador, id);
            logger.info("Treballador actualitzat: {}", updatedTreballador);
            return new ResponseEntity<>(updatedTreballador, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.warn("Treballador amb id: {} no trobat", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error inesperat al actualitzar el treballador amb id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Borrar un treballador
    @DeleteMapping("/{id}")
    public ResponseEntity<Treballador> deleteTreballador(@PathVariable Long id) {
        logger.debug("Eliminant treballador amb id: {}", id);
        try {
            treballadorService.delete(id);
            logger.info("Treballador eliminat amb id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            logger.warn("Treballador amb id: {} no trobat", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error inesperat al eliminar el treballador amb id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
