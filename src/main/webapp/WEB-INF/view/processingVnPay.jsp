<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="util.Config" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TapHoaSo</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
          rel="stylesheet">
    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-lugx-gaming.css">
    <link rel="stylesheet" href="assets/css/owl.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/swiper-bundle.min.css"/>
    <script src="https://cdn.tiny.cloud/1/qmw4wavlc4ekzay2c6m9pxxoyvi1ni12vki7sz9clkyfyyo2/tinymce/6/tinymce.min.js"
            referrerpolicy="origin"></script>
</head>
<body>
<%
    //Begin process return from VNPAY
    Map fields = new HashMap();
    for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
        String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
        String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
        if ((fieldValue != null) && (fieldValue.length() > 0)) {
            fields.put(fieldName, fieldValue);
        }
    }

    String vnp_SecureHash = request.getParameter("vnp_SecureHash");
    if (fields.containsKey("vnp_SecureHashType")) {
        fields.remove("vnp_SecureHashType");
    }
    if (fields.containsKey("vnp_SecureHash")) {
        fields.remove("vnp_SecureHash");
    }
    String signValue = Config.hashAllFields(fields);

%>
<!-- ***** Preloader Start ***** -->
<div id="js-preloader" class="js-preloader">
    <div class="preloader-inner">
        <span class="dot"></span>
        <div class="dots">
            <span></span>
            <span></span>
            <span></span>
        </div>
    </div>
</div>
<!-- ***** Preloader End ***** -->
<header class="header-area header-sticky">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <nav class="main-nav">
                    <a href="index.html" class="logo">

                    </a>
                    <ul class="nav">
                        <li><a href="home">Home</a></li>
                        <li><a href="market">Public market</a></li>
                        <li><a href="sellingPost">Selling posts</a>
                        </li>
                        <li><a href="buyingPost">Buying posts</a>
                        </li>
                        <li><a href="">Contact Us</a>
                        </li>
                        <li><a href="signOut">Sign Out</a></li>
                    </ul>
                    <a class='menu-trigger'>
                        <span>Menu</span>
                    </a>
                </nav>
            </div>
        </div>
    </div>
</header>
<div class="page-heading header-text mb-5">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <h3>Edit Profile</h3>
            </div>
        </div>
    </div>
</div>
<div class="row justify-content-center">
    <div class="col-lg-5">
        <div class="sell-wrap p-4">
            <div class="col-lg-12 mx-auto">
                <div class="card">
                    <div class="card-header">
                        <div class="pull-left">
                            <h4 class="card-title mt-2">User information</h4>
                        </div>
                    </div>
                    <div class="card-body">
                        <form action="processVnpayResponse" method="post">
                            <input type="hidden" name="vnp_TxnRef" value="<%=request.getParameter("vnp_TxnRef")%>">
                            <input type="hidden" name="vnp_Amount" value="<%=request.getParameter("vnp_Amount")%>">
                            <input type="hidden" name="vnp_OrderInfo" value="<%=request.getParameter("vnp_OrderInfo")%>">
                            <input type="hidden" name="vnp_ResponseCode" value="<%=request.getParameter("vnp_ResponseCode")%>">
                            <input type="hidden" name="vnp_TransactionNo" value="<%=request.getParameter("vnp_TransactionNo")%>">
                            <input type="hidden" name="vnp_BankCode" value="<%=request.getParameter("vnp_BankCode")%>">
                            <input type="hidden" name="vnp_PayDate" value="<%=request.getParameter("vnp_PayDate")%>">
                            <input type="hidden" name="vnp_TransactionStatus" value="<%=request.getParameter("vnp_TransactionStatus")%>">
                            <!-- Thêm các hidden input fields khác nếu cần -->
                            <input type="submit" value="Submit">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


    </div>
</div>
<footer>
    <div class="container">
        <div class="row justify-content-center>">
            <div class="d-md-flex col-lg-12 align-self-center">
                <p class="w-50" style="font-weight: bold; font-size: 110%; margin-left: auto; margin-top: 10px;">Powered
                    by: TapHoaSo © 2024.</p>
                <p class="w-50" style="font-weight: bold; font-size: 110%; margin-right: auto; margin-top: 10px;">Email
                    Contact: taphoaso@gmail.com</p>
            </div>
        </div>
    </div>
</footer>

<script>
    function reloadCaptcha() {
        var timestamp = new Date().getTime();
        var captchaImage = document.getElementById('captchaImage');
        captchaImage.src = 'generateCaptcha?' + timestamp;
    }

    function resetCaptcha(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của button (submit form)
        reloadCaptcha(); // Gọi hàm tạo mới captcha ở đây
    }

    document.addEventListener("DOMContentLoaded", function () {
        var form = document.getElementById("changePasswordForm");
        var sendButton = document.querySelector("#form [type=submit]");

        form.addEventListener("keypress", function (event) {
            if (event.key === "Enter") {
                event.preventDefault();
                sendButton.click();
            }
        });
    });
</script>

<script>
    document.getElementById("submitBtn").addEventListener("click", function(event) {
        var password = document.getElementById("password").value;
        var rePassword = document.getElementById("repassword").value;
        var message = document.getElementById("message");

        if (password !== rePassword) {
            message.innerHTML = "Passwords do not match";
            event.preventDefault();
        } else {
            message.innerHTML = "";
        }
    });
</script>
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/isotope.min.js"></script>
<script src="assets/js/owl-carousel.js"></script>
<script src="assets/js/counter.js"></script>
<script src="assets/js/custom.js"></script>
</body>
</html>
