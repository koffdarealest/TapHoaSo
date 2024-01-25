package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class VerifyController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String token = req.getParameter("token");
//        TokenDAO tokenDAO = new TokenDAO();
//        Token token1 = tokenDAO.getToken(token);
//        if(token1 == null){
//            req.setAttribute("mess", "Token is invalid!");
//            req.getRequestDispatcher("/view/verify.jsp").forward(req, resp);
//            return;
//        }
//        LocalDateTime expTime = token1.getExpTime();
//        LocalDateTime now = LocalDateTime.now();
//        if(now.isAfter(expTime)){
//            req.setAttribute("mess", "Token is expired!");
//            req.getRequestDispatcher("/view/verify.jsp").forward(req, resp);
//            return;
//        }
//        String email = token1.getEmail();
//        UserDAO userDAO = new UserDAO();
//        User user = userDAO.getUserByEmail(email);
//        user.setActive(true);
//        userDAO.updateUser(user);
//        tokenDAO.deleteToken(token1);
//        req.setAttribute("mess", "Verify successfully!");
//        req.getRequestDispatcher("/view/verify.jsp").forward(req, resp);
    }
}
