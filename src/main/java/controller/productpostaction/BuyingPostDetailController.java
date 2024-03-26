package controller.productpostaction;

import dao.PostDAO;
import dao.UserDAO;
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
public class BuyingPostDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            String code = getCode(req, resp);
            Post post = getPostByCode(req, resp, code);
            if (isDeletedPost(req, resp, post)) {
                notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
                return;
            }
            if (!isValidUserToViewPost(req, resp, post)) {
                notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
                return;
            } else {
                req.setAttribute("chosenPost", post);
            }
            getAllPost(req, resp);
            req.getRequestDispatcher("WEB-INF/view/buyingPostDetail.jsp").forward(req, resp);
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

    private Post getPostByCode(HttpServletRequest req, HttpServletResponse resp, String code) {
        Post post = new Post();
        try {
            PostDAO postDAO = new PostDAO();
            post = postDAO.getPostByTradingCode(code);
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
            return post.getBuyerID().getUsername().equals(username);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void getAllPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            UserDAO userDAO = new UserDAO();
            String username = (String) req.getSession().getAttribute("username");
            User user = userDAO.getUserByUsername(username);
            PostDAO postDAO = new PostDAO();
            List<Post> getAllPost = postDAO.getAllPostByBuyer(user);
            req.setAttribute("user", user);
            req.setAttribute("lPosts", getAllPost);
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


}
