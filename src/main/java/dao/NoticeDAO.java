package dao;

import model.Notice;
import model.Post;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

import java.util.List;

public class NoticeDAO {
    public List<Notice> getAllNotice() {
        List<Notice> notices = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            notices = session.createQuery("from Notice").list();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return notices;
    }

    public void insertNotice( Notice notice) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(notice);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
    public void deleteNotice(Notice notice) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(notice);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
    public Notice getNoticeByUserFrom(User user){
        Notice notice = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            notice = (Notice) session.createQuery("from Notice where userIDFrom = :userIDFrom")
                    .setParameter("userIDFrom", user)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return notice;
    }

    public Notice getNoticeByPostId(Post post){
            Notice notice = null;
            Transaction transaction = null;
            try (Session session = Factory.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();

                notice = (Notice) session.createQuery("from Notice where postID = :postID")
                        .setParameter("postID", post)
                        .uniqueResult();
                transaction.commit();
            } catch (Exception ex) {
                if (transaction == null) {
                    transaction.rollback();
                }
                ex.printStackTrace();
            }
            return notice;
    }

    public void updateNotice(Notice notice){
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(notice);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
}
