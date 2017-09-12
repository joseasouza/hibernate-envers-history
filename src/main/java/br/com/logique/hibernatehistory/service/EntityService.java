package br.com.logique.hibernatehistory.service;

import br.com.logique.hibernatehistory.business.AuditManager;
import br.com.logique.hibernatehistory.dto.History;
import br.com.logique.hibernatehistory.dto.Message;
import br.com.logique.hibernatehistory.dto.Register;
import br.com.logique.hibernatehistory.dto.RevertInformation;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author victor.
 */
@Path("/entities")
@Slf4j
public class EntityService {

    private AuditManager manager;

    public EntityService() {
        this.manager = new AuditManager();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntities() {
        log.debug("Fetching all entities");

        return Response.ok(new Gson().toJsonTree(
                manager.getNomesClassesAuditadas()
        ).toString()).build();

//        return Response.ok(new Gson().toJsonTree(Arrays.asList(
//
//                Entity.builder().name("AnaliseAlarmesAnunciadosPorTempo").displayName().build(),
//                Entity.builder().name("Dashboars").displayName().build()
//
//        )).toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registers/{name}")
    public Response getRegisters(@PathParam("name") String name) {
        log.debug("Requested entity: {}", name);

//        return Response.ok(new Gson().toJsonTree(Arrays.asList(
//
//                Register.builder().id(1L)
//                        .description("ANAL_DS_NOME: " + name + " 1, ANAL_DS_DESCRICAO: Descricao")
//                        .build(),
//
//                Register.builder().id(2L)
//                        .description("ANAL_DS_NOME: " + name + " 2, ANAL_DS_DESCRICAO: Descricao 2")
//                        .build()
//
//        )).toString()).build();


        Response response;
        try {
            List<Register> all = manager.todos(name);
            response = Response.ok(new Gson().toJsonTree(all).toString()).build();

        } catch (Exception e) {
            log.error("Failed at getting information in HibernateEnvers: ", e);
            response = Response.serverError().entity(Message.builder().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .message(e.getMessage())
                    .build()).build();
        }
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registers/{name}/{id}")
    public Response getRegisters(@PathParam("name") String name, @PathParam("id") Long id) {
        log.debug("Requested entity: {} for id {}", name, id);

//        return Response.ok(new Gson().toJsonTree(
//
//                Register.builder().id(id)
//                        .description("ANAL_DS_NOME: " + name + " " + id + ", ANAL_DS_DESCRICAO: Descricao")
//                        .object(Foo.builder().id(id).nome(name + " " + id).quantidade(2d).build())
//                        .build()
//
//        ).toString()).build();

        Response response;
        try {

            Register register = manager.findById(name, id);
            response = Response.ok(new Gson().toJsonTree(register).toString()).build();

        } catch (Exception e) {
            log.error("Failed at getting information in HibernateEnvers: ", e);
            response = Response.serverError().entity(Message.builder().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .message(e.getMessage())
                    .build()).build();
        }
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/history/{name}/{id}")
    public Response getHistories(@PathParam("name") String name, @PathParam("id") Long id) {
        log.debug("Fetching histories for entity {} with id {}", name, id);

//        return Response.ok(new Gson().toJsonTree(Arrays.asList(
//
//                History.builder().revision(1L).revisionType(0)
//                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm:ss")))
//                        .description("ANAL_DS_NOME: " + name + " " + id + ", ANAL_DS_DESCRICAO: Descricao")
//                        .object(Foo.builder().id(id).nome(name + " " + id).quantidade(1d).build())
//                        .build(),
//
//                History.builder().revision(2L).revisionType(1)
//                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm:ss")))
//                        .description("ANAL_DS_NOME: " + name + " " + id + ", ANAL_DS_DESCRICAO: Descricao 2")
//                        .object(Foo.builder().id(id).nome(name + " " + id).quantidade(2d).build())
//                        .build(),
//
//                History.builder().revision(3L).revisionType(2)
//                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm:ss")))
//                        .description("ANAL_DS_NOME: " + name + " " + id + ", ANAL_DS_DESCRICAO: Descricao 3")
//                        .object(Foo.builder().id(id).nome(name + " " + id).quantidade(3d).build())
//                        .build()
//
//        )).toString()).build();

        Response response;
        try {

            List<History> obj = manager.listarRevisoesDaEntidade(name, id);
            response = Response.ok(new Gson().toJsonTree(obj).toString()).build();

        } catch (Exception e) {
            log.error("Failed at getting information in HibernateEnvers: ", e);
            response = Response.serverError().entity(Message.builder().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .message(e.getMessage())
                    .build()).build();
        }
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/history/{name}/{id}/{revision}")
    public Response getHistoryAtRev(@PathParam("name") String name,
                                         @PathParam("id") Long id, @PathParam("revision") Long revision) {
        log.debug("Fetching history of entity with id {} at revision {}", name, id, revision);
 /*       return Response.ok(new Gson().toJsonTree(

                History.builder().revision(revision).revisionType(0)
                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY HH:mm:ss")))
                        .description("ANAL_DS_NOME: " + name + " " + id + ", ANAL_DS_DESCRICAO: Descricao")
                        .object(Foo.builder().id(id).nome(name + " " + id).quantidade(1d).build())
                        .build()

        ).toString()).build();*/

        Response response;
        try {

            History history = manager.buscarEntidadePorRevisao(name, id, revision);
            response = Response.ok(new Gson().toJsonTree(history).toString()).build();

        } catch (Exception e) {
            log.error("Failed at getting information in HibernateEnvers: ", e);
            response = Response.serverError().entity(
                    new Gson().toJsonTree(Message.builder().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                            .message(e.getMessage()).build()).toString())
                    .build();
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/revert")
    public Response revertEntityAtRev(RevertInformation revertInformation) {
        log.debug("Reverting entity for: {}", revertInformation);

//        Message message = Message.builder().code(HttpServletResponse.SC_OK)
//                .message(String.format("Entity was succesfully reverted to revision %s!",
//                        revertInformation.getRevision().toString()))
//                .build();
//
//        return Response.ok(new Gson().toJsonTree(message).toString()).build();

        Response response;
        try {

            manager.reverter(revertInformation.getName(), revertInformation.getId(), revertInformation.getRevision());
            response = Response.ok(new Gson().toJsonTree(Message.builder().code(HttpServletResponse.SC_OK)
                    .message(String.format("A entidade foi revertida com sucesso para a revisão %s!", revertInformation.getRevision().toString()))
                    .build()).toString()).build();

        } catch (Exception e) {
            log.error("Failed at getting information in HibernateEnvers: ", e);
            response = Response.serverError().entity(
                    new Gson().toJsonTree(Message.builder().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                            .message(e.getMessage()).build()).toString())
                    .build();
        }
        return response;
    }

}
