package model;

import jakarta.persistence.*;

@Entity
public class Buying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BuyID;
    @OneToOne
    private Post PostID;
    @ManyToOne
    private Users BuyerID;
    private String Status;

    public Buying(Long buyID, Post postID, Users buyerID, String status) {
        BuyID = buyID;
        PostID = postID;
        BuyerID = buyerID;
        Status = status;
    }

    public Long getBuyID() {
        return BuyID;
    }

    public void setBuyID(Long buyID) {
        BuyID = buyID;
    }

    public Post getPostID() {
        return PostID;
    }

    public void setPostID(Post postID) {
        PostID = postID;
    }

    public Users getBuyerID() {
        return BuyerID;
    }

    public void setBuyerID(Users buyerID) {
        BuyerID = buyerID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
