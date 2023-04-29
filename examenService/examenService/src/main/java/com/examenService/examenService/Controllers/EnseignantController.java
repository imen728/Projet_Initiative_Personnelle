package com.examenService.examenService.Controllers;

import com.examenService.examenService.Dto.EnseignantDto;
import com.examenService.examenService.Entities.Enseignant;
import com.examenService.examenService.Services.EnseignantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EnseignantController {

    public final static String FOUND = "FOUND";
    public final static String BAD_REQUEST = "BAD_REQUEST";
    public final static String NOT_FOUND = "NOT_FOUND";
    public final static String NULL = "ID NULL DETECTED";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EnseignantService enseignantService;

    public EnseignantController(EnseignantService enseignantService) {
        super();
        this.enseignantService = enseignantService;
    }

    @GetMapping("/getEnseignants")
    @ResponseBody
    public List<EnseignantDto> getEnseignants() {
        return enseignantService.retrieveAllEnseignants().stream().map(enseignant -> modelMapper.map(enseignant, EnseignantDto.class))
                .collect(Collectors.toList());

    }
    @GetMapping(value = "/getEnseignant/{id}")
    public ResponseEntity<Object> getEnseignant(@PathVariable("id") long id) {
        ResponseEntity<Enseignant> enseignant = enseignantService.retrieveEnseignant(id);
        if (enseignant.getStatusCodeValue() == 200) {
            EnseignantDto enseignantDto = modelMapper.map(enseignant.getBody(), EnseignantDto.class);
            return new ResponseEntity<>(enseignantDto, HttpStatus.OK);
        } else if(enseignant.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }
    }

    @PostMapping("/addEnseignant")
    public ResponseEntity<Object> addEnseignant(@RequestBody EnseignantDto enseignantDTO) {
        Enseignant enseignantReq = modelMapper.map(enseignantDTO, Enseignant.class);
        ResponseEntity<Enseignant> enseignant = enseignantService.addEnseignant(enseignantReq);
        if (enseignant.getStatusCodeValue() == 200) {
            EnseignantDto enseignantRes = modelMapper.map(enseignant.getBody(), EnseignantDto.class);
            return new ResponseEntity<>(enseignantRes, HttpStatus.OK);
        } else if (enseignant.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(FOUND, HttpStatus.FOUND);
        }
    }


    @PutMapping(value = "/Enseignant/{id}")
    public ResponseEntity<Object> updateEnseignant(@PathVariable("id") long id, @RequestBody EnseignantDto enseignantDto) {
        Enseignant enseignantReq = modelMapper.map(enseignantDto, Enseignant.class);
        ResponseEntity<Enseignant> av = enseignantService.updateEnseignant(id, enseignantReq);

        if (av.getStatusCodeValue() == 200) {
            EnseignantDto enseignantRes = modelMapper.map(av.getBody(), EnseignantDto.class);
            return new ResponseEntity<>(enseignantRes, HttpStatus.OK);
        } else if (av.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else if(av.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }

    }

    @DeleteMapping(value = "/Enseignant/{id}")
    public void deleteEnseignant(@PathVariable("id") long id) {
        enseignantService.deleteEnseignant(id);
    }


}
