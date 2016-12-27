package de.himbiss.klim.servlets;

import de.himbiss.klim.DAO;
import de.himbiss.klim.servlets.beans.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vincent on 17.12.2016.
 */
@WebServlet(urlPatterns = { "user/*" })
public class UserServlet extends SecuredServlet {
    @Override
    protected void get(User me, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] split = pathInfo != null ? pathInfo.split("\\/") : new String[0];

        if (split.length == 2) {
            User profileUser = DAO.getInstance().getUser(split[1]);
            if (profileUser != null) {
                request.getRequestDispatcher("/html/user.html?profile_id=" + profileUser.getId() + "&user_id=" + me.getId()).include(request, response);
                return;
            }
        }
        response.sendRedirect("/user/" + me.getUserName());
    }
}