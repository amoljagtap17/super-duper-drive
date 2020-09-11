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
    public String insertUpdateNote(@ModelAttribute("noteForm") NoteForm noteForm, Model model, Authentication authentication) {

        String errorMsg = null;
        boolean success = false;

        User user = (User) authentication.getDetails();
        noteForm.setUserId(user.getUserId());

        try {
            int recordsUpdated = noteService.insertUpdateNote(noteForm);

            if (recordsUpdated == 0) {
                errorMsg = "No notes were inserted/ updated. Please try again.";
            } else {
                success = true;
            }
        } catch (Exception ex) {
            errorMsg = "There was an error inserting/ updating the note. Please try again.";
        }

        model.addAttribute("success", success);
        model.addAttribute("errorMsg", errorMsg);

        return "result";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("noteId") Integer noteId, Model model) {

        String errorMsg = null;
        boolean success = false;

        try {
            int recordsDeleted = noteService.deleteNoteById(noteId);

            if (recordsDeleted == 0) {
                errorMsg = "No notes were deleted. Please try again.";
            } else {
                success = true;
            }
        } catch (Exception ex) {
            errorMsg = "There was an error deleting the note. Please try again.";
        }

        model.addAttribute("success", success);
        model.addAttribute("errorMsg", errorMsg);

        return "result";
    }
}
