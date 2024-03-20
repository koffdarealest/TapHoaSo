package controller.productpostaction;

import dao.NoticeDAO;
import dao.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Notice;
import model.Post;

import java.io.IOException;

@WebServlet(urlPatterns = {"/complaint"})
public class ComplaintController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            String tradingCode = getCode(req, resp);
            Post post = getPostByCode(req, resp, tradingCode);
            if (isComplainablePost(req, resp, post, username)) {
                SetNotice(req, resp, post);
                complain(req, resp, post);
            } else {
                notifyUser(req, resp, "Invalid error! <a href=home>Go back here</a>");
            }
        }
    }

    private void SetNotice(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            Notice notice = new Notice();
            notice.setContent("You have report by " + post.getBuyerID().getNickname() + " about the post " + post.getTradingCode() + ". Please check it");
            notice.setAdminReceive(false);
            notice.setPostID(post);
            notice.setUserIDFrom(post.getBuyerID());
            notice.setUserIDTo(post.getSellerID());
            notice.setDelete(false);
            notice.setRead(false);

            NoticeDAO noticeDAO = new NoticeDAO();
            noticeDAO.insertNotice(notice);
        } catch (Exception e) {
            e.printStackTrace();
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

    private boolean isComplainablePost(HttpServletRequest req, HttpServletResponse resp, Post post, String username) {
        if (post == null) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        if (!post.getBuyerID().getUsername().equals(username)) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        if (!post.getStatus().equals("buyerChecking")) {
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

    private void complain(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            post.setStatus("buyerComplaining");
            PostDAO postDAO = new PostDAO();
            postDAO.updatePost(post);
            notifyUser(req, resp, "Complain successfully! <a href=sellingPost>Go back here</a>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
