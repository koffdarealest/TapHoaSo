package controller;

import DAO.userDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Constants;
import model.User;
import model.UserGoogleDTO;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
@WebServlet(name = "LoginGoogleHandler", urlPatterns = { "/LoginGoogleHandler" })
public class loginGoogleHandler extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");
        String accessToken = getToken(code);
        UserGoogleDTO userGoogleDTO = getUserInfo(accessToken);
        userDAO userDAO = new userDAO();

//        User userDTO = User.builder().email(userGoogleDTO.getEmail())
//                .username(userGoogleDTO.getName())
//                .build();
//        HttpSession session = request.getSession();
        System.out.println("Code:" + code);
        System.out.println("Access Token:" + accessToken);
        System.out.println("GG:" + userGoogleDTO);
//        response.sendRedirect("view/signup.jsp");
//        //Check email tồn tại chưa, được tạo, đã thay đổi pass
//        if (userDAO.checkExistEmail(userGoogleDTO.getEmail()) == true && userDAO.checkUserSetDate(userGoogleDTO.getEmail()) == true && userDAO.checkUserSetChangePassWord(userGoogleDTO.getEmail()) == true) {
//            User u = userDAO.getUserByGmail(userGoogleDTO.getEmail());
////            session.setAttribute("account", u);
////            response.sendRedirect("home");
//
//        }
//        //Check email tồn tại chưa, chưa được tạo, đã thay đổi pass
//        else if (userDAO.checkExistEmail(userGoogleDTO.getEmail()) == true && userDAO.checkUserSetDate(userGoogleDTO.getEmail()) == false && userDAO.checkUserSetChangePassWord(userGoogleDTO.getEmail()) == true) {
//            User u = userDAO.getUserByGmail(userGoogleDTO.getEmail());
////            session.setAttribute("account", u);
////            session.setAttribute("msgSetDate", "You must set your day of birth!!");
////            response.sendRedirect("user-account-new.jsp");
//
//
//        }
//        //Check email tồn tại chưa, chưa được tạo, chưa thay pass
//        else if (userDAO.checkExistEmail(userGoogleDTO.getEmail()) == true && userDAO.checkUserSetDate(userGoogleDTO.getEmail()) == false && userDAO.checkUserSetChangePassWord(userGoogleDTO.getEmail()) == false) {
//            String key = userDAO.checkRegisterGmail(userDTO, userGoogleDTO.getId(), 1);
//            User u = userDAO.getUserByGmail(userGoogleDTO.getEmail());
////            session.setAttribute("account", u);
////            HttpSession session1 = request.getSession();
////            session1.setAttribute("keyGoogle", u.getEmail() + "-" + key);
////            request.setAttribute("message", "The link to verify account has been sent to your email.");
////            request.getRequestDispatcher("redirect-mail-message.jsp").forward(request, response);
//
//
//
//        } else {
//            String key = userDAO.register(userDTO, userGoogleDTO.getId(), 1);
//            User u = userDAO.getUserGoogleRaw(userDTO.getEmail(), userGoogleDTO.getId());
////            session.setAttribute("account", u);
////            HttpSession session1 = request.getSession();
////            session1.setAttribute("keyGoogle", u.getEmail() + "-" + key);
////            request.setAttribute("message", "The link to verify account has been sent to your email.");
////            request.getRequestDispatcher("redirect-mail-message.jsp").forward(request, response);
//
//
//        }
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
