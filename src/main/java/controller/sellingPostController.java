package controller;

import DAO.postDAO;
import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/sellingPost"})
public class sellingPostController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            userDAO userDAO = new userDAO();
            String username = (String) req.getSession().getAttribute("username");
            User user = userDAO.getUserByUsername(username);
            postDAO postDAO = new postDAO();
            List<Post> getAllPost = postDAO.getAllPostBySeller(user);
            req.setAttribute("lPosts", getAllPost);
            req.getRequestDispatcher("WEB-INF/view/sellingPost.jsp").forward(req, resp);
        }
    }
}
