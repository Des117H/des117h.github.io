import { initializeApp } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-app.js";
import { collection, query, where, getFirestore, getDocs } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-firestore.js";
import { getStorage, ref, getDownloadURL } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-storage.js";
import { btnSave } from "./edit2.js";

const firebaseConfig = {
  apiKey: "AIzaSyCZ52ZecgR_HY-AJ5oSisUdm2SPhoYEZ40",
  authDomain: "architecture-grandma-bea3b.firebaseapp.com",
  databaseURL: "https://architecture-grandma-bea3b-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "architecture-grandma-bea3b",
  storageBucket: "architecture-grandma-bea3b.appspot.com",
  messagingSenderId: "390073017040",
  appId: "1:390073017040:web:0ce22fda1c9c80d2c3f4d5"
};

const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

const storage = getStorage();

// AUTHENTICATION
btnSave.addEventListener("click",saveToFirebase);
// Update and save the file edited
function saveToFirebase() {
    // Get the content from the Quill editor
    console.log("function co chay");
    const editorContent = quill.root.innerHTML;
  
    console.log(editorContent);
  
    // Create a Blob from the content
    const blob = new Blob([editorContent], { type: 'text/html' });
  
    // Generate a unique file name or use your own logic
    const fileName = fileID;
  
    // Reference to the Firebase Storage root
    const storageRef = storage.storageRef();
  
    // Reference to the file in Firebase Storage
    const fileRef = storageRef.child(fileName);
  
    // Upload the file to Firebase Storage
    fileRef.put(blob).then((snapshot) => {
        console.log(snapshot);
        console.log('File uploaded successfully!');
    }).catch((error) => {
      console.error('Error uploading file:', error);
    });
  }