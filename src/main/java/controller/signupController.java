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
import util.EmailSender;
import util.Encryption;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        //get Parameters from signup.jsp
        Map<String, String> getParameters = getParameters(req);
        //check exist username and email
        CheckExistUsernameAndEmail(req, resp, userDAO, getParameters);
        //check password and re-password
        CheckPasswordAndRePassword(req, resp, getParameters);
        // verify captcha
        VerifyCaptcha(req, resp, getParameters);
        //Hash password with MD5 althorithm
        String hashPass = userDAO.encodePassword(getParameters.get("password"));
        //hanlde successful signup
        try {
            handleSuccessfulSignup(req, resp, user, userDAO, encryption, getParameters, hashPass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void VerifyCaptcha(HttpServletRequest req, HttpServletResponse resp, Map<String, String> getParameters) {
        if (!getParameters.get("captcha").equals(req.getSession().getAttribute("captcha"))) {
            System.out.println("captcha entered" + getParameters.get("captcha"));
            System.out.println("captcha session" + req.getSession().getAttribute("captcha"));
            req.setAttribute("error", "Captcha is not correct! Try again!");

            try {
                req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
        System.out.println("captcha entered" + getParameters.get("captcha"));
        System.out.println("captcha session" + req.getSession().getAttribute("captcha"));
    }

    private void handleSuccessfulSignup(HttpServletRequest req, HttpServletResponse resp, User user, userDAO userDAO, Encryption encryption, Map<String, String> getParameters, String hashPass) throws Exception {
        String token = tokenDAO.generateToken();
        tokenDAO.saveToken(getParameters.get("email"), token);

        byte[] key;
        try {
            key = encryption.genAESKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        user.setNickname(getParameters.get("fullname"));
        user.setEmail(getParameters.get("email"));
        user.setUsername(getParameters.get("username"));
        user.setPassword(hashPass);
        user.setAdmin(false);
        user.setDelete(false);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setActivated(false);
        userDAO.insertUser(user);

        //send email to verify account
        sendEmailToVerifyAccount(req, resp, userDAO, encryption, getParameters, user);
    }

    private void sendEmailToVerifyAccount(HttpServletRequest req, HttpServletResponse resp, userDAO userDAO, Encryption token, Map<String, String> getParameters, User user) throws Exception {
        req.setAttribute("mess", "Please check your email to verify your account! If you don't see the email, try again!");
        req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);

        // Gson to save user
        Gson gson = new Gson();
        String json = gson.toJson(userDAO.getUserByUsername(getParameters.get("username")));
        String encodeUser = tokenDAO.encodeToken(json);

        //send email
        String hostname = "smtp.gmail.com";
        int port = 587; // Use the appropriate port for your SMTP server
        String from = "taphoaso391@gmail.com";
        char[] pwd = "yygb zruf iamu vmtg".toCharArray();
        String toAddress = getParameters.get("email");
        String subject = "[TapHoaSo] VERIFY YOUR EMAIL";
        String message = "We received your sign up request." + "<br>" + "<br>" +
                "Please <a href=" + "'http://localhost:8083/verifySignup?tk=" + token + "&user=" + encodeUser + "'" + "> Click here</a> below to verify your account. " + "<br>" +
                "The link will be expired in 5 minutes. " + "<br>" +
                "If you did not request a account sign up, please ignore this email.";

        System.out.println("json: " + json);
        System.out.println("encodedToken: " + encodeUser);
        System.out.println("decodedToken: " + tokenDAO.decodeToken(encodeUser));

        EmailSender emailSender = new EmailSender(hostname, String.valueOf(port), from, pwd, toAddress, subject, message);
        emailSender.start();
    }


    private void CheckPasswordAndRePassword(HttpServletRequest req, HttpServletResponse resp, Map<String, String> getParameters) {
        if (!getParameters.get("password").equals(getParameters.get("rePassword"))) {
            req.setAttribute("error", "Password and Re-Password are not the same! Try again!");
            try {
                req.getRequestDispatcher("signup").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
    }

    private void CheckExistUsernameAndEmail(HttpServletRequest req, HttpServletResponse resp, userDAO userDAO, Map<String, String> getParameters) {
        if (userDAO.checkExistUsername(getParameters.get("username"))) {
            req.setAttribute("error", "Username already exists! Try again!");
            try {
                req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);
                return;
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if (userDAO.checkExistEmail(getParameters.get("email"))) {
            req.setAttribute("error", "Email already exists! Try again!");
            System.out.println("Email already exists! Try again!");
            try {
                req.getRequestDispatcher("signup").forward(req, resp);
                return;
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
    }

    private Map<String, String> getParameters(HttpServletRequest req) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("fullname", req.getParameter("fullname"));
        parameters.put("email", req.getParameter("email"));
        parameters.put("username", req.getParameter("username"));
        parameters.put("password", req.getParameter("password"));
        parameters.put("rePassword", req.getParameter("re-password"));
        parameters.put("captcha", req.getParameter("captcha"));
        return parameters;
    }


}
