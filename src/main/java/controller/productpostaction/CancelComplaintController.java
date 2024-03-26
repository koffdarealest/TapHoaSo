package controller.productpostaction;

import dao.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;

import java.io.IOException;


@WebServlet(urlPatterns = {"/cancelComplaint"})
public class CancelComplaintController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            String tradingCode = getCode(req, resp);
            Post post = getPostByCode(req, resp, tradingCode);
            if (canCancelComplaintPost(req, resp, post, username)) {
                cancelComplaint(req, resp, post);
            } else {
                notifyUser(req, resp, "Invalid error! <a href=home>Go back here</a>");
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

    private boolean canCancelComplaintPost(HttpServletRequest req, HttpServletResponse resp, Post post, String username) {
        if (post == null) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        if (!post.getBuyerID().getUsername().equals(username)) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        if (!post.getStatus().equals("buyerComplaining")) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        if (!post.getCanBuyerComplain()) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        return true;
    }

    private void notifyUser(HttpServletRequest req, HttpServletResponse resp, String notification) {
        try {
            req.setAttribute("notification", notification);
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cancelComplaint(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            post.setStatus("buyerCanceledComplaint");
            PostDAO postDAO = new PostDAO();
            postDAO.updatePost(post);
            notifyUser(req, resp, "Cancel complaint successfully! <a href=sellingPost>Go back here</a>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
