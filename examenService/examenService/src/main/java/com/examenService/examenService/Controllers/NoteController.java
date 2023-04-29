package com.examenService.examenService.Controllers;

import com.examenService.examenService.Dto.NoteDto;
import com.examenService.examenService.Entities.Note;
import com.examenService.examenService.Services.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class NoteController {

    public final static String FOUND = "FOUND";
    public final static String BAD_REQUEST = "BAD_REQUEST";
    public final static String NOT_FOUND = "NOT_FOUND";
    public final static String NULL = "ID NULL DETECTED";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    NoteService noteService;

    public NoteController(NoteService noteService) {
        super();
        this.noteService = noteService;
    }

    @GetMapping("/getNotes")
    @ResponseBody
    public List<NoteDto> getNotes() {
        return noteService.retrieveAllNotes().stream().map(note -> modelMapper.map(note, NoteDto.class))
                .collect(Collectors.toList());

    }
    @GetMapping(value = "/getNote/{id}")
    public ResponseEntity<Object> getNote(@PathVariable("id") long id) {
        ResponseEntity<Note> note = noteService.retrieveNote(id);
        if (note.getStatusCodeValue() == 200) {
            NoteDto noteDto = modelMapper.map(note.getBody(), NoteDto.class);
            return new ResponseEntity<>(noteDto, HttpStatus.OK);
        } else if(note.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }
    }

    @PostMapping("/addNote")
    public ResponseEntity<Object> addNote(@RequestBody NoteDto noteDto) {
        Note noteReq = modelMapper.map(noteDto, Note.class);
        ResponseEntity<Note> note = noteService.addNote(noteReq);
        if (note.getStatusCodeValue() == 200) {
            NoteDto noteRes = modelMapper.map(note.getBody(), NoteDto.class);
            return new ResponseEntity<>(noteRes, HttpStatus.OK);
        } else if (note.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(FOUND, HttpStatus.FOUND);
        }
    }


    @PutMapping(value = "/Note/{id}")
    public ResponseEntity<Object> updateNote(@PathVariable("id") long id, @RequestBody NoteDto noteDto) {
        Note noteReq = modelMapper.map(noteDto, Note.class);
        ResponseEntity<Note> av = noteService.updateNote(id, noteReq);

        if (av.getStatusCodeValue() == 200) {
            NoteDto noteRes = modelMapper.map(av.getBody(), NoteDto.class);
            return new ResponseEntity<>(noteRes, HttpStatus.OK);
        } else if (av.getStatusCodeValue() == 400) {
            return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } else if(av.getStatusCodeValue() == 404){
            return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(NULL,HttpStatus.OK);

        }

    }

    @DeleteMapping(value = "/Note/{id}")
    public void deleteNote(@PathVariable("id") long id) {
        noteService.deleteNote(id);
    }


}

