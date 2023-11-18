package com.example.eeet2582.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.eeet2582.Service.FileStorageService;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Generate a unique file name or use the original file name
            String fileName = "documents/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Upload the file to S3
            fileStorageService.uploadFile(fileName, file);

            // Return a success message or the S3 URL
            return "File uploaded successfully! S3 URL: " + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file.";
        }
    }
}
