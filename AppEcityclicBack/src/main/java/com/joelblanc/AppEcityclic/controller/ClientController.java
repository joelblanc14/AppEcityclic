package com.joelblanc.AppEcityclic.controller;

import com.joelblanc.AppEcityclic.entity.Client;
import com.joelblanc.AppEcityclic.entity.Projecte;
import com.joelblanc.AppEcityclic.service.ClientService;
import com.joelblanc.AppEcityclic.service.ProjecteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/empresa/{empresaId}/client")
@CrossOrigin("http://localhost:4200")
public class ClientController { 

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;
    private final ProjecteService projecteService;

    // Obtenir tots els clients d'un projecte
    @GetMapping("/{projecteId}")
    public ResponseEntity<List<Client>> getClientsByProjecte(@PathVariable Long empresaId, @PathVariable Long projecteId) {
        logger.debug("Obtenint clients per projecteId: {}", projecteId);
        List<Client> clients = clientService.findByProjecteId(projecteId);
        logger.info("Clients obtinguts: {}", clients);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<Client> getClientById(@PathVariable Long empresaId, @PathVariable Long id) {
        logger.debug("Obtenint client per id: {}", id);
        Client client = clientService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client no trobat amb id: " + id));
        logger.info("Client trobat: {}", client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    // Crear un client i associar-lo a un projecte
    @PostMapping("/{projecteId}")
    public ResponseEntity<Client> createClient(@PathVariable Long empresaId, @PathVariable Long projecteId, @RequestBody Client client) {
        logger.debug("Creant client per projecteId: {}", projecteId);
        Projecte projecte = projecteService.findById(projecteId)
                .orElseThrow(() -> new EntityNotFoundException("Projecte no trobat amb id: " + projecteId));

        client.setProjecte(projecte);
        Client newClient = clientService.save(client);

        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    // Actualitzar un client
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long empresaId, @RequestBody Client client, @PathVariable Long id) {
        logger.debug("Actualitzant client per id: {}", id);
        try {
            Client updatedClient = clientService.update(client, id);
            logger.info("Client actualitzat: {}", updatedClient);
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.warn("Client no trobat amb el id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al actualitzar client: {}", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Borrar un client
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Long empresaId, @PathVariable Long id) {
        logger.debug("Eliminant client per id: {}", id);
        try {
            clientService.delete(id);
            logger.info("Client eliminat");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            logger.warn("Client no trobat amb el id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al eliminar client amb ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

