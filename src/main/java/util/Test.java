package util;

import dao.UserDAO;
import model.User;

public class Test {
//    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//        User user = new User();
//        user.setUsername("admin");
//        String password = "admin";
//        String hassedPassword = userDAO.encodePassword(password);
//        user.setPassword(hassedPassword);
//        user.setNickname("system_admin");
//        user.setBalance(1000000L);
//        user.setAdmin(true);
//        user.setActivated(true);
//        userDAO.insertUser(user);
//
//
//    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setUsername("phuc");
        String password = "11";
        String hassedPassword = userDAO.encodePassword(password);
        user.setPassword(hassedPassword);
        user.setNickname("Lyly");
        user.setBalance(10000L);
        user.setAdmin(false);
        user.setActivated(true);
        userDAO.insertUser(user);
    }

//    public static void main(String[] args) {
//        User user = new User();
//        UserDAO userDAO = new UserDAO();
//        user = userDAO.getUserByUsername("phucly");
//        String hash = userDAO.encodePassword("1234");
//        user.setPassword(hash);
//        userDAO.updateUser(user);
//
//    }
}
