package DAO;

import model.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Factory;

import java.util.List;

public class UserDAO {
    public List<Users> getAllUser(){
        List<Users> ListUsers = null;
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            ListUsers = session.createQuery("from Users").getResultList();

            transaction.commit();
        } catch (Exception ex){
            if(transaction == null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return ListUsers;
    }

    public boolean checkUser(String username, String password) {
        List<Users> ListUsers = getAllUser();
        for (Users user : ListUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
