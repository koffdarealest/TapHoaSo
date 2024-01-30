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
import util.Encryption;

import java.io.IOException;
import java.net.URLDecoder;

@WebServlet(urlPatterns = {"/verifySignup"})
public class verifySignupController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tk = req.getParameter("tk");
        tokenDAO tokenDAO = new tokenDAO();
//        if(tk == null) {
//            req.setAttribute("mess", "Your link is expired or unvalid! Try again!");
//            req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
//            return;
//        }
        try {
            if(tokenDAO.isTokenExpired(tk)) {
                tokenDAO.deleteToken(tk);
                req.setAttribute("mess", "Your link is expired or unvalid! Try again!");
                req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
            } else {

                String jsonUser = req.getParameter("user");
                String userString = tokenDAO.decodeToken(jsonUser);
                Gson gson = new Gson();
                User user = gson.fromJson(userString, User.class);
                userDAO userDAO = new userDAO();
                userDAO.insertUser(user);
                tokenDAO.deleteToken(tk);
                req.setAttribute("mess", "Sign up successfully!");
                req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
