package br.com.logique.hibernatehistory.service;

import br.com.logique.hibernatehistory.dto.Foo;
import br.com.logique.hibernatehistory.dto.Entity;
import br.com.logique.hibernatehistory.dto.History;
import br.com.logique.hibernatehistory.dto.Register;
import br.com.logique.hibernatehistory.dto.RevertInformation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author victor.
 */
@Path("/entities")
public class EntityService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntities() {

        return Response.ok(new Gson().toJsonTree(Arrays.asList(

                Entity.builder().name("AnaliseAlarmesAnunciadosPorTempo").build(),
                Entity.builder().name("Dashboars").build()

        )).toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registers/{name}")
    public Response getRegisters(@PathParam("name") String name) {
        return Response.ok(new Gson().toJsonTree(Arrays.asList(

                Register.builder().id(1L)
                        .description("ANAL_DS_NOME: " + name + " 1, ANAL_DS_DESCRICAO: Descricao")
                        .build(),

                Register.builder().id(2L)
                        .description("ANAL_DS_NOME: " + name + " 2, ANAL_DS_DESCRICAO: Descricao 2")
                        .build()

        )).toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registers/{name}/{id}")
    public Response getRegisters(@PathParam("name") String name, @PathParam("id") Long id) {
        return Response.ok(new Gson().toJsonTree(

                Register.builder().id(id)
                        .description("ANAL_DS_NOME: " + name + " " + id + ", ANAL_DS_DESCRICAO: Descricao")
                        .object(Foo.builder().id(id).nome(name + " " + id).quantidade(2d).build())
                        .build()

        ).toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/history/{name}/{id}")
    public Response getHistories(@PathParam("name") String name, @PathParam("id") Long id) {
        return Response.ok(new Gson().toJsonTree(Arrays.asList(

                History.builder().revision(1L).revisionType(0)
                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm:ss")))
                        .description("ANAL_DS_NOME: " + name + " " + id + ", ANAL_DS_DESCRICAO: Descricao")
                        .object(Foo.builder().id(id).nome(name + " " + id).quantidade(1d).build())
                        .build(),

                History.builder().revision(2L).revisionType(1)
                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm:ss")))
                        .description("ANAL_DS_NOME: " + name + " " + id + ", ANAL_DS_DESCRICAO: Descricao 2")
                        .object(Foo.builder().id(id).nome(name + " " + id).quantidade(2d).build())
                        .build(),

                History.builder().revision(3L).revisionType(2)
                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm:ss")))
                        .description("ANAL_DS_NOME: " + name + " " + id + ", ANAL_DS_DESCRICAO: Descricao 3")
                        .object(Foo.builder().id(id).nome(name + " " + id).quantidade(3d).build())
                        .build()

        )).toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/history/{name}/{id}/{revision}")
    public Response getHistoryAtRev(@PathParam("name") String name,
                                         @PathParam("id") Long id, @PathParam("revision") Long revision) {
        return Response.ok(new Gson().toJsonTree(

                History.builder().revision(revision).revisionType(0)
                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm:ss")))
                        .description("ANAL_DS_NOME: " + name + " " + id + ", ANAL_DS_DESCRICAO: Descricao")
                        .object(Foo.builder().id(id).nome(name + " " + id).quantidade(1d).build())
                        .build()

        ).toString()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/revert")
    public Response revertEntityAtRev(RevertInformation revertInformation) {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("message", "Entity was succesfully reverted!");
        return Response.ok(
                jsonObj.toString()
        ).build();
    }

}
