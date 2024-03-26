package controller.adminaction;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Transaction;
import model.User;
import dao.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@WebServlet(urlPatterns = {"/adminManage"})
public class AdminManageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
        } else {
            //User user = new UserDAO().getUserByUsername(username);
            if (username.equals("admin")) {
                getAllTransaction(req, resp);
                getAllUser(req, resp);
            } else {
                req.setAttribute("notification", "Invalid action! <a href=home>Go back here</a>");
                req.getRequestDispatcher("/WEB-INF/view/statusNotification.jsp").forward(req, resp);
            }
        }
        getAllUser(req, resp);
    }

    private void getAllTransaction(HttpServletRequest req, HttpServletResponse resp) {
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransaction();
        List<Transaction> withdrawTrans = new ArrayList<>();

        Transaction tran = new Transaction();


        for(Transaction transaction : transactions) {
            if(Objects.equals(transaction.getDescription().trim(), "Withdraw money".trim())){
                withdrawTrans.add(transaction);
            }
        }
        req.setAttribute("transactions", withdrawTrans);
    }

    private void getAllUser(HttpServletRequest req, HttpServletResponse resp) {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUser();
        List<String> username = getListUsernameOnline(req, resp);
        req.setAttribute("users", users);
        req.setAttribute("onlineUsernames", username);
        try {
            req.getRequestDispatcher("WEB-INF/view/manageUser.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getListUsernameOnline(HttpServletRequest req, HttpServletResponse resp) {
        List<String> usernameList = new ArrayList<>();
        Set<HttpSession> activeSessions = (Set<HttpSession>) req.getServletContext().getAttribute("activeSessions");
        for (HttpSession session : activeSessions) {
            String user = (String) session.getAttribute("username");
            System.out.println(user);
            usernameList.add(user);
        }
        return usernameList;
    }


}
