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
                    <ul class="nav">
                        <li><a href="home">Home</a></li>
                        <li><a href="market">Public market</a></li>
                        <li><a href="sellingPost">Selling posts</a>
                        </li>
                        <li><a href="buyingPost">Buying posts</a>
                        </li>
                        <li><a href="transactionHistory" id="money">${user.balance}</a></li>
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
                        <form action="editProfile" method="post">
                            <div class="d-flex mb-3 align-items-center">
                                <div class="label-form col-md-3"><label class="label">Username</label></div>
                                <div class="col-md-9"><input type="text" name="username" value="${user.username}"
                                                             class="form-control" disabled></div>
                            </div>
                            <div class="d-flex mb-3 align-items-center">
                                <div class="label-form col-md-3"><label class="label">Email</label></div>
                                <div class="col-md-9"><input type="text" name="email" value="${user.email}"
                                                             class="form-control" disabled></div>
                            </div>
                            <div class="d-flex mb-3 align-items-center">
                                <div class="label-form col-md-3"><label class="label">Nickname</label></div>
                                <div class="col-md-9"><input type="text" name="nickname" value="${user.nickname}"
                                                             class="form-control"></div>
                            </div>
                            <div class="d-flex mb-3 align-items-center">
                                <div class="form-group col-md-12 text-center">
                                    <button type="submit" class="col-md-3 btn btn-primary p-3">UPDATE PROFILE</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-lg-1"></div> <!-- Khoảng cách ở giữa -->
    <%--        <div class="mt-3"></div> <!-- Giảm khoảng cách ở giữa -->--%>


    <div class="col-lg-5">
        <!-- Your second form here -->

        <!-- Example: -->
        <div class="sell-wrap p-4">
            <div class="col-lg-12 mx-auto">
                <div class="card">
                    <div class="card-header">
                        <div class="pull-left">
                            <h4 class="card-title mt-2">Change Password</h4>
                        </div>
                    </div>
                    <div class="card-body">
                        <form action="editPassword" method="post" id="changePasswordForm">
                            <div class="d-flex mb-3 align-items-center">
                                <div class="label-form col-md-4">
                                    <label class="label" style="text-align: left;">PASSWORD</label>
                                </div>
                                <div class="col-md-8">
                                    <input type="password" name="password" class="form-control"
                                           placeholder="Enter your password">
                                </div>
                            </div>

                            <div class="d-flex mb-3 align-items-center">
                                <div class="label-form col-md-4"><label class="label" style="text-align: left;">NEW
                                    PASSWORD</label></div>
                                <div class="col-md-8"><input type="password" name="newpassword" class="form-control"
                                                             placeholder="Enter new password" id="password"></div>
                            </div>
                            <div class="d-flex mb-3 align-items-center">
                                <div class="label-form col-md-4"><label class="label" style="text-align: left;">ENTER
                                    NEW PASSWORD</label></div>
                                <div class="col-md-8"><input type="password" name="newpassword2" class="form-control"
                                                             placeholder="Re-enter new password" id="repassword"></div>
                            </div>
                            <h6 class="text-danger" id="message"></h6>
                            <!-- --------------captcha field-------------- -->
                            <div class="form-group mb-3">
                                <label class="label">Captcha</label>
                                <div class="d-flex align-content-center">
                                    <div class="content">
                                        <img style="height: 48px; width: 200px; border-radius: 5px"
                                             src="generateCaptcha" alt="Captcha Image"
                                             id="captchaImage">
                                    </div>
                                    <button class="btn input-group-prepend" onclick="resetCaptcha(event)">
                                        <i class="fa fa-refresh"></i>
                                    </button>
                                    <input type="text" class="form-control" name="captcha" required
                                           placeholder="Enter Captcha"/>
                                </div>
                            </div>
                            <!-- --------------error n success field-------------- -->
                            <div class="d-flex mb-3 align-items-center">
                                <div class="col-md-12">
                                    <h6 class="text-danger text-center">${error}</h6>
                                    <h6 class="text-success text-center">${success}</h6>
                                </div>
                            </div>
                            <!-- --------------submit button-------------- -->
                            <div class="d-flex mb-4 align-items-center">
                                <div class="form-group col-md-12 text-center">
                                    <button type="changepassword" class="col-md-5 btn btn-primary p-3" id="submitBtn">
                                        CHANGE PASSWORD
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- End of your second form -->
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
    document.getElementById("submitBtn").addEventListener("click", function (event) {
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
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/isotope.min.js"></script>
<script src="assets/js/owl-carousel.js"></script>
<script src="assets/js/counter.js"></script>
<script src="assets/js/custom.js"></script>
</body>
</html>
