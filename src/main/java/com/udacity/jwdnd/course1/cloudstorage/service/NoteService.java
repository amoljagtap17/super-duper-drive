package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    /*public NoteForm getNoteById(Integer noteId) {
        return noteMapper.getNoteById(noteId);
    }*/

    public List<NoteForm> getNotesByUserId(Integer userId) {
        return noteMapper.getNotesByUserId(userId);
    }

    public int insertUpdateNote(NoteForm noteForm) {
        if (noteForm.getNoteId() != null) {
            return noteMapper.updateNote(noteForm);
        } else {
            return noteMapper.insertNote(noteForm);
        }
    }

    public int deleteNoteById(Integer noteId) {
        return noteMapper.deleteNoteById(noteId);
    }
}
