package controller.verifier;

import dao.TokenDAO;
import dao.TransactionDAO;
import dao.WithdrawRequestDAO;
import jakarta.persistence.Entity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.TransactionProcessor;
import model.Transaction;
import model.WithdrawRequest;
import wrapper.TransactionWrapper;

import java.io.IOException;
import java.util.concurrent.*;

@WebServlet(urlPatterns = {"/verifyWithdraw"})
public class VerifyWithdrawController extends HttpServlet {
    //-----------------TransactionProcessor-----------------
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private BlockingQueue<TransactionWrapper> transactionQueue = new LinkedBlockingQueue<>();
    TransactionProcessor transactionProcessor = new TransactionProcessor(transactionQueue);
    public void init() {
        executor.submit(transactionProcessor);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String tk = req.getParameter("tk");
        String withdrawCode = req.getParameter("wCode");
        if (isTokenExpired(req, resp, tk)) {
            notifyUser(req, resp, "Your link is invalid! Try again!");
            return;
        }
        if (!isValidTokenType(tk)) {
            TokenDAO tokenDAO = new TokenDAO();
            tokenDAO.deleteToken(tk);
            notifyUser(req, resp, "Your link is invalid! Try again!");
            return;
        } else {
            TokenDAO tokenDAO = new TokenDAO();
            tokenDAO.deleteToken(tk);
        }
        WithdrawRequest withdrawRequest = getWithdrawRequest(withdrawCode);
        if (withdrawRequest == null) {
            notifyUser(req, resp, "Your link is invalid! Try again!");
            return;
        } else {
            deleteWithdrawRequest(withdrawRequest);
        }
        if (createTransaction(withdrawRequest)) {
            notifyUser(req, resp, "Withdraw request successful! The money will be transferred to you within 3 hours. <a href=home>Go back here</a>");
        } else {
            notifyUser(req, resp, "Your withdraw request is failed to process! Create a new withdraw request <a href=withdraw>here</a>");
        }

    }

    private boolean isTokenExpired(HttpServletRequest req, HttpServletResponse resp, String tk) throws IOException, ServletException {
        TokenDAO tokenDAO = new TokenDAO();
        if (tokenDAO.isTokenExpired(tk)) {
            tokenDAO.deleteToken(tk);
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidTokenType(String tk) {
        TokenDAO tokenDAO = new TokenDAO();
        return tokenDAO.getTokenType(tk).equals("withdraw");
    }

    private WithdrawRequest getWithdrawRequest(String withdrawCode) {
        WithdrawRequestDAO withdrawRequestDAO = new WithdrawRequestDAO();
        return withdrawRequestDAO.getWithdrawRequestByCode(withdrawCode);
    }

    private boolean createTransaction(WithdrawRequest withdrawRequest) {
        boolean status = false;
        TransactionDAO transactionDAO = new TransactionDAO();
        Transaction trans = transactionDAO.createWithdrawTrans(withdrawRequest);
        TransactionWrapper transWrapper = new TransactionWrapper(trans);
        try {
            transactionQueue.add(transWrapper);
            status = transWrapper.getFuture().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    private void notifyUser(HttpServletRequest req, HttpServletResponse resp, String notification) {
        try {
            req.setAttribute("notification", notification);
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteWithdrawRequest(WithdrawRequest withdrawRequest) {
        WithdrawRequestDAO withdrawRequestDAO = new WithdrawRequestDAO();
        withdrawRequestDAO.deleteWithdrawRequest(withdrawRequest);
    }
}
