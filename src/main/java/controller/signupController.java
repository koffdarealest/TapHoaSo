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
import util.EmailUtility;
import util.Encryption;

import java.io.IOException;
import java.net.URLEncoder;
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

        if(userDAO.checkExistEmail(email)) {
            req.setAttribute("error", "Email already exists! Try again!");
            req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);
            return;
        }
        if(userDAO.checkExistUsername(username)) {
            req.setAttribute("error", "Username already exists! Try again!");
            req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);
            return;
        }

        if(password.equals(rePassword)) {
            tokenDAO tokenDAO = new tokenDAO();
            String token = tokenDAO.generateToken();
            tokenDAO.saveToken(email, token);
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
            Gson gson = new Gson();
            String json = gson.toJson(user);
            String encodeUser = URLEncoder.encode(json, "UTF-8");
            EmailUtility emailUtility = new EmailUtility();
            String hostname = "smtp.gmail.com";
            int port = 587; // Use the appropriate port for your SMTP server
            String from = "taphoaso391@gmail.com";
            char[] pwd = "yygb zruf iamu vmtg".toCharArray();
            String toAddress = email;
            String subject = "[TapHoaSo] VERIFY YOUR EMAIL";
            String message = "We received your sign up request." + "<br>" + "<br>" +
                    "Please <a href=" + "'http://localhost:8080/verifySignup?tk=" + token + "&user=" + encodeUser + "'" + "> Click here</a> below to reset your password. " + "<br>" +
                    "The link will be expired in 5 minutes. " + "<br>" +
                    "If you did not request a account sign up, please ignore this email.";

            try {
                emailUtility.sendEmail(hostname, String.valueOf(port), from, pwd, toAddress, subject, message);
                req.setAttribute("mess", "Please check your email to verify your account!");
                req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);
            } catch (Exception e) {
                System.out.println("Failed to send email: " + e.getMessage());
                e.printStackTrace();
            }

            req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);

        } else {
            req.setAttribute("error", "Passwords don't match! Try again!");
            req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);
        }
    }
}
