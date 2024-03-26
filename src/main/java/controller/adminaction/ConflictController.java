package controller.adminaction;

import dao.NoticeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/conflictManage"})
public class ConflictController extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            NoticeDAO noticeDAO = new NoticeDAO();
            List<Notice> listAll = noticeDAO.getAllNotice();
            List<Notice> listAdminCanSee = new ArrayList<>();
            for (Notice notice : listAll) {
                if (notice.getAdminReceive() == true) {
                    notice.setContent("The system has detected a conflict in the post " + notice.getPostID().getTradingCode() + ". Please check it");
                    listAdminCanSee.add(notice);
                }
            }
            req.setAttribute("listNotice", listAdminCanSee);

            req.getRequestDispatcher("WEB-INF/view/conflictPage.jsp").forward(req, resp);
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
