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
    private Boolean isRead;
    @ManyToOne
    private User userIDFrom;
    @ManyToOne
    private User userIDTo;

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

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public User getUserIDFrom() {
        return userIDFrom;
    }

    public void setUserIDFrom(User userIDFrom) {
        this.userIDFrom = userIDFrom;
    }

    public User getUserIDTo() {
        return userIDTo;
    }

    public void setUserIDTo(User userIDTo) {
        this.userIDTo = userIDTo;
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
