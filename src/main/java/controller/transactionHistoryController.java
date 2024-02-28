package controller;

import DAO.TransactionHistoryDAO;
import DAO.postDAO;
import DAO.transactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.User_Transaction_History;
import DAO.userDAO;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/transactionHistory"})
public class transactionHistoryController extends HttpServlet {
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
        userDAO userDAO = new userDAO();
        postDAO postDAO = new postDAO();
        transactionDAO transactionDAO = new transactionDAO();

        //get id user session login
        Long idUser = (Long) req.getSession().getAttribute("id");
        checkSession(idUser, resp);

        //get name of person created post
        String nameOfUserCreatedPost = getUserCreatedPost(postDAO, idUser);
        req.setAttribute("nameOfUserCreatedPost", nameOfUserCreatedPost);

        //get user by id to check transaction history
        User listUser = userDAO.getUserByUserID(idUser);

        //update transaction history to processed
        Object Transaction = req.getSession().getAttribute("transaction");
        User_Transaction_History TransactionToUpdate = (User_Transaction_History) Transaction;
        transactionDAO.executeTrans(TransactionToUpdate);

        //get list transaction history by user
        List<User_Transaction_History> listTransaction = transactionHistoryDAO.getTransactionByUserID(listUser);
        return listTransaction;
    }

    private String getUserCreatedPost(postDAO postDAO, Long idUser) {
        return postDAO.getUserCreatedPost(idUser);
    }

    private boolean checkSession(Long username, HttpServletResponse resp) throws IOException {
        if (username == null) {
            resp.sendRedirect("signin");
            return false;
        }
        return true;
    }
}
