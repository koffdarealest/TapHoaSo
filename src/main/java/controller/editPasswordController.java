package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


import model.User;

@WebServlet("/editPassword")
public class EditPasswordController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (!checkSession(username, response)) {
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

        if (!checkSession(username, response)) {
            return;
        }

        if (!checkNull(oldPassword, response) || !checkNull(newPassword, response) || !checkNull(newPassword2, response)){
            request.setAttribute("error", "Password null! Try again!");
            request.getRequestDispatcher("WEB-INF/view/editProfile.jsp").forward(request, response);
            return;
        }

        // Kiểm tra captcha
        if (!isTrueCaptcha(request, response, captcha)) {
            request.setAttribute("error", "Captcha is not correct! Try again!");
            request.getRequestDispatcher("WEB-INF/view/editProfile.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu cũ và mới có giống nhau không
        if (isOldPasswordLikeNewPassword(oldPassword, newPassword)) {
            request.setAttribute("error", "Old password and new password are the same! Try again!");
            request.getRequestDispatcher("WEB-INF/view/editProfile.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);

        String passencode = userDAO.encodePassword(oldPassword);
        if (!user.getPassword().equals(passencode)) {
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

    private boolean checkSession(String username, HttpServletResponse resp) throws IOException {
        if (username == null) {
            resp.sendRedirect("/signin");
            return false;
        }
        return true;
    }

    private boolean checkNull(String pass, HttpServletResponse resp) throws IOException {
        if (pass == null || pass.isEmpty()) {
//            resp.sendRedirect("/editProfile");
            return false;
        }
        return true;
    }

    private boolean isTrueCaptcha(HttpServletRequest req, HttpServletResponse resp, String enteredCaptcha) throws ServletException, IOException {
        try {
            String captcha = (String) req.getSession().getAttribute("captcha");
            if (enteredCaptcha.equals(captcha)) {
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
