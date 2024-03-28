package dao;

import model.WithdrawRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

public class WithdrawRequestDAO {
    public void saveWithdrawRequest(WithdrawRequest withdrawRequest) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(withdrawRequest);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }


    public WithdrawRequest getWithdrawRequestByCode(String withdrawCode) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            WithdrawRequest withdrawRequest = session.createQuery("from WithdrawRequest where withdrawCode = :withdrawCode", WithdrawRequest.class)
                    .setParameter("withdrawCode", withdrawCode)
                    .uniqueResult();

            transaction.commit();
            return withdrawRequest;
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            return null;
        }
    }

    public void deleteWithdrawRequest(WithdrawRequest withdrawRequest) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(withdrawRequest);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
}
