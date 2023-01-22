package com.dmitri.backend.controller;

import com.dmitri.backend.model.AuthorizationManager;
import com.dmitri.backend.model.UserInfo;
import com.dmitri.backend.util.AuthStatus;
import com.dmitri.backend.util.JwtTokenUtil;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthController {

    @EJB
    private AuthorizationManager authorizationManager;

    @EJB
    private JwtTokenUtil jwtTokenUtil;

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTempLog() {
        JSONObject jsonResopnse = new JSONObject();
        jsonResopnse.append("login", "ivan");
        jsonResopnse.append("password", "12345678");
        jsonResopnse.append("token", "qwerty123452281337");
        return Response.ok(jsonResopnse.toString()).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorize(JSONObject data) {
        String username = data.getString("username");
        String password = data.getString("password");
        UserInfo userInfo = new UserInfo(username, password);
        AuthStatus authStatus = authorizationManager.authenticate(userInfo);
        String error = "";
        if (authStatus == AuthStatus.AUTH_OK) {
            String token = jwtTokenUtil.generateToken(userInfo);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.append("token", token);
            return Response.ok(jsonResponse.toString()).build();
        } else if (authStatus == AuthStatus.AUTH_WRONG_LOGIN) {
            error = "Такого пользователя не существует";
        } else if (authStatus == AuthStatus.AUTH_WRONG_PASSWORD) {
            error = "Неправильный пароль";
        }
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.append("error", error);
        return Response.status(400).entity(jsonResponse.toString()).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(JSONObject data) {
        String username = data.getString("username");
        String password = data.getString("password");
        UserInfo userInfo = new UserInfo(username, password);
        AuthStatus authStatus = authorizationManager.addUser(username, userInfo);
        if (authStatus == AuthStatus.AUTH_OK) {
            return Response.ok().build();
        } else {
            String error = "Такой пользователь уже существует";
            JSONObject jsonResopnse = new JSONObject();
            jsonResopnse.append("error", error);
            return Response.status(400).entity(jsonResopnse.toString()).build();
        }

    }
}
