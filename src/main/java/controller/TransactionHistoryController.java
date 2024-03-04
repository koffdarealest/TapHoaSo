package controller;
import dao.TransactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.User_Transaction_History;
import dao.UserDAO;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/transactionHistory"})
public class TransactionHistoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            User user = getUser(req, resp, username);
            List<User_Transaction_History> transactionHistory = getTransactionHistory(req, resp, user);
            req.setAttribute("list", transactionHistory);
            req.getRequestDispatcher("/WEB-INF/view/transactionHistory.jsp").forward(req, resp);
        }
    }

    private User getUser(HttpServletRequest req, HttpServletResponse resp, String username) {
        UserDAO userDAO = new UserDAO();
        return userDAO.getUserByUsername(username);
    }

    private List<User_Transaction_History> getTransactionHistory(HttpServletRequest req, HttpServletResponse resp, User user) {
        TransactionDAO transaction = new TransactionDAO();
        return transaction.getTransactionByUser(user);
    }


}

