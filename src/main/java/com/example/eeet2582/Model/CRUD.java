package com.example.eeet2582.Model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CRUD {
    private String documentId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<String> uploadedFileReferences;

}
