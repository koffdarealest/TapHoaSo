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
        String password = req.getParameter("password");
        String rePassword = req.getParameter("re-password");
        String token = req.getParameter("token");
        System.out.println(token);
        if(password.equals(rePassword)) {
            tokenDAO tokenDAO = new tokenDAO();
            userDAO userDAO = new userDAO();
            String email = tokenDAO.getEmailByToken(token);
            User user =  userDAO.getUserByGmail(email);
            user.setPassword(password);
            userDAO.updateUser(user);
            req.setAttribute("mess", "Reset password successfully!");
            req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
        } else {
            req.setAttribute("token", token);
            req.setAttribute("mess", "Reset password failed! Try again!");
            req.getRequestDispatcher("/view/resetPassword.jsp").forward(req, resp);
        }
    }
}
