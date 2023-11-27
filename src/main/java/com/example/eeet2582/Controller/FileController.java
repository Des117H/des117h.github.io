package com.example.eeet2582.Controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.eeet2582.Service.FileService;

@RestController
public class FileController {
    public FileService fileService;

     public FileController(FileService fileService){
        this.fileService = fileService;
    }
    
    @PostMapping("/upload")
    public String uploadFile(@RequestParam(name = "file") MultipartFile file) throws IOException{
        return fileService.saveTest(file);
    }

}
