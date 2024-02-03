package controller;

import DAO.tokenDAO;
import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/reset"})
public class resetPwdController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            String token = req.getParameter("token");
//            req.setAttribute("token", token);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        req.getRequestDispatcher("/view/resetPassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String password = req.getParameter("password");
//        String rePassword = req.getParameter("re-password");
//        String token = req.getParameter("token");
//        System.out.println(token);
//        if(password.equals(rePassword)) {
//            tokenDAO tokenDAO = new tokenDAO();
//            userDAO userDAO = new userDAO();
//            String email = tokenDAO.getEmailByToken(token);
//            User user =  userDAO.getUserByGmail(email);
//            user.setPassword(password);
//            userDAO.updateUser(user);
//            tokenDAO.deleteToken(token);
//            req.setAttribute("mess", "Reset password successfully!");
//            req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
//        } else {
//            req.setAttribute("token", token);
//            req.setAttribute("mess", "Reset password failed! Try again!");
//            req.getRequestDispatcher("/view/resetPassword.jsp").forward(req, resp);
//        }
        if(!isTrueCaptcha(req, resp)) {
            req.setAttribute("error", "Captcha is not correct! Try again!");
            req.setAttribute("token", req.getParameter("token"));
            req.getRequestDispatcher("/view/resetPassword.jsp").forward(req, resp);
            return;
        }
        if(isExpiredToken(req, resp)) {
            req.setAttribute("mess", "Your link is expired or invalid! Try again!");
            req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
        } else {
            updatePassword(req, resp);
        }
    }

    private HashMap<String, String> getParams(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> params = new HashMap<>();
        try {
            String password = req.getParameter("password");
            String rePassword = req.getParameter("re-password");
            String token = req.getParameter("token");
            params.put("password", password);
            params.put("re-password", rePassword);
            params.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    private void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> params = getParams(req, resp);
        String password = params.get("password");
        String rePassword = params.get("re-password");
        String token = params.get("token");
        if (password.equals(rePassword)) {
            tokenDAO tokenDAO = new tokenDAO();
            userDAO userDAO = new userDAO();
            User user = userDAO.getUserByToken(token);
            String hashedPassword = userDAO.encodePassword(password);
            user.setPassword(hashedPassword);
            userDAO.updateUser(user);
            tokenDAO.deleteToken(token);
            req.setAttribute("mess", "Reset password successfully!");
            req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
        } else {
            req.setAttribute("token", token);
            req.setAttribute("error", "Reset password failed! Passwords don't match!");
            req.getRequestDispatcher("/view/resetPassword.jsp").forward(req, resp);
        }
    }

    private boolean isExpiredToken(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> params = getParams(req, resp);
        String token = params.get("token");
        tokenDAO tokenDAO = new tokenDAO();
        return tokenDAO.isTokenExpired(token);
    }

    private boolean isTrueCaptcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String enteredCaptcha = req.getParameter("captcha");
        String captcha = (String) req.getSession().getAttribute("captcha");
        if (!enteredCaptcha.equals(captcha)) {
            try {
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
