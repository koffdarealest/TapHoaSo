package controller.productpostaction;

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
import model.User;
import model.Transaction;
import wrapper.TransactionWrapper;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@WebServlet(urlPatterns = {"/buy"})
public class BuyController extends HttpServlet {
    //-----------------TransactionProcessor-----------------
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private BlockingQueue<TransactionWrapper> transactionQueue = new LinkedBlockingQueue<>();
    TransactionProcessor transactionProcessor = new TransactionProcessor(transactionQueue);
    public void init() {
        executor.submit(transactionProcessor);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            String code = getCode(req, resp);
            Post post = getPostByCode(req, resp, code);
            User user = getUser(req, resp, username);
            if (isDeletedPost(req, resp, post)) {
                notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
                return;
            }
            if (isPostOwner(req, resp, post, username)) {
                notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
                return;
            }
            if (isBuyedPost(req, resp, post)) {
                notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
                return;
            }
            if (!isBalanceEnough(req, resp, post, user)) {
                notifyUser(req, resp,"Your balance is not enough! Please <a href=" + "deposit>" + "top up</a> your balance!");
                return;
            }
            boolean isDone = buyPost(req, resp, post, user);
            if (isDone) {
                notifyUser(req, resp, "Buy post successfully! <a href=buyingPost>View your buying post</a>");
            } else {
                notifyUser(req, resp, "Buy error! <a href=home>Go back here</a>");
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

    private User getUser(HttpServletRequest req, HttpServletResponse resp, String username) {
        User user = new User();
        try {
            UserDAO userDAO = new UserDAO();
            user = userDAO.getUserByUsername(username);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
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

    private boolean isPostOwner(HttpServletRequest req, HttpServletResponse resp, Post post, String username) {
        String postOwner = post.getSellerID().getUsername();
        try {
            if (username.equals(postOwner)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isBuyedPost(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            if (post.getBuyerID() != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isBalanceEnough(HttpServletRequest req, HttpServletResponse resp, Post post, User user) {
        try {
            Long balance = user.getBalance();
            Long total = post.getTotalSpendForBuyer();
            if (balance >= total) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean buyPost(HttpServletRequest req, HttpServletResponse resp, Post post, User user) {
        TransactionDAO transactionDAO = new TransactionDAO();
        PostDAO postDAO = new PostDAO();
        boolean status = false;
        try {
            postDAO.buyPost(post, user);
            Transaction trans = transactionDAO.createBuyProductPostTrans(post);
            TransactionWrapper transactionWrapper = new TransactionWrapper(trans);
            transactionQueue.add(transactionWrapper);
            status = transactionWrapper.getFuture().get();
            if (status) {
                postDAO.updatePost(post);
            }
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
