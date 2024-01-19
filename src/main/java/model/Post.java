package model;


import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PostID;
    @ManyToOne
    private Users SellerID;
    private String TradingCode;
    private String Topic;
    private Long Price;
    private Long Fee;
    private Boolean SellerPayFee;
    @Column(columnDefinition = "TEXT")
    private String Describe;
    private String DescribeImage;
    private String Contact;
    @Column(columnDefinition = "TEXT")
    private String Hidden;
    private Boolean isPublic;

}
