package com.dmitri.backend.filter;

import com.dmitri.backend.annotation.Secured;
import com.dmitri.backend.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

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
    }

    public void abortRequestWithUnauthorized(ContainerRequestContext requestContext, String message) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("error", message);
        requestContext.abortWith(
                Response.status(401)
                        .entity(jsonResponse.toString())
                        .build()
        );
    }
}
