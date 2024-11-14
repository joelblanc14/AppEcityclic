package com.joelblanc.AppEcityclic.service;

import com.joelblanc.AppEcityclic.entity.Projecte;
import com.joelblanc.AppEcityclic.repository.ProjecteRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProjecteService {

    private static final Logger logger = LoggerFactory.getLogger(ProjecteService.class);
    private final ProjecteRepository projecteRepository;

    public List<Projecte> findByEmpresaId(Long empresaId) {
        logger.debug("Intentant trobar projectes de l'empresa amb ID {}", empresaId);
        List<Projecte> projectes = projecteRepository.findByEmpresa_EmpresaId(empresaId);
        logger.info("S'han trobat {} projectes", projectes.size());
        return projectes;
    }

    public Optional<Projecte> findByIdAndEmpresaId(Long id, Long empresaId) {
        logger.debug("Intentant trobar projecte amb ID {} i empresaId {}", id, empresaId);
        Optional<Projecte> projecte = projecteRepository.findByProjecteIdAndEmpresa_EmpresaId(id, empresaId);
        if (projecte.isPresent()) {
            logger.info("Projecte trobat: {}", projecte.get());
        } else {
            logger.warn("Projecte no trobat amb ID {} i empresaId {}", id, empresaId);
        }
        return projecte;
    }

    public Optional<Projecte> findById(Long id) {
        logger.debug("Intentant trobar projecte amb ID {}", id);
        Optional<Projecte> projecte = projecteRepository.findById(id);
        if (projecte.isPresent()) {
            logger.info("Projecte trobat: {}", projecte.get());
        } else {
            logger.warn("Projecte no trobat amb ID {}", id);
        }
        return projecte;
    }

    public Projecte save(Projecte projecte) {
        logger.debug("Intentant guardar projecte: {}", projecte);
        try {
            Projecte savedProjecte = projecteRepository.save(projecte);
            logger.info("Projecte guardat: {}", savedProjecte);
            return savedProjecte;
        } catch (Exception e) {
            logger.error("Error al guardar projecte: {}", e.getMessage());
            throw e;
        }
    }

    public Projecte update(Projecte projecte, Long id, Long empresaId) {
        logger.debug("Intentant actualitzar projecte amb ID {} i empresaId {}", id, empresaId);
        Optional<Projecte> projecteOptional = projecteRepository.findById(id);
        if (projecteOptional.isPresent() && projecteOptional.get().getEmpresa().getEmpresaId().equals(empresaId)) {
            Projecte existingProject = projecteOptional.get();
            logger.debug("Projecte trobat: {}", existingProject);

            if (projecte.getNom() != null) {
                logger.debug("Actualitzant nom del projecte: {}", projecte.getNom());
                existingProject.setNom(projecte.getNom());
                logger.info("Nom actualitzat: {}", existingProject.getNom());
            }
            if (projecte.getEstat() != null) {
                logger.debug("Actualitzant estat del projecte: {}", projecte.getEstat());
                existingProject.setEstat(projecte.getEstat());
                logger.info("Estat actualitzat: {}", existingProject.getEstat());
            }
            if (projecte.getDescripcio() != null) {
                logger.debug("Actualitzant descripció del projecte: {}", projecte.getDescripcio());
                existingProject.setDescripcio(projecte.getDescripcio());
                logger.info("Descripció actualitzada: {}", existingProject.getDescripcio());
            }

            if ("completat".equals(projecte.getEstat()) || "cancelat".equals(projecte.getEstat())) {
                logger.debug("Marcant projecte com a completat o cancelat");
                existingProject.marcarComplet();
                logger.info("Projecte marcat com a completat o cancelat");
            }

            try {
                Projecte updatedProjecte = projecteRepository.save(existingProject);
                logger.info("Projecte actualitzat: {}", updatedProjecte);
                return updatedProjecte;
            } catch (Exception e) {
                logger.error("Error al actualitzar projecte: {}", e.getMessage());
                throw e;
            }
        } else {
            logger.error("Projecte no trobat amb el id: {} i empresaId: {}", id, empresaId);
            throw new EntityNotFoundException("Projecte no trobat amb el id: " + id + " i empresaId: " + empresaId);
        }
    }

    public void delete(Long id, Long empresaId) {
        logger.debug("Intentant eliminar projecte amb ID {} i empresaId {}", id, empresaId);
        Optional<Projecte> projecteOptional = projecteRepository.findById(id);
        if (projecteOptional.isPresent() && projecteOptional.get().getEmpresa().getEmpresaId().equals(empresaId)) {
            logger.debug("Projecte trobat i eliminat: {}", projecteOptional.get());
            projecteRepository.delete(projecteOptional.get());
            logger.info("Projecte eliminat");
        }
        try {
            projecteRepository.delete(projecteOptional.get());
            logger.info("Projecte eliminat");
        } catch (Exception e) {
            logger.error("Error al eliminar projecte: {}", e);
            throw e;
        }
    }
}