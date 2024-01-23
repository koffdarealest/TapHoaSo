package model;

import jakarta.persistence.*;

@Entity
public class Notice extends BaseAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeID;
    @ManyToOne
    private Buying buyID;
    private String content;
    private Boolean isAdminReceive;
    public Notice() {
    }

    public Notice(Buying buyID, String content, Boolean isAdminReceive) {
        this.buyID = buyID;
        this.content = content;
        this.isAdminReceive = isAdminReceive;
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
}
