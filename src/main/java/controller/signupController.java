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

@WebServlet(urlPatterns = {"/signup"})
public class signupController extends HttpServlet {
    public signupController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        super.doGet(req, resp);
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
            resp.sendRedirect("index.jsp");
        } else {
            req.setAttribute("mess", "password anf rePassword doesn't match");
            req.getRequestDispatcher("TapHoaSo/view/signup.jsp").forward(req, resp);
        }
    }
}
