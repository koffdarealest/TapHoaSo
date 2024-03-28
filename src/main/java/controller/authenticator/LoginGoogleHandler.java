package controller.authenticator;

import dao.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Constants;
import model.User;
import model.UserGoogleDTO;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

@WebServlet(name = "LoginGoogleHandler", urlPatterns = {"/LoginGoogleHandler"})
public class LoginGoogleHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");
        String accessToken = getToken(code);
        UserGoogleDTO userGoogleDTO = getUserInfo(accessToken);

//        request.setAttribute("id", userGoogleDTO.getId());
//        request.setAttribute("name", userGoogleDTO.getName());
//        request.setAttribute("email", userGoogleDTO.getEmail());

        UserDAO userDAO = new UserDAO();

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
            request.getSession().setAttribute("username", userDTO.getUsername());
            request.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(request, response);

        } else {
            User user = userDAO.getUserByGmail(userGoogleDTO.getEmail());
            String username = user.getUsername();
            boolean isLoginWithGoogle = user.getSigninWithGoogle();
            if (isLoginWithGoogle) {
                request.setAttribute("user", user);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
