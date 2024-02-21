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
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect("/signin");
        } else {
            req.getRequestDispatcher("/WEB-INF/view/sell.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> params = getParams(req);
        if (!isValidParams(params)) {
            req.setAttribute("notification", "Invalid input! Please <a href=sell>try again</a>!");
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
            return;
        }
        if (!isValidPrice(Long.parseLong(params.get("price")))) {
            req.setAttribute("priceError", "Invalid price! Please try again!");
            req.getRequestDispatcher("/WEB-INF/view/sell.jsp").forward(req, resp);
            return;
        }
        if (isBalanceEnough(req, resp)) {
            payPrepostFee(req, resp);
            createPost(req, resp, params);
            resp.sendRedirect("/home");
        } else {
            req.setAttribute("notification", "Your balance is not enough! Please <a href=" + "deposit>" + "top up</a> your balance!");
            req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
        }
    }

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
        if (title == null || price == null || feePayer == null || description == null || contact == null || hidden == null ||
                title.trim().isEmpty() || price.trim().isEmpty() || feePayer.trim().isEmpty() ||
                description.trim().isEmpty() || contact.trim().isEmpty() || hidden.trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private User getUser(HttpServletRequest req) {
        User user = new User();
        try {
            String username = (String) req.getSession().getAttribute("username");
            userDAO userDAO = new userDAO();
            user = userDAO.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private void createPost(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> params) {
        Post post = new Post();
        postDAO postDAO = new postDAO();
        String tradingCode = postDAO.createTradingCode();
        try {
            Long price = Long.parseLong(params.get("price"));
            Long fee = price * 5 / 100;
            post.setSellerID(getUser(req));
            post.setTradingCode(tradingCode);
            post.setTopic(params.get("title"));
            post.setPrice(price);
            post.setFee(fee);
            post.setWhoPayFee(params.get("feePayer"));
            post.setDescription(params.get("description"));
            post.setContact(params.get("contact"));
            post.setHidden(params.get("hidden"));
            post.setPublic(true);
            post.setStatus("readyToSell");
            post.setTotalReceiveForSeller(params.get("feePayer"));
            post.setTotalSpendForBuyer(params.get("feePayer"));
            post.setUpdateable(true);
            post.setCanBuyerComplain(false);
            postDAO.insertPost(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void payPrepostFee(HttpServletRequest req, HttpServletResponse resp) {
        userDAO userDAO = new userDAO();
        User user = getUser(req);
        Long balance = user.getBalance();
        Long prepostFee = 500L;
        userDAO.updateBalance(user, balance - prepostFee);

    }

    private boolean isBalanceEnough(HttpServletRequest req, HttpServletResponse resp) {
        User user = getUser(req);
        postDAO postDAO = new postDAO();
        Long price = 500L;
        boolean isEnough = postDAO.isBalanceEnough(user, price);
        if (isEnough) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidPrice(Long price) {
        if (price % 1000L == 0 && price >= 1000L) {
            return true;
        } else {
            return false;
        }
    }






}
