package de.himbiss.klim.rest;

import de.himbiss.klim.DAO;
import de.himbiss.klim.servlets.beans.Post;
import de.himbiss.klim.servlets.beans.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Vincent on 19.12.2016.
 */
@Produces(MediaType.APPLICATION_JSON)
public class PostsResource {
    private final CommentsResource commentsResource = new CommentsResource();

    @GET
    public List<Post> getPosts(@PathParam("userId") int userId) {
        return DAO.getInstance().getAllPostsToUser(userId);
    }

    @GET
    @Path("/{postId}")
    public Post getUser(@PathParam("postId") int postId) {
        return DAO.getInstance().getPost(postId);
    }

    @POST
    @Path("/")
    public Response makePost(@FormParam("userId") int userId, @FormParam("profileId") int profileId, @FormParam("content") String content) {
        int postingId = DAO.getInstance().createPosting(userId, profileId, content);
        if (postingId != -1) {
            return Response.ok(DAO.getInstance().getPost(postingId)).build();
        }
        else {
            return Response.serverError().build();
        }
    }
    @Path("/{postId}/comments")
    public CommentsResource comments() {
        return commentsResource;
    }
}
