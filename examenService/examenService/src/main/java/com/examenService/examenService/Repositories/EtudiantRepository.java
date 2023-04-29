package com.examenService.examenService.Repositories;

import com.examenService.examenService.Entities.Etudiant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {
}
