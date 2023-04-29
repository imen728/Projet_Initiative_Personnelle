package com.examenService.examenService.Controllers;


import com.examenService.examenService.Dto.ExamenDto;
import com.examenService.examenService.Entities.Examen;
import com.examenService.examenService.Services.ExamenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ExamenController {

    public final static String FOUND = "FOUND";
    public final static String BAD_REQUEST = "BAD_REQUEST";
    public final static String NOT_FOUND = "NOT_FOUND";
    public final static String NULL = "ID NULL DETECTED";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ExamenService examenService;

    public ExamenController(ExamenService examenService) {
        super();
        this.examenService = examenService;
    }

    @GetMapping("/getExamens")
    @ResponseBody
    public List<ExamenDto> getExamens() {
        return examenService.retrieveAllExamens().stream().map(examen -> modelMapper.map(examen, ExamenDto.class))
                .collect(Collectors.toList());

    }
    @GetMapping(value = "/getExamen/{id}")
    public ResponseEntity<Object> getExamen(@PathVariable("id") long id) {
        ResponseEntity<Examen> examen = examenService.retrieveExamen(id);
        if (examen.getStatusCodeValue() == 200) {
            ExamenDto examenDto = modelMapper.map(examen.getBody(), ExamenDto.class);
            return new ResponseEntity<>(examenDto, HttpStatus.OK);
        } else if(examen.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }
    }

    @PostMapping("/addExamen")
    public ResponseEntity<Object> addExamen(@RequestBody ExamenDto examenDTO) {
        Examen examenReq = modelMapper.map(examenDTO, Examen.class);
        ResponseEntity<Examen> examen = examenService.addExamen(examenReq);
        if (examen.getStatusCodeValue() == 200) {
            ExamenDto examenRes = modelMapper.map(examen.getBody(), ExamenDto.class);
            return new ResponseEntity<>(examenRes, HttpStatus.OK);
        } else if (examen.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(FOUND, HttpStatus.FOUND);
        }
    }


    @PutMapping(value = "/Examen/{id}")
    public ResponseEntity<Object> updateExamen(@PathVariable("id") long id, @RequestBody ExamenDto examenDto) {
        Examen examenReq = modelMapper.map(examenDto, Examen.class);
        ResponseEntity<Examen> av = examenService.updateExamen(id, examenReq);

        if (av.getStatusCodeValue() == 200) {
            ExamenDto examenRes = modelMapper.map(av.getBody(), ExamenDto.class);
            return new ResponseEntity<>(examenRes, HttpStatus.OK);
        } else if (av.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else if(av.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }

    }

    @DeleteMapping(value = "/Examen/{id}")
    public void deleteExamen(@PathVariable("id") long id) {
        examenService.deleteExamen(id);
    }


}

