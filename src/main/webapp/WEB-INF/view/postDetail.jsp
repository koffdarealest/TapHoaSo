<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 2/10/2024
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
          rel="stylesheet">

    <title>Public Market</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-lugx-gaming.css">
    <link rel="stylesheet" href="assets/css/owl.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/swiper-bundle.min.css"/>
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
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
                        <li><a href="home">Home</a></li>
                        <li><a href="market">Public market</a></li>
                        <li><a href="sellingPost">Selling posts</a>
                        </li>
                        <li>
                            <div class="icon">
                                <i class="fas fa-bell" style="color: white"></i>
                            </div>
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
                <h3>Public Market</h3>

            </div>
        </div>
    </div>
</div>

<div class="container d-flex" style="max-width: 1700px;">
    <div class="col-lg-1"></div>
    <div class="col-lg-6">
        <div class="detail-post-wrap p-4" style="margin-right: 30px;">
            <div class="col-lg-12 mx-auto">
                <div class="card">
                    <div class="card-header">
                        <div class="pull-left">
                            <h4 class="card-title mt-2">Product post information</h4>
                        </div>
                    </div>
                    <div class="card-body">
                        <!-- ---------------Trading Code--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Trading Code </label>
                            </div>
                            <div class="col-md-9">
                                <input type="text" class="form-control" disabled
                                       value="${chosenPost.tradingCode}">
                            </div>
                        </div>
                        <!-- ---------------Seller--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Seller </label>
                            </div>
                            <div class="col-md-9">
                                <input type="text" class="form-control" disabled
                                       value="${chosenPost.sellerID.nickname}">      <!-- input Title -->
                            </div>
                        </div>
                        <!-- ---------------Status--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Status </label>
                            </div>
                            <div class="col-md-9">
                                <input type="text" class="form-control" disabled
                                       value="${status}">      <!-- input Title -->
                            </div>
                        </div>
                        <!-- ---------------Title--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Title </label>
                            </div>
                            <div class="col-md-9">
                                <input type="text" name="title" class="form-control" required readonly
                                       value="${chosenPost.topic}">      <!-- input Title -->
                            </div>
                        </div>
                        <!-- ---------------Total--------------- -->
                        <div class="d-flex mb-3 align-items-center" id="total">
                            <div class="label-form col-md-3">
                                <label class="label">Total spend</label>
                                <p class="text-muted" style="font-size: small">Click to view detail</p>
                            </div>
                            <div class="col-md-9 form-group">
                                <input name="price" class="form-control" required readonly
                                       value="${chosenPost.totalSpendForBuyer}" style="font-weight: bold">   <!-- input Price -->
                                <span class="text-muted"
                                      style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">VND</span>
                            </div>
                        </div>
                        <!-- ---------------Price--------------- -->
                        <div id="priceField" style="display: none">
                            <div class="d-flex mb-3 align-items-center">
                                <div class="label-form col-md-3">
                                    <label class="label">Price </label>
                                </div>
                                <div class="col-md-9 form-group">
                                    <input type="text" name="price" class="form-control" required readonly id="price"
                                           value="${chosenPost.price}">   <!-- input Price -->
                                    <span class="text-muted"
                                          style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">VND</span>
                                </div>
                            </div>
                            <!-- ---------------Fee--------------- -->
                            <div class="d-flex mb-3 align-items-center">
                                <div class="label-form col-md-3">
                                    <label class="label">Fee </label>
                                </div>
                                <div class="col-md-9 form-group">
                                    <input type="number" name="fee" class="form-control" required readonly id="fee"
                                           value="${chosenPost.fee}" style="font-style: oblique">    <!-- input Fee -->
                                    <span class="text-muted"
                                          style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%);">VND</span>
                                </div>
                            </div>
                            <!-- ---------------Fee Payer--------------- -->
                            <div class="d-flex mb-3 align-items-center">
                                <div class="label-form col-md-3">
                                    <label class="label">Fee Payer </label>
                                </div>
                                <div class="col-md-9 form-group">
                                    <select class="form-control" name="feePayer" required readonly>
                                        <!-- select Fee Payer -->
                                        <c:choose>
                                            <c:when test="${chosenPost.whoPayFee == 'buyer'}">
                                                <option selected>Buyer</option>
                                            </c:when>
                                            <c:when test="${chosenPost.whoPayFee == 'half'}">
                                                <option selected>Half - Half</option>
                                            </c:when>
                                            <c:when test="${chosenPost.whoPayFee == 'seller'}">
                                                <option selected>Seller</option>
                                            </c:when>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <!-- ---------------Description--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Description </label>
                            </div>
                            <div class="col-md-9 form-group">
                                <textarea class="form-control" name="description" id="description" required
                                          readonly>${chosenPost.description}</textarea>     <!-- input Description -->
                            </div>
                        </div>
                        <!-- ---------------Contact--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Contact </label>
                            </div>
                            <div class="col-md-9 form-group">
                                <textarea class="form-control" name="contact" required
                                          readonly>${chosenPost.contact}</textarea>  <!-- input Contact -->
                            </div>
                        </div>
                        <!-- ---------------Created At--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Created At </label>
                            </div>
                            <div class="col-md-9">
                                <input type="text" id="createdAt" class="form-control" disabled
                                       value="">
                            </div>
                        </div>
                        <!-- ---------------Updated At--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Last Updated </label>
                            </div>
                            <div class="col-md-9">
                                <input type="text" id="updatedAt" class="form-control" disabled
                                       value="">      <!-- input Title -->
                            </div>
                        </div>
                        <!-- ---------------Submit--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="form-group col-md-12 text-center">
                                <button type="submit" class="col-md-3 btn p-3" style="background: green; --bs-btn-hover-bg: #0fae00; --bs-btn-color: white;"
                                        onclick="openBuyPopup('${chosenPost.tradingCode}')"><i class="fas fa-shopping-cart"></i> BUY
                                </button>
                                <!-- Submit -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="detail-market-wrap sticky-table">
            <div>
                <table class="table table-bordered table-scroll" id="marketTable">
                    <thead class="sticky-header">
                    <tr>
                        <th>Title</th>
                        <th>Total spend</th>
                        <th>Seller</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="post" items="${lPosts}">
                        <tr>
                            <td>${post.topic}</td>
                            <td>${post.totalSpendForBuyer}</td>
                            <td>${post.sellerID.nickname}</td>
                            <td>
                                <button class="custom-button btn btn-lg" onclick="viewPostDetail('${post.tradingCode}')"
                                        style="font-size: small">
                                    <i class="fas fa-info-circle"></i> Detail
                                </button>
                            </td>

                        </tr>
                    </c:forEach>
                    <!-- Repeat the above row for 20 records -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>

