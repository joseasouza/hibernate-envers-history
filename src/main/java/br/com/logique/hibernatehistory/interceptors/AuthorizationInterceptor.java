package br.com.logique.hibernatehistory.interceptors;

import br.com.logique.hibernatehistory.model.NotIntercepted;
import br.com.logique.hibernatehistory.model.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static br.com.logique.hibernatehistory.model.Session.HIBERNATE_ENVERS_HISTORY_SESSION;

/**
 * @author victor.
 */
@Provider
public class AuthorizationInterceptor implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        boolean mustIntercepts = Arrays.stream(resourceInfo.getResourceClass().getAnnotations()).anyMatch(a ->
                a.annotationType().equals(NotIntercepted.class));

        if (!mustIntercepts) {

            HttpSession httpSession = request.getSession(true);
            Session session = (Session)
                    Optional.ofNullable(httpSession.getAttribute(HIBERNATE_ENVERS_HISTORY_SESSION)).orElse(new Session());

            if (!session.isLogged()) {
                throw new NotAuthorizedException("User not logged in");
            }
        }
    }
}
