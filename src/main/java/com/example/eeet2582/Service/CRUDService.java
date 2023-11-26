package com.example.eeet2582.Service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.eeet2582.Model.CRUD;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class CRUDService {

    public String createCRUD(CRUD crud) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("User_account").document(crud.getName()).set(crud);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public CRUD getCRUD(String documentId) throws ExecutionException, InterruptedException{
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("User_account").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        CRUD crud;
        if (document.exists()) {
            crud = document.toObject(CRUD.class);
            return crud;
        }
        return null;
    }

    public String updateCRUD(CRUD crud) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("User_account").document(crud.getName()).set(crud);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteCRUD(String documentId) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("User_account").document(documentId).delete();
        return "Successfully deleted " + documentId;
    }

    
}