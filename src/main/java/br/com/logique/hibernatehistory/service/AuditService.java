package br.com.logique.hibernatehistory.service;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author victor.
 */
@Path("/")
public class AuditService {

    @GET
    @Path("/{filename : .*}")
    public InputStream index(@PathParam("filename") String filename) throws FileNotFoundException {

        if (filename == null || filename.isEmpty()) {
            filename = "index.html";
        }

        return new FileInputStream(getFileFromSomewhere(filename));

    }

    private File getFileFromSomewhere(String filename) {

        URL resource = getClass().getClassLoader().getResource("source/" + filename);
        if (resource == null) {
            throw new NotFoundException();
        }
        return new File(resource.getFile());
    }

}
