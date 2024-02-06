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
import java.util.HashMap;

@WebServlet(urlPatterns = {"/sell"})
public class sellController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/sell.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        createPost(req, resp);
        resp.sendRedirect("/home");
    }

    private HashMap<String, String> getParams(HttpServletRequest req) {
        HashMap<String, String> params = new HashMap<>();
        try {
            params.put("title", req.getParameter("title"));
            params.put("price", req.getParameter("price"));
            params.put("fee", req.getParameter("fee"));
            params.put("feePayer", req.getParameter("feePayer"));
            params.put("description", req.getParameter("description"));
            params.put("contact", req.getParameter("contact"));
            params.put("hidden", req.getParameter("hidden"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------fee" + params.get("fee"));
        return params;
    }

    private User getUser(HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");
        System.out.println("-------usernameeeeeeeeeee: " + username);
        userDAO userDAO = new userDAO();
        User user = userDAO.getUserByUsername(username);
        return user;
    }

    private void createPost(HttpServletRequest req, HttpServletResponse resp) {
        HashMap<String, String> params = getParams(req);
        Post post = new Post();
        postDAO postDAO = new postDAO();
        String tradingCode = postDAO.createTradingCode();
        try {
            post.setSellerID(getUser(req));
            System.out.println("-------sellerID: " + post.getSellerID() + "-------tradingCode: " + getUser(req).getUsername());
            post.setTradingCode(tradingCode);
            post.setTopic(params.get("title"));
            post.setPrice(Long.parseLong(params.get("price")));
            post.setFee(Long.parseLong(params.get("fee")));
            post.setWhoPayFee(params.get("feePayer"));
            post.setDescription(params.get("description"));
            post.setContact(params.get("contact"));
            post.setHidden(params.get("hidden"));
            post.setPublic(true);
            postDAO.insertPost(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
