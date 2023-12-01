package com.example.eeet2582.Controller;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.eeet2582.Model.User;
import com.example.eeet2582.Service.UserService;

@RestController
public class UserController {

    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user) throws InterruptedException, ExecutionException {
        return userService.createUser(user);
    }

    @GetMapping("/get")
    public User getUser(@RequestParam String userID) throws InterruptedException, ExecutionException {
        return userService.getUser(userID);
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User user) throws InterruptedException, ExecutionException {
        return userService.updateUser(user);
    }

    @GetMapping("/get/documentsList/{userID}")
    public HashMap<String, String> getDocuments(@PathVariable String userID) throws InterruptedException, ExecutionException {
        return userService.getDocumentsList(userID);
    }

    @PutMapping("/delete")
    public String deleteUser(@RequestParam String userID) throws InterruptedException, ExecutionException {
        return userService.deleteUser(userID);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testGetEndpoint() {
        return ResponseEntity.ok("Test get Endpoint is Working");
    }
}
