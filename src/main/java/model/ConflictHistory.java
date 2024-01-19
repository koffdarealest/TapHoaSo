package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class ConflictHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ConflictID;
    @OneToOne
    private Buying BuyID;
    private Long ConflictByBuyer;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    private String createBy;

    public ConflictHistory(Long conflictID, Buying buyID, Long conflictByBuyer, Date createAt, String createBy) {
        ConflictID = conflictID;
        BuyID = buyID;
        ConflictByBuyer = conflictByBuyer;
        this.createAt = createAt;
        this.createBy = createBy;
    }

    public Long getConflictID() {
        return ConflictID;
    }

    public void setConflictID(Long conflictID) {
        ConflictID = conflictID;
    }

    public Buying getBuyID() {
        return BuyID;
    }

    public void setBuyID(Buying buyID) {
        BuyID = buyID;
    }

    public Long getConflictByBuyer() {
        return ConflictByBuyer;
    }

    public void setConflictByBuyer(Long conflictByBuyer) {
        ConflictByBuyer = conflictByBuyer;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
