package model;

import jakarta.persistence.*;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;

import java.util.Date;

@Entity
public class User_Transaction_History {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long TransactionID;
    @ManyToOne
    private Users UserID;
    private Date TransactionDate;
    private Long Amount;
    private String TransactionType;
    @Column(columnDefinition = "TEXT")
    private String Description;
    private String Status;

    public User_Transaction_History() {
    }

    public User_Transaction_History(Long transactionID, Users userID, Date transactionDate, Long amount, String transactionType, String description, String status) {
        TransactionID = transactionID;
        UserID = userID;
        TransactionDate = transactionDate;
        Amount = amount;
        TransactionType = transactionType;
        Description = description;
        Status = status;
    }

    public Long getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(Long transactionID) {
        TransactionID = transactionID;
    }

    public Users getUserID() {
        return UserID;
    }

    public void setUserID(Users userID) {
        UserID = userID;
    }

    public Date getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        TransactionDate = transactionDate;
    }

    public Long getAmount() {
        return Amount;
    }

    public void setAmount(Long amount) {
        Amount = amount;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
