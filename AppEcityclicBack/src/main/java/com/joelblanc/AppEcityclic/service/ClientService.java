package com.joelblanc.AppEcityclic.service;

import com.joelblanc.AppEcityclic.entity.Client;
import com.joelblanc.AppEcityclic.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> findByProjecteId(Long projecteId) {
        return clientRepository.findByProjecte_ProjecteId(projecteId);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client update(Client client, Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client existingClient = clientOptional.get();

            if (client.getNom() != null)
                existingClient.setNom(client.getNom());
            if (client.getEmail() != null)
                existingClient.setEmail(client.getEmail());
            if (client.getAdreca() != null)
                existingClient.setAdreca(client.getAdreca());

            return clientRepository.save(existingClient);
        } else {
            throw new EntityNotFoundException("Client no trobat amb el id: " + id);
        }
    }

    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client no trobat amb el id: " + id);
        }
        clientRepository.deleteById(id);
    }
}
