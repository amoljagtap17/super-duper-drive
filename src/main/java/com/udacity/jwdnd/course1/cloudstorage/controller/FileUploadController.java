package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file-upload")
public class FileUploadController {

    private UserService userService;
    private FileService fileService;

    public FileUploadController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping()
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication) throws IOException {

        User user = userService.getUser(authentication.getName());
        File fileToUpload = new File(
                null,
                fileUpload.getOriginalFilename(),
                fileUpload.getContentType(),
                String.valueOf(fileUpload.getSize()),
                user.getUserId(),
                fileUpload.getBytes()
        );

        fileService.uploadFile(fileToUpload);

        return "redirect:/home";
    }
}
