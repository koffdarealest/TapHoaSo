package util;

import DAO.userDAO;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.UUID;

public class test {
//    public static void main(String[] args) throws Exception {
//        try {
//            SessionFactory sessionFactory  = Factory.getSessionFactory();
//            if(sessionFactory!=null) {
//                Session session = sessionFactory.openSession();
//                try {
//                    Transaction tr = session.beginTransaction();
//                    Encryption encryption = new Encryption();
//                    byte[] key = encryption.genAESKey();
//                    User user = new User("tuta", "tamtamtam", "tam@gmail.com", "tamancut", 1000000L, false, key);
//                    session.save(user);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//
//                    session.close();
//                }
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        userDAO userDAO = new userDAO();
//        byte[] key = userDAO.getSecretKey("tuta");
//        System.out.println("key1211: " + key);
//        String str = "tamtamtam";
//        Encryption encryption = new Encryption();
//        String eStr = encryption.encrypt(str, key);
//
//
//    }

    public static void main(String[] args) {
        User user = new User();
        userDAO userDAO = new userDAO();
        user = userDAO.getUserByUsername("tuta");
        System.out.println("user: " + user);
        System.out.println("user: " + user.getUsername() + "---user" + user.getNickname());
        user.setNickname("tamkhongancutnua");
        userDAO.updateUser(user);
    }
}
