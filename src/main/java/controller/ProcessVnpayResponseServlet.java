package controller;

import dao.BillDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import dao.VnPayTransactionDAO;
import model.Bill;
import model.VnPayTransaction;
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


        if ("00".equals(transactionStatus)) {
            // Giao dịch thành công

            // Kiểm tra trạng thái thanh toán và cập nhật hóa đơn nếu cần
            if (checkPaymentStatus(transactionCode)) {
                // Nếu thanh toán đã được xác nhận thành công

                String username = (String) request.getSession().getAttribute("username");
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUserByUsername(username);

                // Gửi thông tin người dùng đến viewVnPayResult.jsp để hiển thị
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/view/viewVnPayResult.jsp").forward(request, response);
            } else {
                // Trạng thái thanh toán không hợp lệ, xử lý tùy ý
                response.sendRedirect(request.getContextPath() + "/transactionFailed");
            }
        } else {
            // Giao dịch thất bại
            // Xử lý tùy ý, ví dụ hiển thị thông báo lỗi cho người dùng
            response.sendRedirect(request.getContextPath() + "/transactionFailed");
        }
    }



        public boolean checkPaymentStatus(String transactionCode) {
        // Lấy hóa đơn từ mã giao dịch
        BillDAO billDAO = new BillDAO();
        Bill bill = new Bill();
        bill = billDAO.getBillByTransactionCode(transactionCode);

        // Kiểm tra xem hóa đơn có tồn tại không
        if (bill != null) {
            // Kiểm tra trạng thái thanh toán của hóa đơn
            if (bill.getPaymentStatus().equals("processing")) {
                // Nếu trạng thái thanh toán là "processing", cập nhật thành "success"
                bill.setPaymentStatus("success");
                // Cập nhật lại hóa đơn trong cơ sở dữ liệu
                billDAO.save(bill);

                return true; // Trả về true để báo hiệu rằng thanh toán đã được xử lý và cập nhật thành công
            } else if (bill.getPaymentStatus().equals("success")) {
                // Nếu trạng thái thanh toán đã là "success", không cần phải thực hiện gì thêm
                return true; // Trả về true để báo hiệu rằng thanh toán đã được xử lý
            } else {
                // Trạng thái thanh toán không hợp lệ, có thể xảy ra khi hóa đơn không tồn tại hoặc trạng thái không đúng
                return false; // Trả về false để báo hiệu rằng có lỗi xảy ra khi kiểm tra trạng thái thanh toán
            }
        } else {
            // Hóa đơn không tồn tại
            return false; // Trả về false để báo hiệu rằng không tìm thấy hóa đơn
        }
    }


}