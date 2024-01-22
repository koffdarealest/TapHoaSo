package model;

import jakarta.persistence.*;

@Entity
public class Buying extends AuditableBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyID;
    @OneToOne
    private Post postID;
    @ManyToOne
    private User buyerID;
    private String status;

    public Buying() {
    }

    public Buying(Post postID, User buyerID, String status) {
        this.postID = postID;
        this.buyerID = buyerID;
        this.status = status;
    }

    public Long getBuyID() {
        return buyID;
    }

    public void setBuyID(Long buyID) {
        this.buyID = buyID;
    }

    public Post getPostID() {
        return postID;
    }

    public void setPostID(Post postID) {
        this.postID = postID;
    }

    public User getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(User buyerID) {
        this.buyerID = buyerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
