package com.example.eeet2582.Controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.eeet2582.Model.FileDocument;
import com.example.eeet2582.Service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController("/document")
public class FileController {
    public FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "user-id") String user,
            @RequestParam(name = "uploaded-at") String dateTime) {
        try {
            return fileService.saveTest(file, user, dateTime);
        } catch (IOException e) {
            return "error: " + e.getMessage();
        }
    }

    @GetMapping("/get-metadata/{file-id}")
    public FileDocument getFileUploaded(@PathVariable String fileID) {
        try {
            return fileService.getDocumentCRUD(fileID);
        } catch (Exception e) {
            return null;
        }
    }

}
