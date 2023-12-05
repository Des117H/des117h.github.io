import { initializeApp } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-app.js";
import { getAuth, signInWithEmailAndPassword, createUserWithEmailAndPassword } from 'https://www.gstatic.com/firebasejs/10.7.0/firebase-auth.js';
import { getStorage, ref, getDownloadURL } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-storage.js";
import { collection, query, where, getFirestore, getDocs } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-firestore.js";
import { btnLogin } from './login.js'

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

// AUTHENTICATION
const auth = getAuth(app);

const loginEmailPassword = async () => {
  const loginEmail = txtEmail.value;
  const loginPassword = txtPassword.value;
  console.log(loginEmail + " + " + loginPassword);
  try {
    const userCredential = await signInWithEmailAndPassword(auth, loginEmail, loginPassword);
    const userID = userCredential.user.uid;

    const q = query(collection(db, "User_account"), where("documentId", "==", userID));

    const querySnapshot = await getDocs(q);
    querySnapshot.forEach((doc) => {
      let userData = doc.data();
      const signUpEmail = userData.email;
      const signUpFirstName = userData.firstName;
      const signUpLastName = userData.lastName;
      const signUpPhone = userData.phone;
      
      addDataToLocalStorage(userID, signUpEmail, signUpFirstName, signUpLastName, signUpPhone)
      window.location.replace("http://localhost:8080/homepage");
    });
  }
  catch (error) {
    console.log(error);
  }
}

const signUpEmailPassword = async () => {
  const signUpEmail = emailSignUp.value;
  const signUpPassword = passwordSignUp.value;
  const signUpFirstName = firstNameSignUp.value;
  const signUpLastName = lastNameSignUp.value;
  const signUpPhone = phoneSignUp.value;
  console.log(signUpEmail + " + " + signUpPassword);
  try {
    console.log(auth);
    const userCredential = await createUserWithEmailAndPassword(auth, signUpEmail, signUpPassword);
    console.log(userCredential.user.uid);
    const userID = userCredential.user.uid;

    if (userID != null) {
      const formData = appendFormData(userID, signUpEmail, signUpFirstName, signUpLastName, signUpPhone);

      const response = await fetch("/create", {
        method: "POST",
        body: formData,
      })
        .then((response) => response.json())
        .then((data) => {
          addDataToLocalStorage(userID, signUpEmail, signUpFirstName, signUpLastName, signUpPhone)

          // Retrieve the object from storage
          // var retrievedObject = localStorage.getItem('testObject');
          // console.log('retrievedObject: ', JSON.parse(retrievedObject));
          window.location.replace("http://localhost:8080/homepage");
        })
        .catch((error) => {
          console.error('Upload failed:', error);
        });

      console.log(response);
    }
  }
  catch (error) {
    console.log(error);
  }
}

function appendFormData(userID, signUpEmail, signUpFirstName, signUpLastName, signUpPhone) {
  const formData = new FormData();
  formData.append("documentId", userID);
  formData.append("email", signUpEmail);
  formData.append("firstName", signUpFirstName);
  formData.append("lastName", signUpLastName);
  formData.append("phoneNumber", signUpPhone);
  return formData;
}

function addDataToLocalStorage(userID, signUpEmail, signUpFirstName, signUpLastName, signUpPhone) {
  var user = {
    "documentId": userID,
    "email": signUpEmail,
    "firstName": signUpFirstName,
    "lastName": signUpLastName,
    "phoneNumber": signUpPhone
  };
  localStorage.setItem('userData', JSON.stringify(user));
}

// AUTHENTICANTION - functions
btnLogin.addEventListener("click", loginEmailPassword);
btnSignUp.addEventListener("click", signUpEmailPassword);

// STORAGE
const storage = getStorage();
getDownloadURL(ref(storage, '0186e0bf-2e30-4d1d-b23b-f72bf7520fbc|1|2023-11-30_17:12:1.docx'))
  .then((url) => {
    // `url` is the download URL for 'images/stars.jpg'
    const storage = getStorage();
    const starsRef = ref(storage, '0186e0bf-2e30-4d1d-b23b-f72bf7520fbc|1|2023-11-30_17:12:1.docx');
    // Get the download URL
    getDownloadURL(starsRef)
      .then((url) => {
        console.log(url)
      })
      .catch((error) => {
        // A full list of error codes is available at
        // https://firebase.google.com/docs/storage/web/handle-errors
        switch (error.code) {
          case 'storage/object-not-found':
            // File doesn't exist
            break;
          case 'storage/unauthorized':
            // User doesn't have permission to access the object
            break;
          case 'storage/canceled':
            // User canceled the upload
            break;

          // ...

          case 'storage/unknown':
            // Unknown error occurred, inspect the server response
            break;
        }
      });
  })
  .catch((error) => {
    // Handle any errors
  });


