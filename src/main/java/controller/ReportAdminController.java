package controller;

import dao.NoticeDAO;
import dao.PostDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Notice;
import model.Post;
import model.User;

import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = {"/reportAdmin"})
public class ReportAdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //check session of user
        CheckSession(req, resp);
        //report to admin
        ReportToAdmin(req, resp);
        //update notification to user
        UpdateNotification(req, resp, "The post has been reported to admin! <a href=buyingPost>Go back here</a>");
    }

    private void UpdateNotification(HttpServletRequest req, HttpServletResponse resp, String notification) {
        try {
            req.setAttribute("notification", notification);
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ReportToAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = (String) req.getSession().getAttribute("username");
        String tradingCode = req.getParameter("tradingCode");
        User user = new UserDAO().getUserByUsername(username);
        Post post = new PostDAO().getPostByTradingCode(tradingCode);

        if (post != null) {

            /*NoticeDAO noticeDAO = new NoticeDAO();
            Notice notice = noticeDAO.getNoticeByPostId(post);
            if(notice == null){
                resp.sendRedirect("login");
            }
            notice.setAdminReceive(true);
            notice.setContent("The post has been reported to admin by " + user.getNickname() + " at " + new Date() + "!");
            notice.setRead(true);
            noticeDAO.updateNotice(notice);*/

            insertNoticeToAdmin(post, user);
        }

    }

    private void insertNoticeToAdmin(Post post, User user) {
        NoticeDAO noticeDAO = new NoticeDAO();
        Notice notice = new Notice();

        notice.setContent("The post has been reported to admin by " + user.getNickname() + " at " + new Date() + "!");
        notice.setAdminReceive(true);
        notice.setPostID(post);
        notice.setUserIDFrom(user);
        notice.setUserIDTo(new UserDAO().getUserByUsername("admin"));
        notice.setDelete(false);
        notice.setRead(false);

        noticeDAO.insertNotice(notice);
    }

    private void CheckSession(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = (String) req.getSession().getAttribute("username");
        if(username == null){
            resp.sendRedirect(req.getContextPath() + "/signin");
        }
    }

}
