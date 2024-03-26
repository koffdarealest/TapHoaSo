package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Bill {
    @Id
    private String id;

    private String type;
    private short command;
    private Long userId;
    private Long amount;
    private String paymentStatus;

    public Bill() {
    }

    public Bill(String id, String type, short command, Long userId, Long amount, String paymentStatus) {
        this.id = id;
        this.type = type;
        this.command = command;
        this.userId = userId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public short getCommand() {
        return command;
    }

    public void setCommand(short command) {
        this.command = command;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
