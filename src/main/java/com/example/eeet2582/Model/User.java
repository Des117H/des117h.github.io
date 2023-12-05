package com.example.eeet2582.Model;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String documentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private HashMap<String, String> docList;

    public void initialize(String userID, String signUpEmail, String signUpFirstName, String signUpLastName,
            String signUpPhone) {
        this.documentId = userID;
        this.email = signUpEmail;
        this.firstName = signUpFirstName;
        this.lastName = signUpLastName;
        this.phoneNumber = signUpPhone;
        this.docList = new HashMap<>();
    }
}
