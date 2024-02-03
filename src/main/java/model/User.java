package model;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;
import DAO.userDAO;
import util.Encryption;

import java.util.UUID;
//@Builder
@Entity
public class User extends BaseAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private Long balance;
    private Boolean isAdmin;
    private Boolean isActivated;
    private byte[] secretKey;
    public User() {
    }

    public User(String username, String password, String email, String nickname, Long balance, Boolean isAdmin, boolean isActivated, byte[] secretKey) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.balance = balance;
        this.isAdmin = isAdmin;
        this.isActivated = isActivated;
        this.secretKey = secretKey;
    }
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public byte[] getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }
}
