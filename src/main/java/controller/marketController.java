package controller;

import DAO.postDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/market"})
public class marketController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        postDAO postDAO = new postDAO();
        List<Post> getAllPost = postDAO.getAllPost();
        req.setAttribute("lPosts",getAllPost);
        req.getRequestDispatcher("WEB-INF/view/market.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
