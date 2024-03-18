package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import dao.UserDAO;
import model.User;

@WebServlet("/editProfile")
public class EditProfileController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (!checkSession(username, response, request)) {
            return;
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);
        request.setAttribute("user", user);
        request.getRequestDispatcher("WEB-INF/view/editProfile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Nhận thông tin từ form
        String username = (String) request.getSession().getAttribute("username");
        String newNickname = request.getParameter("nickname");
        if (!checkSession(username, response, request)) {
            return;
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);
        user.setNickname(newNickname);
        userDAO.updateUser(user);
        response.sendRedirect(request.getContextPath() + "/viewProfile");
    }

    private boolean checkSession(String username, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
            return false;
        }
        return true;
    }

}
