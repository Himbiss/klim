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
                request.setAttribute("me", me);
                request.setAttribute("user", profileUser);
                request.setAttribute("posts", DAO.getInstance().getAllPostsToUser(profileUser.getId()));
                request.setAttribute("friends", DAO.getInstance().getAllFollowers(profileUser.getId()));
                request.setAttribute("my_friends", DAO.getInstance().getAllFollowers(me.getId()));
                request.getRequestDispatcher("/jsp/user.jsp").include(request, response);
                return;
            }
        }
        response.sendRedirect("/user/" + me.getUserName());
    }

    @Override
    protected void post(User user, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        int profileId = Integer.parseInt(request.getParameter("profileId"));
        int userId = user.getId();

        switch (action) {
            case "submit_post":
                String content = request.getParameter("content");
                if (DAO.getInstance().createPosting(userId, profileId, content) != -1) {
                    response.sendRedirect("/user/" + DAO.getInstance().getUser(profileId).getUserName());
                }
                else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                break;
            case "follow":
                if (DAO.getInstance().followUser(userId, profileId)) {
                    response.sendRedirect("/user/" + DAO.getInstance().getUser(profileId).getUserName());
                }
                else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                break;
            case "unfollow":
                if (DAO.getInstance().unfollowUser(userId, profileId)) {
                    response.sendRedirect("/user/" + DAO.getInstance().getUser(profileId).getUserName());
                }
                else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
