package model;

import jakarta.persistence.*;

import java.util.Date;


@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long UserID;
    private String Username;
    private String Password;
    private String Email;
    private String Nickname;
    private Boolean isAdmin;
    private Long Balance;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    private boolean isDelete;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;


    public Users() {
    }

    public Users(Long userID, String username, String password, String email, String nickname, Boolean isAdmin, Long balance, Date createAt, Date updateAt, boolean isDelete, Date deleteAt) {
        UserID = userID;
        Username = username;
        Password = password;
        Email = email;
        Nickname = nickname;
        this.isAdmin = isAdmin;
        Balance = balance;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isDelete = isDelete;
        this.deleteAt = deleteAt;
    }

    public Long getUserID() {
        return UserID;
    }

    public void setUserID(Long userID) {
        UserID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Long getBalance() {
        return Balance;
    }

    public void setBalance(Long balance) {
        Balance = balance;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }
}
