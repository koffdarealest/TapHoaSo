package controller.productpostaction;

import dao.PostDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;
import util.StatusFormat;

import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = {"/postDetail"})
public class PostDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            String code = getCode(req, resp);
            Post post = getPost(req, resp, code);
            if (isDeletedPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            if (isPostOwner(req, resp, post, username)) {
                resp.sendRedirect("postDetailUpdate?tradingCode=" + code);
                return;
            }
            UserDAO userDAO = new UserDAO();
            req.setAttribute("user", userDAO.getUserByUsername(username));
            String status = StatusFormat.formatStatus(post.getStatus());
            req.setAttribute("status", status);
            req.setAttribute("chosenPost", post);
            getAllPost(req, resp);
            req.getRequestDispatcher("WEB-INF/view/postDetail.jsp").forward(req, resp);
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

    private Post getPost(HttpServletRequest req, HttpServletResponse resp, String code) {
        Post post = new Post();
        try {
            PostDAO postDAO = new PostDAO();
            post = postDAO.getPostByTradingCode(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private void getAllPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PostDAO postDAO = new PostDAO();
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
