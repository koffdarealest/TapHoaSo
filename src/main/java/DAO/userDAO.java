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
import java.sql.*;
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

//    public boolean checkUserSetDate(String userEmail) {
//        String sql = "SELECT * from [User] where userEmail = ? AND userIsSetDate = 1 ";
//
//        try {
//            Connection conn = DBUtils.getConnection();
//            PreparedStatement st = conn.prepareStatement(sql);
//            st.setString(1, userEmail);
//            ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//
//                return true;
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return false;
//    }
//    public boolean checkUserSetChangePassWord(String userEmail) {
//        String sql = "SELECT * from [User] where userEmail = ? AND userGoogleIsChangePassword = 1 ";
//
//        try {
//            Connection conn = DBUtils.getConnection();
//            PreparedStatement st = conn.prepareStatement(sql);
//            st.setString(1, userEmail);
//            ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//
//                return true;
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return false;
//    }
//    public String checkRegisterGmail(User u, String password, int role) {
//        String key = generateRandomString(10);
//
//        SendMail.send(u.getUserEmail(), "Verify new user!", "<div style=\"width: 100%; height: 70px; background-color: black; text-align: center;\"><h2 style=\"color: white; line-height: 70px;\">11-12 STUDIO</h2></div>" + "<h1 style=\"text-align: center;\">Welcome to 11twellCine</h1> " + "<div>Your password now is : " + password + "</div>"
//                + "<div>Please change the password to verify your account.</div>" + " <a href=\"http://localhost:8080/11twell/change-password?email=" + u.getUserEmail() + "&key=" + key
//                + "\" >Click here to change your password! </a> " + "<div style=\"width: 100%; height: 70px; background-color: black; margin-top: 30px;\"></div>");
//        return key;
//
//    }
//    public String register(User u, String password, int role) {
//        String key = generateRandomString(10);
//
//        String sql = "INSERT INTO [dbo].[User] \n"
//                + "           ([userName] \n"
//                + "           ,[userEmail] \n"
//                + "           ,[userPassword] \n"
//                + "           ,[userGender] \n"
//                + "           ,[userPhone] \n"
//                + "           ,[userRegion] \n"
//                + "           ,[userDOB] \n"
//                + "           ,[userRole] \n"
//                + "           ,[userIsActive] \n"
//                + "           ,[userIsSetDate] \n"
//                + "           ,[userGoogleIsChangePassword])\n"
//                + "     VALUES          \n"
//                + "          (?, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0) ";
//
//        try {
//            Connection conn = DBUtils.getConnection();
//            PreparedStatement st = conn.prepareStatement(sql);
//            st.setString(1, u.getUserName());
//            st.setString(2, u.getUserEmail());
//            st.setString(3, password);
//            st.setString(4, u.getUserGender());
//            st.setString(5, u.getUserPhone());
//            st.setString(6, u.getUserRegion());
//            st.setDate(7, u.getUserDOB());
//            st.setInt(8, role);
//
//            st.executeUpdate();
//            SendMail.send(u.getUserEmail(), "Verify new user!", "<div style=\"width: 100%; height: 70px; background-color: black; text-align: center;\"><h2 style=\"color: white; line-height: 70px;\">11-12 STUDIO</h2></div>" + "<h1 style=\"text-align: center;\">Welcome to 11twellCine</h1> " + "<div>Your password now is : " + password + "</div>"
//                    + "<div>Please change the password to verify your account.</div>" + " <a href=\"http://localhost:8080/11twell/change-password?email=" + u.getUserEmail() + "&key=" + key
//                    + "\" >Click here to change your password! </a> " + "<div style=\"width: 100%; height: 70px; background-color: black; margin-top: 30px;\"></div>");
//            return key;
//        } catch (SQLException e) {
//            System.out.println(e);
//            return null;
//        }
//    }
//    public User getUserGoogleRaw(String userEmail, String password) {
//        String sql = "SELECT [userID]\n"
//                + "      ,[userName]\n"
//                + "      ,[userEmail]\n"
//                + "      ,[userPassword]\n"
//                + "      ,[userGender]\n"
//                + "      ,[userPhone]\n"
//                + "      ,[userRegion]\n"
//                + "      ,[userDOB]\n"
//                + "      ,[userRole]\n"
//                + "      ,[userIsActive]\n"
//                + "  FROM [dbo].[User]\n"
//                + "  WHERE userEmail = ? \n"
//                + "  AND userPassword = ? \n"
//                + "  AND userIsActive = 0 ";
//
//        try {
//            Connection conn = DBUtils.getConnection();
//            PreparedStatement st = conn.prepareStatement(sql);
//            st.setString(1, userEmail);
//            st.setString(2, password);
//            ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//                userEmail = rs.getString("userEmail");
//                String userName = rs.getString("userName");
//                String userGender = rs.getString("userGender");
//                String userPhone = rs.getString("userPhone");
//                String userRegion = rs.getString("userRegion");
//                Date userDOB = rs.getDate("userDOB");
//                int userRole = rs.getInt("userRole");
//                int userID = rs.getInt("userID");
//                User u = new User(userID, userName, userEmail, userGender, userPhone, userRegion, userDOB, userRole);
//                return u;
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return null;
//    }
}