package com.joelblanc.AppEcityclic.controller;

import com.joelblanc.AppEcityclic.entity.Empresa;
import com.joelblanc.AppEcityclic.service.EmpresaService;
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
@RequestMapping("/api/empresa")
@CrossOrigin("http://localhost:4200/")
public class EmpresaController {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaController.class);
    private final EmpresaService empresaService;

    // Obtenir totes les empreses
    @GetMapping
    public ResponseEntity<List<Empresa>> getAllEmpreses() {
        logger.debug("Intentant trobar totes les empreses");
        List<Empresa> empreses = empresaService.findAll();
        logger.info("S'han trobat {} empreses", empreses.size());
        return new ResponseEntity<>(empreses, HttpStatus.OK);
    }

    // Obtenir una empresa per ID
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresaById(@PathVariable Long id) {
        logger.debug("Intentant trobar l'empresa amb id {}", id);
        Optional<Empresa> empresa = empresaService.findById(id);
        if (empresa.isPresent()) {
            logger.info("Empresa amb ID {} trobada", id);
            return new ResponseEntity<>(empresa.get(), HttpStatus.OK);
        } else {
            logger.warn("Empresa amb ID {} no trobada", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear una empresa
    @PostMapping
    public ResponseEntity<Empresa> createEmpresa(@RequestBody Empresa empresa) {
        logger.debug("Intentant crear l'empresa {}", empresa);
        try {
            Empresa newEmpresa = empresaService.save(empresa);
            logger.info("Empresa creada correctament amb ID {}", empresa.getEmpresaId());
            return new ResponseEntity<>(newEmpresa, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error al crear l'empresa: {}", empresa, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualitzar una empresa
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(@RequestBody Empresa empresa, @PathVariable Long id) {
        logger.debug("Intentant actualitzar l'empresa {} amb ID {}", empresa, id);
        try {
            Empresa updatedEmpresa = empresaService.update(empresa, id);
            logger.info("Empresa amb ID {} actualitzada correctament", id);
            return new ResponseEntity<>(updatedEmpresa, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.warn("Empresa amb ID {} no trobada", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error inesperat al actualitzar l'empresa amb ID {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Borrar una empresa
    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> deleteEmpresa(@PathVariable Long id) {
        logger.debug("Intentant eliminar l'empresa amb ID {}", id);
        try {
            empresaService.delete(id);
            logger.info("Empresa amb ID {} eliminada correctament", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            logger.warn("Empresa amb ID {} no trobada", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error inesperat al borrar l'empresa amb ID {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
