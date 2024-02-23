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
    public List<User_Transaction_History> getTransactionByID(Long id) {
        List<User_Transaction_History> list = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            list = session.createQuery("from User_Transaction_History where id = :id", User_Transaction_History.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public void deleteTransaction(Long id) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            List<User_Transaction_History> transactionHistory = getTransactionByID(id);
            session.delete(transactionHistory);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

}
