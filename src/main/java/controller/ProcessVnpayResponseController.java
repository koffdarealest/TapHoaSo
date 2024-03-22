package controller;

import dao.BillDAO;
import dao.UserDAO;
import model.Bill;
import model.VnPayReturnData;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(urlPatterns = {"/processVnpayResponse"})
public class ProcessVnpayResponseController extends HttpServlet {

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
        if (request.getParameter("vnp_return_data") == null) {
            VnPayReturnData vnPayReturnData = new VnPayReturnData();
            vnPayReturnData.setTransactionCode(request.getParameter("vnp_TxnRef"));
            vnPayReturnData.setAmount(request.getParameter("vnp_Amount"));
            vnPayReturnData.setOrderInfo(request.getParameter("vnp_OrderInfo"));
            vnPayReturnData.setResponseCode(request.getParameter("vnp_ResponseCode"));
            vnPayReturnData.setTransactionNo(request.getParameter("vnp_TransactionNo"));
            vnPayReturnData.setBankCode(request.getParameter("vnp_BankCode"));
            vnPayReturnData.setPayDate(request.getParameter("vnp_PayDate"));
            vnPayReturnData.setTransactionStatus(request.getParameter("vnp_TransactionStatus"));
            response.sendRedirect(request.getContextPath() + "/processVnpayResponse?vnp_return_data=" + vnPayReturnData.encode());
        } else {
            String vnp_return_data = request.getParameter("vnp_return_data");
            VnPayReturnData vnPayReturnData = VnPayReturnData.decode(vnp_return_data);
            String transactionCode = vnPayReturnData.getTransactionCode();
            String amount = vnPayReturnData.getAmount();
            String transactionStatus = vnPayReturnData.getTransactionStatus();

//            String transactionCode = request.getParameter("vnp_TxnRef");
//            String amount = request.getParameter("vnp_Amount");
//            String orderInfo = request.getParameter("vnp_OrderInfo");
//            String responseCode = request.getParameter("vnp_ResponseCode");
//            String transactionNo = request.getParameter("vnp_TransactionNo");
//            String bankCode = request.getParameter("vnp_BankCode");
//            String payDate = request.getParameter("vnp_PayDate");
//            String transactionStatus = request.getParameter("vnp_TransactionStatus");

            // Kiểm tra xem giao dịch thành công hay thất bại và xử lý tương ứng
            if ("00".equals(transactionStatus)) {
                // Giao dịch thành công
                BillDAO billDAO = new BillDAO();
                Bill bill = billDAO.getBillByTransactionCode(transactionCode);
                if (bill == null)
                    response.sendRedirect(request.getContextPath() + "/transactionFailed");
                // Nếu thanh toán đã được xác nhận thành công
                String username = (String) request.getSession().getAttribute("username");
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUserByUsername(username);
                if (Objects.equals(bill.getPaymentStatus(), "processing")) {
                    bill.setPaymentStatus("success");
                    billDAO.update(bill);
                    user.setBalance(user.getBalance() + Long.parseLong(amount) / 100);
                    userDAO.updateUser(user);
                }
                // Gửi thông tin người dùng đến viewVnPayResult.jsp để hiển thị
                request.setAttribute("user", user);
                request.setAttribute("vnpayData", vnPayReturnData);
                request.getRequestDispatcher("/WEB-INF/view/viewVnPayResult.jsp").forward(request, response);

            } else {
                // Giao dịch thất bại
                // Xử lý tùy ý, ví dụ hiển thị thông báo lỗi cho người dùng
                response.sendRedirect(request.getContextPath() + "/transactionFailed");
            }
        }

    }
}