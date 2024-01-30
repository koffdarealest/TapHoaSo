package DAO;

import model.Token;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.Factory;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

public class tokenDAO {
    public boolean isTokenExpired(String token) {
        try {
            Session session = Factory.getSessionFactory().openSession();
            Token tk = session.get(Token.class, token);
            session.close();
            if (token == null) {
                return true;
            }
            return tk.getExpTime().isBefore(LocalDateTime.now());
        } catch (Exception e) {
            return true;
        }
    }

    public void saveToken(String gmail, String token1) {
        try {
            SessionFactory sessionFactory = Factory.getSessionFactory();
            if (sessionFactory != null) {
                Session session = sessionFactory.openSession();
                try {
                    Transaction tr = session.beginTransaction();
                    Token token = new Token();
                    token.setToken(token1);
                    token.setEmail(gmail);
                    token.setExpTime(java.time.LocalDateTime.now().plusMinutes(2));
                    session.save(token);
                    session.getTransaction().commit();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    session.close();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public String getEmailByToken(String token) {
        Session session = Factory.getSessionFactory().openSession();
        Token tk = session.get(Token.class, token);
        session.close();
        if (token == null) {
            return null;
        }
        return tk.getEmail();
    }

    public void deleteToken(String token) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(session.get(Token.class, token));

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public String generateToken() {
        return java.util.UUID.randomUUID().toString();
    }
    /*public static String encodeToken(String token) {
        StringBuilder encodedToken = new StringBuilder();
        for (char c : token.toCharArray()) {
            // Mã hóa từng ký tự bằng mã ASCII và thêm vào chuỗi mã hóa
            encodedToken.append(String.format("%%%02X", (int) c));
        }
        return encodedToken.toString();
    }
    public static String decodeToken(String encodedToken) throws Exception {
        return URLDecoder.decode(encodedToken, "UTF-8");
    }*/
    public static String encodeToken(String token) {
        return Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodeToken(String encodedToken) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedToken);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
    public String encodeUser(String password){
        MessageDigest md;
        String result = "";
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            BigInteger bi = new BigInteger(1, md.digest());

            result = bi.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        tokenDAO tokenDAO = new tokenDAO();
        String a = tokenDAO.getEmailByToken("1fb31a77-7ffe-4c6a-b59f-6caa8b8a84a4");
        System.out.println(a);

    }
}
