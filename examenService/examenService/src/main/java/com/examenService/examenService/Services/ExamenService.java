package com.examenService.examenService.Services;

import com.examenService.examenService.Entities.Examen;
import com.examenService.examenService.Repositories.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamenService {
    @Autowired
    ExamenRepository examenRepository;

    public List<Examen> retrieveAllExamens() {
        List<Examen> examens = (List<Examen>) examenRepository.findAll();

        return examens;
    }
    public ResponseEntity<Examen> retrieveExamen(long id) {

        Optional<Examen> examen = examenRepository.findById(id);
        if (examen.isPresent()) {
            return ResponseEntity.ok(examen.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Examen> addExamen(Examen examen) {

        if (examen == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        examenRepository.save(examen);
        return ResponseEntity.ok(examen);
    }


    public ResponseEntity<Examen> updateExamen(long id, Examen examen) {

        if (examen == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Examen> optionalExamen = examenRepository.findById(id);
        if (optionalExamen.isPresent()) {
            examen.setId(id);
            examenRepository.save(examen);
            return ResponseEntity.ok(optionalExamen.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public void deleteExamen(long id) {

        examenRepository.deleteById(id);
    }


}
