package util;

import dao.UserDAO;
import model.User;


import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        byte[] key;
        try {
            key = Encryption.genAESKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        user.setUsername("admin");
        String password = "admin";
        String hassedPassword = userDAO.encodePassword(password);
        user.setPassword(hassedPassword);
        user.setNickname("system_admin");
        user.setBalance(0L);
        user.setAdmin(true);
        user.setActivated(true);
        user.setSecretKey(key);
        user.setDelete(false);
        user.setEmail("taphoaso391@gmail.com");
        userDAO.insertUser(user);
    }

//    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//        User user = new User();
//        user.setUsername("tata");
//        String password = "11";
//        String hassedPassword = userDAO.encodePassword(password);
//        user.setPassword(hassedPassword);
//        user.setNickname("Lyly");
//        user.setBalance(10000L);
//        user.setAdmin(false);
//        user.setActivated(true);
//        userDAO.insertUser(user);
//    }
}
