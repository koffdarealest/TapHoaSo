package dao;
import model.VnPayTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;
import util.Hibernate;

import java.util.List;

public class VnPayTransactionDAO {
    private Session session;

    public VnPayTransactionDAO(Session session) {
        this.session = session;
    }

    public VnPayTransactionDAO() {
        session = Hibernate.getSessionFactory().openSession();
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

//    public static String getTransactionStatus(String transactionId) {
//        try (Session session = Factory.getSessionFactory().openSession()) {
//            VnPayTransaction vnPayTransaction = session.get(VnPayTransaction.class, transactionId);
//            if (vnPayTransaction != null) {
//                return vnPayTransaction.getTransactionStatus();
//            } else {
//                // Nếu không tìm thấy giao dịch với transactionId tương ứng
//                return null;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
//
//    public void setTransactionStatus(String transactionId, String newStatus) {
//        Transaction transaction = null;
//        try (Session session = Factory.getSessionFactory().openSession()) {
//            // Bắt đầu giao dịch
//            transaction = session.beginTransaction();
//
//            // Lấy đối tượng VnPayTransaction từ cơ sở dữ liệu bằng transactionId
//            VnPayTransaction vnPayTransaction = session.get(VnPayTransaction.class, transactionId);
//            if (vnPayTransaction != null) {
//                // Cập nhật trạng thái giao dịch
//                vnPayTransaction.setTransactionStatus(newStatus);
//                // Lưu thay đổi vào cơ sở dữ liệu
//                session.update(vnPayTransaction);
//            }
//
//            // Commit giao dịch
//            transaction.commit();
//        } catch (Exception ex) {
//            // Nếu có lỗi, rollback giao dịch
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            ex.printStackTrace();
//        }
//    }
}