package de.himbiss.klim.rest;

import de.himbiss.klim.DAO;
import de.himbiss.klim.LoginRequest;
import de.himbiss.klim.LoginResponse;
import de.himbiss.klim.servlets.beans.User;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Vincent on 19.12.2016.
 */
@Provider
@Path("/")
public class EntryPoint {
    private final UsersResource usersResource = new UsersResource();
    private final FollowersResource followersResource = new FollowersResource();
    private final PostsResource postsResource = new PostsResource();
    private final PhotoResource photoResource = new PhotoResource();
    private final DownloadResource downloadResource = new DownloadResource();

    @Path("users")
    public UsersResource users() {
        return usersResource;
    }

    @Path("posts")
    public PostsResource posts() {
        return postsResource;
    }

    @Path("followers")
    public FollowersResource followers() {
        return followersResource;
    }

    @Path("photos")
    public PhotoResource photos() {
        return photoResource;
    }

    @Path("download")
    public DownloadResource download() {
        return downloadResource;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("login")
    public Response login(LoginRequest loginRequest) {
        System.err.println(loginRequest.getUsername() + ":" + loginRequest.getPassword());
        User user = DAO.getInstance().getUser(loginRequest.getUsername());
        if (user != null) {
            String hash = user.getPasswordHash().substring(user.getPasswordHash().indexOf(":") + 1);
                String hashCheck = hashPassword(loginRequest.getPassword());

                System.err.println("HASH: " + hash + " : " + hashCheck);
                if (! hash.equals(hashCheck)) {
                    throw new NotAuthorizedException("Wrong Credentials!");
                }
            UUID token = SessionManager.getInstance().createSession(user);
            return Response.ok(new LoginResponse(token.toString(), user)).build();
        }
        return Response.status(404).build();
    }

    private String hashPassword(String pwd) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(pwd.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}