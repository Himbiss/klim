package de.himbiss.klim.servlets;

import org.eclipse.jetty.security.authentication.SessionAuthentication;
import org.eclipse.jetty.server.UserIdentity;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

/**
 * Created by Vincent on 17.12.2016.
 */
@MultipartConfig
@WebServlet(urlPatterns = { "uploadservlet" })
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            SessionAuthentication authentication = ((SessionAuthentication) session.getAttribute(SessionAuthentication.__J_AUTHENTICATED));
            if (authentication != null) {
                UserIdentity userIdentity = authentication.getUserIdentity();
                post(userIdentity, request, response);
            }

        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    protected void post(UserIdentity user, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = user.getUserPrincipal().getName();

        Part file = request.getPart("file");
        String filename = getFilename(file);
        InputStream filecontent = file.getInputStream();
        // ... Do your file saving job here.

        response.getWriter().write("File " + filename + " successfully uploaded (" + name + ")");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] split = req.getServletPath().split("\\/");
        resp.setContentType("image/jpeg");
        for (String s : split) {
            try (BufferedInputStream is = new BufferedInputStream(new FileInputStream("D:\\Bilder\\blub.jpg"))) {
                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = is.read(buffer)) != -1) {
                    resp.getOutputStream().write(buffer, 0, read);
                }
            }
        }
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

}
