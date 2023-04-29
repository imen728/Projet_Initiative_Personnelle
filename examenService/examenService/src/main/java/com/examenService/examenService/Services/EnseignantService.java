package com.examenService.examenService.Services;

import com.examenService.examenService.Entities.Enseignant;
import com.examenService.examenService.Repositories.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnseignantService {
    @Autowired
    EnseignantRepository enseignantRepository;

    public List<Enseignant> retrieveAllEnseignants() {
        List<Enseignant> enseignants = (List<Enseignant>) enseignantRepository.findAll();

        return enseignants;
    }
    public ResponseEntity<Enseignant> retrieveEnseignant(long id) {

        Optional<Enseignant> enseignant = enseignantRepository.findById(id);
        if (enseignant.isPresent()) {
            return ResponseEntity.ok(enseignant.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Enseignant> addEnseignant(Enseignant enseignant) {

        if (enseignant == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        enseignantRepository.save(enseignant);
        return ResponseEntity.ok(enseignant);
    }


    public ResponseEntity<Enseignant> updateEnseignant(long id, Enseignant enseignant) {

        if (enseignant == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Enseignant> optionalEnseignant = enseignantRepository.findById(id);
        if (optionalEnseignant.isPresent()) {
            enseignant.setId(id);
            enseignantRepository.save(enseignant);
            return ResponseEntity.ok(optionalEnseignant.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public void deleteEnseignant(long id) {

        enseignantRepository.deleteById(id);
    }


}
