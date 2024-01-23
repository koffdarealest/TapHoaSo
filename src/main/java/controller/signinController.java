package controller;

import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.Encryption;
import java.io.IOException;


@WebServlet(urlPatterns = {"/signin"})

public class signinController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        userDAO userDAO = new userDAO();
        Encryption encryption = new Encryption();
        if (userDAO.checkValidUser(username, password)) {
            req.getSession().setAttribute("username", username);
            String sessionID = req.getSession().getId();
            byte[] key = userDAO.getSecretKey(username);
            try {                                                               //encrypt sessionID and send it to client
                String eSID = encryption.encrypt(sessionID, key);
                Cookie sessionIDCookie = new Cookie("sID", eSID);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (remember != null) {                                             //if remember me is checked, encrypt password and send it to client
                try {
                    String ePwd = encryption.encrypt(password, key);
                    Cookie passwordCookie = new Cookie("pwd", ePwd);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            req.setAttribute("error", "Wrong username or password");
            req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
        }
    }
}
