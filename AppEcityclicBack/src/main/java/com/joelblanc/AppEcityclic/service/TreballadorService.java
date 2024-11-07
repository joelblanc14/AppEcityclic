package com.joelblanc.AppEcityclic.service;

import com.joelblanc.AppEcityclic.entity.Treballador;
import com.joelblanc.AppEcityclic.repository.TreballadorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TreballadorService {

    private final TreballadorRepository treballadorRepository;


    public List<Treballador> findByEmpresa_Id(Long empresaId) {
        return treballadorRepository.findByEmpresa_EmpresaId(empresaId);
    }

    public Optional<Treballador> findById(Long id) {
        return treballadorRepository.findById(id);
    }

    public Treballador save(Treballador treballador) {
        return treballadorRepository.save(treballador);
    }

    public Treballador update(Treballador treballador, Long id) {
        Optional<Treballador> treballadorOptional = treballadorRepository.findById(id);
        if (treballadorOptional.isPresent()) {
            Treballador existingTreballador = treballadorOptional.get();

            if (treballador.getNom() != null)
                existingTreballador.setNom(treballador.getNom());
            if (treballador.getPosicio() != null)
                existingTreballador.setPosicio(treballador.getPosicio());
            if (treballador.getSalari() != null)
                existingTreballador.setSalari(treballador.getSalari());
            if (treballador.getEmail() != null)
                existingTreballador.setEmail(treballador.getEmail());

            return treballadorRepository.save(existingTreballador);
        } else {
            throw new EntityNotFoundException("Treballador no trobat amb id: " + id);
        }
    }

    public void delete(Long id) {
        if (!treballadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Treballador no trobat amb id: " + id);
        }
        treballadorRepository.deleteById(id);
    }
}
