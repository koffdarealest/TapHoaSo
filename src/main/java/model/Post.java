package model;

import jakarta.persistence.*;

@Entity
public class Post extends BaseAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    @ManyToOne
    private User sellerID;
    private String tradingCode;
    private String topic;
    private Long price;
    private Long fee;
    private Boolean sellerPayFee;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String contact;
    @Column(columnDefinition = "TEXT")
    private String hidden;
    private Boolean isPublic;

    public Post() {
    }

    public Post(User sellerID, String tradingCode, String topic, Long price, Long fee, Boolean sellerPayFee, String description, String contact, String hidden, Boolean isPublic) {
        this.sellerID = sellerID;
        this.tradingCode = tradingCode;
        this.topic = topic;
        this.price = price;
        this.fee = fee;
        this.sellerPayFee = sellerPayFee;
        this.description = description;
        this.contact = contact;
        this.hidden = hidden;
        this.isPublic = isPublic;
    }

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public User getSellerID() {
        return sellerID;
    }

    public void setSellerID(User sellerID) {
        this.sellerID = sellerID;
    }

    public String getTradingCode() {
        return tradingCode;
    }

    public void setTradingCode(String tradingCode) {
        this.tradingCode = tradingCode;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Boolean getSellerPayFee() {
        return sellerPayFee;
    }

    public void setSellerPayFee(Boolean sellerPayFee) {
        this.sellerPayFee = sellerPayFee;
    }

    public String getDescribe() {
        return description;
    }

    public void setDescribe(String describe) {
        this.description = describe;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
