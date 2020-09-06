package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping()
    public String insertUpdateNote(@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication) {

        User user = (User) authentication.getDetails();
        noteForm.setUserId(user.getUserId());

        noteService.insertUpdateNote(noteForm);

        return "redirect:/home";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("noteId") Integer noteId) {

        noteService.deleteNoteById(noteId);

        return "redirect:/home";
    }
}
