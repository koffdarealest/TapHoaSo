package controller;

import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.EmailUtility;

import java.io.IOException;

@WebServlet(urlPatterns = {"/forgot"})
public class forgotController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        userDAO userDAO = new userDAO();
        if(!userDAO.checkExistEmail(email)) {
            req.setAttribute("mess", "Email does not exist! Try again!");
            req.getRequestDispatcher("/view/forgot.jsp").forward(req, resp);
            return;
        }
        EmailUtility emailUtility = new EmailUtility();
        String hostname = "smtp.gmail.com";
        int port = 587; // Use the appropriate port for your SMTP server
        String username = "taphoaso391@gmail.com";
        char[] password = "yygb zruf iamu vmtg".toCharArray();
        String toAddress = email;
        String subject = "Test Email";
        String message = "This is a test email." <a href"localhost:----/reset?key=sadhfjasjdfhjjajshdfhj">;

        try {
            emailUtility.sendEmail(hostname, String.valueOf(port), username, password, toAddress, subject, message);
            resp.sendRedirect("signin");
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
