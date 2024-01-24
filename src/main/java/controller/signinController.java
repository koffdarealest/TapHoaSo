package controller;


import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.Encryption;

import java.io.IOException;


@WebServlet(urlPatterns = {"/signin"})

public class signinController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userDAO userDAO = new userDAO();
        Encryption encryption = new Encryption();
        Cookie arrCookie[] = req.getCookies();
        String user = "";
        String ePwd = "";
        if (arrCookie != null) {
            for (Cookie cookie : arrCookie) {
                if (cookie.getName().equals("userC")) {
                    user = cookie.getValue();
                }
                if (cookie.getName().equals("pwdC")) {
                    ePwd = cookie.getValue();
                }
            }
        }
        if (user.equals("") || ePwd.equals("")) {
            req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
        } else {
            try {
                req.setAttribute("username", user);
                byte[] key = userDAO.getSecretKey(user);
                String password = encryption.decrypt(ePwd, key);
                req.setAttribute("password", password);
                req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        userDAO userDAO = new userDAO();
        Encryption encryption = new Encryption();
        if (userDAO.CheckValidUser(username, password)) {
            boolean isAdmin = userDAO.getUserByUsername(username).isAdmin();
            if (isAdmin) {
                req.getSession().setAttribute("username", username);
                resp.sendRedirect(req.getContextPath() + "/admin");
            } else {
                boolean isDeletedUser = userDAO.getUserByUsername(username).getDelete();
                if (!isDeletedUser) {
                    req.getSession().setAttribute("username", username);
                    byte[] key = userDAO.getSecretKey(username);
                    if (remember != null) {                                             //if remember me is checked, encrypt password and send it to client
                        try {
                            String ePwd = encryption.encrypt(password, key);
                            Cookie usernameCookie = new Cookie("userC", username);
                            Cookie passwordCookie = new Cookie("pwdC", ePwd);
                            usernameCookie.setMaxAge(60 * 60 * 24);
                            passwordCookie.setMaxAge(60 * 60 * 24);
                            resp.addCookie(usernameCookie);
                            resp.addCookie(passwordCookie);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                resp.sendRedirect(req.getContextPath() + "/home");
            }

        } else {
            req.setAttribute("error", "Wrong username or password");
            req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
        }
    }
}
