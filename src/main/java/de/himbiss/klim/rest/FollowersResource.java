package de.himbiss.klim.rest;

import de.himbiss.klim.DAO;
import de.himbiss.klim.servlets.beans.User;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Vincent on 19.12.2016.
 */
@Produces(MediaType.APPLICATION_JSON)
public class FollowersResource {

    @GET
    public List<User> getFollowers(@PathParam("userId") int userId) {
        return DAO.getInstance().getAllFollowers(userId);
    }
}
