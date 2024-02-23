package DAO;

import model.Post;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

import java.util.Date;
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

            post.setDelete(true);
            post.setDeletedAt(new Date());

            session.update(post);

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

    public List<Post> getAllPublicPost(){
        List<Post> ListPosts = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            ListPosts = session.createQuery("from Post where buyerID is null and (isDelete != true or isDelete is null)").getResultList();

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return ListPosts;
    }

    public List<Post> getAllPostBySeller(User user){
        List<Post> ListPosts = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            ListPosts = session.createQuery("from Post where sellerID = :user and (isDelete != true or isDelete is null)")
                    .setParameter("user", user)
                    .getResultList();

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return ListPosts;
    }

    public List<Post> getAllPostByBuyer(User user) {
        List<Post> ListPosts = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ListPosts = session.createQuery("from Post where buyerID = :user and (isDelete != true or isDelete is null)")
                    .setParameter("user", user)
                    .getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return ListPosts;
    }

    public void buyPost(Post post, User user){
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            post.setBuyerID(user);
            post.setStatus("buyed");
            post.setUpdateable(false);
            post.setCanBuyerComplain(true);

            session.update(post);

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
//         postDAO postDAO = new postDAO();
//         List<Post> ls = postDAO.getAllPost();
//         Post post = ls.getFirst();
//         System.out.println(post.getTradingCode());
    }
}
