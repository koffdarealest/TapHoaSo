package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long NotifyID;
    @ManyToOne
    private Buying BuyID;
    private String content;
    private boolean IsAdminReceice;
    private Long UserReceiceID;
    private boolean isDeleted; // Trường đánh dấu xóa mềm
    private Date createdAt;
    private Long createdBy;

    public Notification(Long notifyID, Buying buyID, String content, boolean isAdminReceice, Long userReceiceID, boolean isDeleted, Date createdAt, Long createdBy) {
        NotifyID = notifyID;
        BuyID = buyID;
        this.content = content;
        IsAdminReceice = isAdminReceice;
        UserReceiceID = userReceiceID;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public Long getNotifyID() {
        return NotifyID;
    }

    public void setNotifyID(Long notifyID) {
        NotifyID = notifyID;
    }

    public Buying getBuyID() {
        return BuyID;
    }

    public void setBuyID(Buying buyID) {
        BuyID = buyID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAdminReceice() {
        return IsAdminReceice;
    }

    public void setAdminReceice(boolean adminReceice) {
        IsAdminReceice = adminReceice;
    }

    public Long getUserReceiceID() {
        return UserReceiceID;
    }

    public void setUserReceiceID(Long userReceiceID) {
        UserReceiceID = userReceiceID;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
