package de.himbiss.klim.rest;

import de.himbiss.klim.fileservice.FileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by vincent on 29.12.16.
 */
public class DownloadResource {
    @GET
    @Path("/img/{id}")
    public Response downloadPdfFile(@PathParam("id") int id)
    {
        StreamingOutput fileStream = output -> {
            try
            {
                byte[] data = FileService.getInstance().getFile(id);
                output.write(data);
                output.flush();
            }
            catch (Exception e)
            {
                throw new WebApplicationException("File Not Found !!");
            }
        };
        return Response.ok(fileStream, "image/png").build();
    }
}
