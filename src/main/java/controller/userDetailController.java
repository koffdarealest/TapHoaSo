package controller;

import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/userDetail"})
public class userDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userDAO userDAO = new userDAO();
        String idUser = req.getParameter("id");
        User listUser = userDAO.getUserByUserID(Long.parseLong(idUser));
        System.out.println("user details: " + listUser.getBalance());
        req.setAttribute("user", listUser);
        req.getRequestDispatcher("/WEB-INF/view/UserDetails.jsp").forward(req, resp);
    }
}
