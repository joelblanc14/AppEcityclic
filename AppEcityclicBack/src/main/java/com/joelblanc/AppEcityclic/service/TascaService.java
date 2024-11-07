package com.joelblanc.AppEcityclic.service;

import com.joelblanc.AppEcityclic.entity.Tasca;
import com.joelblanc.AppEcityclic.repository.TascaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TascaService {

    private final TascaRepository tascaRepository;


    public List<Tasca> getTasquesPerProjecte(Long projecteId) {
        return tascaRepository.findByProjecte_projecteId(projecteId);
    }

    public List<Tasca> getTasquesPerTreballador(Long treballadorId) {
        return tascaRepository.findByTreballadorTreballadorId(treballadorId);
    }

    public Optional<Tasca> findById(Long id) {
        return tascaRepository.findById(id);
    }

    public Tasca save(Tasca tasca) {
        return tascaRepository.save(tasca);
    }

    public Tasca update(Tasca tasca, Long id) {
        Optional<Tasca> tascaOptional = tascaRepository.findById(id);
        if (tascaOptional.isPresent()) {
            Tasca existingTasca = tascaOptional.get();

            if (tasca.getNom() != null)
                existingTasca.setNom(tasca.getNom());
            if (tasca.getDescripcio() != null)
                existingTasca.setDescripcio(tasca.getDescripcio());
            if (tasca.getEstat() != null)
                existingTasca.setEstat(tasca.getEstat());

            if ("completat".equals(tasca.getEstat())) {
                existingTasca.marcarComplet();
            }

            return tascaRepository.save(existingTasca);
        } else {
            throw new EntityNotFoundException("Tasca no trobada amb el id: " + id);
        }
    }
    public void delete(Long id) {
        if (!tascaRepository.existsById(id)) {
            throw new EntityNotFoundException("Tasca no trobada amb el id: " + id);
        }
        tascaRepository.deleteById(id);
    }
}