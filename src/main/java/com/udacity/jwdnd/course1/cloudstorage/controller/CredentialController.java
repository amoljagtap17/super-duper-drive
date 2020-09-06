package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping()
    public String insertUpdateCredential(@ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication authentication) {

        User user = (User) authentication.getDetails();
        credentialForm.setUserId(user.getUserId());
        credentialForm.setUserName(user.getUsername());

        credentialService.insertUpdateCredential(credentialForm);

        return "redirect:/home";
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("credentialId")Integer credentialId) {

        credentialService.deleteCredentialById(credentialId);

        return "redirect:/home";
    }
}
