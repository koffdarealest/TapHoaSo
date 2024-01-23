package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class User_Transaction_History extends BaseAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;
    @ManyToOne
    private User userID;
    private Date transactionDate;
    private Long amount;
    private String type;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String status;

    public User_Transaction_History() {
    }

    public User_Transaction_History(User userID, Date transactionDate, Long amount, String type, String description, String status) {
        this.userID = userID;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.status = status;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
