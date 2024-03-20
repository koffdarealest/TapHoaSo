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

@WebServlet(urlPatterns = {"/confirmCorrectPost"})
public class ConfirmCorrectPostController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            String tradingCode = getCode(req, resp);
            Post post = getPostByCode(req, resp, tradingCode);
            if (canConfirmCorrectPost(req, resp, post, username)) {
                confirmCorrectPost(req, resp, post);
                updateNotice(req, resp, post, "The post has confirmed correct");
                notifyUser(req, resp, "Confirm correct post successfully! <a href=home>Go back here</a>");
            } else {
                notifyUser(req, resp, "Invalid error! <a href=home>Go back here</a>");
            }
        }
    }

    private void updateNotice(HttpServletRequest req, HttpServletResponse resp, Post post, String thePostHasConfirmedCorrect) {
        NoticeDAO noticeDAO = new NoticeDAO();
        Notice notice = new Notice();

        notice.setContent(thePostHasConfirmedCorrect + post.getBuyerID().getNickname());
        notice.setAdminReceive(false);
        notice.setPostID(post);
        notice.setUserIDFrom(post.getSellerID());
        notice.setUserIDTo(post.getBuyerID());
        notice.setDelete(false);
        notice.setRead(false);

        noticeDAO.insertNotice(notice);
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

    private boolean canConfirmCorrectPost(HttpServletRequest req, HttpServletResponse resp, Post post, String username) {
        if (post == null) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        if (!post.getSellerID().getUsername().equals(username)) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return false;
        }
        if (!post.getStatus().equals("buyerComplaining")) {
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

    private void confirmCorrectPost(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            PostDAO postDAO = new PostDAO();
            post.setStatus("sellerDeniedComplaint");
            postDAO.updatePost(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
