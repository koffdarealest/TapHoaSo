package controller;

import DAO.TransactionHistoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User_Transaction_History;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/transactionHistory"})
public class TransactionHistoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TransactionHistoryDAO transactionHistoryDAO = new TransactionHistoryDAO();
        //get list transaction history
        List<User_Transaction_History> listTransaction = GetListTransaction(transactionHistoryDAO, req, resp);
        req.setAttribute("list", listTransaction);
        req.getRequestDispatcher("/WEB-INF/view/transactionHistory.jsp").forward(req, resp);
    }

    private List<User_Transaction_History> GetListTransaction(TransactionHistoryDAO transactionHistoryDAO, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long idUser = (Long) req.getSession().getAttribute("id");
        checkSession(idUser, resp);
        List<User_Transaction_History> listTransaction = transactionHistoryDAO.getTransactionByID(idUser);
        return listTransaction;
    }

    private boolean checkSession(Long username, HttpServletResponse resp) throws IOException {
        if (username == null) {
            resp.sendRedirect("signin");
            return false;
        }
        return true;
    }
}
