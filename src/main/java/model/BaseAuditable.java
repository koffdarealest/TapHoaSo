package model;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
public class BaseAuditable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    private Boolean isDelete;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "deleted_by")
    private Long deletedBy;
    @Column(name = "create2")
    private LocalDateTime create2;
    @Column(name = "update2")
    private LocalDateTime update2;
    @Column(name = "delete2")
    private LocalDateTime delete2;

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

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDateTime getCreate2() {
        return create2;
    }

    public void setCreate2(LocalDateTime create2) {
        this.create2 = create2;
    }

    public LocalDateTime getUpdate2() {
        return update2;
    }

    public void setUpdate2(LocalDateTime update2) {
        this.update2 = update2;
    }

    public LocalDateTime getDelete2() {
        return delete2;
    }

    public void setDelete2(LocalDateTime delete2) {
        this.delete2 = delete2;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        isDelete = false;
        create2 = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
        update2 = LocalDateTime.now();
    }
    @PreRemove
    protected void onDelete() {
        deletedAt = new Date();
        delete2 = LocalDateTime.now();
    }
}
