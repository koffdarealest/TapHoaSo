package controller.adminaction;

import dao.TransactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Transaction;

import java.io.IOException;

@WebServlet(urlPatterns = {"/changeAction"})
public class ChangeActionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TransactionDAO transactionDAO = new TransactionDAO();

        String transactionID = req.getParameter("transactionID");
        String action = req.getParameter("action");

        if (transactionID != null && action != null) {
            Transaction transaction = transactionDAO.getTransactionByID(Long.parseLong(transactionID));

            if (transaction != null) {
                switch (action) {
                    case "process":
                        transaction.setProcessed(false);
                        transactionDAO.update(transaction);
                        break;
                    case "done":
                        transaction.setProcessed(true);
                        transactionDAO.update(transaction);
                        break;
                    default:
                        break;
                }
            } else {
                // Handle transaction not found error here
            }
        } else {
            // Handle missing parameters error here
        }

        resp.sendRedirect(req.getContextPath() + "/WithdrawTransaction");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
