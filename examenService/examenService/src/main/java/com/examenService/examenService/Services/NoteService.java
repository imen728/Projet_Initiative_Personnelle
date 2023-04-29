package com.examenService.examenService.Services;


import com.examenService.examenService.Entities.Note;

import com.examenService.examenService.Repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    NoteRepository noteRepository;

    public List<Note> retrieveAllNotes() {
        List<Note> Notes = (List<Note>) noteRepository.findAll();

        return Notes;
    }
    public ResponseEntity<Note> retrieveNote(long id) {

        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            return ResponseEntity.ok(note.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Note> addNote(Note note) {

        if (note == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        noteRepository.save(note);
        return ResponseEntity.ok(note);
    }


    public ResponseEntity<Note> updateNote(long id, Note note) {

        if (note == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            note.setId(id);
            noteRepository.save(note);
            return ResponseEntity.ok(optionalNote.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public void deleteNote(long id) {

        noteRepository.deleteById(id);
    }


}

