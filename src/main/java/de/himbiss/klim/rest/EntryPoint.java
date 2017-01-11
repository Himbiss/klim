package de.himbiss.klim.rest;

import de.himbiss.klim.DAO;
import de.himbiss.klim.LoginRequest;
import de.himbiss.klim.LoginResponse;
import de.himbiss.klim.servlets.beans.User;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Vincent on 19.12.2016.
 */
@Path("/")
public class EntryPoint {
    private final UsersResource usersResource = new UsersResource();
    private final FollowersResource followersResource = new FollowersResource();
    private final PostsResource postsResource = new PostsResource();
    private final PhotoResource photoResource = new PhotoResource();
    private final DownloadResource downloadResource = new DownloadResource();

    private final Map<User, String> sessionMap = new HashMap<>();

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
            String sessionId = sessionMap.get(user);
            if (sessionId != null) {
                return Response.ok(new LoginResponse(sessionId, user)).build();
            }
            else {
                return Response.ok(new LoginResponse(UUID.randomUUID().toString(), user)).build();
            }
        }
        return Response.status(404).build();
    }
}