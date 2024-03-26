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
                        <li><a href="buyingPost">Buying posts</a>
                        </li>
                        <li><a href="" id="money">${user.balance}</a></li>
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
                <h3>Sell Product</h3>

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
                        <h4 class="card-title mt-2">Product post information</h4>
                    </div>
                </div>
                <div class="card-body">
                    <p class="text-muted mb-2">(*): Required</p>
                    <form action="sell" method="post" id="sellForm">
                        <!-- ---------------Title--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Title (*)</label>
                            </div>
                            <div class="col-md-9">
                                <input type="text" name="title" class="form-control" required>      <!-- input Title -->
                            </div>
                        </div>
                        <!-- ---------------Price--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Price (=>1000) (*)</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <input type="text" name="price" class="form-control" required id="price">
                                <!-- input Price -->
                                <span class="text-muted"
                                      style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">VND</span>
                            </div>
                        </div>
                        <!-- ---------------Price Error--------------- -->
                        <div class="d-flex align-items-center">
                            <div class="col-md-10" id="priceError" style="color: red; display: none;text-align: end;">
                                Price must be divisible by 1000
                            </div>
                        </div>
                        <div class="d-flex mb-3 align-items-center">
                            <div class="col-md-10" id="priceErrorBackend" style="color: red; text-align: end;">
                                ${priceError}
                            </div>
                        </div>
                        <!-- ---------------Fee--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Fee (5% of Price)</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <input type="number" name="fee" class="form-control" required readonly id="fee" style="font-style: oblique">
                                <!-- input Fee -->
                                <span class="text-muted"
                                      style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">VND</span>
                            </div>
                        </div>
                        <!-- ---------------Fee Payer--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Fee Payer (*)</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <select class="form-control" name="feePayer" required id="feePayer"> <!-- select Fee Payer -->
                                    <option value="half">Half - Half</option>
                                    <option value="seller">Seller</option>
                                    <option value="buyer">Buyer</option>
                                </select>
                            </div>
                        </div>
                        <!-- ---------------Total--------------- -->
                        <div class="d-flex mb-3 align-items-center" >
                            <div class="label-form col-md-3">
                                <label class="label">Total receive</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <input name="price" class="form-control"
                                       value="${chosenPost.totalReceiveForSeller}" id="total" style="font-weight: bold">
                                <span class="text-muted"
                                      style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">VND</span>
                            </div>
                        </div>
                        <!-- ---------------Description--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Description (*)</label>
                                <p class="text-muted">The more detailed the description, the better because this will be
                                    the legal basis for resolving complaints if any arise later</p>
                            </div>
                            <div class="col-md-9 form-group">
                                <textarea class="form-control" name="description" id="description" required></textarea>
                                <!-- input Description -->

                            </div>
                        </div>
                        <!-- ---------------Contact--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Contact</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <textarea class="form-control" name="contact" required></textarea>
                                <!-- input Contact -->
                            </div>
                        </div>
                        <!-- ---------------Hidden--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Hidden content (*)</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <textarea class="form-control" name="hidden" id="hidden" required></textarea>
                                <!-- input Hidden content -->
                            </div>
                        </div>
                        <!-- ---------------View mode--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">View mode (*)</label>
                            </div>
                            <div class="col-md-9 form-group">
                                <button type="button" class="col-md-4 btn btn-primary button-border" style="margin-right: 20px" id="publicBtn">
                                    <i class="fas fa-globe"></i> Public</button>

                                <button type="button" class="col-md-4 btn btn-secondary button-border" id="privateBtn">
                                    <i class="fas fa-lock"></i> Private</button>
                                <input type="hidden" name="viewMode" value="public"> <!-- Giá trị mặc định -->
                            </div>
                        </div>
                        <div class="d-flex mb-3 align-items-center">
                            <div class="col-md-3"></div>
                            <div class="col-md-9 form-group">
                                <span id="viewModeNotice" style="display: none;"></span>
                            </div>
                        </div>
                        <!-- ---------------Confirm--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="col-md-2"></div>
                            <div class="col-md-10">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="" id="confirmCheckBox"
                                           required> <!-- checkbox Confirm -->
                                    <label class="form-check label" for="confirmCheckBox">
                                        I confirm that the above information is true. I will be responsible if there is
                                        any incorrect information in the post.
                                    </label>
                                    <label class="text-muted form-check label">TapHoaSo will charge you 500 VND for your
                                        product post</label>
                                    <!--<span id="confirmAlert" class="text-danger"></span> -->
                                    <!-- ---------------Alert--------------- -->
                                </div>
                            </div>
                        </div>
                        <!-- ---------------Submit--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="form-group col-md-12 text-center">
                                <button type="submit" class="col-md-3 btn btn-primary p-3 button-border">
                                    <i class="fas fa-upload"></i> CREATE POST
                                </button>
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
<!-- ---------------Auto Price, Fee, Total--------------- -->
<script>
    var price = document.getElementById('price');
    var fee = document.getElementById('fee');
    var priceError = document.getElementById('priceError');
    var priceErrorBackend = document.getElementById('priceErrorBackend');
    var total = document.getElementById('total');
    var feePayer = document.getElementById('feePayer');

    price.addEventListener('input', function () {
        var priceValue = parseInt(price.value);
        if (priceValue % 1000 === 0 && priceValue >= 1000) {
            fee.style.display = 'block';
            priceError.style.display = 'none';
            var feeValue = priceValue * 0.05;
            fee.value = feeValue;
            if (feePayer.value === 'buyer') {
                total.value = priceValue;
            } else if (feePayer.value === 'half') {
                total.value = priceValue - feeValue / 2;
            } else {
                total.value = priceValue - feeValue;
            }
        } else {
            fee.value = 0;
            priceError.style.display = 'block';
            priceErrorBackend.style.display = 'none';
        }
    });

    feePayer.addEventListener('change', function () {
        var priceValue = parseInt(price.value);
        var feeValue = priceValue * 0.05;
        fee.value = feeValue;
        if (feePayer.value === 'buyer') {
            total.value = priceValue;
        } else if (feePayer.value === 'half') {
            total.value = priceValue - feeValue / 2;
        } else {
            total.value = priceValue - feeValue;
        }
    });

    total.addEventListener('input', function () {
        var priceValue = parseInt(total.value);
        price.value = priceValue;
        var feeValue = priceValue * 0.05;
        fee.value = feeValue;
        feePayer.value = 'buyer'
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
<!-- ---------------TinyMCE--------------- -->
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

<!-- ---------------View mode--------------- -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const publicBtn = document.getElementById("publicBtn")
        const privateBtn = document.getElementById("privateBtn");
        const viewModeInput = document.querySelector("input[name='viewMode']");
        const viewModeNotice = document.getElementById("viewModeNotice");

        publicBtn.addEventListener("click", function() {
            viewModeInput.value = "public";
            publicBtn.classList.add("active");
            publicBtn.style.backgroundColor = "#0090ff";
            publicBtn.style.borderColor = "#0a53be";
            privateBtn.classList.remove("active");
            privateBtn.style.backgroundColor = "#6c757d";
            privateBtn.style.borderColor = "#ffffff";
            viewModeNotice.style.display = "block";
            viewModeNotice.innerText = "Your post will be public on the market";
            viewModeNotice.style.color = "#0090ff";
        });

        privateBtn.addEventListener("click", function() {
            viewModeInput.value = "private";
            privateBtn.classList.add("active");
            privateBtn.style.backgroundColor = "#99001a";
            privateBtn.style.borderColor = "#660012";
            publicBtn.classList.remove("active");
            publicBtn.style.backgroundColor = "#6c757d";
            publicBtn.style.borderColor = "#ffffff";
            viewModeNotice.style.display = "block";
            viewModeNotice.innerText = "Your post will be private, only people with private links can view";
            viewModeNotice.style.color = "#99001a";
        });
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
