package com.examenService.examenService.Repositories;

import com.examenService.examenService.Entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant,Long> {
}