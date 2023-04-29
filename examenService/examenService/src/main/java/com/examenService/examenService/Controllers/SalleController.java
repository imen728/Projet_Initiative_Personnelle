package com.examenService.examenService.Controllers;

import com.examenService.examenService.Dto.SalleDto;
import com.examenService.examenService.Entities.Salle;
import com.examenService.examenService.Services.SalleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalleController {

    public final static String FOUND = "FOUND";
    public final static String BAD_REQUEST = "BAD_REQUEST";
    public final static String NOT_FOUND = "NOT_FOUND";
    public final static String NULL = "ID NULL DETECTED";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    SalleService salleService;

    public SalleController(SalleService salleService) {
        super();
        this.salleService = salleService;
    }

    @GetMapping("/getSalles")
    @ResponseBody
    public List<SalleDto> getSalles() {
        return salleService.retrieveAllSalles().stream().map(salle -> modelMapper.map(salle, SalleDto.class))
                .collect(Collectors.toList());

    }
    @GetMapping(value = "/getSalle/{id}")
    public ResponseEntity<Object> getSalle(@PathVariable("id") long id) {
        ResponseEntity<Salle> salle = salleService.retrieveSalle(id);
        if (salle.getStatusCodeValue() == 200) {
            SalleDto salleDto = modelMapper.map(salle.getBody(), SalleDto.class);
            return new ResponseEntity<>(salleDto, HttpStatus.OK);
        } else if(salle.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }
    }

    @PostMapping("/addSalle")
    public ResponseEntity<Object> addSalle(@RequestBody SalleDto salleDto) {
        Salle salleReq = modelMapper.map(salleDto, Salle.class);
        ResponseEntity<Salle> salle = salleService.addSalle(salleReq);
        if (salle.getStatusCodeValue() == 200) {
            SalleDto salleRes = modelMapper.map(salle.getBody(), SalleDto.class);
            return new ResponseEntity<>(salleRes, HttpStatus.OK);
        } else if (salle.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(FOUND, HttpStatus.FOUND);
        }
    }


    @PutMapping(value = "/Salle/{id}")
    public ResponseEntity<Object> updateSalle(@PathVariable("id") long id, @RequestBody SalleDto salleDto) {
        Salle salleReq = modelMapper.map(salleDto, Salle.class);
        ResponseEntity<Salle> av = salleService.updateSalle(id, salleReq);

        if (av.getStatusCodeValue() == 200) {
            SalleDto salleRes = modelMapper.map(av.getBody(), SalleDto.class);
            return new ResponseEntity<>(salleRes, HttpStatus.OK);
        } else if (av.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else if(av.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }

    }

    @DeleteMapping(value = "/Salle/{id}")
    public void deleteSalle(@PathVariable("id") long id) {
        salleService.deleteSalle(id);
    }


}


