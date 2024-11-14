package com.joelblanc.AppEcityclic.service;

import com.joelblanc.AppEcityclic.entity.Treballador;
import com.joelblanc.AppEcityclic.repository.TreballadorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TreballadorService {

    private static final Logger logger = LoggerFactory.getLogger(TreballadorService.class);
    private final TreballadorRepository treballadorRepository;


    public List<Treballador> findByEmpresa_Id(Long empresaId) {
        logger.debug("Buscant treballadors per empresa id: {}", empresaId);
        List<Treballador> treballadors = treballadorRepository.findByEmpresa_EmpresaId(empresaId);
        logger.info("Trobats {} treballadors per empresa id: {}", treballadors.size(), empresaId);
        return treballadors;
    }

    public Optional<Treballador> findById(Long id) {
        logger.debug("Buscant treballador per id: {}", id);
        Optional<Treballador> treballador = treballadorRepository.findById(id);
        if (treballador.isPresent()) {
            logger.info("Trobat treballador per id: {}", id);
        }
        return treballador;
    }

    public Treballador save(Treballador treballador) {
        logger.debug("Guardant treballador: {}", treballador);
        try {
            Treballador savedTreballador = treballadorRepository.save(treballador);
            logger.info("Treballador guardat: {}", savedTreballador);
            return savedTreballador;
        } catch (Exception e) {
            logger.error("Error al guardar el treballador: {}", treballador, e);
            throw e;
        }
    }

    public Treballador update(Treballador treballador, Long id) {
        logger.debug("Actualitzant treballador: {}", treballador);
        Optional<Treballador> treballadorOptional = treballadorRepository.findById(id);
        if (treballadorOptional.isPresent()) {
            Treballador existingTreballador = treballadorOptional.get();
            logger.debug("Treballador trobat, procedint amb l'actualització");

            if (treballador.getNom() != null) {
                logger.debug("Actualitzant nom de {} a {}", existingTreballador.getNom(), treballador.getNom());
                existingTreballador.setNom(treballador.getNom());
                logger.info("Nom actualitzat a {}", existingTreballador.getNom());
            }
            if (treballador.getPosicio() != null) {
                logger.debug("Actualitzant posició de {} a {}", existingTreballador.getPosicio(), treballador.getPosicio());
                existingTreballador.setPosicio(treballador.getPosicio());
                logger.info("Posició actualitzada a {}", existingTreballador.getPosicio());
            }
            if (treballador.getSalari() != null) {
                logger.debug("Actualitzant salari de {} a {}", existingTreballador.getSalari(), treballador.getSalari());
                existingTreballador.setSalari(treballador.getSalari());
                logger.info("Salari actualitzat a {}", existingTreballador.getSalari());
            }
            if (treballador.getEmail() != null) {
                logger.debug("Actualitzant email de {} a {}", existingTreballador.getEmail(), treballador.getEmail());
                existingTreballador.setEmail(treballador.getEmail());
                logger.info("Email actualitzat a {}", existingTreballador.getEmail());
            }
            try {
                Treballador updatedTreballador = treballadorRepository.save(existingTreballador);
                logger.info("Treballador actualitzat: {}", updatedTreballador);
                return updatedTreballador;
            } catch (Exception e) {
                logger.error("Error al actualitzar el treballador: {}", existingTreballador, e);
                throw e;
            }
        } else {
            logger.error("No s'ha trobat el treballador amb el id: {}", id);
            throw new EntityNotFoundException("Treballador no trobat amb id: " + id);
        }
    }

    public void delete(Long id) {
        logger.debug("Eliminant treballador amb id: {}", id);
        if (!treballadorRepository.existsById(id)) {
            logger.error("No s'ha trobat el treballador amb el id: {}", id);
            throw new EntityNotFoundException("Treballador no trobat amb id: " + id);
        }
        try {
            treballadorRepository.deleteById(id);
            logger.info("Treballador eliminat amb id: {}", id);
        } catch (Exception e) {
            logger.error("Error al eliminar el treballador amb id: {}", id, e);
            throw e;
        }
    }
}
