import { initializeApp } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-app.js";
import {getAuth, signInWithEmailAndPassword} from 'https://www.gstatic.com/firebasejs/10.7.0/firebase-auth.js';
import { getStorage, ref, getDownloadURL } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-storage.js";

import { btnLogin} from './login.js'

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

// AUTHENTICANTION
const auth = getAuth(app);
const loginUsernamePassword = async () => {
    const loginUsername = txtUsername.value + "@gmail.com";
    const loginPassword = txtPassword.value;
    console.log(loginUsername);
    try{
        const userCredetial = await signInWithEmailAndPassword(auth, loginUsername, loginPassword);
        console.log(userCredetial.user);
    }
    catch(error){
        console.log(error);
    }
}

 
// AUTHENTICANTION - functions
btnLogin.addEventListener("click", loginUsernamePassword);

// STORAGE
const storage = getStorage();
getDownloadURL(ref(storage, 'images/stars.jpg'))
.then((url) => {
    // `url` is the download URL for 'images/stars.jpg'

    // This can be downloaded directly:
    const xhr = new XMLHttpRequest();
    xhr.responseType = 'blob';
    xhr.onload = (event) => {
    const blob = xhr.response;
    };
    xhr.open('GET', url);
    xhr.send();

    // Or inserted into an <img> element
    const img = document.getElementById('myimg');
    img.setAttribute('src', url);
})
.catch((error) => {
    // Handle any errors
});

