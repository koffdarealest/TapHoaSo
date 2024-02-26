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
import java.util.List;

@WebServlet(urlPatterns = {"/buyingPostDetail"})
public class buyingPostDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            Long id = getPostID(req, resp);
            Post post = getPostByID(req, resp, id);
            if (isDeletedPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            if (!isValidUserToViewPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            } else {
                req.setAttribute("chosenPost", post);
            }
            getAllPost(req, resp);
            req.getRequestDispatcher("WEB-INF/view/buyingPostDetail.jsp").forward(req, resp);
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

    private boolean isValidUserToViewPost(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            String username = (String) req.getSession().getAttribute("username");
            if (post.getBuyerID().getUsername().equals(username)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void getAllPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            userDAO userDAO = new userDAO();
            String username = (String) req.getSession().getAttribute("username");
            User user = userDAO.getUserByUsername(username);
            postDAO postDAO = new postDAO();
            List<Post> getAllPost = postDAO.getAllPostByBuyer(user);
            req.setAttribute("lPosts", getAllPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
