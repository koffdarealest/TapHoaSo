package model;

import lombok.Getter;
import lombok.Setter;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class VnPayReturnData {
    String transactionCode;
    String amount;
    String orderInfo;
    String responseCode;
    String transactionNo;
    String bankCode;
    String payDate;
    String transactionStatus;

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    // Phương thức mã hóa chuỗi thành dạng Base64
    public static String encodeString(String originalString) {
        byte[] encodedBytes = Base64.getEncoder().encode(originalString.getBytes());
        return new String(encodedBytes);
    }

    // Phương thức giải mã chuỗi Base64
    public static String decodeString(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString.getBytes());
        return new String(decodedBytes);
    }

    public String encode() {
        return encodeString(("transactionCode=" + transactionCode +
                "&amount=" + amount +
                "&orderInfo=" + orderInfo +
                "&responseCode=" + responseCode +
                "&transactionNo=" + transactionNo +
                "&bankCode=" + bankCode +
                "&payDate=" + payDate +
                "&transactionStatus=" + transactionStatus));
    }

    public static VnPayReturnData decode(String encodedData) {
        VnPayReturnData vnpayData = new VnPayReturnData();
        String[] params = decodeString(encodedData).split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            switch (key) {
                case "transactionCode":
                    vnpayData.setTransactionCode(value);
                    break;
                case "amount":
                    vnpayData.setAmount(value);
                    break;
                case "orderInfo":
                    vnpayData.setOrderInfo(value);
                    break;
                case "responseCode":
                    vnpayData.setResponseCode(value);
                    break;
                case "transactionNo":
                    vnpayData.setTransactionNo(value);
                    break;
                case "bankCode":
                    vnpayData.setBankCode(value);
                    break;
                case "payDate":
                    vnpayData.setPayDate(value);
                    break;
                case "transactionStatus":
                    vnpayData.setTransactionStatus(value);
                    break;
                default:
                    // Handle unknown key
                    break;
            }
        }
        return vnpayData;
    }

}
