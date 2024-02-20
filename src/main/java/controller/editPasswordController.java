package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.userDAO;
import model.User;

@WebServlet("/editPassword")
public class editPasswordController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (!checkSession(username, response)) {
            return;
        }
        userDAO userDAO = new userDAO();
        User user = userDAO.getUserByUsername(username);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/view/editProfile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
//        String username = request.getParameter("username");
        String oldPassword = request.getParameter("password");
        String newPassword = request.getParameter("newpassword");
        String newPassword2 = request.getParameter("newpassword2");

        if (!checkSession(username, response)) {
            return;
        }

        userDAO userDAO = new userDAO();
        User user = userDAO.getUserByUsername(username);

       String passencode = userDAO.encodePassword(oldPassword);
        if (!user.getPassword().equals(passencode)) {
//            request.setAttribute("error", "");
//            userDAO userDAO = new userDAO();
//            User user = userDAO.getUserByUsername(username);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/view/editProfile.jsp").forward(request, response);
            return;
        }


        if (!newPassword.equals(newPassword2)) {
//            request.setAttribute("error", "");
            request.setAttribute("user", user);
//            request.getRequestDispatcher("/view/viewProfile.jsp").forward(request, response);
            request.getRequestDispatcher("/view/editProfile.jsp").forward(request, response);
            return;
        }

        // Cập nhật mật khẩu mới vào cơ sở dữ liệu
        String newPassencode = userDAO.encodePassword(newPassword);
        user.setPassword(newPassencode);
        userDAO.updateUser(user);
        response.sendRedirect("/viewProfile");
    }

    private boolean checkSession(String username, HttpServletResponse resp) throws IOException {
        if (username == null) {
            resp.sendRedirect("/signin");
            return false;
        }
        return true;
    }

}
