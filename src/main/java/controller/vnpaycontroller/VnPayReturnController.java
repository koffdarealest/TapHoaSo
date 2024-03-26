package controller.vnpaycontroller;

import dao.BillDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import dao.VnPayTransactionDAO;
import model.Bill;
import model.VnPayTransaction;
import util.Config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import util.VnPayConstant;
import java.text.SimpleDateFormat;

import static java.lang.System.out;


@WebServlet(urlPatterns = {"/processVnpayReturn"})
public class VnPayReturnController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = req.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = URLEncoder.encode(params.nextElement(), StandardCharsets.US_ASCII);
            String fieldValue = URLEncoder.encode(req.getParameter(fieldName), StandardCharsets.US_ASCII);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                fields.put(fieldName, fieldValue);
            }

            String vnp_SecureHash = req.getParameter(VnPayConstant.vnp_SecureHash);
            fields.remove(VnPayConstant.vnp_SecureHashType);
            fields.remove(VnPayConstant.vnp_SecureHash);
            String signValue = Config.hashAllFields(fields);

            String vnpTxnRef = req.getParameter(VnPayConstant.vnp_TxnRef);
            String vnpAmount = req.getParameter(VnPayConstant.vnp_Amount);
            String vnpOrderInfo = req.getParameter(VnPayConstant.vnp_OrderInfo);
            String vnpResponseCode = req.getParameter(VnPayConstant.vnp_ResponseCode);
            String vnpTransactionNo = req.getParameter(VnPayConstant.vnp_TransactionNo);
            String vnpBankCode = req.getParameter(VnPayConstant.vnp_BankCode);
            String vnpPayDate = req.getParameter(VnPayConstant.vnp_PayDate);
            String vnpTransactionStatus = req.getParameter(VnPayConstant.vnp_TransactionStatus);

            req.setAttribute("VAR_TxnRef", vnpTxnRef);
            long amount = Long.parseLong(vnpAmount) / 100L;
            req.setAttribute("VAR_Amount", amount);
            req.setAttribute("VAR_OrderInfo", vnpOrderInfo);
            req.setAttribute("VAR_RespCode", vnpResponseCode);
            req.setAttribute("VAR_TransNo", vnpTransactionNo);
            req.setAttribute("VAR_BankCode", vnpBankCode);
            req.setAttribute("VAR_PayDate", convertTimestamp(vnpPayDate));
            req.setAttribute("IS_SUCCESS", false);
            req.setAttribute("IS_SUCCESS", vnpTransactionStatus);


            String username = (String) req.getSession().getAttribute("username");
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsername(username);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/view/viewVnPayResult.jsp").forward(req, resp);
        }

    }

    public static String convertTimestamp(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = inputFormat.parse(timestamp);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
