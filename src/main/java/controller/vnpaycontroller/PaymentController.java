package controller.vnpaycontroller;

import com.google.gson.JsonObject;
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

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng yêu cầu GET sang phương thức doPost
        doPost(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //format datetime for vnpay in vietnam
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("VST"));
        String bank_code = request.getParameter("bankCode");
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_OrderInfo;
        if (request.getParameter("info") != null) {
            vnp_OrderInfo = request.getParameter("info");
        } else {
            vnp_OrderInfo = "Add Balance";
        }
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;

        long amount = Long.parseLong(request.getParameter("amount")) * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        System.out.println(bank_code);
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        } else {
            vnp_Params.put("vnp_BankCode", "NCB");
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(new Date());
        currentTime.add(Calendar.HOUR, 7);
        String vnp_CreateDate = formatter.format(currentTime.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//        cld.add(Calendar.MINUTE, 15);
        currentTime.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(currentTime.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);

        HttpSession session = request.getSession();
        // Lấy giá trị của thuộc tính "username" từ session
        String username = (String) session.getAttribute("username");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);

        Bill bill = new Bill();
        bill.setId(vnp_TxnRef);
        bill.setAmount(amount);
        bill.setType("a");
        bill.setCommand(Short.parseShort("10"));
        bill.setPaymentStatus("processing");
        bill.setUserId(user.getUserID());
        BillDAO billDAO = new BillDAO();
        billDAO.save(bill);

        VnPayTransaction transaction = new VnPayTransaction();
        transaction.setId(vnp_TxnRef);
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