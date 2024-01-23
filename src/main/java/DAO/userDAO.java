package DAO;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class userDAO {
    public void insertUser(User User){
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            session.save(User);

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public User getUserByUsername(String username){
        User users = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

//            users = session.get(User.class, username);
            users = (User) session.createQuery("from User where username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return users;
    }
    @SuppressWarnings("unchecked")
    public List<User> getAllUser(){
        List<User> ListUsers = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            ListUsers = session.createQuery("from User").getResultList();

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return ListUsers;
    }

    public void deleteUser(String username){
        Transaction transaction = null;
        User userDelete = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            userDelete = getUserByUsername(username);

            if(userDelete != null){
                session.delete(userDelete);
            }

            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void updateUser(User userUpdate){
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            session.update(userUpdate);

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

//    public String encodePassword(String password){
//        MessageDigest md;
//        String result = "";
//        try {
//            md = MessageDigest.getInstance("MD5");
//            md.update(password.getBytes());
//            BigInteger bi = new BigInteger(1, md.digest());
//
//            result = bi.toString(16);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

//    public boolean verifyPassword(String enteredPassword, String storedHash) {
//        String enteredHash = encodePassword(enteredPassword);
//        return enteredHash.equals(storedHash);
//    }

    public boolean CheckValidUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            String userPassword = user.getPassword();
            if (userPassword.equals(password)) {
                return true;
            }
        }
        return false;
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


    public static void main(String[] args) {
        User users = new User();
        userDAO userDAO = new userDAO();

        users = userDAO.getUserByUsername("tuta");
        System.out.println(users);


    }
}