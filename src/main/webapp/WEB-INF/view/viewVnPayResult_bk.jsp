<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="util.Config" %>
<%@ page import="dao.UserDAO" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
          rel="stylesheet">

    <title>TapHoaSo</title>

    <!-- Bootstrap core CSS -->
    <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="../assets/css/fontawesome.css">
    <link rel="stylesheet" href="../assets/css/templatemo-lugx-gaming.css">
    <link rel="stylesheet" href="../assets/css/owl.css">
    <link rel="stylesheet" href="../assets/css/animate.css">
    <link rel="stylesheet" href="../assets/css/style.css">
    <link rel="stylesheet" href="../assets/css/swiper-bundle.min.css"/>
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

<!-- ***** Header Area Start ***** -->
<header class="header-area header-sticky">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <nav class="main-nav">
                    <!-- ***** Logo Start ***** -->
                    <a href="index.html" class="logo">
                        <img src="" alt="" style="width: 158px;">
                    </a>
                    <!-- ***** Logo End ***** -->
                    <!-- ***** Menu Start ***** -->
                    <ul class="nav">
                        <li><a href="home">Home</a></li>
                        <li><a href="market">Public market</a></li>
                        <li><a href="sellingPost">Selling posts</a></li>
                        <li><a href="">Your Balance ${user.balance}VND</a></li>
                        <li><a href="signOut">Sign Out</a></li>
                    </ul>
                    <a class='menu-trigger'>
                        <span>Menu</span>
                    </a>
                    <!-- ***** Menu End ***** -->
                </nav>
            </div>
        </div>
    </div>
</header>
<!-- ***** Header Area End ***** -->

<div class="page-heading header-text mb-5">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <h3>deposit</h3>

            </div>
        </div>
    </div>
</div>

<div class="row justify-content-center">
    <div class="sell-wrap p-4 col-lg-6">
        <div class="col-lg-12 mx-auto">
            <div class="card">
                <div class="card-header">
                    <div class="pull-left">
                        <h4 class="card-title mt-2">VNPAY RESPONSE</h4>
                    </div>
                </div>
                <div class="card-body">

                    <div class="d-flex mb-3 align-items-center">
                        <div class="label-form col-md-4"><label class="label">Merchant Transaction Code:</label></div>
                        <div class="col-md-8"><input type="text" name="title"
                                                     value="<%=request.getParameter("vnp_TxnRef")%>"
                                                     class="form-control" readonly>      <!-- input Title --></div>
                    </div>

                    <div class="d-flex mb-3 align-items-center">
                        <div class="label-form col-md-4"><label class="label">Amount:</label></div>
                        <div class="col-md-8"><input type="text" name="title"
                                                     value="<%=Long.parseLong(request.getParameter("vnp_Amount")) / 100%>"
                                                     class="form-control" readonly>      <!-- input Title --> </div>
                    </div>

                    <div class="d-flex mb-3 align-items-center">
                        <div class="label-form col-md-3"><label class="label">Order info:</label></div>
                        <div class="col-md-9"><input type="text" name="title"
                                                     value="<%=request.getParameter("vnp_OrderInfo")%>"
                                                     class="form-control" readonly>      <!-- input Title --> </div>
                    </div>

                    <div class="d-flex mb-3 align-items-center">
                        <div class="label-form col-md-3"><label class="label">VNPAY Response Code:</label></div>
                        <div class="col-md-9"><input type="text" name="title"
                                                     value="<%=request.getParameter("vnp_ResponseCode")%>"
                                                     class="form-control" readonly>      <!-- input Title --> </div>
                    </div>

                    <div class="d-flex mb-3 align-items-center">
                        <div class="label-form col-md-4"><label class="label">VNPAY Transaction Code:</label></div>
                        <div class="col-md-8"><input type="text" name="title"
                                                     value="<%=request.getParameter("vnp_TransactionNo")%>"
                                                     class="form-control" readonly>      <!-- input Title --> </div>
                    </div>

                    <div class="d-flex mb-3 align-items-center">
                        <div class="label-form col-md-3"><label class="label">Bank Code:</label></div>
                        <div class="col-md-9"><input type="text" name="title"
                                                     value="<%=request.getParameter("vnp_BankCode")%>"
                                                     class="form-control" readonly>      <!-- input Title --> </div>
                    </div>

                    <div class="d-flex mb-3 align-items-center">
                        <div class="label-form col-md-3"><label class="label">Pay Date:</label></div>
                        <div class="col-md-9"><input type="text" name="title"
                                                     value="<%=request.getParameter("vnp_PayDate")%>"
                                                     class="form-control" readonly>      <!-- input Title --> </div>
                    </div>

                    <div class="d-flex mb-3 align-items-center">
                        <div class="label-form col-md-3"><label class="label">Pay Date:</label></div>
                        <div class="col-md-9"><input type="text" name="title"
                                                     value="<%=request.getParameter("vnp_TransactionStatus")%>"
                                                     class="form-control" readonly>      <!-- input Title --> </div>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<footer>
    <div class="container">
        <div class="row justify-content-center">
            <div class="d-md-flex col-lg-12 align-self-center">
                <p class="w-50"
                   style="font-weight: bold; font-size: 110%; margin-left: auto; margin-top: 10px;">Powered
                    by: TapHoaSo © 2024.</p>
                <p class="w-50"
                   style="font-weight: bold; font-size: 110%; margin-right: auto; margin-top: 10px;">Email
                    Contact: taphoaso@gmail.com</p>
            </div>
        </div>
    </div>

</footer>
<!-- Bootstrap core JavaScript -->

<!-- Bootstrap core JavaScript -->
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/isotope.min.js"></script>
<script src="../assets/js/owl-carousel.js"></script>
<script src="../assets/js/counter.js"></script>
<script src="../assets/js/custom.js"></script>


</body>

</html>