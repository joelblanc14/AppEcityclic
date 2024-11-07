package com.joelblanc.AppEcityclic.service;

import com.joelblanc.AppEcityclic.entity.Projecte;
import com.joelblanc.AppEcityclic.repository.ProjecteRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProjecteService {

    private final ProjecteRepository projecteRepository;

    public List<Projecte> findByEmpresaId(Long empresaId) {
        return projecteRepository.findByEmpresa_EmpresaId(empresaId);
    }

    public Optional<Projecte> findByIdAndEmpresaId(Long id, Long empresaId) {
        return projecteRepository.findByProjecteIdAndEmpresa_EmpresaId(id, empresaId);
    }

    public Optional<Projecte> findById(Long id) {
        return projecteRepository.findById(id);
    }

    public Projecte save(Projecte projecte) {
        return projecteRepository.save(projecte);
    }

    public Projecte update(Projecte projecte, Long id, Long empresaId) {
        Optional<Projecte> projecteOptional = projecteRepository.findById(id);
        if (projecteOptional.isPresent() && projecteOptional.get().getEmpresa().getEmpresaId().equals(empresaId)) {
            Projecte existingProject = projecteOptional.get();

            if (projecte.getNom() != null)
                existingProject.setNom(projecte.getNom());
            if (projecte.getEstat() != null)
                existingProject.setEstat(projecte.getEstat());
            if (projecte.getDescripcio() != null)
                existingProject.setDescripcio(projecte.getDescripcio());

            if ("completat".equals(projecte.getEstat()) || "cancelat".equals(projecte.getEstat())) {
                existingProject.marcarComplet();
            }

            return projecteRepository.save(existingProject);
        } else {
            throw new EntityNotFoundException("Projecte no trobat amb el id: " + id + " i empresaId: " + empresaId);
        }
    }

    public void delete(Long id, Long empresaId) {
        Optional<Projecte> projecteOptional = projecteRepository.findById(id);
        if (projecteOptional.isPresent() && projecteOptional.get().getEmpresa().getEmpresaId().equals(empresaId)) {
            projecteRepository.delete(projecteOptional.get());
        } else {
            throw new EntityNotFoundException("Projecte no trobat");
        }
    }
}