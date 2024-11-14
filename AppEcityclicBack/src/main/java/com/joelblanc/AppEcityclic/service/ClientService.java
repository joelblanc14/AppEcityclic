package com.joelblanc.AppEcityclic.service;

import com.joelblanc.AppEcityclic.entity.Client;
import com.joelblanc.AppEcityclic.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;

    public Optional<Client> findById(Long id) {
        logger.debug("Buscant client per id: {}", id);
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            logger.info("Client trobat: {}", client.get());
        } else {
            logger.warn("Client no trobat amb el id: {}", id);
        }
        return client;
    }

    public List<Client> findByProjecteId(Long projecteId) {
        logger.debug("Buscant clients per projecteId: {}", projecteId);
        List<Client> clients = clientRepository.findByProjecte_ProjecteId(projecteId);
        logger.info("Clients obtinguts: {}", clients);
        return clients;
    }

    public Client save(Client client) {
        logger.debug("Guardant client: {}", client);
        try {
            Client savedClient = clientRepository.save(client);
            logger.info("Client guardat: {}", savedClient);
            return savedClient;
        } catch (Exception e) {
            logger.error("Error al guardar client: {}", client, e);
            throw e;
        }
    }

    public Client update(Client client, Long id) {
        logger.debug("Actualitzant client per id: {}", id);
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client existingClient = clientOptional.get();
            logger.info("Client trobat: {}", existingClient);

            if (client.getNom() != null) {
                logger.debug("Actualitzant nom del client: {}", client.getNom());
                existingClient.setNom(client.getNom());
                logger.info("Nom actualitzat: {}", existingClient.getNom());    
            }
            if (client.getEmail() != null) {
                logger.debug("Actualitzant email del client: {}", client.getEmail());
                existingClient.setEmail(client.getEmail());
                logger.info("Email actualitzat: {}", existingClient.getEmail());
            }
            if (client.getAdreca() != null) {
                logger.debug("Actualitzant adreca del client: {}", client.getAdreca());
                existingClient.setAdreca(client.getAdreca());
                logger.info("Adreca actualitzada: {}", existingClient.getAdreca());
            }
            try {
                Client updatedClient = clientRepository.save(existingClient);
                logger.info("Client actualitzat: {}", updatedClient);
                return updatedClient;
            } catch (Exception e) {
                logger.error("Error al actualitzar client: {}", existingClient, e);
                throw e;
            }
        } else {
            logger.warn("Client no trobat amb el id: {}", id);
            throw new EntityNotFoundException("Client no trobat amb el id: " + id);
        }
    }

    public void delete(Long id) {
        logger.debug("Eliminant client per id: {}", id);
        if (!clientRepository.existsById(id)) {
            logger.warn("Client no trobat amb el id: {}", id);
            throw new EntityNotFoundException("Client no trobat amb el id: " + id);
        }
        try {
            clientRepository.deleteById(id);
            logger.info("Client eliminat");
        } catch (Exception e) {
            logger.error("Error al eliminar client amb ID: {}", id, e);
            throw e;
        }
    }
}
