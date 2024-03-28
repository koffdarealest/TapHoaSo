package controller.adminaction;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;

@WebServlet(urlPatterns = {"/userDetail"})
public class UserDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
        }
        if (!username.equals("admin")) {
            req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        }
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String idUser = req.getParameter("id");
        User listUser = userDAO.getUserByUserID(Long.parseLong(idUser));
        req.setAttribute("user", listUser);
        req.getRequestDispatcher("/WEB-INF/view/userDetails.jsp").forward(req, resp);
    }
}
