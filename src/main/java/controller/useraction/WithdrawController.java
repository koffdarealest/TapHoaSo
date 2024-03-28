package controller.useraction;

import dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.TransactionProcessor;
import model.Post;
import model.Transaction;
import model.User;
import model.WithdrawRequest;
import util.EmailSender;
import wrapper.TransactionWrapper;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@WebServlet(urlPatterns = {"/withdraw"})
public class WithdrawController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return;
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/view/withdraw.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return;
        }
        UserDAO userDAO = new UserDAO();

        String amount = req.getParameter("amount");
        Long amountLong = Long.parseLong(amount);
        User user = userDAO.getUserByUsername(username);
        if (amountLong > user.getBalance()) {
            req.setAttribute("error", "You don't have enough money to withdraw!");
            req.getRequestDispatcher("/WEB-INF/view/withdraw.jsp").forward(req, resp);
            return;
        }
        String bankingInfo = req.getParameter("bankingInfo");
        if (amount == null || bankingInfo == null) {
            notifyUser(req, resp, "Please fill in all fields!");
            return;
        }
        String withdrawCode = java.util.UUID.randomUUID().toString();
        saveWithdrawRequest(user, bankingInfo, amountLong, withdrawCode);
        String token = generateToken();
        saveToken(req, resp, token, user);
        sendEmail(req, resp, token, user.getEmail(), amount, bankingInfo, withdrawCode);
        notifyUser(req, resp, "Withdraw request has been sent. Please check your email to confirm withdraw! If you don't confirm within 5 minutes, the request will be canceled.");
    }

    private String generateToken() {
        TokenDAO tokenDAO = new TokenDAO();
        return tokenDAO.generateToken();
    }

    private void sendEmail(HttpServletRequest req, HttpServletResponse resp, String token, String email, String amount, String bankingInfo, String wCode) throws ServletException, IOException {
        String hostname = "smtp.gmail.com";
        int port = 587; // Use the appropriate port for your SMTP server
        String username = "taphoaso391@gmail.com";
        char[] password = "yygb zruf iamu vmtg".toCharArray();
        String toAddress = email;
        String subject = "[TapHoaSo] WITHDRAW REQUEST";
        String message = "We received your withdraw request." + "<br>" + "<br>" +
                "Amount: " + amount + " VND" + "<br>" +
                "Banking Information: " + bankingInfo + "<br>" +
                "Please <a href=" + "'https://taphoaso.me/verifyWithdraw?tk=" + token + "&wCode=" + wCode + "'> Click here</a> below to confirm withdraw request. " + "<br>" +
                "The link will be expired in 5 minutes. " + "<br>" +
                "If you did not request a password reset, please ignore this email.";
        EmailSender emailSender = new EmailSender(hostname, String.valueOf(port), username, password, toAddress, subject, message);
        emailSender.start();
    }

    private void notifyUser(HttpServletRequest req, HttpServletResponse resp, String notification) {
        try {
            req.setAttribute("notification", notification);
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToken(HttpServletRequest req, HttpServletResponse resp, String token, User user) throws ServletException, IOException {
        TokenDAO tokenDAO = new TokenDAO();
        tokenDAO.saveWithdrawToken(user, token);
    }

    private void saveWithdrawRequest(User user, String bankingInfo, Long amount, String withdrawCode) {
        WithdrawRequestDAO withdrawRequestDAO = new WithdrawRequestDAO();

        WithdrawRequest withdrawRequest = new WithdrawRequest(user, withdrawCode, bankingInfo, amount);
        withdrawRequestDAO.saveWithdrawRequest(withdrawRequest);
    }
}
