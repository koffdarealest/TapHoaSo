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
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = {"/postDetailUpdate"})
public class UpdatePostController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
        } else {
            String code = getCode(req, resp);
            Post post = getPostByCode(req, resp, code);
            if (isDeletedPost(req, resp, post)) {
                notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
                return;
            }
            if (!isValidUserToViewPost(req, resp, post)) {
                notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
                return;
            } else {
                req.setAttribute("chosenPost", post);
            }
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsername(username);
            getAllPost(req, resp);
            req.setAttribute("user", user);
            req.getRequestDispatcher("WEB-INF/view/sellingPostDetail.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> params = getParams(req);
        if (!isValidParams(params)) {
            notifyUser(req, resp, "Invalid input! Please <a href=sellingPost>try again</a>!");
            return;
        }
        if (!isValidPrice(Long.parseLong(params.get("price")))) {
            req.setAttribute("priceError", "Invalid price! Please try again!");
            doGet(req, resp);
            return;
        }
        String code = params.get("tradingCode");
        Post post = getPostByCode(req, resp, code);
        if (!isValidUserToViewPost(req, resp, post)) {
            notifyUser(req, resp, "Invalid action! <a href=home>Go back here</a>");
            return;
        }
        if (!isUpdateable(req, resp, post)) {
            notifyUser(req, resp, "This post is not updateable! <a href=sellingPost>Go back here</a>");
            return;
        }
        updatePost(req, resp, params, post);
        notifyUser(req, resp, "Update post successfully! <a href=sellingPost>Go back here</a>");
    }

    //--------------------------------------doGet function --------------------------------------
    private String getCode(HttpServletRequest req, HttpServletResponse resp) {
        String tradingCode = null;
        try {
            tradingCode = req.getParameter("tradingCode");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tradingCode;
    }

    private Post getPostByCode(HttpServletRequest req, HttpServletResponse resp, String code) {
        Post post = new Post();
        try {
            PostDAO postDAO = new PostDAO();
            post = postDAO.getPostByTradingCode(code);
            return post;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private void getAllPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            UserDAO userDAO = new UserDAO();
            String username = (String) req.getSession().getAttribute("username");
            User user = userDAO.getUserByUsername(username);
            PostDAO postDAO = new PostDAO();
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
            params.put("tradingCode", req.getParameter("tradingCode"));
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
        String tradingCode = params.get("tradingCode");
        String title = params.get("title");
        String price = params.get("price");
        String feePayer = params.get("feePayer");
        String description = params.get("description");
        String contact = params.get("contact");
        String hidden = params.get("hidden");
        if (tradingCode == null || title == null || price == null || feePayer == null || description == null || contact == null || hidden == null) {
            return false;
        } else {
            return true;
        }
    }

    private void updatePost(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> params, Post post) {
        PostDAO postDAO = new PostDAO();
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
            return post.getSellerID().getUsername().equals(username);
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

    private void notifyUser(HttpServletRequest req, HttpServletResponse resp, String notification) {
        try {
        req.setAttribute("notification", notification);
        req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
