package com.examenService.examenService.Controllers;

import com.examenService.examenService.Dto.EtudiantDto;
import com.examenService.examenService.Entities.Etudiant;
import com.examenService.examenService.Services.EtudiantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class EtudiantController {

    public final static String FOUND = "FOUND";
    public final static String BAD_REQUEST = "BAD_REQUEST";
    public final static String NOT_FOUND = "NOT_FOUND";
    public final static String NULL = "ID NULL DETECTED";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        super();
        this.etudiantService = etudiantService;
    }

    @GetMapping("/getEtudiants")
    @ResponseBody
    public List<EtudiantDto> getEtudiants() {
        return etudiantService.retrieveAllEtudiants().stream().map(etudiant -> modelMapper.map(etudiant, EtudiantDto.class))
                .collect(Collectors.toList());

    }
    @GetMapping(value = "/getEtudiant/{id}")
    public ResponseEntity<Object> getEtudiant(@PathVariable("id") long id) {
        ResponseEntity<Etudiant> etudiant = etudiantService.retrieveEtudiant(id);
        if (etudiant.getStatusCodeValue() == 200) {
            EtudiantDto etudiantDto = modelMapper.map(etudiant.getBody(), EtudiantDto.class);
            return new ResponseEntity<>(etudiantDto, HttpStatus.OK);
        } else if(etudiant.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }
    }

    @PostMapping("/addEtudiant")
    public ResponseEntity<Object> addEtudiant(@RequestBody EtudiantDto etudiantDTO) {
        Etudiant etudiantReq = modelMapper.map(etudiantDTO, Etudiant.class);
        ResponseEntity<Etudiant> etudiant = etudiantService.addEtudiant(etudiantReq);
        if (etudiant.getStatusCodeValue() == 200) {
            EtudiantDto etudiantRes = modelMapper.map(etudiant.getBody(), EtudiantDto.class);
            return new ResponseEntity<>(etudiantRes, HttpStatus.OK);
        } else if (etudiant.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(FOUND, HttpStatus.FOUND);
        }
    }


    @PutMapping(value = "/Etudiant/{id}")
    public ResponseEntity<Object> updateEtudiant(@PathVariable("id") long id, @RequestBody EtudiantDto etudiantDto) {
        Etudiant etudiantReq = modelMapper.map(etudiantDto, Etudiant.class);
        ResponseEntity<Etudiant> av = etudiantService.updateEtudiant(id, etudiantReq);

        if (av.getStatusCodeValue() == 200) {
            EtudiantDto etudiantRes = modelMapper.map(av.getBody(), EtudiantDto.class);
            return new ResponseEntity<>(etudiantRes, HttpStatus.OK);
        } else if (av.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else if(av.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }

    }

    @DeleteMapping(value = "/Etudiant/{id}")
    public void deleteEtudiant(@PathVariable("id") long id) {
        etudiantService.deleteEtudiant(id);
    }


}
