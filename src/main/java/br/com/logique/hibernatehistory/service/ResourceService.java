package br.com.logique.hibernatehistory.service;

import br.com.logique.hibernatehistory.model.NotIntercepted;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * @author victor.
 */
@NotIntercepted
@Path("/")
public class ResourceService {

    @GET
    @Path("/{filename : .*}")
    public Response index(@PathParam("filename") String filename, @Context HttpServletRequest request) throws FileNotFoundException {

        if (filename == null || filename.isEmpty()) {
            filename = "index.html";
        }

        Response response;
        try {

            File fileRequested = getFileFromResources(filename);

            if (!fileRequested.isFile()) {
                throw new NotFoundException();
            }

            response = Response.ok(fileRequested).build();
        } catch (NotFoundException ex) {
            response = Response.temporaryRedirect(UriBuilder.fromPath(getIndexUrl(request)).build("")).build();
        }
        return response;

    }

    private String getIndexUrl(HttpServletRequest request) {
        return request.getRequestURI().split(request.getServletPath())[0] + request.getServletPath();
    }

    private File getFileFromResources(String filename) {

        URL resource = getClass().getClassLoader().getResource("source/" + filename);
        if (resource == null) {
            throw new NotFoundException();
        }
        return new File(resource.getFile());
    }

}
