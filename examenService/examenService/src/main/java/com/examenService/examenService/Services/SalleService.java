package com.examenService.examenService.Services;

import com.examenService.examenService.Entities.Salle;
import com.examenService.examenService.Repositories.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalleService {
    @Autowired
    SalleRepository salleRepository;

    public List<Salle> retrieveAllSalles() {
        List<Salle> Salles = (List<Salle>) salleRepository.findAll();

        return Salles;
    }

    public ResponseEntity<Salle> retrieveSalle(long id) {

        Optional<Salle> salle = salleRepository.findById(id);
        if (salle.isPresent()) {
            return ResponseEntity.ok(salle.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Salle> addSalle(Salle salle) {

        if (salle == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        salleRepository.save(salle);
        return ResponseEntity.ok(salle);
    }


    public ResponseEntity<Salle> updateSalle(long id, Salle salle) {

        if (salle == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Salle> optionalSalle = salleRepository.findById(id);
        if (optionalSalle.isPresent()) {
            salle.setId(id);
            salleRepository.save(salle);
            return ResponseEntity.ok(optionalSalle.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public void deleteSalle(long id) {

        salleRepository.deleteById(id);
    }


}
