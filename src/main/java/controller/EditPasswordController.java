package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import dao.UserDAO;
import model.User;

@WebServlet("/editPassword")
public class EditPasswordController extends HttpServlet {
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
        String username = (String) request.getSession().getAttribute("username");
        String oldPassword = request.getParameter("password");
        String newPassword = request.getParameter("newpassword");
        String newPassword2 = request.getParameter("newpassword2");
        String captcha = request.getParameter("captcha");
        if (!checkSession(username, response, request)) {
            return;
        }
        if (!isTrueCaptcha(request, response, captcha)) {
            request.setAttribute("error", "Captcha is not correct! Try again!");
            request.getRequestDispatcher("WEB-INF/view/editProfile.jsp").forward(request, response);
            return;
        }
        if (isOldPasswordLikeNewPassword(oldPassword, newPassword)) {
            request.setAttribute("error", "Old password and new password are the same! Try again!");
            request.getRequestDispatcher("WEB-INF/view/editProfile.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);

        String passencode = userDAO.encodePassword(oldPassword);
        if (!user.getPassword().equals(passencode)) {
//            request.setAttribute("error", "");
//            userDAO userDAO = new userDAO();
//            User user = userDAO.getUserByUsername(username);
            request.setAttribute("user", user);
            request.setAttribute("error", "Old password is not correct! Try again!");
            request.getRequestDispatcher("WEB-INF/view/editProfile.jsp").forward(request, response);
            return;
        }


        if (!newPassword.equals(newPassword2)) {
            request.setAttribute("user", user);
            request.setAttribute("error", "Password and Re-Password are not the same! Try again!");
            request.getRequestDispatcher("WEB-INF/view/editProfile.jsp").forward(request, response);
            return;
        }

        String newPassencode = userDAO.encodePassword(newPassword);
        user.setPassword(newPassencode);
        userDAO.updateUser(user);
        request.setAttribute("user", user);
        request.setAttribute("success", "Change password successfully!");
        request.getRequestDispatcher("WEB-INF/view/editProfile.jsp").forward(request, response);
    }

    private boolean checkSession(String username, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
            return false;
        }
        return true;
    }

    private boolean isTrueCaptcha(HttpServletRequest req, HttpServletResponse resp, String enteredCaptcha) throws ServletException, IOException {
        try {
            String captcha = (String) req.getSession().getAttribute("captcha");
            if (enteredCaptcha.equalsIgnoreCase(captcha)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private boolean isOldPasswordLikeNewPassword(String oldPassword, String newPassword) {
        return oldPassword.equals(newPassword);
    }


}
