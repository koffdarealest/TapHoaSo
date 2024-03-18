<%--
  Created by IntelliJ IDEA.
  User: Tung
  Date: 2/5/2024
  Time: 11:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
          rel="stylesheet">

    <title>Sell Product</title>

    <!-- Bootstrap core CSS -->
    <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="../../assets/css/fontawesome.css">
    <link rel="stylesheet" href="../../assets/css/templatemo-lugx-gaming.css">
    <link rel="stylesheet" href="../../assets/css/owl.css">
    <link rel="stylesheet" href="../../assets/css/animate.css">
    <link rel="stylesheet" href="../../assets/css/style.css">
    <link rel="stylesheet" href="../../assets/css/swiper-bundle.min.css"/>
    <script src="https://cdn.tiny.cloud/1/qmw4wavlc4ekzay2c6m9pxxoyvi1ni12vki7sz9clkyfyyo2/tinymce/6/tinymce.min.js"
            referrerpolicy="origin"></script>


</head>


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
                        <li><a href="sellingPost">Selling posts</a>
                        </li>
                        <li><a href="">Contact Us</a>
                        </li>
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
                <h3>Deposit</h3>

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
                        <h4 class="card-title mt-2">New payment</h4>
                    </div>
                </div>
                <div class="card-body">
                    <p class="text-muted mb-2">(*): Required</p>
                    <form action="payment" id="frmCreateOrder" method="post">

                        <!-- -------------Total Amount------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Amount (=>1000) (*)</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <input type="text" name="amount" class="form-control" required id="price">   <!-- input Amount -->
                                <span class="text-muted"
                                      style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">VND</span>
                            </div>
                        </div>

                        <!-- --------------payment method -------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">payment method (*)</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <select class="form-control" id="bankCode" name="bankCode" required> <!-- select payment method -->
                                    <option value="VNBANK">Payment portal VNPAYBANK</option>
                                    <option value="VNPAYQR">Payment portal VNPAYQR</option>
                                    <option value="INTCARD">Payment portal International Card</option>
                                </select>
                            </div>
                        </div>
                        <!-- --------------payment method -------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">LANGUAGE (*)</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <select class="form-control" id="language" name="language" required> <!-- select payment method -->
                                    <option value="vn">Vietnamese</option>
                                    <option value="en">English</option>
                                </select>
                            </div>
                        </div>

                        <!-- ---------------Submit--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="form-group col-md-12 text-center">
                                <button type="submit" class="col-md-3 btn btn-primary p-3">Submit Payment</button>
                                <!-- Submit -->
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<footer>
    <div class="container">
        <div class="row justify-content-center">
            <div class="d-md-flex col-lg-12 align-self-center">
                <p class="w-50" style="font-weight: bold; font-size: 110%; margin-left: auto; margin-top: 10px;">Powered
                    by: TapHoaSo © 2024.</p>
                <p class="w-50" style="font-weight: bold; font-size: 110%; margin-right: auto; margin-top: 10px;">Email
                    Contact: taphoaso391@gmail.com</p>
            </div>
        </div>
    </div>
</footer>

<!-- Scripts -->
<script>
    var price = document.getElementById('price');
    var fee = document.getElementById('fee');
    var priceError = document.getElementById('priceError');
    var priceErrorBackend = document.getElementById('priceErrorBackend');
    price.addEventListener('input', function () {
        var priceValue = parseInt(price.value);
        if (priceValue % 1000 === 0 && priceValue >= 1000) {
            fee.style.display = 'block';
            priceError.style.display = 'none';
            var feeValue = priceValue * 0.05;
            fee.value = feeValue;
        } else {
            fee.value = 0;
            priceError.style.display = 'block';
            priceErrorBackend.style.display = 'none';
        }
    });

    price.addEventListener('input', function () {
        price.value = price.value.replace(/[^0-9]/g, '');
    });

    // document.getElementById('sellForm').addEventListener('submit', function (e) {
    //     var checkBox = document.getElementById('confirmCheckBox');
    //     if (!checkBox.checked) {
    //         e.preventDefault();
    //         var confirmAlert = document.getElementById('confirmAlert');
    //         confirmAlert.value= 'You must confirm before posting';
    //         confirmAlert.style.display = 'block';
    //     } else {
    //         confirmAlert.style.display = 'none';
    //     }
    // });
</script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        tinymce.init({
            selector: '#description',
            setup: function (editor) {
                editor.on('change', function () {
                    editor.save();
                });
            }
        });

        tinymce.init({
            selector: '#hidden',
            setup: function (editor) {
                editor.on('change', function () {
                    editor.save();
                });
            }
        });
    });
</script>
<!-- Bootstrap core JavaScript -->
<script src="../../vendor/jquery/jquery.min.js"></script>
<script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../../assets/js/isotope.min.js"></script>
<script src="../../assets/js/owl-carousel.js"></script>
<script src="../../assets/js/counter.js"></script>
<script src="../../assets/js/custom.js"></script>


</body>

</html>