package model;

import jakarta.persistence.*;

@Entity
public class Image extends AuditableBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageID;
    @ManyToOne
    private Post postID;
    private String image;

    public Image() {
    }

    public Image(Post postID, String image) {
        this.postID = postID;
        this.image = image;
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
    }

    public Post getPostID() {
        return postID;
    }

    public void setPostID(Post postID) {
        this.postID = postID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
