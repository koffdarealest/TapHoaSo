package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String TransactionCode;
    private Long amount;
    private String orderInfo;
    private String vnpayResponseCode;
    private String vnpayTransactionCode;
    private String bankCode;
    private String payDate;
    private String paymentStatus;

    public Bill() {
    }

    public Bill(String TransactionCode, Long amount, String orderInfo, String vnpayResponseCode, String vnpayTransactionCode, String bankCode, Date payDate, String paymentStatus) {
        this.TransactionCode = TransactionCode;
        this.amount = amount;
        this.orderInfo = orderInfo;
        this.vnpayResponseCode = vnpayResponseCode;
        this.vnpayTransactionCode = vnpayTransactionCode;
        this.bankCode = bankCode;
        this.payDate = String.valueOf(payDate);
        this.paymentStatus = paymentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionCode() {
        return TransactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.TransactionCode = transactionCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getVnpayResponseCode() {
        return vnpayResponseCode;
    }

    public void setVnpayResponseCode(String vnpayResponseCode) {
        this.vnpayResponseCode = vnpayResponseCode;
    }

    public String getVnpayTransactionCode() {
        return vnpayTransactionCode;
    }

    public void setVnpayTransactionCode(String vnpayTransactionCode) {
        this.vnpayTransactionCode = vnpayTransactionCode;
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
