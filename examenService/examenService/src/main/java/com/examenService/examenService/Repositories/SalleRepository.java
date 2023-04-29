package com.examenService.examenService.Repositories;

import com.examenService.examenService.Entities.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SalleRepository extends JpaRepository<Salle,Long> {
}