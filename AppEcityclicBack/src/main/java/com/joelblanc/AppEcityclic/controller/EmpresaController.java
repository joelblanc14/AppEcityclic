package com.joelblanc.AppEcityclic.controller;

import com.joelblanc.AppEcityclic.entity.Empresa;
import com.joelblanc.AppEcityclic.service.EmpresaService;
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
public class EmpresaController {

    private final EmpresaService empresaService;


    // Obtenir totes les empreses
    @GetMapping
    public ResponseEntity<List<Empresa>> getAllEmpreses() {
        List<Empresa> empreses = empresaService.findAll();
        return new ResponseEntity<>(empreses, HttpStatus.OK);
    }

    // Obtenir una empresa per ID
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresaById(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.findById(id);
        return empresa.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear una empresa
    @PostMapping
    public ResponseEntity<Empresa> createEmpresa(@RequestBody Empresa empresa) {
        Empresa newEmpresa = empresaService.save(empresa);
        return new ResponseEntity<>(newEmpresa, HttpStatus.CREATED);
    }

    // Actualitzar una empresa
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(@RequestBody Empresa empresa, @PathVariable Long id) {
        try {
            Empresa updatedEmpresa = empresaService.update(empresa, id);
            return new ResponseEntity<>(updatedEmpresa, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Borrar una empresa
    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> deleteEmpresa(@PathVariable Long id) {
        try {
            empresaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
