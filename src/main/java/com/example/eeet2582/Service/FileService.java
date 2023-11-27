package com.example.eeet2582.Service;

import com.example.eeet2582.Model.FileDocument;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import com.google.cloud.storage.StorageOptions;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService {

    private Storage storage;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            ClassPathResource serviceAccount = new ClassPathResource("serviceAccountKey.json");
            storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setProjectId("architecture-grandma-bea3b").build().getService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String saveTest(MultipartFile file) throws IOException {
        String imageName = generateFileName(file.getOriginalFilename());
        System.out.println(imageName);
        Map<String, String> map = new HashMap<>();
        map.put("gs://architecture-grandma-bea3b.appspot.com", imageName);
        BlobId blobId = BlobId.of("architecture-grandma-bea3b.appspot.com", imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getInputStream());
        return imageName;
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
}
