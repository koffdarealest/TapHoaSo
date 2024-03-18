package controller;

import dao.TokenDAO;
import dao.UserDAO;
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
public class SignupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        Encryption encryption = new Encryption();
        //get Parameters from signup.jsp
        Map<String, String> getParameters = getParameters(req);
        //verify captcha
        if (!isTrueCaptcha(req, resp, getParameters)) {
            req.setAttribute("error", "Captcha is not correct! Try again!");
            req.getRequestDispatcher("/WEB-INF/view/signup.jsp").forward(req, resp);
            return;
        }
        //check exist username and email
        if (CheckExistUsernameAndEmail(req, resp, userDAO, getParameters)) {
            req.setAttribute("error", "Username or gmail already exists! Try again!");
            req.getRequestDispatcher("/WEB-INF/view/signup.jsp").forward(req, resp);
            return;
        }
        //check password and re-password
        if (!CheckPasswordAndRePassword(req, resp, getParameters)) {
            req.setAttribute("error", "Password and Re-Password are not the same! Try again!");
            req.getRequestDispatcher("/WEB-INF/view/signup.jsp").forward(req, resp);
            return;
        }
        //Hash password with MD5 althorithm
        String hashPass = userDAO.encodePassword(getParameters.get("password"));
        //hanlde successful signup
        try {
            User user = createUser(req, resp, userDAO, encryption, getParameters, hashPass);
            String token = saveToken(req, resp, user);
            sendEmailToVerifyAccount(req, resp, token, user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isTrueCaptcha(HttpServletRequest req, HttpServletResponse resp, Map<String, String> getParameters) {
        String enteredCaptcha = getParameters.get("captcha");
        String captcha = (String) req.getSession().getAttribute("captcha");
        if (!enteredCaptcha.equalsIgnoreCase(captcha)) {
            try {
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private User createUser(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, Encryption encryption, Map<String, String> getParameters, String hashPass) throws Exception {
        User user = new User();
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
        user.setSecretKey(key);
        user.setBalance(0L);
        userDAO.insertUser(user);

//        String token = tokenDAO.generateToken();
//        tokenDAO.saveVerifyEmailToken(user, token);
        return user;

        //send email to verify account
//        sendEmailToVerifyAccount(req, resp, userDAO, encryption, getParameters, user);
    }

    private void sendEmailToVerifyAccount(HttpServletRequest req, HttpServletResponse resp, String token, User user) throws Exception {
        req.setAttribute("notification", "Please check your email to verify your account! If you don't see the email, try again!");
        req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);

        // Gson to save user
//        Gson gson = new Gson();
//        String json = gson.toJson(user);
//        String encodeUser = tokenDAO.encodeToken(json);

        //send email
        String hostname = "smtp.gmail.com";
        int port = 587; // Use the appropriate port for your SMTP server
        String from = "taphoaso391@gmail.com";
        char[] pwd = "yygb zruf iamu vmtg".toCharArray();
        String toAddress = user.getEmail();
        String subject = "[TapHoaSo] VERIFY YOUR EMAIL";
        String message = "We received your sign up request." + "<br>" + "<br>" +
                "Please <a href=" + "'http://localhost:8080/verifySignup?tk=" + token + "'> Click here</a> to verify your account. " + "<br>" +
                "The link will be expired in 5 minutes. " + "<br>" +
                "If you did not request a account sign up, please ignore this email.";
        EmailSender emailSender = new EmailSender(hostname, String.valueOf(port), from, pwd, toAddress, subject, message);
        emailSender.start();
    }


    private boolean CheckPasswordAndRePassword(HttpServletRequest req, HttpServletResponse resp, Map<String, String> getParameters) {
        if (!getParameters.get("password").equals(getParameters.get("rePassword"))) {
            return false;
        }
        return true;
    }

    private boolean CheckExistUsernameAndEmail(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, Map<String, String> getParameters) {
        if (userDAO.checkExistUsername(getParameters.get("username"))) {
            return true;
        }
        if (userDAO.checkExistEmail(getParameters.get("email"))) {
            return true;
        }
        return false;
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

    private String saveToken(HttpServletRequest req, HttpServletResponse resp, User user) {
        TokenDAO tokenDAO = new TokenDAO();
        String token = tokenDAO.generateToken();
        tokenDAO.saveSignUpToken(user, token);
        return token;
    }


}
