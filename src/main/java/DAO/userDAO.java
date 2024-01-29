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




}