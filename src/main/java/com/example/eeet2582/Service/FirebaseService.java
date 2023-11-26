package com.example.eeet2582.Service;

import java.io.FileInputStream;
import java.io.File;
import java.util.Objects;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;

public class FirebaseService {
  
	  ClassLoader classLoader = FirebaseService.class.getClassLoader();

    File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
    FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build();

	FirebaseApp.initializeApp(options);
}
