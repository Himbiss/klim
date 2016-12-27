package de.himbiss.klim;

import de.himbiss.klim.servlets.beans.User;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * Created by Vincent on 17.12.2016.
 */
public class JSPUtil {

    public static void createChip(String contextPath, JspWriter out, int userId) throws IOException {
        User user = DAO.getInstance().getUserById(userId);
        out.write("<div class=\"chip\">");
        out.write("<a href=\"" + contextPath + "/user/" + user.getUserName() + "\"><img src=\"" + user.getAvatarImg() + "\" width=\"96\" height=\"96\"></a>");
        out.write("<p class=\"chip_name\">" + user.getUserName() + "</p>");
        //out.write("<i class=\"fa fa-comments\" aria-hidden=\"true\"></i>\n");
        out.write("</div>");
    }

    public static void createChip(String contextPath, JspWriter out, User user) throws IOException {
        out.write("<div class=\"chip\">");
        out.write("<a href=\"" + contextPath + "/user/" + user.getUserName() + "\"><img src=\"" + user.getAvatarImg() + "\" width=\"96\" height=\"96\"></a>");
        out.write("<p class=\"chip_name\">" + user.getUserName() + "</p>");
        //out.write("<i class=\"fa fa-comments\" aria-hidden=\"true\"></i>\n");
        out.write("</div>");
    }
}
