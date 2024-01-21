package model;

import jakarta.persistence.*;

@Entity
public class Notice extends AuditableBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeID;
    @ManyToOne
    private Buying buyID;
    private String content;
    private Boolean isAdminReceive;
    private User userReceive;

    public Notice() {
    }

    public Notice(Buying buyID, String content, Boolean isAdminReceive, User userReceive) {
        this.buyID = buyID;
        this.content = content;
        this.isAdminReceive = isAdminReceive;
        this.userReceive = userReceive;
    }

    public Long getNoticeID() {
        return noticeID;
    }

    public void setNoticeID(Long noticeID) {
        this.noticeID = noticeID;
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

    public Boolean getAdminReceive() {
        return isAdminReceive;
    }

    public void setAdminReceive(Boolean adminReceive) {
        isAdminReceive = adminReceive;
    }

    public User getUserReceive() {
        return userReceive;
    }

    public void setUserReceive(User userReceive) {
        this.userReceive = userReceive;
    }
}
