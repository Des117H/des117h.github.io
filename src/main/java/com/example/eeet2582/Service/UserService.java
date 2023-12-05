package com.example.eeet2582.Service;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.eeet2582.Model.User;
import com.example.eeet2582.Model.FileDocument;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class UserService {

    public String createUser(User user) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("User_account")
                .document(user.getDocumentId())
                .set(user);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public User getUser(String userID) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("User_account").document(userID);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        User user;
        if (document.exists()) {
            user = document.toObject(User.class);
            return user;
        }
        return null;
    }

    public String updateUser(User user) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("User_account")
                .document(user.getDocumentId())
                .set(user);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteUser(String userID) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("User_account").document(userID).delete();
        return "Successfully deleted " + userID;
    }

    public String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String uploadDocumentUser(FileDocument file) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("Documents").document(file.getDocumentID())
                .set(file);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public static FileDocument getDocumentUser(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("Documents").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        FileDocument file;
        if (document.exists()) {
            file = document.toObject(FileDocument.class);
            return file;
        }
        return null;
    }

    public HashMap<String, String> getDocumentsList(String userID) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("User_account").document(userID);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        User user;
        if (document.exists()) {
            user = document.toObject(User.class);
            return user.getDocList();
        }
        return null;
    }
}