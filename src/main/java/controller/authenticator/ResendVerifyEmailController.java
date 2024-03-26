package controller.authenticator;

import dao.TokenDAO;
import dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import util.EmailSender;

import java.io.IOException;

@WebServlet(urlPatterns = {"/resendVerifyEmail"})

public class ResendVerifyEmailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUser(req);
        if (isUserNull(user)) {
            resp.sendRedirect( req.getContextPath() + "/signin");
            return;
        }
        resendVerificationEmail(req, resp, user);
        req.setAttribute("notification", "A new verification email has been sent to your email address. Please check your email and verify your account.");
        req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
    }

    private User getUser(HttpServletRequest req) {
        User user = new User();
        try {
            String username = (String) req.getSession().getAttribute("username");
            UserDAO userDAO = new UserDAO();
            user = userDAO.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private void resendVerificationEmail(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException, ServletException {
        TokenDAO tokenDAO = new TokenDAO();
        String token = tokenDAO.generateToken();
        tokenDAO.saveSignUpToken(user, token);

        String hostname = "smtp.gmail.com";
        int port = 587; // Use the appropriate port for your SMTP server
        String from = "taphoaso391@gmail.com";
        char[] pwd = "yygb zruf iamu vmtg".toCharArray();
        String toAddress = user.getEmail();
        String subject = "[TapHoaSo] VERIFY YOUR EMAIL";
        String message = "We received your active account request." + "<br>" + "<br>" +
                "Please <a href=" + "'http://localhost:8080/verifySignup?tk=" + token + "'> Click here</a> to verify your account. " + "<br>" +
                "The link will be expired in 5 minutes. " + "<br>" +
                "If you did not request any thing, please ignore this email.";
        EmailSender emailSender = new EmailSender(hostname, String.valueOf(port), from, pwd, toAddress, subject, message);
        emailSender.start();
    }

    private boolean isUserNull(User user) {
        return user == null;
    }

}
