package com.example.eeet2582.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.eeet2582.Model.FileDocument;
import com.example.eeet2582.Service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class FileController {
    public FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/document/upload/")
    public String uploadFile(@RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "user-id") String user,
            @RequestParam(name = "uploaded-at") String dateTime) {
        try {
            return fileService.saveTest(file, user, dateTime);
        } catch (IOException e) {
            return "error: " + e.getMessage();
        }
    }

    @GetMapping("/document/get-metadata/{fileID}")
    public FileDocument getFileUploaded(@PathVariable String fileID) throws InterruptedException, ExecutionException {
        return fileService.getDocumentCRUD(fileID);
    }

}
