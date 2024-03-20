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
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String idUser = req.getParameter("id");
        User listUser = userDAO.getUserByUserID(Long.parseLong(idUser));
        req.setAttribute("user", listUser);
        req.getRequestDispatcher("/WEB-INF/view/UserDetails.jsp").forward(req, resp);
    }
}
