package controller;

import DAO.userDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Constants;
import model.User;
import model.UserGoogleDTO;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import util.Encryption;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "LoginGoogleHandler", urlPatterns = {"/LoginGoogleHandler"})
public class loginGoogleHandler extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");
        String accessToken = getToken(code);
        UserGoogleDTO userGoogleDTO = getUserInfo(accessToken);

//        request.setAttribute("id", userGoogleDTO.getId());
//        request.setAttribute("name", userGoogleDTO.getName());
//        request.setAttribute("email", userGoogleDTO.getEmail());

        userDAO userDAO = new userDAO();
        if (userDAO.checkExistEmail(userGoogleDTO.getEmail()) == false) {
            User userDTO = new User();
            userDTO.setUsername(userGoogleDTO.getEmail());
            userDTO.setEmail(userGoogleDTO.getEmail());
            userDTO.setNickname(userGoogleDTO.getName());
            userDTO.setAdmin(false);
            userDTO.setBalance(0L);
            userDTO.setActivated(true);
            userDTO.setDelete(false);
            userDTO.setSigninWithGoogle(true);
            userDAO.insertUser(userDTO);
            request.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(request, response);
        } else {
            User user = userDAO.getUserByGmail(userGoogleDTO.getEmail());
            String username = user.getUsername();
            boolean isLoginWithGoogle = user.getSigninWithGoogle();
            if (isLoginWithGoogle) {
                request.getSession().setAttribute("username", username);
                request.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "This email is already used for signing up with Username!");
                request.getRequestDispatcher("/WEB-INT/view/signin.jsp").forward(request, response);
            }

            //setCookie(request, response);
            //login(request, response);
        }
    }

    public static String getToken(String code) throws ClientProtocolException, IOException {
        // call api to get token
        String response = Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form()
                        .add("client_id", Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", Constants.GOOGLE_GRANT_TYPE)
                        .build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static UserGoogleDTO getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        UserGoogleDTO googlePojo = new Gson().fromJson(response, UserGoogleDTO.class);

        return googlePojo;
    }

    private HashMap<String, String> getParameter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = new HashMap<>();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        map.put("username", username);
        map.put("password", password);
        return map;
    }

    private void setCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = getParameter(req, resp);
        String email = map.get("email");
        userDAO userDAO = new userDAO();
        List<User> users = userDAO.getAllUser();
        User USer = userDAO.getUserByGmail(email);
        Encryption encryption = new Encryption();
        byte[] key = userDAO.getSecretKeyByUsername(USer.getUsername());
        String encryptedPassword = "";
        try {
            encryptedPassword = encryption.encrypt(USer.getPassword(), key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("online", user.getUserID());
                    user.setOnline(true);
                    userDAO.updateUser(user);
                }
            }

            Cookie usernameCookie = new Cookie("userC", USer.getUsername());
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

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userDAO userDAO = new userDAO();
        HashMap<String, String> map = getParameter(req, resp);
        List<User> users = userDAO.getAllUser();
        String email = map.get("email");
        User USer = userDAO.getUserByGmail(email);
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                HttpSession session = req.getSession();
                session.setAttribute("online", user.getUserID());
                user.setOnline(true);
                userDAO.updateUser(user);
            }
        }
        try {
            req.getSession().setAttribute("username", USer.getUsername());
            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
