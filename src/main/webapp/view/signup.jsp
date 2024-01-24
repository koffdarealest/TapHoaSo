<%--
  Created by IntelliJ IDEA.
  User: Tung
  Date: 1/20/2024
  Time: 10:50 PM
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

    <title>Sign up</title>

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
                        <img src="" alt=""
                             style="width: 158px;">
                    </a>
                    <!-- ***** Logo End ***** -->
                    <!-- ***** Menu Start ***** -->
                    <ul class="nav">
                        <li><a href="index.html">##</a></li>
                        <li><a href="shop.html">###</a></li>
                        <li><a href="product-details.html">####</a>
                        </li>
                        <li><a href="contact.html">Contact Us</a>
                        </li>
                        <li><a href="signin">Sign In</a></li>
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
                <h2 class="m-0">SIGN UP</h2>
            </div>
            <form action="signup" method="post" name="signup" id="captcha">
                <div class="form-group mb-3">
                    <label class="label" >Fullname</label>
                    <input type="text" class="form-control" placeholder="Fullname"
                           required name="fullname">
                </div>
                <div class="form-group mb-3">
                    <label class="label" >Gmail</label>
                    <input type="email" class="form-control" placeholder="Email"
                           required name="email">
                </div>
                <div class="form-group mb-3">
                    <label class="label" >Username</label>
                    <input type="text" class="form-control" placeholder="Username"
                           required name="username">
                </div>
                <div class="form-group mb-4">
                    <label class="label" >Password</label>
                    <input type="password" class="form-control" placeholder="Password"
                           required name="password">
                </div>
                <div class="form-group mb-4">
                    <label class="label">Re-Password</label>
                    <input type="password" class="form-control" placeholder="Re-password"
                           required name="re-password">
                </div>
                <div class="g-recaptcha" data-sitekey="6LeIV1gpAAAAAN-g1_A6MNU4BsbewNhjMD8i0lxq"></div>
                <div class="mb-2" id="error"></div>
                <h6 class="text-danger mb-2">${mess}</h6>
                <div class="form-group mb-3 text-center">
                    <button type="submit" class="col-lg-8 btn btn-primary btn-lg">SIGN UP</button>
                </div>
            </form>
            <p class="text-center" style="font-size: 15px;">Already have an account?  <a data-toggle="tab"
                                                                                         href="signin">Back to Sign In</a></p>
        </div>
    </div>
</div>

<footer>
    <div class="container">
        <div class="row justify-content-center">
            <div class="d-md-flex col-lg-12 align-self-center">
                <p class="w-50" style="font-weight: bold; font-size: 110%; margin-left: auto; margin-top: 10px;">Powered by: TapHoaSo © 2024.</p>
                <p class="w-50" style="font-weight: bold; font-size: 110%; margin-right: auto; margin-top: 10px;">Email Contact: taphoaso@gmail.com</p>
            </div>
        </div>
    </div>
</footer>

<!-- Scripts -->
<script>
    window.onload = function (){
        let isValid = false;
        const form = document.getElementById("captcha");
        const error = document.getElementById("error");

        form.addEventListener("submit", function (event){
            event.preventDefault();
            const response = grecaptcha.getResponse();
            if (response){
                form.submit();
            } else {
                error.innerHTML = "Please enter captcha! ";
            }
        });
    }
</script>
<!-- Bootstrap core JavaScript -->
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/isotope.min.js"></script>
<script src="../assets/js/owl-carousel.js"></script>
<script src="../assets/js/counter.js"></script>
<script src="../assets/js/custom.js"></script>


</body>

</html>