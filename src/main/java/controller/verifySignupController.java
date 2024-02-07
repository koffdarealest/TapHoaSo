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
                req.setAttribute("notification", "Your link is invalid! Try again!");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
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
        req.setAttribute("notification", "Your link is invalid! Try again!");
        req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
    }

    private void handleValidSignupToken(HttpServletRequest req, HttpServletResponse resp, String tk) throws Exception {
        userDAO userDAO = new userDAO();
        User user = userDAO.getUserByToken(tk);
        user.setActivated(true);
        userDAO.updateUser(user);
        tokenDAO tokenDAO = new tokenDAO();
        tokenDAO.deleteToken(tk);

        req.setAttribute("notification", "Sign up successfully! <a href=" + "signin" + ">Back to sign in</a>");
        req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
    }

    private boolean isValidTokenType(String tk) {
        tokenDAO tokenDAO = new tokenDAO();
        return tokenDAO.getTokenType(tk).equals("signup");
    }
}
