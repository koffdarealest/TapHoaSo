package controller;

import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import util.Encryption;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@WebServlet(name = "signup", urlPatterns = {"/signup"})
public class signupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        userDAO userDAO = new userDAO();
        Encryption encryption = new Encryption();
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("re-password");

        if(password.equals(rePassword)) {
            byte[] key;
            try {
                key = encryption.genAESKey();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            user = new User(
                    username,
                    password,
                    email,
                    fullname,
                    0L,
                    false,
                    key
            );
            user.setDelete(false);

            userDAO.insertUser(user);

            resp.sendRedirect(req.getContextPath() + "/index.jsp");

        } else {
            req.setAttribute("mess", "Passwords don't match! Try again!");
            req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);
        }
    }
}
