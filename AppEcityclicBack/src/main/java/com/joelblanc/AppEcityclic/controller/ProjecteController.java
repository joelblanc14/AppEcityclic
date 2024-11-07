package com.joelblanc.AppEcityclic.controller;

import com.joelblanc.AppEcityclic.entity.Empresa;
import com.joelblanc.AppEcityclic.entity.Projecte;
import com.joelblanc.AppEcityclic.service.EmpresaService;
import com.joelblanc.AppEcityclic.service.ProjecteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
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

    private final ProjecteService projecteService;
    private final EmpresaService empresaService;

    // Obtenir tots els projectes d'una empresa
    @GetMapping
    public ResponseEntity<List<Projecte>> getProjectesByEmpresaId(@PathVariable Long empresaId) {
        List<Projecte> projectes = projecteService.findByEmpresaId(empresaId);
        return new ResponseEntity<>(projectes, HttpStatus.OK);
    }

    // Obtenir un projecte per ID
    @GetMapping("/{projecteId}")
    public ResponseEntity<Projecte> getProjecteById(@PathVariable Long empresaId, @PathVariable Long projecteId) {
        Optional<Projecte> projecte = projecteService.findByIdAndEmpresaId(projecteId, empresaId);
        return projecte.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un projecte
    @PostMapping
    public ResponseEntity<Projecte> createProjecte(@PathVariable Long empresaId, @RequestBody Projecte projecte) {
        Optional<Empresa> empresa = empresaService.findById(empresaId);

        if (empresa.isPresent()) {
            projecte.setEmpresa(empresa.get());
            Projecte newProjecte = projecteService.save(projecte);
            return new ResponseEntity<>(newProjecte, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualitzar un projecte
    @PutMapping("/{projecteId}")
    public ResponseEntity<Projecte> updateProjecte(@PathVariable Long empresaId, @RequestBody Projecte projecte, @PathVariable Long projecteId) {
        try {
            Projecte updatedProjecte = projecteService.update(projecte, projecteId, empresaId);
            return new ResponseEntity<>(updatedProjecte, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{projecteId}")
    public ResponseEntity<Projecte> deleteProjecte(@PathVariable Long empresaId, @PathVariable Long projecteId) {
        try {
            projecteService.delete(projecteId, empresaId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
