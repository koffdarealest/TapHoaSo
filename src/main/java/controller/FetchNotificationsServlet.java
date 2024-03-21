package controller;

import com.google.gson.Gson;
import dao.NoticeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/fetchNotifications")
public class FetchNotificationsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NoticeDAO noticeDAO = new NoticeDAO();

        // Retrieve new notifications from the database
        List<String> newNotifications = noticeDAO.getNewNotifications();

        // Convert new notifications to JSON
        String jsonResponse = new Gson().toJson(newNotifications);

        // Set response content type
        response.setContentType("application/json");

        // Write JSON response to the client
        response.getWriter().write(jsonResponse);
    }
}
