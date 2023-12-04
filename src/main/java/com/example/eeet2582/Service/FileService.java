package com.example.eeet2582.Service;

import com.example.eeet2582.Model.FileDocument;
import com.example.eeet2582.Model.User;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.core.Path;

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

    public String saveTest(MultipartFile file, String userID, String dateTime) throws IOException {
        String fileName = generateFileName(file.getOriginalFilename(), userID, dateTime);

        Map<String, String> map = new HashMap<>();
        map.put("gs://architecture-grandma-bea3b.appspot.com", fileName);

        BlobId blobId = BlobId.of("architecture-grandma-bea3b.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getInputStream());

        if (addDocumentDataToUser(fileName, userID, dateTime))
            return fileName;
        else
            return null;
    }

    

    public FileDocument getDocumentCRUD(String fileID) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("Documents").document(fileID);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        FileDocument fileDocument;
        if (document.exists()) {
            fileDocument = document.toObject(FileDocument.class);
            return fileDocument;
        }

        return null;
    }

    private boolean addDocumentDataToUser(String fileName, String userID, String dateTime) {
        String documentID = generateId();
        String downloadURL = "gs://architecture-grandma-bea3b.appspot.com/" + documentID;
        FileDocument fileDocument = new FileDocument();
        fileDocument.initialize(documentID, fileName, downloadURL, dateTime, userID);

        createDocument(fileDocument);

        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("User_account").document(userID);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document;

        try {
            document = future.get();

            User user = document.toObject(User.class);
            user.getDocList().put(documentID, downloadURL);

            ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("User_account")
                    .document(user.getDocumentId())
                    .set(user);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String createDocument(FileDocument document) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("Documents")
                .document(document.getDocumentID())
                .set(document);
        try {
            return collectionApiFuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateFileName(String originalFileName, String userID, String dateTime) {
        return userID + "|" + extractFileName(originalFileName) + "|" + dateTime + "|."
                + getExtension(originalFileName);
    }

    private String extractFileName(String originalFileName) {
        return originalFileName.replace("." + getExtension(originalFileName), "");
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    private String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String extractTextFromDocx(String fileReferencePath) throws IOException {
        Blob blob = storage.get("architecture-grandma-bea3b.appspot.com", fileReferencePath);
        File tempFile = new File("/tmp/downloaded_file.docx");
        java.nio.file.Path tempFilePath = tempFile.toPath();
        blob.downloadTo(tempFilePath);
        try {
            InputStream inputStream = new FileInputStream(tempFile);
            XWPFDocument document = new XWPFDocument(inputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            String text = extractor.getText();
            extractor.close();
            return text;
        } finally {
            tempFile.deleteOnExit(); // Clean up temporary file
        }
    }
}
