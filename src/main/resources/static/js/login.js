var btn = document.getElementById('btn')

function leftClick() {
    btn.style.left = '0px';
    $(".login-container").css("display", "block");
    $(".signin-container").css("display", "none");
}

function rightClick() {
    btn.style.left = '110';
    $(".signin-container").css("display", "block");
    $(".login-container").css("display", "none");
}