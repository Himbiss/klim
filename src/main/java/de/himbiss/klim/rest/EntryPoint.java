package de.himbiss.klim.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vincent on 19.12.2016.
 */
@Path("/")
public class EntryPoint {
    private final UsersResource usersResource = new UsersResource();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return "Hallo";
    }

    @Path("users")
    public UsersResource users() {
        return usersResource;
    }
}