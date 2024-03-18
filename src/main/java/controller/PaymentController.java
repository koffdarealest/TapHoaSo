package controller;

import dao.VnPayTransactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
public class PaymentController  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng yêu cầu GET sang phương thức doPost
        doPost(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //format datetime for vnpay in vietnam
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        String bank_code = request.getParameter("bankCode");
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_OrderInfo = (request.getParameter("info") != null) ? request.getParameter("info") : "Add Balance";
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;

        long amount = Long.parseLong(request.getParameter("amount")) * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", (bank_code != null && !bank_code.isEmpty()) ? bank_code : "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        String locate = request.getParameter("language");
        vnp_Params.put("vnp_Locale", (locate != null && !locate.isEmpty()) ? locate : "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
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
            if (fieldValue != null && fieldValue.length() > 0) {
                //Build hash data
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        VnPayTransaction transaction = new VnPayTransaction();
        String transactionNumber = generateRandomString();
        String transactionUUID = generateRandomString();
// Đặt giá trị cho trường transactionId trong đối tượng VnPayTransaction
        transaction.setTransactionNo(transactionNumber);
        transaction.setTransactionId(transactionUUID);
        transaction.setVersion(vnp_Params.get("vnp_Version"));
        transaction.setCommand(vnp_Params.get("vnp_Command"));
//        transaction.setTmnCode(vnp_Params.get("vnp_TmnCode"));
        transaction.setAmount(new BigInteger(vnp_Params.get("vnp_Amount")));
        transaction.setCurrentCode(vnp_Params.get("vnp_CurrCode"));
        transaction.setBankCode(vnp_Params.get("vnp_BankCode"));
//        transaction.setTxnRef(vnp_Params.get("vnp_TxnRef"));
        transaction.setOrderInfo(vnp_Params.get("vnp_OrderInfo"));
        transaction.setOrderType(vnp_Params.get("vnp_OrderType"));
        transaction.setLocale(vnp_Params.get("vnp_Locale"));
//        transaction.setReturnUrl(vnp_Params.get("vnp_ReturnUrl"));
        transaction.setIpAddr(vnp_Params.get("vnp_IpAddr"));
        transaction.setCreateDate(vnp_Params.get("vnp_CreateDate"));
        transaction.setExpireDate(vnp_Params.get("vnp_ExpireDate"));
        transaction.setType("a");
        // Đặt các giá trị khác cho đối tượng transaction tương ứng với thông tin thu thập được
// Tính toán secureHash dựa trên các thông tin trong vnp_Params
        String secureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        transaction.setSecureHash(secureHash);

// Lưu đối tượng VnPayTransaction vào cơ sở dữ liệu
        try {
            VnPayTransactionDAO transactionDAO = new VnPayTransactionDAO();
            transactionDAO.save(transaction);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý nếu có lỗi khi lưu vào cơ sở dữ liệu
        }
        // Chuyển hướng đến trang thanh toán
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