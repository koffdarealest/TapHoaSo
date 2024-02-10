<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Tung
  Date: 2/8/2024
  Time: 11:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html lang="en">
<style>
    td {
        word-wrap: break-word;
        max-width: 200px;
    }

    .custom-button {
        background-color: #4CAF50; /* Màu xanh */
        border: none;
        color: white;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        cursor: pointer;
        border-radius: 5px;
        transition-duration: 0.4s;
    }

    .custom-button:hover {
        background-color: #45a049; /* Màu xanh nhạt khi hover */
    }
</style>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
          rel="stylesheet">

    <title>Sign in</title>

    <!-- Bootstrap core CSS -->
    <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="../../assets/css/fontawesome.css">
    <link rel="stylesheet" href="../../assets/css/templatemo-lugx-gaming.css">
    <link rel="stylesheet" href="../../assets/css/owl.css">
    <link rel="stylesheet" href="../../assets/css/animate.css">
    <link rel="stylesheet" href="../../assets/css/style.css">
    <link rel="stylesheet" href="../../assets/css/swiper-bundle.min.css"/>


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
                        <img src="" alt="" style="width: 158px;">
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
                <h3>Public Market</h3>

            </div>
        </div>
    </div>
</div>

<div class="container" style="max-width: 1700px;">
    <div class="market-wrap">
        <div>
            <table class="table table-bordered table-scroll" id="marketTable">
                <thead class="sticky-header">
                <tr>
                    <th>Trading code</th>
                    <th>Title</th>
                    <th>Contact</th>
                    <th>Price</th>
                    <th>Fee payer</th>
                    <th>Fee</th>
                    <th>Total</th>
                    <th>Seller</th>
                    <th>Create time</th>
                    <th>Last modify</th>
                    <th colspan="2">Column 8</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="post" items="${lPosts}">
                    <tr>
                        <td>${post.tradingCode}</td>
                        <td>${post.topic}</td>
                        <td>${post.contact}</td>
                        <td>${post.price}</td>
                        <td>${post.whoPayFee}</td>
                        <td>${post.fee}</td>
                        <td><c:choose>
                            <c:when test="${post.whoPayFee == 'buyer'}">
                                ${post.price + post.fee}
                            </c:when>
                            <c:when test="${post.whoPayFee == 'half_half'}">
                                ${post.price + post.fee * 0.5}
                            </c:when>
                            <c:otherwise>
                                ${post.price}
                            </c:otherwise>
                        </c:choose></td>
                        <td>${post.sellerID.username}</td>
                        <td>
                            <c:set var="createdAt" value="${post.createdAt}"/>
                            <fmt:formatDate value="${createdAt}" pattern="dd/MM/yyyy HH:mm:ss"/>
                        </td>
                        <c:set var="updatedAt" value="${post.updatedAt}" />
                        <td>
                            <c:choose>
                                <c:when test="${updatedAt != null}">
                                    <fmt:formatDate value="${updatedAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                                </c:when>
                                <c:otherwise>&nbsp;</c:otherwise>
                            </c:choose>
                        </td>
                        <td><button class="custom-button" onclick="viewDetail('${post.postID}')">View Detail</button></td>
                        <td>View Detail</td>
                    </tr>
                </c:forEach>
                <!-- Repeat the above row for 20 records -->
                </tbody>
            </table>
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
                    Contact: taphoaso@gmail.com</p>
            </div>
        </div>
    </div>
</footer>

<!-- Scripts -->
<script>
    window.onload = function () {
        const table = document.getElementById('marketTable');
        const headers = table.getElementsByTagName('th');
        const maxRows = 30; // Số hàng tối đa
        const existingRowHeight = table.rows[1].offsetHeight; // Chiều cao của hàng thứ hai
        let rowCounter = table.rows.length - 1; // Số hàng hiện có (trừ đi hàng header)
        // Khởi tạo bảng tới số hàng tối đa
        function addRowIfNeed() {
            if (rowCounter < maxRows) {
                const remainingRows = maxRows - rowCounter;
                for (let i = 0; i < remainingRows; i++) {
                    const row = table.insertRow();
                    row.style.height = (existingRowHeight * 0.85) + 'px'; // Thiết lập chiều cao cho hàng mới
                    for (let j = 0; j <= headers.length; j++) {
                        const cell = row.insertCell();
                    }
                }
            }
        }

        addRowIfNeed();


        for (let header of headers) {
            const dragHandle = document.createElement('div');
            dragHandle.classList.add('dragHandle');
            header.appendChild(dragHandle);

            let startX, startWidth;

            dragHandle.addEventListener('mousedown', function (e) {
                startX = e.pageX;
                startWidth = header.offsetWidth;
                dragHandle.style.backgroundColor = '#ddd'; // Optional: Change color on drag
                document.addEventListener('mousemove', mousemove);
                document.addEventListener('mouseup', mouseup);
            });

            function mousemove(e) {
                const width = startWidth + e.pageX - startX;
                header.style.width = width + 'px';

                // Thay đổi độ rộng của các ô trong cùng cột
                const columnIndex = Array.prototype.indexOf.call(header.parentNode.children, header);
                const tableRows = Array.from(table.getElementsByTagName('tr'));
                for (let row of tableRows.slice(1)) { // Bỏ qua hàng đầu tiên (header)
                    row.children[columnIndex].style.width = width + 'px';
                }
            }

            function mouseup() {
                dragHandle.style.backgroundColor = '#ffffff'; // Optional: Restore color after drag
                document.removeEventListener('mousemove', mousemove);
                document.removeEventListener('mouseup', mouseup);
            }
        }
    };
</script>
<!-- Bootstrap core JavaScript -->
<script src="../../vendor/jquery/jquery.min.js"></script>
<script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../../assets/js/isotope.min.js"></script>
<script src="../../assets/js/owl-carousel.js"></script>
<script src="../../assets/js/counter.js"></script>
<script src="../../assets/js/custom.js"></script>

<script>
    function viewDetail(postId) {
        // Redirect to the detail page with the specific postId
        window.location.href = 'view/detailPage.jsp?id=' + postId;
    }
</script>

</body>

</html>

