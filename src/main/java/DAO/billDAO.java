package DAO;

import model.Bill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class billDAO {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Tạo SessionFactory từ file cấu hình hibernate.cfg.xml (hoặc cấu hình tương tự)
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void updateBillStatus(String vnp_TxnRef, String status) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Bill bill = session.get(Bill.class, vnp_TxnRef);
            if (bill != null) {
                bill.setStatus(status);
                session.update(bill);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public boolean isBillSuccessful(String vnp_TxnRef) {
        try (Session session = sessionFactory.openSession()) {
            Bill bill = session.get(Bill.class, vnp_TxnRef);
            if (bill != null) {
                return "success".equals(bill.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
