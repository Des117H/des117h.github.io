package com.example.eeet2582.Model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CRUD {
    private String id;
    private String username;
    private String password;
    private String name;
    private List<String> uploadedFileReferences;
}
