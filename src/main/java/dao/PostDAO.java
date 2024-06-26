package dao;

import model.Post;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PostDAO {
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

    public Post getPostByTradingCode(String tradingCode) {
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

    public Post getPostByID(Long id) {
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
        UserDAO userDAO = new UserDAO();
        Long balance = user.getBalance();
        if (balance < price) {
            return false;
        }
        return true;
    }

    public List<Post> getAllPublicPost() {
        List<Post> ListPosts = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ListPosts = session.createQuery("from Post where buyerID is null and (isDelete != true or isDelete is null) and isPublic = true").getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return ListPosts;
    }

    public List<Post> getAllPostBySeller(User user) {
        List<Post> ListPosts = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ListPosts = session.createQuery("from Post where sellerID = :user and (isDelete != true or isDelete is null)")
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

    public void buyPost(Post post, User user) {
        try {
            post.setBuyerID(user);
            post.setStatus("buyerChecking");
            post.setUpdateable(false);
            post.setCanBuyerComplain(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmReceivePost(Post post) {
        try {
            post.setStatus("done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Post> getIncompletePost() {
        List<Post> unconfirmedPosts = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            unconfirmedPosts = session.createQuery("from Post where status in (:statuses) ")
                    .setParameterList("statuses", Arrays.asList("buyerChecking", "sellerDeniedComplaint", "buyerCanceledComplain", "buyerComplaining"))
                    .getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return unconfirmedPosts;
    }


    public static void main(String[] args) {

    }
}
