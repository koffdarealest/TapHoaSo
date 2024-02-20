package controller;

import DAO.tokenDAO;
import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import util.EmailSender;
import util.EmailUtility;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@WebServlet(urlPatterns = {"/forgot"})
public class forgotController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String email = req.getParameter("email");
//        userDAO userDAO = new userDAO();
//        if(!userDAO.checkExistEmail(email)) {
//            req.setAttribute("error", "Email does not exist! Try again!");
//            req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
//            return;
//        }
//        req.setAttribute("mess", "Please check your email to reset your password! If you don't see the email, try again!");
//        req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
//        tokenDAO tokenDAO = new tokenDAO();
//        String token = tokenDAO.generateToken();
//        tokenDAO.saveToken(email, token);
//        EmailUtility emailUtility = new EmailUtility();
//        String hostname = "smtp.gmail.com";
//        int port = 587; // Use the appropriate port for your SMTP server
//        String username = "taphoaso391@gmail.com";
//        char[] password = "yygb zruf iamu vmtg".toCharArray();
//        String toAddress = email;
//        String subject = "[TapHoaSo] RESET YOUR PASSWORD";
//        String message = "We received your password reset request." + "<br>" + "<br>" +
//                "Please <a href=" + "'http://localhost:8080/verifyForgot?tk=" + token + "'" + "> Click here</a> below to reset your password. " + "<br>" +
//                "The link will be expired in 5 minutes. " + "<br>" +
//                "If you did not request a password reset, please ignore this email.";
//        EmailSender emailSender = new EmailSender(hostname, String.valueOf(port), username, password, toAddress, subject, message);
//        emailSender.start();
        if (!isTrueCaptcha(req, resp)) {
            req.setAttribute("error", "Captcha is not correct! Try again!");
            req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
            return;
        }
        if (checkEmail(req, resp)) {
            String token = generateToken();
            saveToken(req, resp, token);
            sendEmail(req, resp, token);
            req.setAttribute("mess", "Please check your email to reset your password! If you don't see the email, try again!");
            req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Email does not exist! Try again!");
            req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
        }
    }

    private String getEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        return email;
    }

    private User getUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = getEmail(req, resp);
        userDAO userDAO = new userDAO();
        return userDAO.getUserByGmail(email);
    }

    private String generateToken() {
        tokenDAO tokenDAO = new tokenDAO();
        return tokenDAO.generateToken();
    }

    private boolean checkEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = getEmail(req, resp);
        userDAO userDAO = new userDAO();
        if (userDAO.checkExistEmail(email)) {
            return true;
        } else {
            return false;
        }
    }

    private void sendEmail(HttpServletRequest req, HttpServletResponse resp, String token) throws ServletException, IOException {
        String email = getEmail(req, resp);
        String hostname = "smtp.gmail.com";
        int port = 587; // Use the appropriate port for your SMTP server
        String username = "taphoaso391@gmail.com";
        char[] password = "yygb zruf iamu vmtg".toCharArray();
        String toAddress = email;
        String subject = "[TapHoaSo] RESET YOUR PASSWORD";
        String message = "We received your password reset request." + "<br>" + "<br>" +
                "Please <a href=" + "'http://localhost:9999/verifyForgot?tk=" + token + "'> Click here</a> below to reset your password. " + "<br>" +
                "The link will be expired in 5 minutes. " + "<br>" +
                "If you did not request a password reset, please ignore this email.";
        EmailSender emailSender = new EmailSender(hostname, String.valueOf(port), username, password, toAddress, subject, message);
        emailSender.start();
    }

    private void saveToken(HttpServletRequest req, HttpServletResponse resp, String token) throws ServletException, IOException {
        tokenDAO tokenDAO = new tokenDAO();
        User user = getUser(req, resp);
        tokenDAO.saveForgotToken(user, token);
    }

    private boolean isTrueCaptcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String enteredCaptcha = req.getParameter("captcha");
        String captcha = (String) req.getSession().getAttribute("captcha");
        if (!enteredCaptcha.equals(captcha)) {
            try {
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
