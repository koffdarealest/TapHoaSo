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
import java.util.List;

@WebServlet(urlPatterns = {"/postDetailUpdate"})
public class updatePostController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            Long id = getPostID(req, resp);
            Post post = getPostByID(req, resp, id);
            if (isDeletedPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            }
            if (!isValidUserToViewPost(req, resp, post)) {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
                return;
            } else {
                req.setAttribute("chosenPost", post);
            }
            getAllPost(req, resp);
            req.getRequestDispatcher("WEB-INF/view/sellingPostDetail.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> params = getParams(req);
        if (!isValidParams(params)) {
            req.setAttribute("notification", "Invalid input! Please <a href=sellingPost>try again</a>!");
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
            return;
        }
        if (!isValidPrice(Long.parseLong(params.get("price")))) {
            req.setAttribute("priceError", "Invalid price! Please try again!");
            doGet(req, resp);
            return;
        }
        Long postID = getPostID(req, resp);
        Post post = getPostByID(req, resp, postID);
        if (!isValidUserToViewPost(req, resp, post)) {
            req.setAttribute("notification", "You are not allowed to update this post! <a href=sellingPost>Go back here</a>");
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
            return;
        }
        if (!isUpdateable(req, resp, post)) {
            req.setAttribute("notification", "This post is not updateable! <a href=sellingPost>Go back here</a>");
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
            return;
        }
        updatePost(req, resp, params, postID);
        req.setAttribute("notification", "Update post successfully! <a href=sellingPost>Go back here</a>");
        req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
    }

    //--------------------------------------doGet function --------------------------------------
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

    private Post getPostByID(HttpServletRequest req, HttpServletResponse resp, Long id) {
        Post post = new Post();
        try {
            postDAO postDAO = new postDAO();
            post = postDAO.getPostByID(id);
            return post;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private void getAllPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            userDAO userDAO = new userDAO();
            String username = (String) req.getSession().getAttribute("username");
            User user = userDAO.getUserByUsername(username);
            postDAO postDAO = new postDAO();
            List<Post> getAllPost = postDAO.getAllPostBySeller(user);
            req.setAttribute("lPosts", getAllPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------doPost function --------------------------------------
    private HashMap<String, String> getParams(HttpServletRequest req) {
        HashMap<String, String> params = new HashMap<>();
        try {
            params.put("title", req.getParameter("title"));
            params.put("price", req.getParameter("price"));
            params.put("feePayer", req.getParameter("feePayer"));
            params.put("description", req.getParameter("description"));
            params.put("contact", req.getParameter("contact"));
            params.put("hidden", req.getParameter("hidden"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    private boolean isValidParams(HashMap<String, String> params) {
        String title = params.get("title");
        String price = params.get("price");
        String feePayer = params.get("feePayer");
        String description = params.get("description");
        String contact = params.get("contact");
        String hidden = params.get("hidden");
        if (title == null || price == null || feePayer == null || description == null || contact == null || hidden == null) {
            return false;
        } else {
            return true;
        }
    }

    private void updatePost(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> params, Long postID) {
        postDAO postDAO = new postDAO();
        Post post = postDAO.getPostByID(postID);
        try {
            Long price = Long.parseLong(params.get("price"));
            Long fee = price * 5 / 100;
            post.setTopic(params.get("title"));
            post.setPrice(price);
            post.setFee(fee);
            post.setWhoPayFee(params.get("feePayer"));
            post.setDescription(params.get("description"));
            post.setContact(params.get("contact"));
            post.setHidden(params.get("hidden"));
            post.setTotalSpendForBuyer(params.get("feePayer"));
            post.setTotalReceiveForSeller(params.get("feePayer"));
            postDAO.updatePost(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidPrice(Long price) {
        if (price % 1000L == 0 && price >= 1000L) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidUserToViewPost(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            String username = (String) req.getSession().getAttribute("username");
            if (post.getSellerID().getUsername().equals(username)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isDeletedPost(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            if (post.getDelete()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isUpdateable(HttpServletRequest req, HttpServletResponse resp, Post post) {
        try {
            if (post.getUpdateable()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
