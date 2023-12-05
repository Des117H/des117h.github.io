
export const txtUsername = document.querySelector("#txtUsername")
export const txtPassword = document.querySelector("txtPassword")

export const btnLogin = document.querySelector("#btnLogin")
export const btnSignUp = document.querySelector("#btnSignUp")
export const btnLogOut = document.querySelector("#btnLogOut")

let eyeicon = document.getElementById("eye-icon");
let password = document.getElementById("txtPassword")
eyeicon.onclick = function () {
    if (password.type == "password") {
        password.type = "text";
        eyeicon.src = "/src/main/resources/static/image/eye-open.png"
    }
    else {
        password.type = "password";
        eyeicon.src = "/src/main/resources/static/image/eye-close.png"

    }
}