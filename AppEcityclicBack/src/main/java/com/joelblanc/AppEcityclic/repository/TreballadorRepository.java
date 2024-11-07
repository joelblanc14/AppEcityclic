package com.joelblanc.AppEcityclic.repository;

import com.joelblanc.AppEcityclic.entity.Treballador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreballadorRepository extends JpaRepository<Treballador, Long> {
    List<Treballador> findByEmpresa_EmpresaId(Long empresaId);

}
