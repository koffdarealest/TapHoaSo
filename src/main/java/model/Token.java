package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Token {
    @Id
    private String token;
    private String email;
    @CreationTimestamp
    private LocalDateTime expTime;

    public Token() {
    }

    public Token(String token, String email, LocalDateTime expTime) {
        this.token = token;
        this.email = email;
        this.expTime = expTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getExpTime() {
        return expTime;
    }

    public void setExpTime(LocalDateTime expTime) {
        this.expTime = expTime;
    }
}
