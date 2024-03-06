package dao;

import jakarta.persistence.OptimisticLockException;
import model.Post;
import model.User;
import model.Transaction;
import org.hibernate.Session;
import util.Factory;

import java.util.List;

public class TransactionDAO {

    public boolean executeTrans(Transaction trans, User user) {
        UserDAO userDAO = new UserDAO();
        String type = trans.getType();
        Long transAmount = trans.getAmount();
        Long userBalance = user.getBalance();
        boolean status = false;
        if (type.equals("+")) {
            try {
                status = userDAO.updateBalance(user, userBalance + transAmount);
            } catch (OptimisticLockException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                status = userDAO.updateBalance(user, userBalance - transAmount);
            } catch (OptimisticLockException ex) {
                ex.printStackTrace();
            }
        }
        trans.setProcessed(true);
        return status;
    }

    public Transaction createPrepostFeeTrans(Post post) {
        Transaction trans = new Transaction();
        trans.setUserID(post.getSellerID());
        trans.setAmount(500L);
        trans.setType("-");
        trans.setDescription("Prepost fee for post: " + post.getTradingCode());
        trans.setProcessed(false);
        return trans;
    }

    public Transaction createPayForSellerTrans(Post post) {
        Transaction trans = new Transaction();
        trans.setUserID(post.getSellerID());
        trans.setAmount(post.getTotalReceiveForSeller());
        trans.setType("+");
        trans.setDescription("Receive money from completed post: " + post.getTradingCode());
        trans.setProcessed(false);
        return trans;
    }

    public Transaction createBuyProductPostTrans(Post post) {
        Transaction trans = new Transaction();
        trans.setUserID(post.getBuyerID());
        trans.setAmount(post.getTotalSpendForBuyer());
        trans.setType("-");
        trans.setDescription("Spend money for buying post: " + post.getTradingCode());
        trans.setProcessed(false);
        return trans;
    }

    public Transaction createRefundToBuyerTrans(Post post) {
        Transaction trans = new Transaction();
        trans.setUserID(post.getBuyerID());
        trans.setAmount(post.getTotalSpendForBuyer());
        trans.setType("+");
        trans.setDescription("Refund money for canceled post: " + post.getTradingCode());
        trans.setProcessed(false);
        return trans;
    }

    public void saveTransaction(Transaction trans) {
        org.hibernate.Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(trans);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public List<Transaction> getTransactionByUser(User user) {
        try (Session session = Factory.getSessionFactory().openSession()) {
            return session.createQuery("from Transaction where userID = :user", Transaction.class)
                    .setParameter("user", user)
                    .list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
