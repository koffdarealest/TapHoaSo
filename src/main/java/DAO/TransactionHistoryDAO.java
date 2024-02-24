package DAO;

import model.BaseAuditable;
import model.User;
import model.User_Transaction_History;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

import java.util.List;

public class TransactionHistoryDAO extends BaseAuditable {
    public void insertTransaction(User_Transaction_History transactionHistory){
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            session.save(transactionHistory);

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();

        }
    }
    public void updateTransaction(User_Transaction_History transactionHistory){
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            session.update(transactionHistory);

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();

        }
    }
    public List<User_Transaction_History> getTransactionByUserID(User id) {
        try (Session session = Factory.getSessionFactory().openSession()) {
            return session.createQuery("from User_Transaction_History where userID = :id", User_Transaction_History.class)
                    .setParameter("id", id)
                    .list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void deleteTransaction(User id) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            List<User_Transaction_History> transactionHistory = getTransactionByUserID(id);
            session.delete(transactionHistory);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TransactionHistoryDAO transactionHistoryDAO = new TransactionHistoryDAO();
        //code to get all user
        userDAO userDAO = new userDAO();
        List<User> listUser = userDAO.getAllUser();
        List<User_Transaction_History> list = transactionHistoryDAO.getTransactionByUserID(listUser.get(1));
        for(User_Transaction_History transaction : list){
            System.out.println(transaction.getTransactionID());
        }
    }
}
