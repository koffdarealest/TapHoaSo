package model;

import jakarta.persistence.*;

@Entity
public class Conflict_History extends BaseAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conflictID;
    @ManyToOne
    private Buying buyID;
    private String content;
    public Conflict_History() {
    }

    public Conflict_History(Buying buyID, String content) {
        this.buyID = buyID;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
