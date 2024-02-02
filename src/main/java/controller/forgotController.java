package controller;

import DAO.tokenDAO;
import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.EmailSender;
import util.EmailUtility;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = {"/forgot"})
public class forgotController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (getAndCheckEmail(req, resp)) {
            sendEmail(req, resp);
            req.setAttribute("mess", "Please check your email to reset your password! If you don't see the email, try again!");
            req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Email does not exist! Try again!");
            req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
        }
    }

    private boolean getAndCheckEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        userDAO userDAO = new userDAO();
        if (userDAO.checkExistEmail(email)) {
            return true;
        } else {
            return false;
        }
    }

    private void sendEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        tokenDAO tokenDAO = new tokenDAO();
        String token = tokenDAO.generateToken();
        tokenDAO.saveToken(email, token);
        EmailUtility emailUtility = new EmailUtility();
        String hostname = "smtp.gmail.com";
        int port = 587; // Use the appropriate port for your SMTP server
        String username = "taphoaso391@gmail.com";
        char[] password = "yygb zruf iamu vmtg".toCharArray();
        String toAddress = email;
        String subject = "[TapHoaSo] RESET YOUR PASSWORD";
        String message = "We received your password reset request." + "<br>" + "<br>" +
                "Please <a href=" + "'http://localhost:8080/verifyForgot?tk=" + token + "'" + "> Click here</a> below to reset your password. " + "<br>" +
                "The link will be expired in 5 minutes. " + "<br>" +
                "If you did not request a password reset, please ignore this email.";
        EmailSender emailSender = new EmailSender(hostname, String.valueOf(port), username, password, toAddress, subject, message);
        emailSender.start();
    }
}
