package com.joelblanc.AppEcityclic.repository;

import com.joelblanc.AppEcityclic.entity.Projecte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjecteRepository extends JpaRepository<Projecte, Long> {
    List<Projecte> findByEmpresa_EmpresaId(Long empresaId);
    Optional<Projecte> findByProjecteIdAndEmpresa_EmpresaId(Long projecteId, Long empresaId);
}
