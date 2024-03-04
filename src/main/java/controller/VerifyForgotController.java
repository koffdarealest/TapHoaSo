package controller;

import dao.TokenDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/verifyForgot"})
public class VerifyForgotController extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws jakarta.servlet.ServletException, IOException {
        String tk = req.getParameter("tk");
        handleTokenVerification(req, resp, tk);
    }

    private void handleTokenVerification(HttpServletRequest req, HttpServletResponse resp, String tk) throws ServletException, IOException {
        TokenDAO tokenDAO = new TokenDAO();
//        if(tk == null) {
//            req.setAttribute("mess", "Your link is expired or unvalid! Try again!");
//            req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
//            return;
//        }
        try {
            if (tokenDAO.isTokenExpired(tk)) {
                handleExpiredToken(req, resp, tk);
            } else if (!isValidTokenType(tk)) {
                req.setAttribute("notification", "Your link is invalid! Try again!");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
            } else {
                req.setAttribute("token", tk);
                req.getRequestDispatcher("/WEB-INF/view/resetPassword.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleExpiredToken(HttpServletRequest req, HttpServletResponse resp, String tk) throws ServletException, IOException {
        TokenDAO tokenDAO = new TokenDAO();
        tokenDAO.deleteToken(tk);
        req.setAttribute("notification", "Your link is invalid! Try again!");
        req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
    }


    private boolean isValidTokenType(String tk) {
        TokenDAO tokenDAO = new TokenDAO();
        return tokenDAO.getTokenType(tk).equals("forgot");
    }


}
