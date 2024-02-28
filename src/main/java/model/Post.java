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
    private String whoPayFee;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String contact;
    @Column(columnDefinition = "TEXT")
    private String hidden;
    private Boolean isPublic;
    private String status;
    @ManyToOne
    private User buyerID;
    private Long totalSpendForBuyer;
    private Long totalReceiveForSeller;
    private Boolean updateable;
    private Boolean canBuyerComplain;

    public Post() {
    }

    public Post(User sellerID, String tradingCode, String topic, Long price, Long fee, String whoPayFee, String description,
                String contact, String hidden, Boolean isPublic, String status, User buyerID,
                Long totalSpendForBuyer, Long totalReceiveForSeller, Boolean updateable, Boolean canBuyerComplain) {
        this.sellerID = sellerID;
        this.tradingCode = tradingCode;
        this.topic = topic;
        this.price = price;
        this.fee = fee;
        this.whoPayFee = whoPayFee;
        this.description = description;
        this.contact = contact;
        this.hidden = hidden;
        this.isPublic = isPublic;
        this.status = status;
        this.buyerID = buyerID;
        this.totalSpendForBuyer = totalSpendForBuyer;
        this.totalReceiveForSeller = totalReceiveForSeller;
        this.updateable = updateable;
        this.canBuyerComplain = canBuyerComplain;
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

    public String getWhoPayFee() {
        return whoPayFee;
    }

    public void setWhoPayFee(String whoPayFee) {
        this.whoPayFee = whoPayFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String describe) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(User buyerID) {
        this.buyerID = buyerID;
    }

    public Long getTotalSpendForBuyer() {
        return totalSpendForBuyer;
    }

    public void setTotalSpendForBuyer(String whoPayFee) {
        Long totalSpendForBuyer = 0L;
        if(whoPayFee.equals("buyer")) {
            totalSpendForBuyer = this.price + this.fee;
        } else if(whoPayFee.equals("half")) {
            totalSpendForBuyer = this.price + (this.fee / 2);
        } else {
            totalSpendForBuyer = this.price;
        }

        this.totalSpendForBuyer = totalSpendForBuyer;
    }

    public Long getTotalReceiveForSeller() {
        return totalReceiveForSeller;
    }

    public void setTotalReceiveForSeller(String whoPayFee) {
        Long totalReceiveForSeller = 0L;
        if(whoPayFee.equals("seller")) {
            totalReceiveForSeller = this.price - this.fee;
        } else if(whoPayFee.equals("half")) {
            totalReceiveForSeller = this.price - (this.fee / 2);
        } else {
            totalReceiveForSeller = this.price;
        }
        this.totalReceiveForSeller = totalReceiveForSeller;
    }

    public Boolean getUpdateable() {
        return updateable;
    }

    public void setUpdateable(Boolean updateable) {
        this.updateable = updateable;
    }

    public Boolean getCanBuyerComplain() {
        return canBuyerComplain;
    }

    public void setCanBuyerComplain(Boolean canBuyerComplain) {
        this.canBuyerComplain = canBuyerComplain;
    }
}
