package controller;

import DAO.tokenDAO;
import DAO.userDAO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;

@WebServlet(urlPatterns = {"/verifySignup"})
public class verifySignupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tk = req.getParameter("tk");
        handleSignupVerification(req, resp, tk);
    }

    private void handleSignupVerification(HttpServletRequest req, HttpServletResponse resp, String tk) throws IOException {
        tokenDAO tokenDAO = new tokenDAO();
        try {
            if (tokenDAO.isTokenExpired(tk)) {
                handleExpiredToken(req, resp, tk);
            } else if (!isValidTokenType(tk)) {
                req.setAttribute("mess", "Your link is invalid! Try again!");
                req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
            } else {
                handleValidSignupToken(req, resp, tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleExpiredToken(HttpServletRequest req, HttpServletResponse resp, String tk) throws IOException, ServletException {
        tokenDAO tokenDAO = new tokenDAO();
        tokenDAO.deleteToken(tk);
        req.setAttribute("mess", "Your link is expired or invalid! Try again!");
        req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
    }

    private void handleValidSignupToken(HttpServletRequest req, HttpServletResponse resp, String tk) throws Exception {
        userDAO userDAO = new userDAO();
        User user = userDAO.getUserByToken(tk);
        user.setActivated(true);
        userDAO.updateUser(user);
        tokenDAO.deleteToken(tk);

        req.setAttribute("mess", "Sign up successfully!");
        req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
    }

    private boolean isValidTokenType(String tk) {
        tokenDAO tokenDAO = new tokenDAO();
        return tokenDAO.getTokenType(tk).equals("signup");
    }
}
