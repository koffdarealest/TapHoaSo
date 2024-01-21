package util;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class test {
    public static void main(String[] args) {
        try {
            SessionFactory sessionFactory  = Factory.getSessionFactory();
            if(sessionFactory!=null) {
                Session session = sessionFactory.openSession();
                try {
                    Transaction tr = session.beginTransaction();
                    User cat1 = new User();
                    cat1.setNickname("Ancut");
                    session.save(cat1);
                    tr.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    session.close();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
