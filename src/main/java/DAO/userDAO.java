package DAO;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class userDAO {
    public List<User> getAllUser() {
        List<User> listUsers = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            listUsers = session.createQuery("from User").getResultList();

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return listUsers;
    }

    public boolean checkValidUser(String username, String password) {
        List<User> listUsers = getAllUser();
        for (User user : listUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public String randomString(int length) {
        byte[] bytes = new byte[length];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        String str = bytes.toString();
        return str;
    }

    public byte[] getSecretKey(String username) {
        List<User> listUsers = getAllUser();
        for (User user : listUsers) {
            if (user.getUsername().equals(username)) {
                return user.getSecretKey();
            }
        }
        return null;
    }

}
