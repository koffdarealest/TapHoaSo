package dao;

import model.Bill;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.Factory;
import util.Hibernate;

import static util.Factory.sessionFactory;

public class BillDAO {


    public void save(Bill bill) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(bill);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public boolean isBillExists(String transactionCode) {
        BillDAO billDAO = new BillDAO(); // Khởi tạo đối tượng BillDAO để truy vấn cơ sở dữ liệu
        Bill bill = billDAO.getBillByTransactionCode(transactionCode); // Lấy hóa đơn từ cơ sở dữ liệu dựa trên mã giao dịch

        return bill != null; // Trả về true nếu hóa đơn tồn tại, false nếu không tồn tại
    }


    public Bill getBillByTransactionCode(String transactionCode) {
        Bill bill = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            bill = (Bill) session.createQuery("from Bill where TransactionCode = :transactionCode")
.setParameter("transactionCode", transactionCode)
                    .uniqueResult();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return bill;
    }



}
