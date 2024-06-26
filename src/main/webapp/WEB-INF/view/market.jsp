<%--
  Created by IntelliJ IDEA.
  User: Tung
  Date: 2/8/2024
  Time: 11:03 AM
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


        input.form-control {
            width: 100%;
            padding: .375rem .75rem;
            font-size: 1rem;
            line-height: 1.5;
            color: #495057;
            background-color: #fff;
            background-clip: padding-box;
            border: 1px solid #ced4da;
            border-radius: .25rem;
            transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
        }
        #pagination {
            margin-top: 20px; /* Khoảng cách từ đối tượng trước */
            text-align: center; /* Căn giữa nút và số trang */
        }

        #pagination button,
        #pagination span {
            margin: 0 5px; /* Khoảng cách giữa các nút và số trang */
            padding: 8px 12px; /* Kích thước nút và số trang */
            border: 1px solid #ccc; /* Đường viền */
            background-color: #f9f9f9; /* Màu nền */
            cursor: pointer; /* Con trỏ khi rê chuột */
        }

        #pagination button:hover {
            background-color: #e9e9e9; /* Màu nền khi di chuột qua */
        }

        #pagination button:active {
            background-color: #d9d9d9; /* Màu nền khi nhấn nút */
        }

        #pagination button:disabled,
        #pagination span.disabled {
            color: #aaa; /* Màu chữ xám */
            border-color: #ddd; /* Đường viền mờ */
            cursor: not-allowed; /* Không cho phép nhấp chuột */
        }

        #pagination button#prevPage,
        #pagination button#nextPage {
            font-weight: bold; /* Chữ đậm */
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
                        <li><a href="home">Home</a></li>
                        <li><a href="market">Public market</a></li>
                        <li><a href="sellingPost">Selling posts</a>
                        </li>
                        <li><a href="buyingPost">Buying posts</a>
                        </li>
                        <li>
                            <div class="icon">
                                <i class="fas fa-bell" style="color: white" onclick="toggleNotification()"></i>
                                <!-- Cửa sổ hiển thị thông báo -->
                                <div id="notificationWindow">
                                    <h3>Thông báo</h3>
                                    <c:forEach items="${requestScope.listNotice}" var="notice">
                                        <div class="notification-item">
                                            <a href="#"><c:out value="${notice.content}" /></a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
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

