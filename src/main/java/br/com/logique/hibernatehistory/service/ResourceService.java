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
import java.io.InputStream;
import java.io.InputStreamReader;
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


            InputStreamReader inputStreamReader = getFileFromResources(filename);
            response = Response.ok(inputStreamReader).build();
        } catch (NotFoundException ex) {
            response = Response.temporaryRedirect(UriBuilder.fromPath(getIndexUrl(request)).build("")).build();
        }
        return response;

    }

    private String getIndexUrl(HttpServletRequest request) {
        return request.getRequestURI().split(request.getServletPath())[0] + request.getServletPath();
    }

    private InputStreamReader getFileFromResources(String filename) {

        InputStream inputStream = getClass().getResourceAsStream("/source/" + filename);

        if (inputStream == null) {
            throw new NotFoundException();
        } else {
            return new InputStreamReader(inputStream);
        }
    }

}
