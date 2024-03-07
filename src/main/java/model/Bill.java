package model;

import jakarta.persistence.*;


@Entity
@Table(name = "bills")
public class Bill {
    @Id
    private String vnp_TxnRef; // Giả sử vnp_TxnRef là khóa chính

    private String status;

    // Các phương thức getter và setter
    public String getVnp_TxnRef() {
        return vnp_TxnRef;
    }

    public void setVnp_TxnRef(String vnp_TxnRef) {
        this.vnp_TxnRef = vnp_TxnRef;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
