package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Conflict_History extends AuditableBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conflictID;
    private Buying buyID;
    private User userSend;

    public Conflict_History() {
    }

    public Conflict_History(Buying buyID, User userSend) {
        this.buyID = buyID;
        this.userSend = userSend;
    }

    public Long getConflictID() {
        return conflictID;
    }

    public void setConflictID(Long conflictID) {
        this.conflictID = conflictID;
    }

    public Buying getBuyID() {
        return buyID;
    }

    public void setBuyID(Buying buyID) {
        this.buyID = buyID;
    }

    public User getUserSend() {
        return userSend;
    }

    public void setUserSend(User userSend) {
        this.userSend = userSend;
    }
}
