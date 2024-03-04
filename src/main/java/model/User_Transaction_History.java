package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class User_Transaction_History extends BaseAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User userID;
    private Long amount;
    private String type;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Boolean isProcessed;

    public User_Transaction_History() {
    }

    public User_Transaction_History(User userID, Long amount, String type, String description, Boolean isProcessed) {
        this.userID = userID;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.isProcessed = isProcessed;
    }

    public Long getTransactionID() {
        return id;
    }

    public void setTransactionID(Long transactionID) {
        this.id = transactionID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
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

    public Boolean getProcessed() {
        return isProcessed;
    }

    public void setProcessed(Boolean processed) {
        isProcessed = processed;
    }
}
