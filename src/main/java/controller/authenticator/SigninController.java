package controller.authenticator;


import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import util.Encryption;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@WebServlet(urlPatterns = {"/signin"})

public class SigninController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getRememberInfo(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //-----------------get parameter-----------------
        HashMap<String, String> parameter = getParameter(req, resp);
        //-----------------verify captcha-----------------
        if (!isTrueCaptcha(req, resp, parameter)) {
            req.setAttribute("error", "Captcha is not correct! Try again!");
            doGet(req, resp);
            return;
        }
        if (checkIsAdmin(req, resp, parameter)) {
            req.getSession().setAttribute("username", parameter.get("username"));
            resp.sendRedirect(req.getContextPath() + "/adminManage");
            return;
            //-----------------check deleted user-----------------
        }
        //-----------------get user-----------------
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsernameAndPassword(parameter.get("username"), parameter.get("password"));
        if (user == null) {
            req.setAttribute("error", "Wrong username or password");
            req.getRequestDispatcher("/WEB-INF/view/signin.jsp").forward(req, resp);
            return;
        }
        //-----------------check admin-----------------
        if (checkIsAdmin(req, resp, parameter)) {
            req.getSession().setAttribute("username", parameter.get("username"));
            resp.sendRedirect(req.getContextPath() + "/adminManage");
            //-----------------check deleted user-----------------
        } else if (checkIsDeletedUser(req, resp, parameter, user)) {
            req.setAttribute("notification", "Your account is banned or deleted! Please contact admin to get more information!");
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
            //-----------------check active user-----------------
        } else if (!IsActivated(req, resp, parameter, user)) {
            req.getSession().setAttribute("usernameToSendActivateEmail", parameter.get("username"));
            req.setAttribute("notification", "Your account is not activated! <a href=" + "resendVerifyEmail" + ">Click here</a> to send a new verify email!");
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } else if (isRemember(req, resp, parameter)) {
            setCookie(req, resp, parameter);
            login(req, resp, parameter, user);
        } else {
            login(req, resp, parameter, user);
        }
    }


    private boolean isTrueCaptcha(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> map) {
        try {
            String enteredCaptcha = map.get("captcha");
            String captcha = (String) req.getSession().getAttribute("captcha");
            if (enteredCaptcha.equalsIgnoreCase(captcha)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
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


//    private boolean isTruePassword(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> map, User user) throws ServletException, IOException {
//        String username = map.get("username");
//        String EnteredPassword = map.get("password");
//        UserDAO userDAO = new UserDAO();
//        String password = user.getPassword();
//        if (userDAO.isTruePassword(EnteredPassword, password)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    private boolean checkIsAdmin(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> map) throws ServletException, IOException {
        //return user.getAdmin();
        return map.get("username").equals("admin") && map.get("password").equals("admin");
    }

    private boolean checkIsDeletedUser(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> map, User user) throws ServletException, IOException {
        return user.getDelete();
    }

    private boolean IsActivated(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> map, User user) throws ServletException, IOException {
        return user.getActivated();
    }

    private void setCookie(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> map) throws ServletException, IOException {
        String username = map.get("username");
        String password = map.get("password");
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUser();
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
            req.getRequestDispatcher("/WEB-INF/view/signin.jsp").forward(req, resp);
        } else {
            try {
                UserDAO userDAO = new UserDAO();
                byte[] key = userDAO.getSecretKeyByUsername(username);
                Encryption encryption = new Encryption();
                String decryptedPassword = encryption.decrypt(password, key);
                req.setAttribute("password", decryptedPassword);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("username", username);
            req.getRequestDispatcher("/WEB-INF/view/signin.jsp").forward(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> map, User user) throws ServletException, IOException {
        String username = map.get("username");
        try {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            session.setAttribute("userID", user.getUserID());
            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isRemember(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> map) throws ServletException, IOException {
        String remember = map.get("remember");
        if (remember != null) {
            return true;
        }
        return false;
    }
}
