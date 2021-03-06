package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication authentication) throws IOException {

        String errorMsg = null;

        User user = userService.getUser(authentication.getName());

        File existingFile = fileService.getFileByNameForCurrentUser(
            fileUpload.getOriginalFilename(),
            user.getUserId()
        );

        if (fileUpload.getOriginalFilename().length() == 0) {
            model.addAttribute("success", false);

            errorMsg = "Please select a file!";
        }
        else if (existingFile != null) {
            model.addAttribute("success", false);

            errorMsg = "File already exists";
        } else {
            File fileToUpload = new File(
                    null,
                    fileUpload.getOriginalFilename(),
                    fileUpload.getContentType(),
                    String.valueOf(fileUpload.getSize()),
                    user.getUserId(),
                    fileUpload.getBytes()
            );

            try {
                fileService.uploadFile(fileToUpload);

                model.addAttribute("success", true);
            } catch (Exception ex) {
                model.addAttribute("success", false);

                errorMsg = "There was an error uploading the file. Please try again.";
            }
        }

        model.addAttribute("errorMsg", errorMsg);

        return "result";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("fileId") Integer fileId, Model model) {

        String errorMsg = null;

        try {
            int filesDeleted = fileService.deleteFileById(fileId);

            if (filesDeleted == 0) {
                errorMsg = "No file was deleted. Please try again.";
            } else {
                model.addAttribute("success", true);
            }
        } catch (Exception ex) {
            model.addAttribute("success", false);

            errorMsg = "There was an error deleting the file. Please try again.";
        }

        model.addAttribute("errorMsg", errorMsg);

        return "result";
    }

    /*@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] downloadFile(@RequestParam("fileId") Integer fileId, HttpServletResponse response) throws IOException {

        File file = fileService.getFileById(fileId);

        System.out.println("File: " + file.toString());

        return file.getFileData();
    }*/

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("fileId") Integer fileId) {

        File file = fileService.getFileById(fileId);

        byte[] output = file.getFileData();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("charset", "utf-8");
        responseHeaders.setContentType(MediaType.valueOf(file.getContentType()));
        responseHeaders.setContentLength(output.length);
        responseHeaders.set("Content-disposition", "attachment; filename=" + file.getFileName());

        return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
    }
}
