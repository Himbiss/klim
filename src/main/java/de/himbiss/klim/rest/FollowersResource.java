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
    public List<User> getFollowers(@PathParam("userId") String userId) {
        return DAO.getInstance().getAllFollowers(DAO.getInstance().getUser(userId).getId());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response follow(FollowRequest followRequest) {
        System.err.println(followRequest.getUserId() + " : " + followRequest.getFollowerId());
        if (DAO.getInstance().followUser(DAO.getInstance().getUser(followRequest.getUserId()).getId(),
                                         DAO.getInstance().getUser(followRequest.getFollowerId()).getId())) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{userId}/{followerId}")
    public Response unfollow(@PathParam("userId") String userId, @PathParam("followerId") String followerId) {
        if (DAO.getInstance().unfollowUser(DAO.getInstance().getUser(userId).getId(), DAO.getInstance().getUser(followerId).getId())) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{userId}/{followerId}")
    public Response hasFollower(@PathParam("userId") String userId, @PathParam("followerId") String followerId) {
        User follower = DAO.getInstance().getUser(followerId);
        if (DAO.getInstance().getAllFollowers(DAO.getInstance().getUser(userId).getId()).contains(follower)) {
            return Response.ok().build();
        }
        else {
            return Response.status(404).build();
        }
    }
}
