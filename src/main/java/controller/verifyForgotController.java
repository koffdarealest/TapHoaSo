package controller;

import DAO.tokenDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(urlPatterns = {"/verifyForgot"})
public class verifyForgotController extends HttpServlet {

    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, java.io.IOException {
        String tk = req.getParameter("tk");
        handleTokenVerification(req, resp, tk);
    }

    private void handleTokenVerification(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp, String tk) throws jakarta.servlet.ServletException, java.io.IOException {
        tokenDAO tokenDAO = new tokenDAO();
        try {
            if (tokenDAO.isTokenExpired(tk)) {
                handleExpiredToken(req, resp, tk);
            } else {
                handleValidToken(req, resp, tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleExpiredToken(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp, String tk) throws jakarta.servlet.ServletException, java.io.IOException {
        tokenDAO tokenDAO = new tokenDAO();
        tokenDAO.deleteToken(tk);
        req.setAttribute("mess", "Your link is expired or invalid! Try again!");
        req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
    }

    private void handleValidToken(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp, String tk) throws jakarta.servlet.ServletException, java.io.IOException {
        req.setAttribute("token", tk);
        req.getRequestDispatcher("/view/resetPassword.jsp").forward(req, resp);
    }
}
