package controller;

import dao.NoticeDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Notice;
import model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet(urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return;
        }

        User user = getUserByUsername(username);
        List<Notice> listNotice = getAllNotice();
        List<Notice> listNoticeOfUser = getNoticeOfUser(user, listNotice);

        Collections.reverse(listNoticeOfUser);

        List<String> listContent = new ArrayList<>();

        for (Notice notice : listNoticeOfUser) {
            if (notice.getUserIDFrom().getUserID().equals(user.getUserID())) {
                listContent.add("You have sent a report to " + notice.getUserIDTo().getNickname() + "!");
            }
            if (notice.getUserIDTo().getUserID().equals(user.getUserID())) {
                listContent.add(notice.getUserIDFrom().getNickname() + " has sent a report to you!");
            }
        }

        req.setAttribute("listContent", listContent);
        req.setAttribute("listNotice", listNoticeOfUser);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);
    }

    private User getUserByUsername(String username) {
        UserDAO userDAO = new UserDAO();
        return userDAO.getUserByUsername(username);
    }

    private List<Notice> getAllNotice() {
        NoticeDAO noticeDAO = new NoticeDAO();
        return noticeDAO.getAllNotice();
    }

    private List<Notice> getNoticeOfUser(User user, List<Notice> listNotice) {
        List<Notice> listNoticeOfUser = new ArrayList<>();
        for (Notice notice : listNotice) {
            if (user.getUserID().equals(notice.getUserIDTo().getUserID()) ||
                    user.getUserID().equals(notice.getUserIDFrom().getUserID())) {
                listNoticeOfUser.add(notice);
            }
        }
        return listNoticeOfUser;
    }
}
