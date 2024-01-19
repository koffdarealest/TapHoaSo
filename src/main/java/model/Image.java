package model;

import model.Post;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ImageID;
    @ManyToOne
    private Post PostID;
    private byte[] image;

    // Thêm các trường mới
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private String createBy;
    private boolean isDelete; //them truong xoa mem
    @Temporal(TemporalType.TIMESTAMP)
    private String deletedAt;
    private String deletedBy;

    public Image(Long imageID, Post postID, byte[] image, Date createdAt, Date updatedAt, String createBy, boolean isDelete, String deletedAt, String deletedBy) {
        ImageID = imageID;
        PostID = postID;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
    }

    public Long getImageID() {
        return ImageID;
    }

    public void setImageID(Long imageID) {
        ImageID = imageID;
    }

    public Post getPostID() {
        return PostID;
    }

    public void setPostID(Post postID) {
        PostID = postID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
}
