package controller.productpostaction;

import dao.NoticeDAO;
import dao.PostDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.TransactionProcessor;
import model.Notice;
import model.Post;
import model.Transaction;
import model.User;
import wrapper.TransactionWrapper;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@WebServlet(urlPatterns = {"/reportAdmin"})
public class ReportAdminController extends HttpServlet {
    //-----------------TransactionProcessor-----------------
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private BlockingQueue<TransactionWrapper> transactionQueue = new LinkedBlockingQueue<>();
    TransactionProcessor transactionProcessor = new TransactionProcessor(transactionQueue);
    public void init() {
        executor.submit(transactionProcessor);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
        }
        User user = new UserDAO().getUserByUsername(username);
        String code = getCode(req, resp);
        Post post = getPostByCode(req, resp, code);
        if (payReportAdminFee(req, resp, post)) {
            //update notification to user
            ReportToAdmin(req, resp, post, user);
            //update notification to user
            UpdateNotification(req, resp, "The post has been reported to admin");
        } else {
            //update notification to user
            UpdateNotification(req, resp, "You don't have enough balance to report this post to admin");
        }

    }

    private void UpdateNotification(HttpServletRequest req, HttpServletResponse resp, String notification) {
        try {
            req.setAttribute("notification", notification);
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ReportToAdmin(HttpServletRequest req, HttpServletResponse resp, Post post, User user) throws IOException {

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

    private boolean payReportAdminFee(HttpServletRequest req, HttpServletResponse resp, Post post) {
        TransactionDAO transactionDAO = new TransactionDAO();
        PostDAO postDAO = new PostDAO();
        boolean status = false;
        try {
            Transaction trans = transactionDAO.createReportToAdminTrans(post);
            TransactionWrapper transactionWrapper = new TransactionWrapper(trans);
            transactionQueue.add(transactionWrapper);
            status = transactionWrapper.getFuture().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
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

}
