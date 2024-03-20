package controller;

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
@WebServlet(urlPatterns = {"/notice"})
public class NoticeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoticeDAO noticeDAO = new NoticeDAO();

        Long UserId = (Long) req.getSession().getAttribute("userId");

        List<Notice> listNotice = noticeDAO.getAllNotice();

        req.setAttribute("listNotice", listNotice);

        req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoticeDAO noticeDAO = new NoticeDAO();

        List<Notice> listNotice = new ArrayList<>();

        req.setAttribute("listNotice", listNotice);

        req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);
    }
}
