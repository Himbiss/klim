package de.himbiss.klim.rest;

import de.himbiss.klim.DAO;
import de.himbiss.klim.FollowRequest;
import de.himbiss.klim.servlets.beans.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vincent on 19.12.2016.
 */
@Produces(MediaType.APPLICATION_JSON)
public class FollowersResource {

    @GET
    @Path("/{userId}")
    public List<User> getFollowers(@PathParam("userId") int userId) {
        return DAO.getInstance().getAllFollowers(userId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response follow(FollowRequest followRequest) {
        System.err.println(followRequest.getUserId() + " : " + followRequest.getFollowerId());
        if (DAO.getInstance().followUser(followRequest.getUserId(), followRequest.getFollowerId())) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{userId}/{followerId}")
    public Response unfollow(@PathParam("userId") int userId, @PathParam("followerId") int followerId) {
        if (DAO.getInstance().unfollowUser(userId, followerId)) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{userId}/{followerId}")
    public Response hasFollower(@PathParam("userId") int userId, @PathParam("followerId") int followerId) {
        User follower = DAO.getInstance().getUser(followerId);
        if (DAO.getInstance().getAllFollowers(userId).contains(follower)) {
            return Response.ok().build();
        }
        else {
            return Response.status(404).build();
        }
    }
}
