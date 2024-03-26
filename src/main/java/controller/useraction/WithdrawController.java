package controller.useraction;

import dao.PostDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.TransactionProcessor;
import model.Post;
import model.Transaction;
import model.User;
import wrapper.TransactionWrapper;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@WebServlet(urlPatterns = {"/withdraw"})
public class WithdrawController extends HttpServlet {
    //-----------------TransactionProcessor-----------------
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private BlockingQueue<TransactionWrapper> transactionQueue = new LinkedBlockingQueue<>();
    TransactionProcessor transactionProcessor = new TransactionProcessor(transactionQueue);
    public void init() {
        executor.submit(transactionProcessor);
    }

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return;
        }
        String amount = req.getParameter("amount");
        if (amount == null || amount.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/withdraw");
            return;
        }
        long amountLong = Long.parseLong(amount);
        if (amountLong <= 0) {
            resp.sendRedirect(req.getContextPath() + "/withdraw");
            return;
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);

        if (withdraw(req, resp, user, amountLong)) {
            notifyUser(req, resp, "Withdrawal request successful! The money will be transferred to you within 2 hours. <a href=home>Go back here</a>");
        } else {
            notifyUser(req, resp, "Withdraw error! <a href=home>Go back here</a>");
        }
    }

    private boolean withdraw(HttpServletRequest req, HttpServletResponse resp, User user, long amountLong) {
        TransactionDAO transactionDAO = new TransactionDAO();
        boolean status = false;
        try {
            Transaction trans = transactionDAO.createWithdrawTrans(user, amountLong);
            TransactionWrapper transactionWrapper = new TransactionWrapper(trans);
            transactionQueue.add(transactionWrapper);
            status = transactionWrapper.getFuture().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    private void notifyUser(HttpServletRequest req, HttpServletResponse resp, String notification) {
        try {
            req.setAttribute("notification", notification);
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
