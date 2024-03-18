package scheduler;

import dao.TransactionDAO;
import listener.TransactionProcessor;
import model.Post;
import model.Transaction;
import wrapper.TransactionWrapper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class PaymentToSeller {
    //-----------------TransactionProcessor-----------------
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private BlockingQueue<TransactionWrapper> transactionQueue = new LinkedBlockingQueue<>();
    TransactionProcessor transactionProcessor = new TransactionProcessor(transactionQueue);
    public void init() {
        executor.submit(transactionProcessor);
    }

    public void payMoneyToSeller(Post post) {
        TransactionDAO transactionDAO = new TransactionDAO();
        try {
            Transaction trans = transactionDAO.createPayForSellerTrans(post);
            TransactionWrapper transactionWrapper = new TransactionWrapper(trans);
            transactionQueue.add(transactionWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
