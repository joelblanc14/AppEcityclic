package com.joelblanc.AppEcityclic.repository;

import com.joelblanc.AppEcityclic.entity.Tasca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TascaRepository extends JpaRepository<Tasca, Long> {

    List<Tasca> findByProjecte_projecteId(Long projecteId);
    List<Tasca> findByTreballadorTreballadorId(Long treballadorId);
}
