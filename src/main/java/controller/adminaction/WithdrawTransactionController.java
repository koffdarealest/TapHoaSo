package controller.adminaction;

import dao.TransactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(urlPatterns = {"/WithdrawTransaction"})
public class WithdrawTransactionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransaction();
        List<Transaction> withdrawTrans = new ArrayList<>();

        for(Transaction transaction : transactions) {
            if(transaction.getAction().equals("withdraw")){
                withdrawTrans.add(transaction);
            }
        }


        req.setAttribute("list", withdrawTrans);
        req.setAttribute("transactions", withdrawTrans);
        try {
            req.getRequestDispatcher("WEB-INF/view/transactionWithdrawAdmin.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
