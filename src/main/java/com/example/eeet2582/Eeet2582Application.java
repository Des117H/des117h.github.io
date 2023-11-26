package com.example.eeet2582;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class Eeet2582Application {

	public static void main(String[] args) throws IOException {

    FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://architecture-grandma-bea3b-default-rtdb.asia-southeast1.firebasedatabase.app")
        .build();
        
    if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
    } else {
        FirebaseApp.getInstance();
    }
	SpringApplication.run(Eeet2582Application.class, args);
	}

}
