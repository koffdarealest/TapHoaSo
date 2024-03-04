package controller;

import dao.PostDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;
import model.User;
import model.User_Transaction_History;

import java.io.IOException;

@WebServlet(urlPatterns = {"/buy"})
public class BuyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            Long id = getPostID(req, resp);
            Post post = getPostByID(req, resp, id);
            User user = getUser(req, resp);
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
            try{
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean isDone = buyPost(req, resp, post, user);
            if (isDone) {
                notifyUser(req, resp, "Buy post successfully! <a href=buyingPost>View your buying post</a>");
            } else {
                notifyUser(req, resp, "Buy error! <a href=home>Go back here</a>");
            }
        }
    }


    private Long getPostID(HttpServletRequest req, HttpServletResponse resp) {
        Long postID = null;
        try {
            String ID = req.getParameter("postID");
            postID = Long.parseLong(ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postID;
    }

    private Post getPostByID(HttpServletRequest req, HttpServletResponse resp, Long id) {
        Post post = new Post();
        try {
            PostDAO postDAO = new PostDAO();
            post = postDAO.getPostByID(id);
            return post;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private User getUser(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User();
        try {
            String username = (String) req.getSession().getAttribute("username");
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
            User_Transaction_History trans = transactionDAO.createBuyProductPostTrans(post);
            status = transactionDAO.executeTrans(trans);
            if (status) {
                transactionDAO.saveTransaction(trans);
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
