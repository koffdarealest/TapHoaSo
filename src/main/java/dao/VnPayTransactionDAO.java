package dao;
import model.VnPayTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

import java.util.List;

public class VnPayTransactionDAO {
    private Session session;

    public VnPayTransactionDAO(Session session) {
        this.session = session;
    }

    public VnPayTransactionDAO() {
        session = Factory.getSessionFactory().openSession();
    }

    public void saveVnPay(VnPayTransaction transaction) {
        try {
            session.beginTransaction();
            session.save(transaction);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    public void save(VnPayTransaction transaction) {
        Transaction tx = session.beginTransaction();
        session.save(transaction);
        tx.commit();
    }

    public VnPayTransaction get(Long id) {
        return session.get(VnPayTransaction.class, id);
    }

    public List<VnPayTransaction> getAll() {
        return session.createQuery("FROM VnPayTransaction", VnPayTransaction.class).list();
    }

    public void update(VnPayTransaction transaction) {
        Transaction tx = session.beginTransaction();
        session.update(transaction);
        tx.commit();
    }

    public void delete(VnPayTransaction transaction) {
        Transaction tx = session.beginTransaction();
        session.delete(transaction);
        tx.commit();
    }
}