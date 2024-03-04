<%--
  Created by IntelliJ IDEA.
  User: Tung
  Date: 2/21/2024
  Time: 5:02 PM
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

    <title>Buying Posts</title>

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
                        <li><a href="home">Home</a></li>
                        <li><a href="market">Public market</a></li>
                        <li><a href="sellingPost">Selling posts</a>
                        </li>
                        <li><a href="buyingPost">Buying posts</a>
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
                <h3>Buying Product Post</h3>

            </div>
        </div>
    </div>
</div>

<div class="container d-flex" style="max-width: 1700px;">
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

                        <!-- ---------------PostID--------------- -->
                        <input type="hidden" name="postID" value="${chosenPost.postID}">
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
                                       value="${chosenPost.sellerID.nickname}" readonly>      <!-- input Seller -->
                            </div>
                        </div>
                        <!-- ---------------Title--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Title </label>
                            </div>
                            <div class="col-md-9">
                                <input type="text" name="title" class="form-control" required
                                       value="${chosenPost.topic}" readonly>      <!-- input Title -->
                            </div>
                        </div>
                        <!-- ---------------Price--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Price </label>
                            </div>
                            <div class="col-md-9 form-group">
                                <input type="text" name="price" class="form-control" required id="price"
                                       value="${chosenPost.price}" readonly>   <!-- input Price -->
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
                                <input type="number" name="fee" class="form-control" required id="fee"
                                       value="${chosenPost.fee}" readonly>    <!-- input Fee -->
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
                                <select class="form-control" name="feePayer" required disabled>
                                    <!-- select Fee Payer -->
                                    <c:choose>
                                        <c:when test="${chosenPost.whoPayFee == 'buyer'}">
                                            <option selected value="buyer">Buyer</option>
                                        </c:when>
                                        <c:when test="${chosenPost.whoPayFee == 'half'}">
                                            <option selected value="half">Half - Half</option>
                                        </c:when>
                                        <c:when test="${chosenPost.whoPayFee == 'seller'}">
                                            <option selected value="seller">Seller</option>
                                        </c:when>
                                    </c:choose>
                                </select>
                            </div>
                        </div>
                        <!-- ---------------Description--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Description </label>
                            </div>
                            <div class="col-md-9 form-group">
                                    <textarea class="form-control" name="description" id="description"
                                              required readonly>${chosenPost.description}</textarea>
                                <!-- input Description -->
                            </div>
                        </div>
                        <!-- ---------------Contact--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Contact </label>
                            </div>
                            <div class="col-md-9 form-group">
                                    <textarea class="form-control" name="contact"
                                              required readonly>${chosenPost.contact}</textarea>  <!-- input Contact -->
                            </div>
                        </div>
                        <!-- ---------------Hidden--------------- -->
                        <div class="d-flex mb-3 align-items-center">
                            <div class="label-form col-md-3">
                                <label class="label">Hidden content </label>
                            </div>
                            <div class="col-md-9 form-group">
                                <textarea class="form-control" name="hidden" id="hidden" required
                                          readonly>${chosenPost.hidden}</textarea>       <!-- input Hidden content -->
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
                        <c:choose>
                            <c:when test="${chosenPost.status == 'buyerChecking'}">
                                <!-- ---------------Buttons--------------- -->
                                <div class="d-flex mb-3 align-items-center">
                                    <div class="col-md-12 text-center">
                                        <button class="col-md-5 btn custom-button p-2"
                                                style="font-size: medium; margin-right: 20px; background: #d21300">
                                            <i class="fa fa-times-circle" style="margin-right: 4px"></i>REPORT PROBLEM
                                        </button>
                                        <button class="col-md-5 btn custom-button p-2" style="font-size: medium"
                                                onclick="openConfirmationPopup(${chosenPost.postID})">
                                            <i class="fa fa-check-circle" style="margin-right: 4px"></i>
                                            CONFIRM PRODUCT RECEIVED
                                        </button>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-lg-6">
        <div class="detail-selling-market-wrap sticky-table">
            <div>
                <table class="table table-bordered table-scroll" id="marketTable">
                    <thead class="sticky-header">
                    <tr>
                        <th>Title</th>
                        <th>Fee</th>
                        <th>Total spend</th>
                        <th>Status</th>
                        <th colspan="2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="post" items="${lPosts}">
                        <tr>
                            <td>${post.topic}</td>
                            <td>${post.fee}</td>
                            <td>${post.totalSpendForBuyer}</td>
                            <td>${post.status}</td>
                            <td>
                                <button class="custom-button btn btn-lg"
                                        onclick="viewBuyingDetail('${post.tradingCode}')" style="font-size: small">
                                    <i class="fas fa-info-circle"></i> Detail
                                </button>
                            </td>
                            <td></td>
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
<div id="confirmationPopup" class="popup">
    <div class="popup-content">
        <h6 class="mb-1">After confirmation, you can no longer complain about this product post!</h6>
        <p class="mb-3">Your action cannot be recovered!</p>
        <button onclick="donePostConfirmed()" style="background: #0fae00">Yes</button>
        <button onclick="closePopup()">No</button>
    </div>
</div>

<!-- Scripts -->
<!-- ------------------Table------------------ -->
<script>
    window.onload = function () {
        const table = document.getElementById('marketTable');
        const headers = table.getElementsByTagName('th');
        const maxRows = 20; // Số hàng tối đa
        const existingRowHeight = table.rows[1].offsetHeight; // Chiều cao của hàng thứ hai
        let rowCounter = table.rows.length - 1; // Số hàng hiện có (trừ đi hàng header)
        // Khởi tạo bảng tới số hàng tối đa
        function addRowIfNeed() {
            if (rowCounter < maxRows) {
                const remainingRows = maxRows - rowCounter;
                for (let i = 0; i < remainingRows; i++) {
                    const row = table.insertRow();
                    row.style.height = (existingRowHeight * 0.8) + 'px'; // Thiết lập chiều cao cho hàng mới
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
<!-- ------------------TinyMCE------------------ -->
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

    function viewBuyingDetail(tradingCode) {
        window.location.href = 'buyingPostDetail?tradingCode=' + tradingCode;
    }
</script>
<!-- ------------------Price Control------------------ -->
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
<!-- ------------------Confirmation Popup------------------ -->
<script>
    var id;

    function openConfirmationPopup(postID) {
        document.getElementById('overlay').style.display = 'block';
        document.getElementById('confirmationPopup').style.display = 'block';
        id = postID;
    }

    function donePostConfirmed() {
        window.location.href = 'confirmReceive?postID=' + id;
    }

    function closePopup() {
        document.getElementById('confirmationPopup').style.display = 'none';
        document.getElementById('overlay').style.display = 'none';
    }
</script>

<!-- Bootstrap core JavaScript -->
<script src="https://cdn.tiny.cloud/1/qmw4wavlc4ekzay2c6m9pxxoyvi1ni12vki7sz9clkyfyyo2/tinymce/6/tinymce.min.js"
        referrerpolicy="origin"></script>
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/isotope.min.js"></script>
<script src="assets/js/owl-carousel.js"></script>
<script src="assets/js/counter.js"></script>
<script src="assets/js/custom.js"></script>


</body>

</html>
