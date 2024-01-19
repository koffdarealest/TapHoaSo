package model;

import jakarta.persistence.*;


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


    public Users() {
    }

    public Users(Long userID, String username, String password, String email, String nickname, Boolean isAdmin, Long balance) {
        UserID = userID;
        Username = username;
        Password = password;
        Email = email;
        Nickname = nickname;
        this.isAdmin = isAdmin;
        Balance = balance;
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
}
