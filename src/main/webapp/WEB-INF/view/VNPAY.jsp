<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <title>VNPAY Demo</title>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Custom styles for this template -->
    <link href="../../assets/css/jumbotron-narrow.css" rel="stylesheet">
    <script src="../../assets/js/jquery-1.11.3.min.js"></script>
</head>

<body>

<div class="container">
    <div class="header clearfix">

        <h3 class="text-muted">VNPAY DEMO</h3>
    </div>
    <h3>New payment</h3>
    <div class="table-responsive">
        <form action="payment" id="frmCreateOrder" method="post">
            <div class="form-group">
                <label for="amount">Total Amount: </label>
                <input class="form-control" data-val="true" data-val-number="The field Amount must be a number." data-val-required="The Amount field is required."
                       id="amount" max="100000000" min="10000" name="amount" type="text" />
            </div>
            <h4>Choose payment method</h4>
            <div class="form-group">
                <h5> Redirect to VNPAY Portal to choose payment method</h5>
                <input type="radio" Checked="True" id="bankCode" name="bankCode" value="VNBANK">
                <label for="bankCode">Payment portal VNPAYQR</label><br>
            </div>
            <div class="form-group">
                <h5>Select payment interface language:</h5>
                <input type="radio" id="language" Checked="True" name="language" value="vn">
                <label for="language">Vietnamese</label><br>
                <input type="radio" id="language" name="language" value="en">
                <label for="language">English</label><br>

            </div>
            <button type="submit" class="btn btn-default" href>Submit Payment</button>
            <button type="button" class="btn btn-default" onclick="home()">Back to Home</button>
        </form>
    </div>
    <p>
        &nbsp;
    </p>
    <footer class="footer">
        <p>&copy; VNPAY</p>
    </footer>
</div>

<link href="https://pay.vnpay.vn/lib/vnpay/vnpay.css" rel="stylesheet" />
<script src="https://pay.vnpay.vn/lib/vnpay/vnpay.min.js"></script>
<script type="text/javascript">
    function home() {
        window.location.href = './home';
    }
</script>
</body>
</html>