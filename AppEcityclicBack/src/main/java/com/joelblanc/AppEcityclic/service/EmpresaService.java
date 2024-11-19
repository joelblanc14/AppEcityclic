package com.joelblanc.AppEcityclic.service;

import com.joelblanc.AppEcityclic.entity.Empresa;
import com.joelblanc.AppEcityclic.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmpresaService {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaService.class);
    private final EmpresaRepository empresaRepository;

    public List<Empresa> findAll() {
        logger.debug("Buscant totes les empreses");
        List<Empresa> empreses = empresaRepository.findAll();
        logger.info("Trobades {} empreses", empreses.size());
        return empreses;
    }

    public Optional<Empresa> findById(Long id) {
        logger.debug("Buscant empresa amb id: {}", id);
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (empresa.isPresent()) {
            logger.info("Trobada empresa amb id: {}", id);
        } else {
            logger.warn("No s'ha trobat cap empresa amb id: {}", id);
        }
        return empresa;
    }

    public Empresa save(Empresa empresa) {
        logger.debug("Intentant guardar empresa: {}", empresa);
        try {
            Empresa savedEmpresa = empresaRepository.save(empresa);
            logger.info("Empresa guardada correctament amb ID: {}", savedEmpresa.getEmpresaId());
            return savedEmpresa;
        } catch (Exception e) {
            logger.error("Error al guardar l'empresa: {}", empresa, e);
            throw e;
        }
    }

    public Empresa update(Empresa empresa, Long id) {
        logger.debug("Intentant actualitzar empresa amb id: {}", id);
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);

        if (empresaOptional.isPresent()) {
            Empresa existingEmpresa = empresaOptional.get();
            logger.debug("Empresa trobada, procedint amb l'actualització");

            if (empresa.getNom() != null) {
                logger.debug("Actualitzant nom de {} a {}", existingEmpresa.getNom(), empresa.getNom());
                existingEmpresa.setNom(empresa.getNom());
                logger.info("Nom actualitzat a {}", existingEmpresa.getNom());
            }
            if (empresa.getAdreca() != null) {
                logger.debug("Actualitzand adreça de {} a {}", existingEmpresa.getAdreca(), empresa.getAdreca());
                existingEmpresa.setAdreca(empresa.getAdreca());
                logger.info("Adreç actualitzada a {}", existingEmpresa.getAdreca());
            }
            if (empresa.getCif() != null) {
                logger.debug("Actualitzant cif de {} a {}", existingEmpresa.getCif(), empresa.getCif());
                existingEmpresa.setCif(empresa.getCif());
                logger.info("Cif actualitzat a {}", existingEmpresa.getCif());
            }

            try {
                Empresa updatedEmpresa = empresaRepository.save(existingEmpresa);
                logger.info("Empresa actualitzada correctament amb id: {}", id);
                return updatedEmpresa;
            } catch (Exception e) {
                logger.error("Error al actualitzar l'empresa amb id: {}", id, e);
                throw e;
            }
        } else {
            logger.warn("No s'ha trobat l'empresa amb el id: {}", id);
            throw new EntityNotFoundException("Empresa no trobada amb el id" + id);
        }
    }

    public void delete(Long id) {
        logger.debug("Intent d'eliminar empresa amb id: {}", id);
        if (!empresaRepository.existsById(id)) {
            logger.warn("Intent d'eliminar una empresa inexistent amb id: {}", id);
            throw new EntityNotFoundException("Empresa no trobada amb el id: " + id);
        }
        try {
            empresaRepository.deleteById(id);
            logger.info("Empresa eliminada correctament amb id: {}", id);
        } catch (Exception e) {
            logger.error("Error al eliminar empresa amb id: {}", id, e);
            throw e;
        }
    }
}