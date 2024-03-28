<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-lugx-gaming.css">
    <link rel="stylesheet" href="assets/css/owl.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/swiper-bundle.min.css"/>
    <style>
        #notificationWindow {
            display: none;
            position: absolute;
            background-color: #fff;
            border: 1px solid #ccc;
            padding: 10px;
            z-index: 9999;
            max-height: 400px; /* Giới hạn chiều cao của cửa sổ thông báo */
            overflow-y: auto; /* Cho phép cuộn nếu nội dung vượt quá chiều cao */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Đổ bóng */
            border-radius: 5px; /* Bo tròn góc */
            width: 300px; /* Độ rộng của cửa sổ thông báo */
            margin-left: -255px;
        }

        #notificationWindow h3 {
            margin-top: 0;
            font-size: 16px;
            color: #333;
        }

        #notificationWindow p {
            margin: 5px 0;
            font-size: 14px;
            color: #666;
        }

        .notification-item {
            margin-bottom: 10px;
        }

        .notification-item a {
            text-decoration: none;
            color: #0a53be;
            transition: color 0.3s ease;
        }

        .notification-item a:hover {
            color: #0a53be;
            text-decoration: underline;
        }

        #notificationWindow a {
            text-decoration: none;
            color: #0a53be;
            transition: color 0.3s ease;
            font-size: 13px;
        }

        .notification-item:nth-child(3n+3) {
            border-bottom: 1px solid #ccc;
        }
        /* Tạo khoảng cách giữa các dòng thông báo */
        .notification-item a {
            margin: 0;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        #notificationWindow a:hover {
            color: #0a53be;
            text-decoration: underline;
        }

        .icon:hover {
            color: #0a53be;
        }

        .icon {
            position: relative;
        }

        #notificationWindow p{
            margin-top: 5px;
        }
    </style>
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
                    <!-- ***** Menu Start ***** -->
                    <ul class="nav">
                        <li><a href="viewProfile">Welcome! ${user.nickname}</a></li>
                        <li><a href="transactionHistory" id="money">${user.balance}</a></li>
                        <!--start notification icon-->
                        <li>
                            <div class="icon">
                                <i class="fas fa-bell" style="color: white" onclick="toggleNotification()"></i>
                                <!-- Cửa sổ hiển thị thông báo -->
                                <div id="notificationWindow">
                                    <h3>Thông báo</h3>
                                    <c:if test="${empty requestScope.listNotice}">
                                        <p>Không có thông báo mới</p>
                                    </c:if>
                                    <%--<c:forEach items="${requestScope.listNotice}" var="notice">
                                        <div class="notification-item">
                                            <c:set var="u" value="${session.getAttribute('user')}" />
                                            <c:choose>
                                                <c:when test="${notice.getUserIDTo().getUserID() == u.getUserID()}">
                                                    <a href="sellingPost">You have received a report on the post <c:out value="${notice.getPostID().getTradingCode()}" /></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="buyingPost">You have reported the post <c:out value="${notice.getPostID().getTradingCode()}" /></a>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:forEach>--%>
                                    <div class="notification-item" id="notificationArea">
                                        <c:forEach items="${requestScope.listContent}" var="content">
                                            <a href="#">${content}</a>
                                        </c:forEach>
                                    </div>

                                </div>
                            </div>
                        </li>


                        <!--end notification icon-->
                        <li><a STYLE="font-size: 15px" href="signOut">Sign out</a></li>
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

<div class="main-banner">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 align-self-center">
                <div class="caption header-text">
                    <h6>Welcome to THS</h6>
                    <h2>TAPHOASO PROVIP!</h2>
                    <p>Welcome to TAPHOASO, which provides all intermediary solutions for buying and selling digital
                        accounts. Protect buyers and sellers from scammers.</p>
                </div>
            </div>
            <div class="col-lg-4 offset-lg-2">
                <div class="right-image">
                    <img src="" alt="">
                </div>
            </div>
        </div>
    </div>
</div>

<div class="features">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-6">
                <a href="sell">
                    <div class="item">
                        <div class="image">
                            <img src="assets/images/sell.png" alt="" style="max-width: 44px;">
                        </div>
                        <h4>Sell Product</h4>
                    </div>
                </a>
            </div>
            <div class="col-lg-3 col-md-6">
                <a href="market">
                    <div class="item">
                        <div class="image">
                            <img src="assets/images/post.png" alt="" style="max-width: 44px;">
                        </div>
                        <h4>Public Market</h4>
                    </div>
                </a>
            </div>
            <div class="col-lg-3 col-md-6">
                <a href="transactionHistory">
                    <div class="item">
                        <div class="image">
                            <img src="assets/images/dolla.png" alt="" style="max-width: 44px;">
                        </div>
                        <h4>Balance & Transactions</h4>
                    </div>
                </a>
            </div>
            <div class="col-lg-3 col-md-6">
                <a href="viewProfile">
                    <div class="item">
                        <div class="image">
                            <img src="assets/images/user.png" alt="" style="max-width: 44px;">
                        </div>
                        <h4>Account Profile</h4>
                    </div>
                </a>
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
                    Contact: taphoaso391@gmail.com</p>
            </div>
        </div>
    </div>

</footer>

<script>
    document.querySelectorAll("[id^='money']").forEach(function (cell) {
        var totalSpend = cell.textContent;
        var formattedTotalSpend = new Intl.NumberFormat("vi-VN", {
            style: "currency",
            currency: "VND"
        }).format(totalSpend);
        cell.textContent = formattedTotalSpend;
    });

    function toggleNotification() {
        var notificationWindow = document.getElementById('notificationWindow');

        if (notificationWindow.style.display === 'block') {
            notificationWindow.style.display = 'none';
        } else {
            notificationWindow.style.display = 'block';
        }
    }

    $(document).ready(function() {
        // Function to fetch new notifications from the server
        function fetchNotifications() {
            $.ajax({
                url: '/fetchNotifications', // Replace with your server endpoint
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    updateNotificationArea(response);
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching notifications:', error);
                }
            });
        }

        // Function to update the notification area with new notifications
        function updateNotificationArea(notifications) {
            $('#notificationArea').empty(); // Clear existing notifications
            notifications.forEach(function(notification) {
                $('#notificationArea').append('<a href="#">' + notification + '</a><br>');
            });
        }

        setInterval(fetchNotifications, 3000);
    });





</script>

<!-- Scripts -->
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/isotope.min.js"></script>
<script src="assets/js/owl-carousel.js"></script>
<script src="assets/js/counter.js"></script>
<script src="assets/js/custom.js"></script>

</body>
</html>