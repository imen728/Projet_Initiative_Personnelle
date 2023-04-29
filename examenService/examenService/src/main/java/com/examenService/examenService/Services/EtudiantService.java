package com.examenService.examenService.Services;

import com.examenService.examenService.Entities.Etudiant;
import com.examenService.examenService.Repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {
    @Autowired
    EtudiantRepository etudiantRepository;

    public List<Etudiant> retrieveAllEtudiants() {
        List<Etudiant> etudiants = (List<Etudiant>) etudiantRepository.findAll();

        return etudiants;
    }
    public ResponseEntity<Etudiant> retrieveEtudiant(long id) {

        Optional<Etudiant> etudiant = etudiantRepository.findById(id);
        if (etudiant.isPresent()) {
            return ResponseEntity.ok(etudiant.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Etudiant> addEtudiant(Etudiant etudiant) {

        if (etudiant == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        etudiantRepository.save(etudiant);
        return ResponseEntity.ok(etudiant);
    }


    public ResponseEntity<Etudiant> updateEtudiant(long id, Etudiant etudiant) {

        if (etudiant == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(id);
        if (optionalEtudiant.isPresent()) {
            etudiant.setId(id);
            etudiantRepository.save(etudiant);
            return ResponseEntity.ok(optionalEtudiant.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public void deleteEtudiant(long id) {

        etudiantRepository.deleteById(id);
    }


}
