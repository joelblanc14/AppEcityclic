package com.joelblanc.AppEcityclic.service;

import com.joelblanc.AppEcityclic.entity.Tasca;
import com.joelblanc.AppEcityclic.repository.TascaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TascaService {

    private static final Logger logger = LoggerFactory.getLogger(TascaService.class);
    private final TascaRepository tascaRepository;

    public List<Tasca> getTasquesPerProjecte(Long projecteId) {
        logger.debug("Obtenint tasques per projecte amb ID: {}", projecteId);
        List<Tasca> tasques = tascaRepository.findByProjecte_projecteId(projecteId);
        logger.info("Tasques obtingudes: {}", tasques);
        return tasques;
    }

    public List<Tasca> getTasquesPerTreballador(Long treballadorId) {
        logger.debug("Obtenint tasques per treballador amb ID: {}", treballadorId);
        List<Tasca> tasques = tascaRepository.findByTreballadorTreballadorId(treballadorId);
        logger.info("Tasques obtingudes: {}", tasques);
        return tasques;
    }

    public Optional<Tasca> findById(Long id) {
        logger.debug("Obtenint tasca per ID: {}", id);
        Optional<Tasca> tasca = tascaRepository.findById(id);
        logger.info("Tasca obtinguda: {}", tasca);
        return tasca;
    }

    public Tasca save(Tasca tasca) {
        logger.debug("Guardant tasca: {}", tasca);
        try {
            Tasca savedTasca = tascaRepository.save(tasca);
            logger.info("Tasca guardada: {}", savedTasca);
            return savedTasca;
        } catch (Exception e) {
            logger.error("Error al guardar tasca: {}", e);
            throw e;
        }
    }

    public Tasca update(Tasca tasca, Long id) {
        logger.debug("Actualitzant tasca per ID: {}", id);
        Optional<Tasca> tascaOptional = tascaRepository.findById(id);
        if (tascaOptional.isPresent()) {
            Tasca existingTasca = tascaOptional.get();
            logger.debug("Tasca existent: {}", existingTasca);

            if (tasca.getNom() != null) {
                logger.debug("Actualitzant nom de la tasca: {}", tasca.getNom());
                existingTasca.setNom(tasca.getNom());
                logger.info("Nom actualitzat: {}", existingTasca.getNom());
            }
            if (tasca.getDescripcio() != null) {
                logger.debug("Actualitzant descripcio de la tasca: {}", tasca.getDescripcio());
                existingTasca.setDescripcio(tasca.getDescripcio());
                logger.info("Descripcio actualitzat: {}", existingTasca.getDescripcio());
            }
            if (tasca.getEstat() != null) {
                logger.debug("Actualitzant estat de la tasca: {}", tasca.getEstat());
                existingTasca.setEstat(tasca.getEstat());
                logger.info("Estat actualitzat: {}", existingTasca.getEstat());
            }

            if ("completat".equals(tasca.getEstat())) {
                logger.debug("Marcant tasca com a completada");
                existingTasca.marcarComplet();
                logger.info("Tasca marcada com a completada");
            }
            try {
                Tasca updatedTasca = tascaRepository.save(existingTasca);
                logger.info("Tasca actualitzada: {}", updatedTasca);
                return updatedTasca;
            } catch (Exception e) {
                logger.error("Error al actualitzar tasca: {}", e);
                throw e;
            }
        } else {
            logger.warn("Tasca no trobada amb el id: {}", id);
            throw new EntityNotFoundException("Tasca no trobada amb el id: " + id);
        }
    }
    public void delete(Long id) {
        logger.debug("Eliminant tasca per ID: {}", id);
        if (!tascaRepository.existsById(id)) {
            logger.warn("Tasca no trobada amb el id: {}", id);
            throw new EntityNotFoundException("Tasca no trobada amb el id: " + id);
        }
        try {
            tascaRepository.deleteById(id);
            logger.info("Tasca eliminada");
        } catch (Exception e) {
            logger.error("Error al eliminar tasca: {}", e);
            throw e;
        }
    }
}