package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.HashSet;
import java.util.Set;

@WebListener
public class SessionListener implements HttpSessionListener, ServletContextListener {
    private static Set<HttpSession> activeSessions = new HashSet<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("activeSessions", activeSessions);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        sce.getServletContext().removeAttribute("activeSessions");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        activeSessions.add(se.getSession());
        se.getSession().setMaxInactiveInterval(60 * 10);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        activeSessions.remove(se.getSession());
    }

    public static Set<HttpSession> getActiveSessions() {
        return activeSessions;
    }

    public static int getActiveSessionCount() {
        return activeSessions.size();
    }
}
