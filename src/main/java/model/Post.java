package model;

import jakarta.persistence.*;
import model.Users;

import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;

    @ManyToOne
    private Users sellerID;

    private String tradingCode;
    private String topic;
    private Long price;
    private Long fee;
    private Boolean sellerPayFee;

    @Column(columnDefinition = "TEXT")
    private String describe;
    private String describeImage;
    private String contact;

    @Column(columnDefinition = "TEXT")
    private String hidden;
    private Boolean isPublic;

    // Thêm các trường mới
    private boolean isDelete; //trường đánh dấu xóa mềm
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    private Users updatedBy;

    public Post(Long postID, Users sellerID, String tradingCode, String topic, Long price, Long fee, Boolean sellerPayFee, String describe, String describeImage, String contact, String hidden, Boolean isPublic, boolean isDelete, Date createdAt, Date updatedAt, Users updatedBy) {
        this.postID = postID;
        this.sellerID = sellerID;
        this.tradingCode = tradingCode;
        this.topic = topic;
        this.price = price;
        this.fee = fee;
        this.sellerPayFee = sellerPayFee;
        this.describe = describe;
        this.describeImage = describeImage;
        this.contact = contact;
        this.hidden = hidden;
        this.isPublic = isPublic;
        this.isDelete = isDelete;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public Users getSellerID() {
        return sellerID;
    }

    public void setSellerID(Users sellerID) {
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
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDescribeImage() {
        return describeImage;
    }

    public void setDescribeImage(String describeImage) {
        this.describeImage = describeImage;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Users getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Users updatedBy) {
        this.updatedBy = updatedBy;
    }
}
