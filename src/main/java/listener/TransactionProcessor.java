package listener;

import dao.PostDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import model.Transaction;
import model.User;
import wrapper.TransactionWrapper;

import java.util.concurrent.BlockingQueue;
public class TransactionProcessor implements Runnable{
    private BlockingQueue<TransactionWrapper> transactionsQueue;
    public TransactionProcessor(BlockingQueue<TransactionWrapper> transactionsQueue) {
        this.transactionsQueue = transactionsQueue;

    }

    @Override
    public void run() {
        while (true) {
            try {
                TransactionWrapper transactionWrapper = transactionsQueue.take();
                processTransaction(transactionWrapper);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processTransaction(TransactionWrapper transactionWrapper) {
        Transaction transaction = transactionWrapper.getTransaction();
        User uncheckedUserID = transaction.getUserID();
        Long id = uncheckedUserID.getUserID();
        UserDAO userDAO = new UserDAO();
        User checkedUser = userDAO.getUserByUserID(id);
        Long price = transaction.getAmount();
        String type = transaction.getType();
        if (type.equals("-")) {
            PostDAO postDAO = new PostDAO();
            boolean isEnough = postDAO.isBalanceEnough(checkedUser, price);
            if (isEnough) {
                TransactionDAO transactionDAO = new TransactionDAO();
                boolean isDone = transactionDAO.executeTrans(transaction, checkedUser);
                if (isDone) {
                    transactionDAO.saveTransaction(transaction);
                    transactionWrapper.getFuture().complete(true);
                } else {
                    transactionWrapper.getFuture().complete(false);
                }
            } else {
                transactionWrapper.getFuture().complete(false);
            }
        } else {
            TransactionDAO transactionDAO = new TransactionDAO();
            boolean isDone = transactionDAO.executeTrans(transaction, checkedUser);
            if (isDone) {
                transactionDAO.saveTransaction(transaction);
                transactionWrapper.getFuture().complete(true);
            } else {
                transactionWrapper.getFuture().complete(false);
            }
        }
    }
}
