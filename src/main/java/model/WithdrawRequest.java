package model;

import jakarta.persistence.*;

@Entity
public class WithdrawRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User userID;

    private String withdrawCode;
    private String bankingInfo;
    private Long amount;

    public WithdrawRequest() {
    }

    public WithdrawRequest(User userID, String withdrawCode, String bankingInfo, Long amount) {
        this.userID = userID;
        this.bankingInfo = bankingInfo;
        this.amount = amount;
        this.withdrawCode = withdrawCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public String getWithdrawCode() {
        return withdrawCode;
    }

    public void setWithdrawCode(String withdrawCode) {
        this.withdrawCode = withdrawCode;
    }

    public String getBankingInfo() {
        return bankingInfo;
    }

    public void setBankingInfo(String bankingInfo) {
        this.bankingInfo = bankingInfo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
