package controller;

import dao.BillDAO;
import dao.UserDAO;
import model.Bill;
import util.Config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import static java.lang.System.out;

@WebServlet(urlPatterns = {"/processVnpayResponse"})
public class ProcessVnpayResponseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processVnpayResponse(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processVnpayResponse(request, response);
    }

    private void processVnpayResponse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String transactionCode = request.getParameter("vnp_TxnRef");
        String amount = request.getParameter("vnp_Amount");
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String responseCode = request.getParameter("vnp_ResponseCode");
        String transactionNo = request.getParameter("vnp_TransactionNo");
        String bankCode = request.getParameter("vnp_BankCode");
        String payDate = request.getParameter("vnp_PayDate");
        String transactionStatus = request.getParameter("vnp_TransactionStatus");

        // Kiểm tra xem giao dịch thành công hay thất bại và xử lý tương ứng
        transactionCode = request.getParameter("vnp_TxnRef");
        transactionStatus = request.getParameter("vnp_TransactionStatus");
        if ("00".equals(transactionStatus)) {
            // Giao dịch thành công
            processSuccessfulTransaction(request);
            String username = (String) request.getSession().getAttribute("username");
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsername(username);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/view/viewVnPayResult.jsp").forward(request, response);
        } else {
            // Giao dịch thất bại
            // Xử lý tùy ý, ví dụ hiển thị thông báo lỗi cho người dùng
            response.sendRedirect(request.getContextPath() + "/transactionFailed");
        }
    }

    private void processSuccessfulTransaction(HttpServletRequest request) {
        String transactionCode = request.getParameter("vnp_TxnRef");

        // Kiểm tra xem bill đã tồn tại hay chưa
        BillDAO billDAO = new BillDAO();
        if (!billDAO.isBillExists(transactionCode)) {
            // Nếu bill chưa tồn tại, thực hiện cập nhật balance và lưu bill vào cơ sở dữ liệu
            // Lấy thông tin người dùng từ session
            String username = (String) request.getSession().getAttribute("username");
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsername(username);

            // Cập nhật số dư của người dùng
            long amount = Long.parseLong(request.getParameter("vnp_Amount"));
            long newBalance = user.getBalance() + amount / 100;
            user.setBalance(newBalance);
            userDAO.updateUser(user);

            // Tạo mới đối tượng Bill và lưu vào cơ sở dữ liệu
            Bill bill = new Bill();
            bill.setTransactionCode(transactionCode);
            bill.setAmount(amount);
            bill.setOrderInfo(request.getParameter("vnp_OrderInfo"));
            bill.setVnpayResponseCode(request.getParameter("vnp_ResponseCode"));
            bill.setVnpayTransactionCode(request.getParameter("vnp_TransactionNo"));
            bill.setBankCode(request.getParameter("vnp_BankCode"));
            bill.setPayDate(request.getParameter("vnp_PayDate"));
            bill.setPaymentStatus(request.getParameter("vnp_TransactionStatus"));

            billDAO.save(bill);
        }
}
}

