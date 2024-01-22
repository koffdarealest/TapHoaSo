<%--
  Created by IntelliJ IDEA.
  User: Tung
  Date: 1/20/2024
  Time: 3:02 PM
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

    <title>Sign in</title>

    <!-- Bootstrap core CSS -->
    <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="../assets/css/fontawesome.css">
    <link rel="stylesheet" href="../assets/css/templatemo-lugx-gaming.css">
    <link rel="stylesheet" href="../assets/css/owl.css">
    <link rel="stylesheet" href="../assets/css/animate.css">
    <link rel="stylesheet" href="../assets/css/style.css">
    <link rel="stylesheet" href="../assets/css/swiper-bundle.min.css"/>
</head>


<body>

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
                        <img src="../assets/images/logo.png" alt="" style="width: 158px;">
                    </a>
                    <!-- ***** Logo End ***** -->
                    <!-- ***** Menu Start ***** -->
                    <ul class="nav">
                        <li><a href="index.html">Home</a></li>
                        <li><a href="shop.html">Our Shop</a></li>
                        <li><a href="product-details.html">Product Details</a>
                        </li>
                        <li><a href="contact.html" class="active">Contact Us</a>
                        </li>
                        <li><a href="#">Sign In</a></li>
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
            </div>
        </div>
    </div>
</div>

<div class="row justify-content-center">
    <div class="login-wrap p-4 p-md-5 col-lg-4">
        <div class="col-lg-10 mx-auto">
            <div class="card-header text-center p-3 mb-4">
                <h2 class="m-0">SIGN IN</h2>
            </div>
            <form action="login" method="post">
                <div class="form-group mb-3">
                    <label class="label">Username</label>
                    <input type="text" class="form-control" placeholder="Username" required name="username">
                </div>
                <div class="form-group mb-4">
                    <label class="label">Password</label>
                    <input type="password" class="form-control" placeholder="Password" required name="password">
                </div>
                <h6 class="text-danger">${error}</h6>
                <div class="form-group mb-3 text-center">
                    <button type="submit" class="col-lg-8 btn btn-primary btn-lg">Sign In</button>
                </div>
                <div class="form-group d-md-flex mb-3">
                    <div class="w-50 text-left">
                        <label class="">Remember Me <input type="checkbox" name="remember"><span class="checkmark"></span></label>
                    </div>
                    <div class="w-50" style="text-align: end;">
                        <a href="#">Forgot Password</a>
                    </div>
                </div>
            </form>
            <p class="text-center" style="font-size: 15px;">Not a member? <a data-toggle="tab" href="signup">Sign
                Up Here</a></p>
        </div>
    </div>
</div>

<footer>
    <div class="container">
        <div class="col-lg-12">
            <p>Copyright © 2048 LUGX Gaming Company. All rights reserved. &nbsp;&nbsp; <a rel="nofollow"
                                                                                          href="https://templatemo.com"
                                                                                          target="_blank">Design:
                TemplateMo</a></p>
        </div>
    </div>
</footer>

<!-- Scripts -->
<!-- Bootstrap core JavaScript -->
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/isotope.min.js"></script>
<script src="../assets/js/owl-carousel.js"></script>
<script src="../assets/js/counter.js"></script>
<script src="../assets/js/custom.js"></script>

</body>

</html>
