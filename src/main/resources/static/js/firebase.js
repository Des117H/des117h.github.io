import { initializeApp } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-app.js";

import { btnLogin} from './login.js'

import {getAuth, signInWithEmailAndPassword} from 'https://www.gstatic.com/firebasejs/10.7.0/firebase-auth.js';


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

btnLogin.addEventListener("click", loginUsernamePassword);