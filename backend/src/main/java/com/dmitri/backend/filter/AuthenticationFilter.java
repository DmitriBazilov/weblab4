package com.dmitri.backend.filter;

import com.dmitri.backend.annotation.Secured;
import com.dmitri.backend.model.UserPrincipal;
import com.dmitri.backend.util.JwtTokenUtil;
import com.dmitri.backend.util.UserSecurityContext;
import io.jsonwebtoken.ExpiredJwtException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

@Secured
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @EJB
    JwtTokenUtil jwtTokenUtil;

    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String requestTokenHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);


        String username = null;
        String jwtToken = null;


        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                abortRequestWithUnauthorized(requestContext, "Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                abortRequestWithUnauthorized(requestContext, "JWT Token has expired");
            }
        } else {
            abortRequestWithUnauthorized(requestContext, "JWT Token does not begin with Bearer String");
        }


        // Once we get the token validate it.
        if (username == null || !jwtTokenUtil.validateToken(jwtToken)) {
            abortRequestWithUnauthorized(requestContext, "Пользователь не авторизован");
        }
        final SecurityContext securityContext = requestContext.getSecurityContext();

        String scheme = requestContext.getUriInfo().getRequestUri().getScheme();
        requestContext.setSecurityContext(new UserSecurityContext(new UserPrincipal(username), scheme));
    }

    public void abortRequestWithUnauthorized(ContainerRequestContext requestContext, String message) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", message);
        requestContext.abortWith(
                Response.status(401)
//                        .entity(jsonResponse.toString())
//                    .header("Access-Control-Allow-Origin", "*")
//                    .header("Access-Control-Allow-Credentials", "true")
//                    .header("Access-Control-Allow-Headers",
//                        "origin, content-type, accept, authorization")
//                    .header("Access-Control-Allow-Methods",
//                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .build()
        );
    }
}
