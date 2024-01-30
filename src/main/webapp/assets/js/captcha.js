
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
};

function validateCaptcha() {
    var inputCaptcha = document.getElementById("inputCaptcha").value;

    if (inputCaptcha == code) {
        document.getElementById("inputCaptcha").value = "";
        alert("Captcha Matched");
        return true;
    } else {
        document.getElementById("error").innerText = "Invalid Captcha. Please try again.";
        alert("Captcha did not match")
        return false;
    }
}

function resetCaptcha(event) {
    event.preventDefault(); // Ngăn chặn hành vi mặc định của button (submit form)
    createCaptcha(); // Gọi hàm tạo mới captcha ở đây
}