package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.userDAO;

import java.io.IOException;

import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/signOut"})
public class signoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userDAO userDAO = new userDAO();
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object onlineUserID = session.getAttribute("online");
            if (onlineUserID != null) {
                userDAO.updateUserOnlineStatus((Long) onlineUserID, false);
                session.invalidate();
            }

//            response.sendRedirect("/signin");
        }
        response.sendRedirect(request.getContextPath() + "/signin");
    }
}
