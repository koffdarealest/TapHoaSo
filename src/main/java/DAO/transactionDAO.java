package DAO;

import model.Post;
import model.User;
import model.User_Transaction_History;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

public class transactionDAO {
    public void executeTrans(User_Transaction_History trans) {  // update user balance and set transaction to processed
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            userDAO userDAO = new userDAO();
            User user = trans.getUserID();
            String type = trans.getType();
            Long transAmount = trans.getAmount();
            Long userBalance = user.getBalance();
            if (type.equals("+")) {
                userDAO.updateBalance(user, userBalance + transAmount);
            } else {
                userDAO.updateBalance(user, userBalance - transAmount);
            }
            trans.setProcessed(true);

            session.update(trans);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void createPrepostFeeTrans(Post post) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User_Transaction_History trans = new User_Transaction_History();
            trans.setUserID(post.getSellerID());
            trans.setAmount(500L);
            trans.setType("-");
            trans.setDescription("Prepost fee for post: " + post.getTradingCode());
            trans.setProcessed(false);

            session.save(trans);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void createDoneProductPostTrans(Post post) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User_Transaction_History trans = new User_Transaction_History();
            trans.setUserID(post.getSellerID());
            trans.setAmount(post.getTotalReceiveForSeller());
            trans.setType("+");
            trans.setDescription("Receive money from completed post: " + post.getTradingCode());
            trans.setProcessed(false);

            session.save(trans);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void createBuyProductPostTrans(Post post) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User_Transaction_History trans = new User_Transaction_History();
            trans.setUserID(post.getBuyerID());
            trans.setAmount(post.getTotalSpendForBuyer());
            trans.setType("-");
            trans.setDescription("Spend money for buying post: " + post.getTradingCode());
            trans.setProcessed(false);

            session.save(trans);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void createRefundToBuyerTrans(Post post) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User_Transaction_History trans = new User_Transaction_History();
            trans.setUserID(post.getBuyerID());
            trans.setAmount(post.getTotalSpendForBuyer());
            trans.setType("+");
            trans.setDescription("Refund money for canceled post: " + post.getTradingCode());
            trans.setProcessed(false);

            session.save(trans);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
}
