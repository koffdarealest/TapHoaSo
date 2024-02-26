package controller;

import DAO.postDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;

import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = {"/postDetail"})
public class postDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            Long id = getPostID(req, resp);
            Post post = getPost(req, resp, id);
            if (isDeletedPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            if (isPostOwner(req, resp, post, username)) {
                resp.sendRedirect("postDetailUpdate?postID=" + id);
                return;
            }
            req.setAttribute("chosenPost", post);
            getAllPost(req, resp);
            req.getRequestDispatcher("WEB-INF/view/postDetail.jsp").forward(req, resp);
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

    private Post getPost(HttpServletRequest req, HttpServletResponse resp, Long id) {
        Post post = new Post();
        try {
            postDAO postDAO = new postDAO();
            post = postDAO.getPostByID(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private void getAllPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            postDAO postDAO = new postDAO();
            List<Post> getAllPost = postDAO.getAllPublicPost();
            req.setAttribute("lPosts", getAllPost);
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

}
