package com.example.eeet2582.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.eeet2582.Model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class CRUDService {

    public String createCRUD(User crud) throws ExecutionException, InterruptedException {
        crud.setDocumentId(generateId());
        // crud.setUploadedFileReferences(null);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("User_account").document(crud.getDocumentId())
                .set(crud);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public User getCRUD(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("User_account").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        User crud;
        if (document.exists()) {
            crud = document.toObject(User.class);
            return crud;
        }
        return null;
    }

    public String updateCRUD(User crud) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("User_account").document(crud.getDocumentId())
                .set(crud);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteCRUD(String documentId) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("User_account").document(documentId).delete();
        return "Successfully deleted " + documentId;
    }

    public String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    

}