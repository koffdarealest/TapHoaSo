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
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = {"/signin"})

public class signinController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Cookie arrCookie[] = req.getCookies();
//        String user = "";
//        String ePwd = "";
//        if (arrCookie != null) {
//            for (Cookie cookie : arrCookie) {
//                if (cookie.getName().equals("userC")) {
//                    user = cookie.getValue();
//                }
//                if (cookie.getName().equals("pwdC")) {
//                    ePwd = cookie.getValue();
//                }
//            }
//        }
//        if (user.equals("") || ePwd.equals("")) {
//            req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
//        } else {
//            try {
//                req.setAttribute("username", user);
//                byte[] key = userDAO.getSecretKey(user);
//                String password = encryption.decrypt(ePwd, key);
//                req.setAttribute("password", password);
//                req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
        setRememberInfo(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //-----------------get parameter-----------------
        Map<String, String> getParameters = getParameter(req, resp);
        //-----------------check valid user-----------------
        if (checkValidUsernameAndPassword(req, resp)) {
            //-----------------check admin-----------------
            if (checkIsAdmin(req, resp)) {
                req.getSession().setAttribute("username", getParameters.get("username"));
                resp.sendRedirect(req.getContextPath() + "/admin");
                //-----------------check deleted user-----------------
            } else if (checkIsDeletedUser(req, resp)) {
                resp.sendRedirect(req.getContextPath() + "/deletedUser");
                //-----------------check active user-----------------
            } else if (checkIsActivated(req, resp)) {
                resp.sendRedirect(req.getContextPath() + "/notActivated");
            } else {
                setLogin(req, resp);
            }
        } else {
            req.setAttribute("error", "Wrong username or password");
            req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
        }
    }

    private HashMap<String, String> getParameter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = new HashMap<>();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        map.put("username", username);
        map.put("password", password);
        map.put("remember", remember);
        return map;
    }

    private boolean checkValidUsernameAndPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = getParameter(req, resp);
        String username = map.get("username");
        String password = map.get("password");
        userDAO userDAO = new userDAO();
        boolean isValid = userDAO.CheckValidUser(username, password);
        return isValid;
    }

    private boolean checkIsAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = getParameter(req, resp);
        String username = map.get("username");
        userDAO userDAO = new userDAO();
        boolean isAdmin = userDAO.getUserByUsername(username).getAdmin();
        return isAdmin;
    }

    private boolean checkIsDeletedUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = getParameter(req, resp);
        String username = map.get("username");
        userDAO userDAO = new userDAO();
        boolean isDeletedUser = userDAO.getUserByUsername(username).getDelete();
        return isDeletedUser;
    }

    private boolean checkIsActivated(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = getParameter(req, resp);
        String username = map.get("username");
        userDAO userDAO = new userDAO();
        boolean isActivated = userDAO.getUserByUsername(username).getActivated();
        return isActivated;
    }

    private void setLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = getParameter(req, resp);
        String username = map.get("username");
        String password = map.get("password");
        String remember = map.get("remember");
        req.getSession().setAttribute("username", username);
        if (remember != null) {                                             //if remember me is checked, encrypt password and send it to client
            try {
                Cookie usernameCookie = new Cookie("userC", username);
                Cookie passwordCookie = new Cookie("pwdC", password);
                usernameCookie.setMaxAge(60 * 60 * 24);
                passwordCookie.setMaxAge(60 * 60 * 24);
                resp.addCookie(usernameCookie);
                resp.addCookie(passwordCookie);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    private Cookie[] getCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie arrCookie[] = req.getCookies();
        return arrCookie;
    }

    private String getUsernameCookieValue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie arrCookie[] = getCookie(req, resp);
        String username = "";
        if (arrCookie != null) {
            for (Cookie cookie : arrCookie) {
                if (cookie.getName().equals("userC")) {
                    username = cookie.getValue();
                }
            }
        }
        return username;
    }

    private String getPasswordCookieValue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie arrCookie[] = getCookie(req, resp);
        String password = "";
        if (arrCookie != null) {
            for (Cookie cookie : arrCookie) {
                if (cookie.getName().equals("pwdC")) {
                    password = cookie.getValue();
                }
            }
        }
        return password;
    }

    private void setRememberInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = getUsernameCookieValue(req, resp);
        String password = getPasswordCookieValue(req, resp);
        if (username.equals("") || password.equals("")) {
            req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
        } else {
            try {
                req.setAttribute("username", username);
                req.setAttribute("password", password);
                req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}
