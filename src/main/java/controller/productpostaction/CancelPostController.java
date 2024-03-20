package controller.productpostaction;

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

@WebServlet(urlPatterns = {"/cancelPost"})
public class CancelPostController extends HttpServlet {
    //-----------------TransactionProcessor-----------------
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private BlockingQueue<TransactionWrapper> transactionQueue = new LinkedBlockingQueue<>();
    TransactionProcessor transactionProcessor = new TransactionProcessor(transactionQueue);
    public void init() {
        executor.submit(transactionProcessor);
    }
    //-----------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            String tradingCode = getCode(req, resp);
            Post post = getPostByCode(req, resp, tradingCode);
            if (canCancelPost(req, resp, post, username)) {
                cancelPost(req, resp, post);
                if (refundToBuyer(req, resp, post)) {
                    notifyUser(req, resp, "Cancel post successfully! <a href=home>Go back here</a>");
                } else {
                    notifyUser(req, resp, "Refund error! <a href=home>Go back here</a>");
                }
            } else {
                notifyUser(req, resp, "Invalid error! <a href=home>Go back here</a>");
            }
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

    private boolean canCancelPost(HttpServletRequest req, HttpServletResponse resp, Post post, String username) {
        if (post == null) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        if (!post.getSellerID().getUsername().equals(username)) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        if (!post.getStatus().equals("buyerComplaining")) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        return true;
    }

    private void notifyUser(HttpServletRequest req, HttpServletResponse resp, String notification) {
        try {
            req.setAttribute("notification", notification);
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cancelPost(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            PostDAO postDAO = new PostDAO();
            post.setStatus("cancelled");
            postDAO.updatePost(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean refundToBuyer(HttpServletRequest req, HttpServletResponse resp, Post post) {
        TransactionDAO transactionDAO = new TransactionDAO();
        boolean status = false;
        try {
            Transaction trans = transactionDAO.createRefundToBuyerTrans(post);
            TransactionWrapper transactionWrapper = new TransactionWrapper(trans);
            transactionQueue.add(transactionWrapper);
            status = transactionWrapper.getFuture().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
