package com.joelblanc.AppEcityclic.controller;

import com.joelblanc.AppEcityclic.entity.Empresa;
import com.joelblanc.AppEcityclic.entity.Treballador;
import com.joelblanc.AppEcityclic.service.TreballadorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/empresa/{empresaId}/treballador")
@CrossOrigin(origins = "http://localhost:4200")
public class TreballadorController {

    private final TreballadorService treballadorService;

    // Obtenir tots els treballadors
    @GetMapping
    public ResponseEntity<List<Treballador>> getTreballadorsByEmpresaId(@PathVariable Long empresaId) {
        List<Treballador> treballadors = treballadorService.findByEmpresa_Id(empresaId);
        return new ResponseEntity<>(treballadors, HttpStatus.OK);
    }

    // Obtenir un treballador per ID
    @GetMapping("/{id}")
    public ResponseEntity<Treballador> getTreballadorById(@PathVariable Long id) {
        Optional<Treballador> treballador = treballadorService.findById(id);
        return treballador.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un treballador
    @PostMapping
    public ResponseEntity<Treballador> crearTreballador(@PathVariable Long empresaId, @RequestBody Treballador treballador) {
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
        try {
            Treballador updatedTreballador = treballadorService.update(treballador, id);
            return new ResponseEntity<>(updatedTreballador, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Borrar un treballador
    @DeleteMapping("/{id}")
    public ResponseEntity<Treballador> deleteTreballador(@PathVariable Long id) {
        try {
            treballadorService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
