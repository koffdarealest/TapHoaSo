package DAO;

import model.Token;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.Factory;

import java.time.LocalDateTime;

public class tokenDAO {
    public boolean isTokenExpired(String token) {
        Session session = Factory.getSessionFactory().openSession();
        Token tk = session.get(Token.class, token);
        session.close();
        if (token == null) {
            return true;
        }
        return tk.getExpTime().isBefore(LocalDateTime.now());
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
        Session session = Factory.getSessionFactory().openSession();
        Token tk = session.get(Token.class, token);
        session.close();
        if (token == null) {
            return;
        }
        session.delete(tk);
    }

    public String generateToken() {
        return java.util.UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        tokenDAO tokenDAO = new tokenDAO();
        String a = tokenDAO.getEmailByToken("1fb31a77-7ffe-4c6a-b59f-6caa8b8a84a4");
        System.out.println(a);

    }
}
