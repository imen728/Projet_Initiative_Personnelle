package com.examenService.examenService.Repositories;

import com.examenService.examenService.Entities.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenRepository extends JpaRepository<Examen,Long> {
}
