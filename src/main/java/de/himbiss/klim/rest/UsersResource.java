package de.himbiss.klim.rest;

import de.himbiss.klim.DAO;
import de.himbiss.klim.servlets.beans.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vincent on 19.12.2016.
 */
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    @GET
    public List<User> getUsers() {
        return Arrays.asList(DAO.getInstance().getUserById(1), DAO.getInstance().getUserById(2));
    }

    @GET
    @Path("/{userId}")
    public User getUser(@PathParam("userId") String userId) {
        return DAO.getInstance().getUser(userId);
    }
}
