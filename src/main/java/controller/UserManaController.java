package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import DAO.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/userManage"})
public class UserManaController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userDAO userDAO = new userDAO();
        //view user to list
        getAllUser(req, resp, userDAO);
    }

    private void getAllUser(HttpServletRequest req, HttpServletResponse resp, userDAO userDAO) {
        List<User> users = userDAO.getAllUser();

        List<User> userNotDeleted = new ArrayList<>();
        for (User user : users) {
            if (!user.getDelete()) {
                userNotDeleted.add(user);
            }
        }
        req.setAttribute("users", users);
        try {
            req.getRequestDispatcher("WEB-INF/view/ManageUser.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