<footer>
    <div class="container">
        <div class="row justify-content-center">
            <div class="d-md-flex col-lg-12 align-self-center">
                <p class="w-50" style="font-weight: bold; font-size: 110%; margin-left: auto; margin-top: 10px;">
                    Powered
                    by: TapHoaSo © 2024.</p>
                <p class="w-50" style="font-weight: bold; font-size: 110%; margin-right: auto; margin-top: 10px;">
                    Email
                    Contact: taphoaso391@gmail.com</p>
            </div>
        </div>
    </div>
</footer>
<!-- -----------------Confirm Pop-up-----------------  -->
<div id="overlay" class="overlay" onclick="closePopup()"></div>
<div id="buyPopup" class="popup">
    <div class="popup-content">
        <h6 class="mb-1">Are you sure you want to buy this product?</h6>
        <p class="mb-3">Your action cannot be recovered!</p>
        <button onclick="buyPostConfirmed()" style="background: #0fae00">Yes</button>
        <button onclick="closePopup()" style="background: #646464">No</button>
    </div>
</div>
<!-- Scripts -->
<!-- ----------------Table---------------- -->
<script>
    window.onload = function () {
        const table = document.getElementById('marketTable');
        const headers = table.getElementsByTagName('th');
        const maxRows = 50; // Số hàng tối đa
        const existingRowHeight = table.rows[1].offsetHeight; // Chiều cao của hàng thứ hai
        let rowCounter = table.rows.length - 1; // Số hàng hiện có (trừ đi hàng header)
        // Khởi tạo bảng tới số hàng tối đa
        function addRowIfNeed() {
            if (rowCounter < maxRows) {
                const remainingRows = maxRows - rowCounter;
                for (let i = 0; i < remainingRows; i++) {
                    const row = table.insertRow();
                    row.style.height = (existingRowHeight * 0.85) + 'px'; // Thiết lập chiều cao cho hàng mới
                    for (let j = 0; j < headers.length; j++) {
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
<!-- ----------------TinyMCE---------------- -->
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
<!-- ----------------View Post Detail button---------------- -->
<script>
    function viewPostDetail(tradingCode) {
        window.location.href = 'postDetail?tradingCode=' + tradingCode;
    }
</script>
<!-- ----------------Format Date---------------- -->
<script>
    var createdAt = new Date('${chosenPost.createdAt}');
    var updatedAt = new Date('${chosenPost.updatedAt}');
    var fmtCreatedAtDate = createdAt.getDate() + '/' + (createdAt.getMonth() + 1) + '/' + createdAt.getFullYear() + ' ' +
        ('0' + createdAt.getHours()).slice(-2) + ':' +
        ('0' + createdAt.getMinutes()).slice(-2) + ':' +
        ('0' + createdAt.getSeconds()).slice(-2);
    document.getElementById('createdAt').value = fmtCreatedAtDate;
    if (updatedAt != null && !isNaN(updatedAt.getTime())) {
        var fmtUpdatedAtDate = updatedAt.getDate() + '/' + (updatedAt.getMonth() + 1) + '/' + updatedAt.getFullYear() + ' ' +
            ('0' + updatedAt.getHours()).slice(-2) + ':' +
            ('0' + updatedAt.getMinutes()).slice(-2) + ':' +
            ('0' + updatedAt.getSeconds()).slice(-2);
        document.getElementById('updatedAt').value = fmtUpdatedAtDate;
    }
</script>
<!-- ----------------Buy button---------------- -->
<script>
    var code;

    function openBuyPopup(tradingCode) {
        document.getElementById('overlay').style.display = 'block';
        document.getElementById('buyPopup').style.display = 'block';
        code = tradingCode;
    }

    function buyPostConfirmed() {
        window.location.href = 'buy?tradingCode=' + code;
    }

    function closePopup() {
        document.getElementById('buyPopup').style.display = 'none';
        document.getElementById('overlay').style.display = 'none';
    }
</script>

<!-- ----------------Toggle Price Field---------------- -->
<script>
    // document.addEventListener("DOMContentLoaded", function () {
    //     const total = document.getElementById('total');
    //     const priceField = document.getElementById('priceField');
    //     total.addEventListener("click", function () {
    //         if (priceField.style.display === "none") {
    //             priceField.style.display = "block";
    //         } else {
    //             priceField.style.display = "none";
    //         }
    //     });
    // });

    $(document).ready(function () {
        let isVisible = false;

        $("#total").click(function () {
            if (!isVisible) {
                $("#priceField").slideDown("slow");
                isVisible = true;
            } else {
                $("#priceField").slideUp("slow");
                isVisible = false;
            }
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
<script src="https://cdn.tiny.cloud/1/qmw4wavlc4ekzay2c6m9pxxoyvi1ni12vki7sz9clkyfyyo2/tinymce/6/tinymce.min.js"
        referrerpolicy="origin"></script>
<script src="assets/js/isotope.min.js"></script>
<script src="assets/js/owl-carousel.js"></script>
<script src="assets/js/counter.js"></script>
<script src="assets/js/custom.js"></script>




</body>

</html>
