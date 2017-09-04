package br.com.logique.hibernatehistory.service;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author victor.
 */
//@Path("/resources")
public class ResourceService {


//    @GET
//    @Path("/{filename : .*}")
//    public File getFile(@PathParam("filename") String filename) {
//
//
//
//        if (filename == null || filename.isEmpty()) {
//            filename = "index.html";
//        }
//
//
//        return getFileFromSomewhere(filename);
//
////        StreamingOutput fileStream =  new StreamingOutput()
////        {
////            @Override
////            public void write(java.io.OutputStream output) throws IOException, WebApplicationException
////            {
////                try
////                {
////                    java.nio.file.Path path = Paths.get(f);
////                    byte[] data = Files.readAllBytes(path);
////                    output.write(data);
////                    output.flush();
////                }
////                catch (Exception e)
////                {
////                    throw new WebApplicationException("File Not Found !!");
////                }
////            }
////        };
////        return Response
////                .ok(fileStream, MediaType.WILDCARD)
////                .build();
//    }
//
//    private File getFileFromSomewhere(String filename) {
//
//        URL resource = getClass().getClassLoader().getResource("source/" + filename);
//        if (resource == null) {
//            throw new NotFoundException();
//        }
//        return new File(resource.getFile());
//    }

}
