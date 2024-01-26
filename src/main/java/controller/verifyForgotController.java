package controller;

import DAO.tokenDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(urlPatterns = {"/verifyForgot"})
public class verifyForgotController extends HttpServlet {
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, java.io.IOException {
        String tk = req.getParameter("tk");
        tokenDAO tokenDAO = new tokenDAO();
        try {
            if(tokenDAO.isTokenExpired(tk)) {
                req.setAttribute("mess", "Your link is expired or unvalid! Try again!");
                req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
            } else {
                req.setAttribute("token", tk);
                req.getRequestDispatcher("/view/resetPassword.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
