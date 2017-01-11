package de.himbiss.klim.rest;

import de.himbiss.klim.DAO;
import de.himbiss.klim.PostRequest;
import de.himbiss.klim.servlets.beans.Photo;
import de.himbiss.klim.servlets.beans.Post;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by vincent on 29.12.16.
 */
@Produces(MediaType.APPLICATION_JSON)
public class PhotoResource {
    private final CommentsResource commentsResource = new CommentsResource();

    @GET
    public List<Photo> getPhotos(@QueryParam("userId") String userId, @QueryParam("postId") int postId) {
        if (userId != null && ! userId.isEmpty()) {
            return DAO.getInstance().getAllPhotosOfUser(DAO.getInstance().getUser(userId).getId());
        }
        else {
            return DAO.getInstance().getAllPhotosOfPost(postId);
        }
    }

    @GET
    @Path("/{photoId}")
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
