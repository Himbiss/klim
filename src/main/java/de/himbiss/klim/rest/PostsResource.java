package de.himbiss.klim.rest;

import de.himbiss.klim.DAO;
import de.himbiss.klim.PostRequest;
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
    public List<Post> getPosts(@QueryParam("userId") String userId) {
        return DAO.getInstance().getAllPostsToUser(DAO.getInstance().getUser(userId).getId());
    }

    @GET
    @Path("/{postId}")
    public Post getPost(@PathParam("postId") int postId) {
        return DAO.getInstance().getPost(postId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makePost(PostRequest postRequest) {
        int postingId = DAO.getInstance().createPosting(postRequest.getUserId(), postRequest.getProfileId(), postRequest.getContent());
        if (postingId != -1) {
            return Response.ok(DAO.getInstance().getPost(postingId)).build();
        }
        else {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{postId}")
    public Response deletePost(@PathParam("postId") int postId) {
        if (DAO.getInstance().deletePosting(postId)) {
            return Response.ok().build();
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
