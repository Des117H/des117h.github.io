package com.example.eeet2582.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    @RequestMapping("/")
    public String uploadForm() {
        return "upload-form";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        // Handle the file upload logic
        if (!file.isEmpty()) {
            try {
                // Save the file to a storage location or process it
                // For simplicity, we just print the file name here
                System.out.println("Received file: " + file.getOriginalFilename());
                return "File uploaded successfully!";
            } catch (Exception e) {
                return "Failed to upload file!";
            }
        } else {
            return "Please select a file to upload.";
        }
    }
}
