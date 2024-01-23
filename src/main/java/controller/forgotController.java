package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Utility.*;

import java.io.IOException;

@WebServlet(urlPatterns = {"/forgot"})
public class forgotController extends HttpServlet {
    public forgotController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("view/forgot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        EmailUtility emailUtility = new EmailUtility();
        String hostname = "smtp.gmail.com";
        int port = 587; // Use the appropriate port for your SMTP server
        String userName = "Sonhxhe172036@fpt.edu.vn";
        char[] password = "tjbg bmfk brch edxl".toCharArray();
        String toAddress = email;
        String subject = "Test Email";
        String message = "This is a test email.";

        try {
            emailUtility.sendEmail(hostname, String.valueOf(port), userName, password, toAddress, subject, message);
            resp.sendRedirect("signin");
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
