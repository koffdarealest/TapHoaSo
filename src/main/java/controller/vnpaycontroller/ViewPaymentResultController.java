package controller.vnpaycontroller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(urlPatterns = {"/viewpayresult"})
public class ViewPaymentResultController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = (String) req.getSession().getAttribute("username");
        if (!checkSession(username, resp, req)) {
            return;
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/view/processingVnPay.jsp").forward(req, resp);
    }
    private boolean checkSession(String username, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
            return false;
        }
        return true;
    }
}

