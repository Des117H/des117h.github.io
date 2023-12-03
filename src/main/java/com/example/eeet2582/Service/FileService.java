package com.example.eeet2582.Service;

import com.example.eeet2582.Model.FileDocument;
import com.example.eeet2582.Model.User;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.extractor.POIOLE2TextExtractor;
import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;


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

    

    // public User getUser(String userID) throws ExecutionException,
    // InterruptedException {
    // Firestore dbFirestore = FirestoreClient.getFirestore();
    // DocumentReference documentReference =
    // dbFirestore.collection("User_account").document(userID);
    // ApiFuture<DocumentSnapshot> future = documentReference.get();
    // DocumentSnapshot document = future.get();
    // User user;
    // if (document.exists()) {
    // user = document.toObject(User.class);
    // return user;
    // }
    // return null;
    // }

    public FileDocument getDocumentCRUD(String fileID) throws IOException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("Documents").document(fileID);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document;
        
        try {
            document = future.get();
            if (document.exists()) {
                return document.toObject(FileDocument.class);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean addDocumentDataToUser(String fileName, String userID, String dateTime) {
        String documentID = generateId();
        String downloadURL = "gs://architecture-grandma-bea3b.appspot.com/" + documentID;
        FileDocument fileDocument = new FileDocument(documentID, fileName, downloadURL, dateTime, userID);

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
        return userID + "|" + extractFileName(originalFileName) + "|" + dateTime + "."
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
        // Storage storage = StorageOptions.newBuilder();
        fileReferencePath = "0186e0bf-2e30-4d1d-b23b-f72bf7520fbc|1|2023-12-1_17:40:48|.docx";
        Blob blob = storage.get("architecture-grandma-bea3b.appspot.com", fileReferencePath);
        System.out.println(blob);
        // InputStream inputStream = blob.reader();
        // XWPFDocument document = new XWPFDocument(inputStream);
        // XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        // String textContent = extractor.getText();
        // extractor.close();
        // document.close();
        // return textContent;
        return "run";
    }

    // public  void textProcess(){
    //     String inputFile;
    //     FileInputStream fis = new FileInputStream(inputFile);
    //     POIFSFileSystem fileSystem = new POIFSFileSystem(fis);
    //     // Firstly, get an extractor for the Workbook
    //     POIOLE2TextExtractor oleTextExtractor = ExtractorFactory.createExtractor(fileSystem);
    //     // Then a List of extractors for any embedded Excel, Word, PowerPoint
    //     // or Visio objects embedded into it.
    //     POITextExtractor[] embeddedExtractors =
    //     ExtractorFactory.getEmbededDocsTextExtractors(oleTextExtractor);
    //     for (POITextExtractor textExtractor : embeddedExtractors) {
    //     // If the embedded object was an Excel spreadsheet.
    //     if (textExtractor instanceof WordExtractor) {
    //         WordExtractor wordExtractor = (WordExtractor) textExtractor;
    //         String[] paragraphText = wordExtractor.getParagraphText();
    //         for (String paragraph : paragraphText) {
    //             System.out.println(paragraph);
    //         }
    //         // Display the document's header and footer text
    //         System.out.println("Footer text: " + wordExtractor.getFooterText());
    //         System.out.println("Header text: " + wordExtractor.getHeaderText());
    //         }
    //     }
    // }
}
