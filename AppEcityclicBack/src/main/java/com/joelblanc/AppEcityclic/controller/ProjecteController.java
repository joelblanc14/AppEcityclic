package com.joelblanc.AppEcityclic.controller;

import com.joelblanc.AppEcityclic.entity.Empresa;
import com.joelblanc.AppEcityclic.entity.Projecte;
import com.joelblanc.AppEcityclic.service.EmpresaService;
import com.joelblanc.AppEcityclic.service.ProjecteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/empresa/{empresaId}/projecte")
@CrossOrigin("http://localhost:4200")
public class ProjecteController {

    private static final Logger logger = LoggerFactory.getLogger(ProjecteController.class);
    private final ProjecteService projecteService;
    private final EmpresaService empresaService;

    // Obtenir tots els projectes d'una empresa
    @GetMapping
    public ResponseEntity<List<Projecte>> getProjectesByEmpresaId(@PathVariable Long empresaId) {
        logger.debug("Obtenint projectes per empresaId: {}", empresaId);
        List<Projecte> projectes = projecteService.findByEmpresaId(empresaId);
        logger.info("Projectes obtinguts: {}", projectes);
        return new ResponseEntity<>(projectes, HttpStatus.OK);
    }

    // Obtenir un projecte per ID
    @GetMapping("/{projecteId}")
    public ResponseEntity<Projecte> getProjecteById(@PathVariable Long empresaId, @PathVariable Long projecteId) {
        logger.debug("Obtenint projecte per ID: {} i empresaId: {}", projecteId, empresaId);
        Optional<Projecte> projecte = projecteService.findByIdAndEmpresaId(projecteId, empresaId);
        if (projecte.isPresent()) {
            logger.info("Projecte obtingut: {}", projecte.get());
            return new ResponseEntity<>(projecte.get(), HttpStatus.OK);
        } else {
            logger.error("Projecte no trobat amb el id: {} i empresaId: {}", projecteId, empresaId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear un projecte
    @PostMapping
    public ResponseEntity<Projecte> createProjecte(@PathVariable Long empresaId, @RequestBody Projecte projecte) {
        logger.debug("Creant projecte per empresaId: {}", empresaId);
        Optional<Empresa> empresa = empresaService.findById(empresaId);
        if (empresa.isPresent()) {
            projecte.setEmpresa(empresa.get());
            Projecte newProjecte = projecteService.save(projecte);
            logger.info("Projecte creat: {}", newProjecte);
            return new ResponseEntity<>(newProjecte, HttpStatus.CREATED);
        } else {
            logger.error("Empresa no trobada amb l'id: {}", empresaId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualitzar un projecte
    @PutMapping("/{projecteId}")
    public ResponseEntity<Projecte> updateProjecte(@PathVariable Long empresaId, @RequestBody Projecte projecte, @PathVariable Long projecteId) {
        logger.debug("Actualitzant projecte per ID: {} i empresaId: {}", projecteId, empresaId);
        try {
            Projecte updatedProjecte = projecteService.update(projecte, projecteId, empresaId);
            logger.info("Projecte actualitzat: {}", updatedProjecte);
            return new ResponseEntity<>(updatedProjecte, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.warn("Projecte no trobat amb el id: {} i empresaId: {}", projecteId, empresaId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al actualitzar projecte: {}", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{projecteId}")
    public ResponseEntity<Projecte> deleteProjecte(@PathVariable Long empresaId, @PathVariable Long projecteId) {
        logger.debug("Eliminant projecte per ID: {} i empresaId: {}", projecteId, empresaId);
        try {
            projecteService.delete(projecteId, empresaId);
            logger.info("Projecte eliminat");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            logger.warn("Projecte no trobat amb el id: {} i empresaId: {}", projecteId, empresaId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al eliminar projecte: {}", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
