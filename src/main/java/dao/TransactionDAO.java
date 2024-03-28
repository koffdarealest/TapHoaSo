package dao;

import jakarta.persistence.OptimisticLockException;
import model.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
        if(!trans.getAction().equals("withdraw")) {
            trans.setProcessed(true);
        }
        return status;
    }

    public Transaction createPrepostFeeTrans(Post post) {
        Transaction trans = new Transaction();
        trans.setUserID(post.getSellerID());
        trans.setAmount(500L);
        trans.setType("-");
        trans.setAction("postfee");
        trans.setDescription("Prepost fee for post: " + post.getTradingCode());
        trans.setProcessed(false);
        trans.setCreatedBy(post.getSellerID().getUserID());
        return trans;
    }

    public Transaction createPayForSellerTrans(Post post) {
        Transaction trans = new Transaction();
        trans.setUserID(post.getSellerID());
        trans.setAmount(post.getTotalReceiveForSeller());
        trans.setType("+");
        trans.setAction("payforseller");
        trans.setDescription("Receive money from completed post: " + post.getTradingCode());
        trans.setProcessed(false);
        return trans;
    }

    public Transaction createBuyProductPostTrans(Post post) {
        Transaction trans = new Transaction();
        trans.setUserID(post.getBuyerID());
        trans.setAmount(post.getTotalSpendForBuyer());
        trans.setType("-");
        trans.setAction("buy");
        trans.setDescription("Spend money for buying post: " + post.getTradingCode());
        trans.setProcessed(false);
        trans.setCreatedBy(post.getBuyerID().getUserID());
        return trans;
    }

    public Transaction createRefundToBuyerTrans(Post post) {
        Transaction trans = new Transaction();
        trans.setUserID(post.getBuyerID());
        trans.setAmount(post.getTotalSpendForBuyer());
        trans.setType("+");
        trans.setAction("refund");
        trans.setDescription("Refund money for canceled post: " + post.getTradingCode());
        trans.setProcessed(false);
        return trans;
    }

    public Transaction createReportToAdminTrans(Post post) {
        Transaction trans = new Transaction();
        trans.setUserID(post.getBuyerID());
        trans.setAmount(50000L);
        trans.setType("-");
        trans.setAction("reportadmin");
        trans.setDescription("Report Admin fee for post: " + post.getTradingCode());
        trans.setProcessed(false);
        trans.setCreatedBy(post.getBuyerID().getUserID());
        return trans;
    }

    public Transaction createWithdrawTrans(WithdrawRequest withdrawRequest) {
        Transaction trans = new Transaction();
        trans.setUserID(withdrawRequest.getUserID());
        trans.setAmount(withdrawRequest.getAmount());
        trans.setType("-");
        trans.setAction("withdraw");
        trans.setDescription("Withdraw money to: " + withdrawRequest.getBankingInfo());
        trans.setProcessed(false);
        trans.setCreatedBy(withdrawRequest.getUserID().getUserID());
        return trans;
    }

    public Transaction createDepositTrans(User user, Long amount) {
        Transaction trans = new Transaction();
        trans.setUserID(user);
        trans.setAmount(amount);
        trans.setType("+");
        trans.setAction("deposit");
        trans.setDescription("Deposit money");
        trans.setProcessed(false);
        trans.setCreatedBy(user.getUserID());
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
    public List<Transaction> getAllTransaction() {
        try (Session session = Factory.getSessionFactory().openSession()) {
            return session.createQuery("from Transaction", Transaction.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Transaction getTransactionByID(long transactionID) {
        try (Session session = Factory.getSessionFactory().openSession()) {
            return session.get(Transaction.class, transactionID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void update(Transaction transaction){
        org.hibernate.Transaction transaction1 = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction1 = session.beginTransaction();
            session.update(transaction);
            transaction1.commit();
        } catch (Exception ex) {
            if (transaction1 == null) {
                transaction1.rollback();
            }
            ex.printStackTrace();
        }
    }
}
