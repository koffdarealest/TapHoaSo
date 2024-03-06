package controller;

import dao.PostDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.TransactionProcessor;
import model.Post;
import model.User;
import model.Transaction;
import wrapper.TransactionWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@WebServlet(urlPatterns = {"/sell"})
public class SellController extends HttpServlet {
    //-----------------TransactionProcessor-----------------
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private BlockingQueue<TransactionWrapper> transactionQueue = new LinkedBlockingQueue<>();
    TransactionProcessor transactionProcessor = new TransactionProcessor(transactionQueue);
    public void init() {
        executor.submit(transactionProcessor);
    }
    //-----------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect( req.getContextPath() + "/signin");
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
            Post post = createPost(req, resp, params);
            boolean isPaid = false;
            try {
                isPaid = payPrepostFee(req, resp, post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!isPaid) {
                req.setAttribute("notification", "Invalid paid!");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
            } else {

                insertPostToDB(req, resp, post);
                resp.sendRedirect( req.getContextPath() + "/home");
            }
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
            UserDAO userDAO = new UserDAO();
            user = userDAO.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private Post createPost(HttpServletRequest req, HttpServletResponse resp, HashMap<String, String> params) {
        Post post = new Post();
        PostDAO postDAO = new PostDAO();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private boolean payPrepostFee(HttpServletRequest req, HttpServletResponse resp, Post post) {
        TransactionDAO transactionDAO = new TransactionDAO();
        boolean status= false;
        try {
            Transaction trans = transactionDAO.createPrepostFeeTrans(post);
//            status = transactionDAO.executeTrans(trans);
//            if (status) {
//            transactionDAO.saveTransaction(trans);
//            }
            TransactionWrapper transactionWrapper = new TransactionWrapper(trans);
            transactionQueue.add(transactionWrapper);
            status = transactionWrapper.getFuture().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    private boolean isBalanceEnough(HttpServletRequest req, HttpServletResponse resp) {
        User user = getUser(req);
        PostDAO postDAO = new PostDAO();
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

    private void insertPostToDB(HttpServletRequest req, HttpServletResponse resp, Post post) {
        PostDAO postDAO = new PostDAO();
        postDAO.insertPost(post);
    }

}
