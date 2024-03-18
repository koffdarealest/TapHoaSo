package model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

@Entity
@Table(name = "vnpay_transaction")
public class VnPayTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "transaction_id", updatable = false, nullable = false)
    private String transactionId;

    @Column(name = "type", updatable = false, nullable = true, length = 20)
    private String type;

    @Column(name = "version", updatable = false, nullable = false, length = 8)
    private String version;

    @Column(name = "command", updatable = false, nullable = false, length = 256)
    private String command;

    @Column(name = "amount", updatable = false, nullable = false)
    private BigInteger amount;

    @Column(name = "current_code", updatable = false, nullable = false, length = 3)
    private String currentCode;

    @Column(name = "bank_code", updatable = false, length = 10)
    private String bankCode;

    @Column(name = "locale", updatable = false, nullable = false, length = 5)
    private String locale;

    @Column(name = "ip_addr", updatable = false, nullable = false, length = 45)
    private String ipAddr;

    @Column(name = "order_info", updatable = false, nullable = false)
    private String orderInfo;

    @Column(name = "order_type", updatable = false, length = 100)
    private String orderType;

    @Column(name = "create_date", updatable = false, nullable = false, length = 14)
    private String createDate;

    @Column(name = "expire_date", updatable = false, length = 14)
    private String expireDate;

    @Column(name = "pay_date", length = 14)
    private String payDate;

    @Column(name = "card_type", length = 20)
    private String cardType;

    @Column(name = "banktrans_no", length = 20)
    private String bankTransNo;

    @Column(name = "secure_hash", updatable = false, nullable = false, length = 256)
    private String secureHash;

    @Column(name = "transaction_no", nullable = false, length = 256)
    private String transactionNo;

    // Constructors, getters, setters
    // Constructors
    public VnPayTransaction() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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


    // Other getters and setters...
}