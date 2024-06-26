<%--
  Created by IntelliJ IDEA.
  User: Tung
  Date: 3/22/2024
  Time: 8:25 PM
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
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-lugx-gaming.css">
    <link rel="stylesheet" href="assets/css/owl.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/swiper-bundle.min.css"/>



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
                    <!-- ***** Menu Start ***** -->
                    <ul class="nav">
                        <li><a href="home">Home</a></li>
                        <li><a href="market">Public market</a></li>
                        <li><a href="sellingPost">Selling posts</a>
                        </li>
                        <li><a href="transactionHistory" id="money">${user.balance}</a></li>
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
                <h3>WITHDRAW</h3>

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
                        <h4 class="card-title mt-2">New withdraw request</h4>
                    </div>
                </div>
                <div class="card-body">
                    <form action="withdraw" id="frmCreateOrder" method="post">
                        <!-- -------------Total Amount------------- -->
                        <div class="d-flex mb-4 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Amount (=>10000) (*)</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <input placeholder="The amount you need to withdraw" type="text" name="amount" class="form-control" required id="amount">
                                <p class="text-danger" id="priceError"></p>
                            </div>
                        </div>
                        <!-- ---------------Bank Account--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Bank Account (*)</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <textarea placeholder="Your Bank Account" class="form-control" name="bankingInfo" required></textarea>
                                <!-- input Contact -->
                            </div>
                        </div>
                        <p class="text-muted mb-3">Write bank information in the form "Bank - account number - owner name". Wrong or confusingly written bank accounts mean LOSE money when the admin processes your withdrawal!</p>
                        <!-- ---------------Error field--------------- -->
                        <h6 class="text-danger mb-2">${error}</h6>
                        <!-- ---------------Submit--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="form-group col-md-12 text-center">
                                <button type="submit" class="col-md-3 btn btn-primary p-3 button-border">Withdraw</button>
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
    withdrawAmount = document.getElementById('amount');
    priceError = document.getElementById('priceError');
    withdrawAmount.addEventListener('input', function () {
        withdrawAmount.value = withdrawAmount.value.replace(/[^0-9]/g, '');
        if(withdrawAmount.value < 10000){
            priceError.innerHTML = "The amount must be greater than 10000";
        } else {
            priceError.innerHTML = "";
        }
    });
</script>
<script>
    document.querySelectorAll("[id^='money']").forEach(function (cell) {
        var totalSpend = cell.textContent;
        var formattedTotalSpend = new Intl.NumberFormat("vi-VN", {
            style: "currency",
            currency: "VND"
        }).format(totalSpend);
        cell.textContent = formattedTotalSpend;
    });
</script>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/isotope.min.js"></script>
<script src="assets/js/owl-carousel.js"></script>
<script src="assets/js/counter.js"></script>
<script src="assets/js/custom.js"></script>


</body>

</html>
