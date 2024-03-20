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

@WebServlet(urlPatterns = {"/buyingPost"})
public class BuyingPostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            UserDAO userDAO = new UserDAO();
            String username = (String) req.getSession().getAttribute("username");
            User user = userDAO.getUserByUsername(username);
            PostDAO postDAO = new PostDAO();
            List<Post> getAllPost = postDAO.getAllPostByBuyer(user);
            req.setAttribute("lPosts", getAllPost);
            req.getRequestDispatcher("WEB-INF/view/buyingPost.jsp").forward(req, resp);
        }
    }
}
