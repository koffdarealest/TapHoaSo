package controller;

import dao.TokenDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;

@WebServlet(urlPatterns = {"/verifySignup"})
public class VerifySignupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tk = req.getParameter("tk");
        handleSignupVerification(req, resp, tk);
    }

    private void handleSignupVerification(HttpServletRequest req, HttpServletResponse resp, String tk) throws IOException {
        TokenDAO tokenDAO = new TokenDAO();
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
        TokenDAO tokenDAO = new TokenDAO();
        tokenDAO.deleteToken(tk);
        req.setAttribute("notification", "Your link is invalid! Try again!");
        req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
    }

    private void handleValidSignupToken(HttpServletRequest req, HttpServletResponse resp, String tk) throws Exception {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByToken(tk);
        user.setActivated(true);
        userDAO.updateUser(user);
        TokenDAO tokenDAO = new TokenDAO();
        tokenDAO.deleteToken(tk);

        req.setAttribute("notification", "Sign up successfully! <a href=" + "signin" + ">Back to sign in</a>");
        req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
    }

    private boolean isValidTokenType(String tk) {
        TokenDAO tokenDAO = new TokenDAO();
        return tokenDAO.getTokenType(tk).equals("signup");
    }
}
