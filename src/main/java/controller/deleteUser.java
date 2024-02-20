package controller;

import DAO.userDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/deleteUser"})
public class deleteUser  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userDAO userDAO = new userDAO();

        String[] selectedUser = req.getParameterValues("selectedUser");
        List<String> usersID = new ArrayList<>();

        usersID.addAll(Arrays.asList(selectedUser));

        for(String id : usersID){
            userDAO.deleteUser(id);
        }

        resp.sendRedirect(req.getContextPath() + "/userManage");
    }
}
