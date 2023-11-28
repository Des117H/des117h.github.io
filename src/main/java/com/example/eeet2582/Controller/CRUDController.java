package com.example.eeet2582.Controller;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.eeet2582.Model.User;
import com.example.eeet2582.Service.CRUDService;

@RestController
public class CRUDController {

    public CRUDService crudService;

    public CRUDController(CRUDService crudService) {
        this.crudService = crudService;
    }

    @PostMapping("/create")
    public String createCRUD(@RequestBody User crud) throws InterruptedException, ExecutionException {
        return crudService.createCRUD(crud);
    }

    @GetMapping("/get")
    public User getCRUD(@RequestParam String documentId) throws InterruptedException, ExecutionException {
        return crudService.getCRUD(documentId);
    }

    @PutMapping("/update")
    public String updateCRUD(@RequestBody User crud) throws InterruptedException, ExecutionException {
        return crudService.updateCRUD(crud);
    }

    @PutMapping("/delete")
    public String deleteCRUD(@RequestParam String documentId) throws InterruptedException, ExecutionException {
        return crudService.deleteCRUD(documentId);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testGetEndpoint() {
        return ResponseEntity.ok("Test get Endpoint is Working");
    }
}
