package DAO;

import model.Post;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

import java.util.List;
import java.util.UUID;

public class postDAO {
    public void insertPost(Post post) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(post);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public Post getPostByTradingCode (String tradingCode) {
        Post post = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            post = (Post) session.createQuery("from Post where tradingCode = :tradingCode")
                    .setParameter("tradingCode", tradingCode)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return post;
    }

    public Post getPostByID (Long id) {
        Post post = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            post = (Post) session.createQuery("from Post where id = :id")
                    .setParameter("id", id)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return post;
    }

    public void updatePost(Post post) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(post);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void deletePost(Post post) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(post);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public String createTradingCode() {
        String tradingCode = UUID.randomUUID().toString();
        return tradingCode;
    }

    public boolean isBalanceEnough(User user, Long price) {
        userDAO userDAO = new userDAO();
        Long balance = user.getBalance();
        if(balance < price) {
            return false;
        }
        return true;
    }

    public List<Post> getAllPost(){
        List<Post> ListPosts = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            ListPosts = session.createQuery("from Post").getResultList();

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return ListPosts;
    }





    public static void main(String[] args) {
         postDAO postDAO = new postDAO();
         String a = "4";
         Long b = Long.parseLong(a);
         Post post = postDAO.getPostByID(b);
        System.out.println(post.getTopic());
    }
}
