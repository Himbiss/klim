package de.himbiss.klim.servlets;

import de.himbiss.klim.DAO;
import de.himbiss.klim.servlets.beans.User;
import org.eclipse.jetty.security.authentication.SessionAuthentication;
import org.eclipse.jetty.server.UserIdentity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Vincent on 17.12.2016.
 */
public abstract class SecuredServlet extends HttpServlet {

    @Override
    final protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        authenticateAndExecute(request, response, this::post);
    }

    protected void post(User user, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    final protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        authenticateAndExecute(request, response, this::get);
    }

    protected void get(User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    private void authenticateAndExecute(HttpServletRequest request, HttpServletResponse response, SecureCall call) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            SessionAuthentication authentication = ((SessionAuthentication) session.getAttribute(SessionAuthentication.__J_AUTHENTICATED));
            if (authentication != null) {
                User user = DAO.getInstance().getUser(authentication.getUserIdentity().getUserPrincipal().getName());
                if (user != null) {
                    call.invoke(user, request, response);
                }
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @FunctionalInterface
    private interface SecureCall {
        void invoke(User user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    }
}
