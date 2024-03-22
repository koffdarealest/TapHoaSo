package controller.productpostaction;

import dao.PostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;


import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/market"})
public class MarketController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {

            PostDAO postDAO = new PostDAO();
            List<Post> getAllPost = postDAO.getAllPublicPost();
            req.setAttribute("lPosts", getAllPost);
            req.getRequestDispatcher("WEB-INF/view/market.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
