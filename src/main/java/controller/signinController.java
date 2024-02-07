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
        getRememberInfo(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //-----------------get parameter-----------------
        Map<String, String> getParameters = getParameter(req, resp);
        //-----------------verify captcha-----------------
        if (!isTrueCaptcha(req, resp)) {
            req.setAttribute("error", "Captcha is not correct! Try again!");
//            req.getRequestDispatcher("/signin").forward(req, resp);
            doGet(req, resp);
            return;
        }
        //-----------------check valid user-----------------
        if (checkValidUsernameAndPassword(req, resp)) {
            //-----------------check admin-----------------
            if (checkIsAdmin(req, resp)) {
                req.getSession().setAttribute("username", getParameters.get("username"));
                resp.sendRedirect(req.getContextPath() + "/admin");
                //-----------------check deleted user-----------------
            } else if (checkIsDeletedUser(req, resp)) {
                req.setAttribute("notification", "Your account is banned or deleted! Please contact admin to get more information!");
                req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
                //-----------------check active user-----------------
            } else if (!IsActivated(req, resp)) {
                req.getSession().setAttribute("username", getParameters.get("username"));
                req.setAttribute("notification", "Your account is not activated! <a href=" + "resendVerifyEmail" +">Click here</a> to send a new verify email!");
                req.getRequestDispatcher("/view/statusNotification.jsp").forward(req, resp);
            } else if (isRemember(req, resp)) {
                setCookie(req, resp);
                login(req, resp);
            } else {
                login(req, resp);
            }
        } else {
            req.setAttribute("error", "Wrong username or password");
            req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
        }
    }

    private boolean isTrueCaptcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HashMap<String, String> map = getParameter(req, resp);
        String enteredCaptcha = map.get("captcha");
        String captcha = (String) req.getSession().getAttribute("captcha");
        if (!enteredCaptcha.equals(captcha)) {
            try {
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private HashMap<String, String> getParameter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = new HashMap<>();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        String captcha = req.getParameter("captcha");
        map.put("username", username);
        map.put("password", password);
        map.put("remember", remember);
        map.put("captcha", captcha);
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

    private boolean IsActivated(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = getParameter(req, resp);
        String username = map.get("username");
        userDAO userDAO = new userDAO();
        boolean isActivated = userDAO.getUserByUsername(username).getActivated();
        return isActivated;
    }

    private void setCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = getParameter(req, resp);
        String username = map.get("username");
        String password = map.get("password");
        userDAO userDAO = new userDAO();
        Encryption encryption = new Encryption();
        byte[] key = userDAO.getSecretKeyByUsername(username);
        String encryptedPassword = "";
        try {
            encryptedPassword = encryption.encrypt(password, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Cookie usernameCookie = new Cookie("userC", username);
            Cookie passwordCookie = new Cookie("pwdC", encryptedPassword);
            usernameCookie.setMaxAge(60 * 60 * 24);
            passwordCookie.setMaxAge(60 * 60 * 24);
            resp.addCookie(usernameCookie);
            resp.addCookie(passwordCookie);
        } catch (Exception e) {
            System.err.println("Cookie not seted");
            throw new RuntimeException(e);
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

    private void getRememberInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = getUsernameCookieValue(req, resp);
        String password = getPasswordCookieValue(req, resp);

        if (username.equals("") || password.equals("")) {
            req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
        } else {
            try {
                userDAO userDAO = new userDAO();
                byte[] key = userDAO.getSecretKeyByUsername(username);
                Encryption encryption = new Encryption();
                String decryptedPassword = encryption.decrypt(password, key);
                req.setAttribute("password", decryptedPassword);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("username", username);
            req.getRequestDispatcher("/view/signin.jsp").forward(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = getParameter(req, resp);
        String username = map.get("username");
        try {
            req.getSession().setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isRemember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = getParameter(req, resp);
        String remember = map.get("remember");
        if (remember != null) {
            return true;
        }
        return false;
    }


}
