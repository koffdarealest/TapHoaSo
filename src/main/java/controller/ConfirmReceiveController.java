package controller;

import dao.PostDAO;
import dao.TransactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.TransactionProcessor;
import model.Post;
import model.Transaction;
import wrapper.TransactionWrapper;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@WebServlet(urlPatterns = {"/confirmReceive"})
public class ConfirmReceiveController extends HttpServlet {
    //-----------------TransactionProcessor-----------------
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private BlockingQueue<TransactionWrapper> transactionQueue = new LinkedBlockingQueue<>();
    TransactionProcessor transactionProcessor = new TransactionProcessor(transactionQueue);
    public void init() {
        executor.submit(transactionProcessor);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            String code = getCode(req, resp);
            Post post = getPostByCode(req, resp, code);
            if (isDeletedPost(req, resp, post)) {
                notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
                return;
            }
            if (!isValidUserToConfirmPost(req, resp, post)) {
                notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
                return;
            }
            confirmReceive(req, resp, post);
            notifyUser(req, resp, "Confirm receive successfully! <a href=home>Go back here</a>");
            tranferMoneyToSeller(req, resp, post);
        }
    }

    private String getCode(HttpServletRequest req, HttpServletResponse resp) {
        String tradingCode = null;
        try {
            tradingCode = req.getParameter("tradingCode");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tradingCode;
    }

    private Post getPostByCode(HttpServletRequest req, HttpServletResponse resp, String tradingCode) {
        Post post = new Post();
        try {
            PostDAO postDAO = new PostDAO();
            post = postDAO.getPostByTradingCode(tradingCode);
            return post;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private boolean isDeletedPost(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            if (post.getDelete()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isValidUserToConfirmPost(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            String username = (String) req.getSession().getAttribute("username");
            if (post.getBuyerID().getUsername().equals(username) && post.getStatus().equals("buyerChecking")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void confirmReceive(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            PostDAO postDAO = new PostDAO();
            postDAO.confirmReceivePost(post);
            postDAO.updatePost(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyUser(HttpServletRequest req, HttpServletResponse resp, String notification) {
        try {
            req.setAttribute("notification", notification);
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean tranferMoneyToSeller(HttpServletRequest req, HttpServletResponse resp, Post post) {
        TransactionDAO transactionDAO = new TransactionDAO();
        boolean status = false;
        try {
            Transaction trans = transactionDAO.createPayForSellerTrans(post);
            TransactionWrapper transactionWrapper = new TransactionWrapper(trans);
            transactionQueue.add(transactionWrapper);
            status = transactionWrapper.getFuture().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}