<div class="container" style="max-width: 1700px;">
    <div class="market-wrap">
        <div>
            <table class="table table-bordered table-scroll" id="marketTable">
                <thead class="sticky-header">
                <tr>
                    <th data-column-index="0" data-sort-order="desc">Trading code</th>
                    <th data-column-index="1" data-sort-order="desc">Title</th>
                    <th data-column-index="2" data-sort-order="desc">Contact</th>
                    <th data-column-index="3" data-sort-order="desc">Price</th>
                    <th data-column-index="4" data-sort-order="desc">Fee payer</th>
                    <th data-column-index="5" data-sort-order="desc">Fee</th>
                    <th data-column-index="6" data-sort-order="desc">Total spend</th>
                    <th data-column-index="7" data-sort-order="desc">Seller</th>
                    <th data-column-index="8" data-sort-order="desc">Create time</th>
                    <th data-column-index="9" data-sort-order="desc">Last modify</th>
                    <th>Action</th>
                </tr>

                <tr>
                    <!-- Trading code -->
                    <th>
                        <input type="text" placeholder="Trading code" class="form-control">
                    </th>
                    <!-- Title -->
                    <th>
                        <input type="text" placeholder="Title" class="form-control">
                    </th>
                    <!-- Contact -->
                    <th>
                        <input type="text" placeholder="Contact" class="form-control">
                    </th>
                    <!-- Price -->
                    <th>
                        <div class="row">
                            <div class="col" style="padding-right: 0px;">
                                <input placeholder="From" type="text" class="form-control" id="minPrice" >
                            </div>
                            <div class="col" style="padding-left: 1px;">
                                <input placeholder="To" type="text" class="form-control" id="maxPrice" >
                            </div>
                        </div>
                    </th>
                    <!-- Fee payer -->
                    <th>
                        <select class="form-control" id="FeePayer">
                            <option value="All">All</option>
                            <option value="Seller">Seller</option>
                            <option value="Buyer">Buyer</option>
                            <option value="Half - Half">Half - Half</option>
                        </select>
                    </th>
                    <!-- Fee -->
                    <th>
                        <div class="row">
                            <div class="col" style="padding-right: 0px;">
                                <input placeholder="From" type="text" class="form-control" id="minFee">
                            </div>
                            <div class="col" style="padding-left: 1px;">
                                <input placeholder="To" type="text" class="form-control" id="maxFee">
                            </div>
                        </div>
                    </th>
                    <!-- Total spend -->
                    <th>
                        <div class="row">
                            <div class="col" style="padding-right: 0px;">
                                <input placeholder="From" type="text" class="form-control" id="minTotalSpend">
                            </div>
                            <div class="col" style="padding-left: 1px;">
                                <input placeholder="To" type="text" class="form-control" id="maxTotalSpend">
                            </div>
                        </div>
                    </th>
                    <!-- Seller -->
                    <th>
                        <input type="text" placeholder="Seller" class="form-control">
                    </th>
                    <!-- Create time -->
                    <th>
                        <input type="text" placeholder="Create time" class="form-control" readonly>
                    </th>
                    <!-- Last modify -->
                    <th>
                        <input type="text" placeholder="Last modify" class="form-control" readonly>
                    </th>
                    <!-- Action -->
                    <th style="max-width: 200px;">
                        <div class="rt-th rthfc-th-fixed rthfc-th-fixed-right rthfc-th-fixed-right-first">
                            <button type="button" data-toggle="tooltip" title="Bỏ lọc" class="mr-1 btn btn-outline-danger" id="clearFilterButton" style="width: 100%;">
                                <i class="fa fa-remove"></i> Filter
                            </button>
                        </div>
                    </th>
                </tr>
                </thead>

                <tbody>

                <c:forEach var="post" items="${lPosts}">
                    <tr>
                        <td class="td-overflow">${post.tradingCode}</td>
                        <td class="td-overflow">${post.topic}</td>
                        <td class="td-overflow">${post.contact}</td>
                        <td id="money-1">${post.price}</td>
                        <td><c:choose>
                            <c:when test="${post.whoPayFee == 'half'}">
                                Half - Half
                            </c:when>
                            <c:when test="${post.whoPayFee == 'seller'}">
                                Seller
                            </c:when>
                            <c:when test="${post.whoPayFee == 'buyer'}">
                                Buyer
                            </c:when>
                        </c:choose>
                        </td>
                        <td id="money-2">${post.fee}</td>
                        <td id="money-3" style="font-weight: bold">${post.totalSpendForBuyer}</td>
                        <td class="td-overflow">${post.sellerID.nickname}</td>
                        <td>
                            <c:set var="createdAt" value="${post.createdAt}"/>
                            <fmt:formatDate value="${createdAt}" pattern="dd/MM/yyyy HH:mm:ss"/>
                        </td>
                        <c:set var="updatedAt" value="${post.updatedAt}"/>
                        <td>
                            <c:choose>
                                <c:when test="${updatedAt != null}">
                                    <fmt:formatDate value="${updatedAt}" pattern="dd/MM/yyyy HH:mm:ss"/>
                                </c:when>
                                <c:otherwise>&nbsp;</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <button class="custom-button btn btn-lg" onclick="viewPostDetail('${post.tradingCode}')"
                                    style="font-size: large">
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
<div id="pagination">
    <button id="prevPage">Previous</button>
    <span>1</span>
    <span>2</span>
    <span>3</span>
    <button id="nextPage">Next</button>
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
    window.onload = function () {
        const table = document.getElementById('marketTable');
        const headers = table.getElementsByTagName('th');
        const maxRows = 15; // Số hàng tối đa
        const existingRowHeight = table.rows[1].offsetHeight; // Chiều cao của hàng thứ hai
        let rowCounter = table.rows.length - 1; // Số hàng hiện có (trừ đi hàng header)
        // Khởi tạo bảng tới số hàng tối đa
        function addRowIfNeed() {
            if (rowCounter < maxRows) {
                const remainingRows = maxRows - rowCounter;
                for (let i = 0; i < remainingRows; i++) {
                    const row = table.insertRow();
                    row.style.height = (existingRowHeight * 0.85) + 'px'; // Thiết lập chiều cao cho hàng mới
                    for (let j = 0; j < headers.length; j = j + 2) {
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

    function viewPostDetail(tradingCode) {
        window.location.href = 'postDetail?tradingCode=' + tradingCode;
    }
</script>
<!-- Money format -->
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
            /* var xhttp = new XMLHttpRequest();
             xhttp.onreadystatechange = function() {
                 if (this.readyState == 4) {
                     if (this.status == 200) {
                         notificationWindow.innerHTML = this.responseText;
                         notificationWindow.style.display = 'block';
                     } else {
                         console.error('Error:', this.status);
                     }
                 }
             };

             // Sử dụng phương thức GET thay vì POST
             xhttp.open("GET", "notice", true);
             xhttp.send();*/
        }
    }
</script>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/isotope.min.js"></script>
<script src="assets/js/owl-carousel.js"></script>
<script src="assets/js/counter.js"></script>
<script src="assets/js/custom.js"></script>

<script>

    document.addEventListener('DOMContentLoaded', function() {
        let inputFields = document.querySelectorAll('input.form-control');
        inputFields.forEach(function(input) {
            input.addEventListener('input', function() {
                let searchText = this.value.toLowerCase();
                let columnIndex = this.closest('th').cellIndex;

                // Kiểm tra chỉ số của cột để xử lý tìm kiếm
                if (columnIndex === 0 || columnIndex === 1 || columnIndex === 2 || columnIndex === 7) {
                    let rows = document.querySelectorAll('tbody tr');

                    rows.forEach(function(row) {
                        let cell = row.cells[columnIndex];
                        if (cell.textContent.toLowerCase().includes(searchText)) {
                            row.style.display = '';
                        } else {
                            row.style.display = 'none';
                        }
                    });
                }
            });
        });
    });

    document.addEventListener('DOMContentLoaded', function() {
        let inputFields = document.querySelectorAll('input.form-control');
        inputFields.forEach(function(input) {
            input.addEventListener('input', function() {
                let columnIndex = this.closest('th').cellIndex;

                // Kiểm tra chỉ số của cột để xử lý tìm kiếm
                if (columnIndex === 3 || columnIndex === 5 || columnIndex === 6) {
                    let minPrice = parseFloat(document.getElementById('minPrice').value);
                    let maxPrice = parseFloat(document.getElementById('maxPrice').value);
                    let minFee = parseFloat(document.getElementById('minFee').value);
                    let maxFee = parseFloat(document.getElementById('maxFee').value);
                    let minTotalSpend = parseFloat(document.getElementById('minTotalSpend').value);
                    let maxTotalSpend = parseFloat(document.getElementById('maxTotalSpend').value);

                    let rows = document.querySelectorAll('tbody tr');

                    rows.forEach(function(row) {
                        let priceCell = row.cells[3];
                        let feeCell = row.cells[5];
                        let totalSpendCell = row.cells[6];

                        let priceValue = parseFloat(priceCell.textContent.trim().replace(/[.,₫]/g, ''));
                        let feeValue = parseFloat(feeCell.textContent.trim().replace(/[.,₫]/g, ''));
                        let totalSpendValue = parseFloat(totalSpendCell.textContent.trim().replace(/[.,₫]/g, ''));

                        if (((isNaN(minPrice) || priceValue >= minPrice) && (isNaN(maxPrice) || priceValue <= maxPrice)) &&
                            ((isNaN(minFee) || feeValue >= minFee) && (isNaN(maxFee) || feeValue <= maxFee)) &&
                            ((isNaN(minTotalSpend) || totalSpendValue >= minTotalSpend) && (isNaN(maxTotalSpend) || totalSpendValue <= maxTotalSpend))) {
                            row.style.display = '';
                        } else {
                            row.style.display = 'none';
                        }
                    });
                }
            });
        });
    });

    document.addEventListener('DOMContentLoaded', function() {
        const dropdown = document.getElementById('FeePayer');

        dropdown.addEventListener('change', function() {
            const selectedValue = dropdown.value.toLowerCase().trim();
            const rows = document.querySelectorAll('tbody tr');

            rows.forEach(function(row) {
                const cell = row.cells[4]; // Lấy cột FeePayer
                const cellValue = cell.textContent.toLowerCase().trim();

                if (selectedValue === 'all' || cellValue === selectedValue) {
                    row.style.display = ''; // Hiển thị hàng nếu giá trị được chọn là 'all' hoặc giá trị cột tương ứng trùng khớp
                } else {
                    row.style.display = 'none'; // Ẩn hàng nếu không trùng khớp
                }
            });
        });
    });


    document.getElementById('clearFilterButton').addEventListener('click', function() {
        let inputFields = document.querySelectorAll('input.form-control');
        inputFields.forEach(function(input) {
            input.value = ''; // Thiết lập giá trị của input thành rỗng
        });

        // Hiển thị lại tất cả các hàng trong bảng
        let rows = document.querySelectorAll('tbody tr');
        rows.forEach(function(row) {
            row.style.display = '';
        });
    });

    document.addEventListener('DOMContentLoaded', function() {
        const table = document.getElementById('marketTable');
        const headers = table.getElementsByTagName('th');

        // Một đối tượng để lưu trữ thứ tự sắp xếp cho mỗi cột
        const sortOrder = {};

        for (let header of headers) {
            header.addEventListener('click', function() {
                // Lấy chỉ số cột
                const columnIndex = parseInt(header.getAttribute('data-column-index'));

                // Lật lại thứ tự sắp xếp cho lần click tiếp theo hoặc thiết lập mặc định là tăng dần
                sortOrder[columnIndex] = (sortOrder[columnIndex] === undefined || sortOrder[columnIndex] === 1) ? -1 : 1;

                // Lấy tất cả các hàng trong tbody
                const rows = Array.from(table.querySelectorAll('tbody tr'));

                // Sắp xếp các hàng dựa trên giá trị của cột tương ứng
                rows.sort(function(rowA, rowB) {
                    if(columnIndex === 3 || columnIndex === 5 || columnIndex === 6) {
                        const valueA = rowA.cells[columnIndex].textContent.trim().replace(/[.,₫]/g, '');
                        const valueB = rowB.cells[columnIndex].textContent.trim().replace(/[.,₫]/g, '');
                        return sortOrder[columnIndex] * (parseFloat(valueA) - parseFloat(valueB));
                    }
                    else if(columnIndex === 0 || columnIndex === 1 || columnIndex === 2 || columnIndex === 4 || columnIndex === 7){
                        const valueA = rowA.cells[columnIndex].textContent.trim();
                        const valueB = rowB.cells[columnIndex].textContent.trim();
                        if(isNaN(valueA) && isNaN((valueB))) {
                            return sortOrder[columnIndex] * (valueA.localeCompare(valueB));
                        }
                        else if (!isNaN(valueA) && !isNaN(valueB)) {
                            return sortOrder[columnIndex] * (parseFloat(valueA) - parseFloat(valueB));
                        }
                    }
                });

                // Xóa tất cả các hàng trong tbody
                table.querySelector('tbody').innerHTML = '';

                // Thêm lại các hàng đã được sắp xếp vào tbody
                rows.forEach(function(row) {
                    table.querySelector('tbody').appendChild(row);
                });
            });
        }
    });

    document.addEventListener("DOMContentLoaded", function() {
        var currentPage = 1;
        var rowsPerPage = 5; // Số hàng trên mỗi trang

        var tableRows = document.querySelectorAll("#marketTable tbody tr");
        var totalPages = Math.ceil(tableRows.length / rowsPerPage);

        function showPage(page) {
            var startIndex = (page - 1) * rowsPerPage;
            var endIndex = startIndex + rowsPerPage;

            tableRows.forEach(function(row, index) {
                if (index >= startIndex && index < endIndex) {
                    row.style.display = '';
                } else {
                    row.style.display = "none";
                }
            });
        }

        function updatePagination() {
            var paginationDiv = document.getElementById("pagination");
            paginationDiv.innerHTML = "";

            if (totalPages > 1) {
                var prevButton = document.createElement("button");
                prevButton.textContent = "Previous";
                prevButton.addEventListener("click", function() {
                    if (currentPage > 1) {
                        currentPage--;
                        showPage(currentPage);
                    }
                });
                paginationDiv.appendChild(prevButton);

                for (var i = 1; i <= totalPages; i++) {
                    var pageNumberSpan = document.createElement("span");
                    pageNumberSpan.textContent = i;
                    pageNumberSpan.addEventListener("click", function() {
                        currentPage = parseInt(this.textContent);
                        showPage(currentPage);
                    });
                    paginationDiv.appendChild(pageNumberSpan);
                }

                var nextButton = document.createElement("button");
                nextButton.textContent = "Next";
                nextButton.addEventListener("click", function() {
                    if (currentPage < totalPages) {
                        currentPage++;
                        showPage(currentPage);
                    }
                });
                paginationDiv.appendChild(nextButton);
            }
        }

        showPage(currentPage);
        updatePagination();
    });
</script>
</body>

</html>

