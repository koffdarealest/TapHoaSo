package controller;

import DAO.postDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;

import java.io.IOException;


@WebServlet(urlPatterns = {"/deletePost"})
public class deletePostController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            Long id = getPostID(req, resp);
            Post post = getPostByID(req, resp, id);
            if (isDeletedPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            if (!isValidUserToDeletePost(req, resp, post, username)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            if (!isDeleteablePost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
            } else {
                deletePost(req, resp, post);
                req.setAttribute("notification", "Delete post successfully! <a href=sellingPost>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
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
            postDAO postDAO = new postDAO();
            post = postDAO.getPostByID(id);
            return post;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private boolean isValidUserToDeletePost(HttpServletRequest req, HttpServletResponse resp, Post post, String username) {
        try {
            username = (String) req.getSession().getAttribute("username");
            if (post.getSellerID().getUsername().equals(username)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deletePost(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            postDAO postDAO = new postDAO();
            postDAO.deletePost(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private boolean isDeleteablePost(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            if (post.getStatus().equals("readyToSell") || post.getStatus().equals("done")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
