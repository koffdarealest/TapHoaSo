package util;

import dao.UserDAO;
import model.User;

public class Test {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setUsername("admin");
        String password = "admin";
        String hassedPassword = userDAO.encodePassword(password);
        user.setPassword(hassedPassword);
        user.setNickname("system_admin");
        user.setBalance(1000000L);
        user.setAdmin(true);
        user.setActivated(true);
        userDAO.insertUser(user);


    }
}
