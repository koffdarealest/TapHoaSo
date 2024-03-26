package controller.vnpaycontroller;

import dao.BillDAO;
import dao.UserDAO;
import dao.VnPayTransactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bill;
import model.User;
import model.VnPayTransaction;
import util.Config;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.eclipse.tags.shaded.org.apache.xalan.xsltc.compiler.Constants.CHARACTERS;

@WebServlet(urlPatterns = {"/createpayment"})
public class CreatePaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng yêu cầu GET sang phương thức doPost
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";
            long amount = Long.parseLong(request.getParameter("amount")) * 100L;
            String bankCode = request.getParameter("bankCode");

            String transactionId = generateRandomString();
            String vnp_TxnRef = transactionId.toString().replace("-", "");
            String vnp_OrderInfo = (request.getParameter("info") != null) ? request.getParameter("info") : "Add Balance";
            String vnp_IpAddr = Config.getIpAddress(request);
            String vnp_TmnCode = Config.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", Config.vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_BankCode", (bankCode != null && !bankCode.isEmpty()) ? bankCode : "NCB");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", (request.getParameter("info") != null) ? URLEncoder.encode(request.getParameter("info"), StandardCharsets.US_ASCII.toString()) : "Add Balance");
            vnp_Params.put("vnp_OrderType", orderType);
            String locate = request.getParameter("language");
            vnp_Params.put("vnp_Locale", (locate != null && !locate.isEmpty()) ? locate : "vn");
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
            vnp_Params.put("vnp_IpAddr", Config.getIpAddress(request));
            cld.add(Calendar.HOUR, 7);
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);


            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            for (String fieldName : fieldNames) {
                String fieldValue = vnp_Params.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    hashData.append(fieldName).append('=').append(fieldValue);
                    if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                        hashData.append('&');
                    }
                }
            }

// Mã hóa chuỗi hashData
            String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());

// Gán giá trị đã mã hóa vào tham số vnp_SecureHash
            String queryUrl = query.toString() + "&vnp_SecureHash=" + URLEncoder.encode(vnp_SecureHash, StandardCharsets.US_ASCII.toString());
            String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;


//            String queryUrl = query.toString();
//            String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
//            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//            String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

            HttpSession session = request.getSession();
            // Lấy giá trị của thuộc tính "username" từ session
            String username = (String) session.getAttribute("username");
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsername(username);

            Bill bill = new Bill();
            bill.setId(transactionId);
            bill.setAmount(amount);
            bill.setType("a");
            bill.setCommand(Short.parseShort("10"));
//            bill.setCreateDate(new Date());
//            bill.setExpireDate(vnp_ExpireDate);
            bill.setPaymentStatus(request.getParameter("processing"));
            bill.setUserId(user.getUserID());
            BillDAO billDAO = new BillDAO();
            billDAO.save(bill);

            String transactionNo = generateRandomString();
            VnPayTransaction transaction = new VnPayTransaction();
            transaction.setId(transactionNo);
            transaction.setType("a");
            transaction.setVersion(vnp_Params.get("vnp_Version"));
            transaction.setCommand(vnp_Params.get("vnp_Command"));
            transaction.setAmount(new BigInteger(vnp_Params.get("vnp_Amount")));
            transaction.setCurrentCode(vnp_Params.get("vnp_CurrCode"));
            transaction.setBankCode(vnp_Params.get("vnp_BankCode"));
            transaction.setLocale(vnp_Params.get("locale"));
            transaction.setIpAddr(vnp_Params.get("vnp_IpAddr"));
            transaction.setOrderInfo(vnp_Params.get("vnp_OrderInfo"));
            transaction.setCreateDate(vnp_Params.get("vnp_CreateDate"));
            transaction.setExpireDate(vnp_Params.get("vnp_ExpireDate"));
            transaction.setSecureHash(vnp_Params.get("vnp_SecureHash"));
            VnPayTransactionDAO transactionDAO = new VnPayTransactionDAO();
            transactionDAO.save(transaction);

            response.sendRedirect(paymentUrl);
        } catch (Exception e) {
            request.setAttribute("ERROR_MESSAGE", "Tạo giao dịch không thành công!");
        }
    }

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder("6");
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static int generateSixDigitNumber() {
        // Khởi tạo một đối tượng Random
        Random random = new Random();
        // Sinh ra một số ngẫu nhiên từ 100000 đến 999999 (bao gồm cả hai giá trị này)
        int randomNumber = random.nextInt(900000) + 100000;

        return randomNumber;
    }
}
