package br.com.logique.hibernatehistory.service;

import br.com.logique.hibernatehistory.dto.Message;
import br.com.logique.hibernatehistory.dto.User;
import br.com.logique.hibernatehistory.model.NotIntercepted;
import br.com.logique.hibernatehistory.model.Session;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * @author victor.
 */
@Path("/access")
@NotIntercepted
public class AccessService {

    @Context
    private Configuration configuration;

    @Context
    private HttpServletRequest httpRequest;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(User user) {

        String settingsUser = Optional.ofNullable((String) configuration.getProperty("hibernate.envers.history.username"))
                .orElse("admin");
        String settingsPassword = Optional.ofNullable((String) configuration.getProperty("hibernate.envers.history.password"))
                .orElse("admin");

        Response response;
        if (passwordMatches(user, settingsUser, settingsPassword)) {
            doLogin();

            Message message = Message.builder().code(HttpServletResponse.SC_OK)
                    .code(HttpServletResponse.SC_OK)
                    .message("Sucesso!")
                    .build();
            response = Response.ok(new Gson().toJsonTree(message).toString()).build();

        } else {
            Message message = Message.builder().code(HttpServletResponse.SC_UNAUTHORIZED)
                    .message("Usuário ou senha incorretos")
                    .build();
            response = Response.status(HttpServletResponse.SC_UNAUTHORIZED).entity(new Gson().toJsonTree(message).toString()).build();
        }

        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/logout")
    public Response logout() {
        doLogout();
        Message message = Message.builder().code(HttpServletResponse.SC_OK)
                .message("Sucesso!")
                .build();

        return Response.ok(new Gson().toJsonTree(message).toString()).build();
    }

    private void doLogin() {
        Session session = new Session();
        session.setLogged(true);
        HttpSession httpSession = httpRequest.getSession(true);
        httpSession.setAttribute(Session.HIBERNATE_ENVERS_HISTORY_SESSION, session);
    }

    private boolean passwordMatches(User user, String settingsUser, String settingsPassword) {
        return user != null && settingsUser.toLowerCase().equals(user.getUsername()) && settingsPassword.equals(user.getPassword());
    }

    private void doLogout() {
        HttpSession httpSession = httpRequest.getSession(true);
        httpSession.setAttribute(Session.HIBERNATE_ENVERS_HISTORY_SESSION, new Session());
    }

}
