package dao;

import jakarta.persistence.OptimisticLockException;
import model.Post;
import model.User;
import model.User_Transaction_History;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

import java.util.List;

public class TransactionDAO {
//    public void executeTransx(User_Transaction_History trans) {  // update user balance and set transaction to processed
//        Transaction transaction = null;
//        try (Session session = Factory.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            userDAO userDAO = new userDAO();
//            User user = trans.getUserID();
//            String type = trans.getType();
//            Long transAmount = trans.getAmount();
//            Long userBalance = user.getBalance();
//            if (type.equals("+")) {
//                userDAO.updateBalance(user, userBalance + transAmount);
//            } else {
//                userDAO.updateBalance(user, userBalance - transAmount);
//            }
//            trans.setProcessed(true);
//
//            session.update(trans);
//            transaction.commit();
//        } catch (Exception ex) {
//            if (transaction == null) {
//                transaction.rollback();
//            }
//            ex.printStackTrace();
//        }
//    }

    public boolean executeTrans(User_Transaction_History trans) {
        UserDAO userDAO = new UserDAO();
        User user = trans.getUserID();
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

//    public void createPrepostFeeTransx(Post post) {
//        Transaction transaction = null;
//        try (Session session = Factory.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            User_Transaction_History trans = new User_Transaction_History();
//            trans.setUserID(post.getSellerID());
//            trans.setAmount(500L);
//            trans.setType("-");
//            trans.setDescription("Prepost fee for post: " + post.getTradingCode());
//            trans.setProcessed(false);
//
//            session.save(trans);
//            transaction.commit();
//        } catch (Exception ex) {
//            if (transaction == null) {
//                transaction.rollback();
//            }
//            ex.printStackTrace();
//        }
//    }

    public User_Transaction_History createPrepostFeeTrans(Post post) {
        User_Transaction_History trans = new User_Transaction_History();
        trans.setUserID(post.getSellerID());
        trans.setAmount(500L);
        trans.setType("-");
        trans.setDescription("Prepost fee for post: " + post.getTradingCode());
        trans.setProcessed(false);
        return trans;
    }

//    public void createDoneProductPostTransx(Post post) {
//        Transaction transaction = null;
//        try (Session session = Factory.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            User_Transaction_History trans = new User_Transaction_History();
//            trans.setUserID(post.getSellerID());
//            trans.setAmount(post.getTotalReceiveForSeller());
//            trans.setType("+");
//            trans.setDescription("Receive money from completed post: " + post.getTradingCode());
//            trans.setProcessed(false);
//
//            session.save(trans);
//            transaction.commit();
//        } catch (Exception ex) {
//            if (transaction == null) {
//                transaction.rollback();
//            }
//            ex.printStackTrace();
//        }
//    }

    public User_Transaction_History createDoneProductPostTrans(Post post) {
        User_Transaction_History trans = new User_Transaction_History();
        trans.setUserID(post.getSellerID());
        trans.setAmount(post.getTotalReceiveForSeller());
        trans.setType("+");
        trans.setDescription("Receive money from completed post: " + post.getTradingCode());
        trans.setProcessed(false);
        return trans;
    }

//    public void createBuyProductPostTransx(Post post) {
//        Transaction transaction = null;
//        try (Session session = Factory.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            User_Transaction_History trans = new User_Transaction_History();
//            trans.setUserID(post.getBuyerID());
//            trans.setAmount(post.getTotalSpendForBuyer());
//            trans.setType("-");
//            trans.setDescription("Spend money for buying post: " + post.getTradingCode());
//            trans.setProcessed(false);
//
//            session.save(trans);
//            transaction.commit();
//        } catch (Exception ex) {
//            if (transaction == null) {
//                transaction.rollback();
//            }
//            ex.printStackTrace();
//        }
//    }

    public User_Transaction_History createBuyProductPostTrans(Post post) {
        User_Transaction_History trans = new User_Transaction_History();
        trans.setUserID(post.getBuyerID());
        trans.setAmount(post.getTotalSpendForBuyer());
        trans.setType("-");
        trans.setDescription("Spend money for buying post: " + post.getTradingCode());
        trans.setProcessed(false);
        return trans;
    }

//    public void createRefundToBuyerTransx(Post post) {
//        Transaction transaction = null;
//        try (Session session = Factory.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            User_Transaction_History trans = new User_Transaction_History();
//            trans.setUserID(post.getBuyerID());
//            trans.setAmount(post.getTotalSpendForBuyer());
//            trans.setType("+");
//            trans.setDescription("Refund money for canceled post: " + post.getTradingCode());
//            trans.setProcessed(false);
//
//            session.save(trans);
//            transaction.commit();
//        } catch (Exception ex) {
//            if (transaction == null) {
//                transaction.rollback();
//            }
//            ex.printStackTrace();
//        }
//    }

    public User_Transaction_History createRefundToBuyerTrans(Post post) {
        User_Transaction_History trans = new User_Transaction_History();
        trans.setUserID(post.getBuyerID());
        trans.setAmount(post.getTotalSpendForBuyer());
        trans.setType("+");
        trans.setDescription("Refund money for canceled post: " + post.getTradingCode());
        trans.setProcessed(false);
        return trans;
    }

    public void saveTransaction(User_Transaction_History trans) {
        Transaction transaction = null;
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

    public List<User_Transaction_History> getTransactionByUser(User user) {
        try (Session session = Factory.getSessionFactory().openSession()) {
            return session.createQuery("from User_Transaction_History where userID = :user", User_Transaction_History.class)
                    .setParameter("user", user)
                    .list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
