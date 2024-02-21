    package controller;

    import DAO.userDAO;
    import com.google.gson.internal.bind.util.ISO8601Utils;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import model.User;

    import java.io.IOException;
    import java.util.Date;
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
            List<User> list = userDAO.getAllUser();
            System.out.println("manageUsers");

            String[] selectedUser = req.getParameterValues("selectedUser");

            String action = req.getParameter("action");
            System.out.println(action);
            if (action != null && selectedUser != null) {
                if (action.equals("delete")) {
                    System.out.println("OpendeleteUser");
                    updateDeleteUser(list, selectedUser, userDAO);
                } else if (action.equals("open")) {
                    System.out.println("OpenUser");
                    updateOpenUser(list, selectedUser, userDAO);
                }
            }

            // Redirect to userManage page
            resp.sendRedirect(req.getContextPath() + "/userManage");
        }


        private void updateOpenUser(List<User> list, String[] selectedUser, userDAO userDAO) {
            if (selectedUser != null) {
                for (User user : list) {
                    for (String id : selectedUser) {
                        if (user.getUserID().equals(Long.parseLong(id))) {
                            System.out.println("deleteUser" + user.getUserID());
                            user.setDelete(false);
                            user.setDeletedAt(new Date());
                            user.setUpdatedAt(new Date());
                            userDAO.updateUser(user);
                        }
                    }
                }
            } else {
                System.out.println("selectedUser is null");
            }
        }

        private void updateDeleteUser(List<User> list, String[] selectedUser, userDAO userDAO) {
            if (selectedUser != null) {
                for (User user : list) {
                    for (String id : selectedUser) {
                        if (user.getUserID().equals(Long.parseLong(id))) {
                            System.out.println("deleteUser" + user.getUserID());
                            user.setDelete(true);
                            user.setDeletedAt(new Date());
                            user.setUpdatedAt(new Date());
                            userDAO.updateUser(user);
                        }
                    }
                }
            } else {
                System.out.println("selectedUser is null");
            }
        }


    }
