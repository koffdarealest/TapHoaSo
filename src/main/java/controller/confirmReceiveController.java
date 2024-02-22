package controller;

import DAO.postDAO;
import DAO.transactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;

import java.io.IOException;

@WebServlet(urlPatterns = {"/confirmReceive"})
public class confirmReceiveController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("/signin");
        } else {
            Long id = getPostID(req, resp);
            Post post = getPostByID(req, resp, id);
            if (isDeletedPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            if (!isValidUserToConfirmPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            confirmReceive(req, resp, post);
            req.setAttribute("notification", "Confirm receive successfully! <a href=home>Go back here</a>");
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
            transactionDAO transactionDAO = new transactionDAO();
            postDAO postDAO = new postDAO();
            post.setStatus("done");
            postDAO.updatePost(post);
            transactionDAO.createDoneProductPostTrans(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
