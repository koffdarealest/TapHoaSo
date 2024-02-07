package controller;

import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;

@WebServlet(urlPatterns = {"/home"})
public class homeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = (String) req.getSession().getAttribute("username");
            userDAO userDAO = new userDAO();
            User user = userDAO.getUserByUsername(username);
            Long balance = user.getBalance();
            String nickname = user.getNickname();
            req.setAttribute("balance", balance);
            req.setAttribute("fullname", nickname);
            req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);
        } catch (Exception e) {
            req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);
            return;
        }





    }
}
