package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping()
    public String insertUpdateCredential(@ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication authentication) {

        System.out.println("credentialForm1 : " + credentialForm.toString());

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        User user = (User) authentication.getDetails();
        credentialForm.setUserId(user.getUserId());
        credentialForm.setKey(encodedKey);
        credentialForm.setPassword(encryptedPassword);

        System.out.println("credentialForm2 : " + credentialForm.toString());

        credentialService.insertUpdateCredential(credentialForm);

        return "redirect:/home";
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("credentialId")Integer credentialId) {

        credentialService.deleteCredentialById(credentialId);

        return "redirect:/home";
    }
}
