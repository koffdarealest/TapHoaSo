
function createCaptcha() {
    document.getElementById('captcha').innerHTML = "";
    var charsArray =
        "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    var lengthOtp = 6;
    var captcha = [];
    for (var i = 0; i < lengthOtp; i++) {
        //below code will not allow Repetition of Characters
        var index = Math.floor(Math.random() * charsArray.length + 1); //get the next character from the array
        if (captcha.indexOf(charsArray[index]) == -1)
            captcha.push(charsArray[index]);
        else i--;
    };
    var canvas = document.createElement("canvas");
    canvas.id = "captcha";
    canvas.width = 200;
    canvas.height = 50;
    var ctx = canvas.getContext("2d");
    ctx.font = "35px Georgia";
    ctx.padding ="12px"
    ctx.strokeText(captcha.join(""), 0, 30);
    canvas.style.letterSpacing = 5 + 'px';
    //storing captcha so that can validate you can save it somewhere else according to your specific requirements
    code = captcha.join("");
    document.getElementById("captcha").appendChild(canvas);
    // document.getElementById("captchaa") = code;
}

function validateCaptcha() {
    let inputCaptcha = document.getElementById("inputCaptcha").value;
    if (inputCaptcha == code) {
        document.getElementById("inputCaptcha").value = "";
        return true;
    } else {
        document.getElementById("error").innerText = "Invalid Captcha! Please try again!";
        return false;
    }
}
// function validateCaptcha() {
//     if (document.getElementById("captchaText").value == code) {
//         alert("Valid Captcha")
//         document.getElementById("captchaText").value =""
//     } else {
//         alert("Invalid Captcha. try Again");
//         createCaptcha();
//     }
// }

// function validateCaptcha() {
//     var captchaInput = document.getElementById("inputCaptcha").value;
//     var actualCaptcha = document.getElementById("captcha").innerText;
//
//     if (captchaInput === actualCaptcha) {
//         console.log("Captcha right");
//         return true; // Captcha đúng, cho phép gửi yêu cầu đăng nhập
//     } else {
//         document.getElementById("error").innerText = "Invalid Captcha. Please try again.";
//         console.log("Captcha wrong");
//         return false; // Captcha không đúng, ngăn chặn gửi yêu cầu đăng nhập
//     }
// }
function resetCaptcha(event) {
    event.preventDefault(); // Ngăn chặn hành vi mặc định của button (submit form)
    createCaptcha(); // Gọi hàm tạo mới captcha ở đây
}
