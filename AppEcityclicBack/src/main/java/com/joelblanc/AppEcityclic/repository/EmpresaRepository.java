package com.joelblanc.AppEcityclic.repository;

import com.joelblanc.AppEcityclic.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
