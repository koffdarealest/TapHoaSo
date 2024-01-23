package controller;

import DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Users;

import java.io.IOException;
import java.util.Date;

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

        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String username = req.getParameter("userName");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("re-password");

        if(password.equals(rePassword)){

            users.setNickname(fullname);
            users.setEmail(email);
            users.setPassword(password);
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
