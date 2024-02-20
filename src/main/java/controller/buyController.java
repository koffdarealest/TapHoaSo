package controller;

import DAO.postDAO;
import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;
import model.User;

import java.io.IOException;

@WebServlet(urlPatterns = {"/buy"})
public class buyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect("/signin");
        } else {
            Long id = getPostID(req, resp);
            Post post = getPostByID(req, resp, id);
            User user = getUser(req, resp);
            if (isDeletedPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            if (isPostOwner(req, resp, post, username)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            if (isBuyedPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            if (!isBalanceEnough(req, resp, post, user)) {
                req.setAttribute("notification", "Your balance is not enough! Please <a href=" + "deposit>" + "top up</a> your balance!");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            buyPost(req, resp, post, user);
            req.setAttribute("notification", "Buy post successfully! <a href=buying>View your buying post</a>");
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
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
            postDAO postDAO = new postDAO();
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
            userDAO userDAO = new userDAO();
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

    private void buyPost(HttpServletRequest req, HttpServletResponse resp, Post post, User user) {
        try {
            postDAO postDAO = new postDAO();
            userDAO userDAO = new userDAO();
            Long total = post.getTotalSpendForBuyer();
            Long balance = user.getBalance();
            userDAO.updateBalance(user, balance - total);
            postDAO.buyPost(post, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
