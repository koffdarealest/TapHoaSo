package controller.productpostaction;

import dao.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;

import java.io.IOException;

@WebServlet(urlPatterns = {"/postDetailFromLink"})
public class PostDetailFromLinkController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = getCode(req, resp);
        Post post = getPostByCode(req, resp, code);
        String postOwner = post.getSellerID().getUsername();
        try {
            String username = (String) req.getSession().getAttribute("username");
            if (username.equals(postOwner) ) {
                resp.sendRedirect(req.getContextPath() + "/postDetailUpdate?tradingCode=" + code);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isDeletedPost(req, resp, post)) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
        } else {
            req.setAttribute("chosenPost", post);
            req.getRequestDispatcher("WEB-INF/view/postDetailFromLink.jsp").forward(req, resp);
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

    private void notifyUser(HttpServletRequest req, HttpServletResponse resp, String notification) {
        try {
            req.setAttribute("notification", notification);
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
