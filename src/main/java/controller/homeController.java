package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import DAO.userDAO;
import model.User;

@WebServlet(urlPatterns = {"/home"})
public class homeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (!checkSession(username, resp)) {
            return;
        }
        userDAO userDAO = new userDAO();
        User user = userDAO.getUserByUsername(username);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);
    }

    private boolean checkSession(String username, HttpServletResponse resp) throws IOException {
        if (username == null) {
            resp.sendRedirect("signin");
            return false;
        }
        return true;
    }
}
