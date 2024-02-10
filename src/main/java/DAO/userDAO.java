package DAO;

import model.Token;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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

    public void updatePassword(String gmail,String newPassword){
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = getUserByGmail(gmail);

            user.setPassword(newPassword);
            session.update(user);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();

        }
    }

    public User getUserByGmail(String gmail){
        User user = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            user = (User) session.createQuery("from User where email = :email")
                    .setParameter("email", gmail)
                    .uniqueResult();

            transaction.commit();
        } catch (Exception ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return user;
    }


    public boolean CheckValidUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            String userPassword = user.getPassword();
            System.out.println(userPassword);
            return verifyPassword(password, userPassword);
        }
        return false;
    }

    public byte[] getSecretKeyByUsername(String username) {
        List<User> listUsers = getAllUser();
        for (User user : listUsers) {
            if (user.getUsername().equals(username)) {
                return user.getSecretKey();
            }
        }
        return null;
    }

    public boolean checkExistUsername(String username) {
        List<User> listUsers = getAllUser();
        for (User user : listUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkExistEmail(String email) {
        List<User> listUsers = getAllUser();
        for (User user : listUsers) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public String encodePassword(String password){
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

    public boolean verifyPassword(String enteredPassword, String storedHash) {
        String enteredHash = encodePassword(enteredPassword);
        return enteredHash.equals(storedHash);
    }

    public User getUserByToken(String token) {
        User user = new User();
        Transaction transaction = null;
        try {
            Session session = Factory.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "select u from User u join Token t on u.userID = CAST(t.userID as biginteger) where t.token = :token";
            user = (User) session.createQuery(hql)
                    .setParameter("token", token)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    public void updateBalance(User user, Long balance) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            user.setBalance(balance);
            session.update(user);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        userDAO userDAO = new userDAO();
        //String token = "c375624c-ee2d-4b85-8e95-8627144f509b";
        //User user = userDAO.getUserByToken(token);
        List<User> lUsers = userDAO.getAllUser();
        System.out.println(lUsers);


    }




}