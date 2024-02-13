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


@WebServlet(urlPatterns = {"/postDetail"})
public class postDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("/signin");
        } else {
            Long id = getPostID(req, resp);
            getPost(req, resp, id);
            getAllPost(req, resp);
            req.getRequestDispatcher("WEB-INF/view/postDetail.jsp").forward(req,resp);
        }
    }

    private Long getPostID(HttpServletRequest req, HttpServletResponse resp) {
        Long postID = null;
        try {
            String ID = req.getParameter("postID");
            postID = Long.parseLong(ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postID;
    }

    private void getPost(HttpServletRequest req, HttpServletResponse resp, Long id) {
        try {
            postDAO postDAO = new postDAO();
            Post post = postDAO.getPostByID(id);
            System.out.println(post.getTopic());
            req.setAttribute("chosenPost",post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getAllPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            postDAO postDAO = new postDAO();
            List<Post> getAllPost = postDAO.getAllPublicPost();
            req.setAttribute("lPosts",getAllPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
