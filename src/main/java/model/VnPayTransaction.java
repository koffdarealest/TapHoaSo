package model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "vnpay_transaction")
public class VnPayTransaction implements Serializable {
    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "transaction_id", columnDefinition = "CHAR(36)")
    private String transactionId;

    @Column(name = "type", updatable = false, nullable = false, length = 20)
    private String type;

    @Column(name = "version", updatable = false, nullable = false, length = 8)
    private String version;

    @Column(name = "`command`", updatable = false, nullable = false, length = 16)
    private String command;

    @Column(name = "`amount`", updatable = false, nullable = false)
    private BigInteger amount;

    @Column(name = "`current_code`", updatable = false, nullable = false, length = 3)
    private String currentCode;

    @Column(name = "`bank_code`", updatable = false, length = 10)
    private String bankCode;

    @Column(name = "`locale`", length = 5)
    private String locale;

    @Column(name = "`ip_addr`", length = 45)
    private String ipAddr;

    @Column(name = "`order_info`")
    private String orderInfo;

    @Column(name = "`order_type`", length = 100)
    private String orderType;

    @Column(name = "`create_date`", updatable = false, nullable = false, length = 14)
    private String createDate;

    @Column(name = "`expire_date`", updatable = false, length = 14)
    private String expireDate;

    @Column(name = "`pay_date`", length = 14)
    private String payDate;

    @Column(name = "`card_type`", length = 20)
    private String cardType;

    @Column(name = "`banktrans_no`", length = 20)
    private String bankTransNo;

    @Column(name = "`secure_hash`", length = 256)
    private String secureHash;

    @Column(name = "`transaction_no`", length = 15)
    private String transactionNo;

    public VnPayTransaction() {
    }

    public VnPayTransaction(String id, String transactionId, String type, String version, String command, BigInteger amount, String currentCode, String bankCode, String locale, String ipAddr, String orderInfo, String orderType, String createDate, String expireDate, String payDate, String cardType, String bankTransNo, String secureHash, String transactionNo) {
        this.id = id;
        this.transactionId = transactionId;
        this.type = type;
        this.version = version;
        this.command = command;
        this.amount = amount;
        this.currentCode = currentCode;
        this.bankCode = bankCode;
        this.locale = locale;
        this.ipAddr = ipAddr;
        this.orderInfo = orderInfo;
        this.orderType = orderType;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.payDate = payDate;
        this.cardType = cardType;
        this.bankTransNo = bankTransNo;
        this.secureHash = secureHash;
        this.transactionNo = transactionNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getCurrentCode() {
        return currentCode;
    }

    public void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBankTransNo() {
        return bankTransNo;
    }

    public void setBankTransNo(String bankTransNo) {
        this.bankTransNo = bankTransNo;
    }

    public String getSecureHash() {
        return secureHash;
    }

    public void setSecureHash(String secureHash) {
        this.secureHash = secureHash;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }
}