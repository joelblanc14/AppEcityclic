package com.joelblanc.AppEcityclic.controller;

import com.joelblanc.AppEcityclic.entity.Client;
import com.joelblanc.AppEcityclic.entity.Projecte;
import com.joelblanc.AppEcityclic.service.ClientService;
import com.joelblanc.AppEcityclic.service.ProjecteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/empresa/{empresaId}/client")
@CrossOrigin("http://localhost:4200")
public class ClientController {

    private final ClientService clientService;
    private final ProjecteService projecteService;

    // Obtenir tots els clients d'un projecte
    @GetMapping("/{projecteId}")
    public ResponseEntity<List<Client>> getClientsByProjecte(@PathVariable Long empresaId, @PathVariable Long projecteId) {
        List<Client> clients = clientService.findByProjecteId(projecteId);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<Client> getClientById(@PathVariable Long empresaId, @PathVariable Long id) {
        Client client = clientService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client no trobat amb id: " + id));
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    // Crear un client i associar-lo a un projecte
    @PostMapping("/{projecteId}")
    public ResponseEntity<Client> createClient(@PathVariable Long empresaId, @PathVariable Long projecteId, @RequestBody Client client) {
        Projecte projecte = projecteService.findById(projecteId)
                .orElseThrow(() -> new EntityNotFoundException("Projecte no trobat amb id: " + projecteId));

        client.setProjecte(projecte);
        Client newClient = clientService.save(client);

        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    // Actualitzar un client
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long empresaId, @RequestBody Client client, @PathVariable Long id) {
        try {
            Client updatedClient = clientService.update(client, id);
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Borrar un client
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Long empresaId, @PathVariable Long id) {
        try {
            clientService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

