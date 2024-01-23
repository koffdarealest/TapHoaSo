package controller;

import DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Users;
import org.apache.catalina.User;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "signup", urlPatterns = {"/signup"})
public class signupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Remove the following line, as it's not necessary
        //super.doGet(req, resp);
        req.getRequestDispatcher("view/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users users = new Users();
        UserDAO userDAO = new UserDAO();

        List<Users> listUser = userDAO.getAllListUser();

        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String username = req.getParameter("userName");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("re-password");

        if(checkExistEmailAndUsername(email, username)){
            req.setAttribute("mess", "Username or Email is exist. Please check again");
            req.getRequestDispatcher("view/signup.jsp").forward(req, resp);
        } else {
            if(password.equals(rePassword)){
                String encodePassword = userDAO.encodePassword(password);

                users.setNickname(fullname);
                users.setEmail(email);
                users.setPassword(encodePassword);
                users.setUsername(username);
                users.setAdmin(false);
                users.setBalance(0L);
                users.setCreateAt(new Date());
                users.setUpdateAt(null);
                users.setDeleteAt(null);
                users.setDelete(false);

                userDAO.inserUser(users);

                resp.sendRedirect(req.getContextPath() + "/index.jsp");

            } else {
                req.setAttribute("mess", "Password and Re-password don't match");
                req.getRequestDispatcher("view/signup.jsp").forward(req, resp);
            }
        }


    }
    public boolean checkExistEmailAndUsername(String email, String username){
        UserDAO userDAO = new UserDAO();
        boolean check = false;
        List<Users> listUser = userDAO.getAllListUser();

        for(Users users : listUser){
            if(users.getEmail().equals(email) && users.getUsername().equals(username)){
                check = true;
            } else{
                check = false;
            }
        }
        return check;
    }
}
