package controller.productpostaction;

import dao.PostDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;
import model.User;


import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/market"})
public class MarketController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            UserDAO userDAO = new UserDAO();
            PostDAO postDAO = new PostDAO();
            List<Post> getAllPost = postDAO.getAllPublicPost();
            User user = userDAO.getUserByUsername(username);
            req.setAttribute("user", user);
            req.setAttribute("lPosts", getAllPost);
            req.getRequestDispatcher("WEB-INF/view/market.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
