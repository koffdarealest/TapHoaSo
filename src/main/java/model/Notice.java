package model;

import jakarta.persistence.*;

@Entity
public class Notice extends BaseAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Post postID;
    private String content;
    private Boolean isAdminReceive;
    private Boolean isRead = false;

    public Notice() {
    }

    public Notice(Post postID, String content, Boolean isAdminReceive) {
        this.postID = postID;
        this.content = content;
        this.isAdminReceive = isAdminReceive;
    }

    public Long getNoticeID() {
        return id;
    }

    public void setNoticeID(Long noticeID) {
        this.id = noticeID;
    }

    public Post getPostID() {
        return postID;
    }

    public void setPostID(Post postID) {
        this.postID = postID;
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
