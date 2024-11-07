package com.joelblanc.AppEcityclic.service;

import com.joelblanc.AppEcityclic.entity.Empresa;
import com.joelblanc.AppEcityclic.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;


    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Empresa update(Empresa empresa, Long id) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);
        if (empresaOptional.isPresent()) {
            Empresa existingEmpresa = empresaOptional.get();

            if (empresa.getNom() != null)
                existingEmpresa.setNom(empresa.getNom());
            if (empresa.getAdreca() != null)
                existingEmpresa.setAdreca(empresa.getAdreca());
            if (empresa.getCif() != null)
                existingEmpresa.setCif(empresa.getCif());

            return empresaRepository.save(existingEmpresa);
        } else {
            throw new EntityNotFoundException("Empresa no torbada amb el id: " + id);
        }
    }

    public void delete(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new EntityNotFoundException("Empresa no trobada amb el id: " + id);
        }
        empresaRepository.deleteById(id);
    }
}
