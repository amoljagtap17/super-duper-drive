package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String insertUpdateCredential(@ModelAttribute("credentialForm") CredentialForm credentialForm, Model model, Authentication authentication) {

        String errorMsg = null;
        boolean success = false;

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        User user = (User) authentication.getDetails();
        credentialForm.setUserId(user.getUserId());
        credentialForm.setKey(encodedKey);
        credentialForm.setPassword(encryptedPassword);

        try {
            int recordsUpdated = credentialService.insertUpdateCredential(credentialForm);

            if (recordsUpdated == 0) {
                errorMsg = "No credentials were inserted/ updated. Please try again.";
            } else {
                success = true;
            }
        } catch (Exception ex) {
            errorMsg = "There was an error inserting/ updating the credential. Please try again.";
        }

        model.addAttribute("success", success);
        model.addAttribute("errorMsg", errorMsg);

        return "result";
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId, Model model) {

        String errorMsg = null;
        boolean success = false;

        try {
            int recordsDeleted = credentialService.deleteCredentialById(credentialId);

            if (recordsDeleted == 0) {
                errorMsg = "No credentials were deleted. Please try again.";
            } else {
                success = true;
            }
        } catch (Exception ex) {
            errorMsg = "There was an error deleting the credential. Please try again.";
        }

        model.addAttribute("success", success);
        model.addAttribute("errorMsg", errorMsg);

        return "result";
    }
}
