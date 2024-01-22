package model;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public class AuditableBase {
    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Timestamp createdDt;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private Timestamp modifiedDt;

    private Boolean isDelete;

    private String deleteBy;

    private Timestamp deleteDt;

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }

    public Timestamp getDeleteDt() {
        return deleteDt;
    }

    public void setDeleteDt(Timestamp deleteDt) {
        this.deleteDt = deleteDt;
    }
}
