package controller.vnpaycontroller;

import dao.BillDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import listener.TransactionProcessor;
import model.Bill;
import model.Transaction;
import model.VnPayReturnData;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import wrapper.TransactionWrapper;

@WebServlet(urlPatterns = {"/processVnpayResponse"})
public class ProcessVnpayResponseController extends HttpServlet {
    //-----------------TransactionProcessor-----------------
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private BlockingQueue<TransactionWrapper> transactionQueue = new LinkedBlockingQueue<>();
    TransactionProcessor transactionProcessor = new TransactionProcessor(transactionQueue);
    public void init() {
        executor.submit(transactionProcessor);
    }
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
                    request.setAttribute("notification", "Invalid Transaction");
                    response.sendRedirect(request.getContextPath() + "/statusNotification");
                // Nếu thanh toán đã được xác nhận thành công
                String username = (String) request.getSession().getAttribute("username");
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUserByUsername(username);
                if (Objects.equals(bill.getPaymentStatus(), "processing")) {
                    bill.setPaymentStatus("success");
                    billDAO.update(bill);
                    deposit(user, Long.parseLong(amount) / 100L);
                }
                // Gửi thông tin người dùng đến viewVnPayResult.jsp để hiển thị
                request.setAttribute("user", user);
                request.setAttribute("vnpayData", vnPayReturnData);
                request.getRequestDispatcher("/WEB-INF/view/viewVnPayResult.jsp").forward(request, response);

            } else {
                // Giao dịch thất bại
                // Xử lý tùy ý, ví dụ hiển thị thông báo lỗi cho người dùng
                request.setAttribute("notification", "Invalid Transaction");
                response.sendRedirect(request.getContextPath() + "/transactionFailed");
            }
        }
    }

    private void deposit(User user, long amountLong) {
        TransactionDAO transactionDAO = new TransactionDAO();
        try {
            Transaction trans = transactionDAO.createDepositTrans(user, amountLong);
            TransactionWrapper transactionWrapper = new TransactionWrapper(trans);
            transactionQueue.add(transactionWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}