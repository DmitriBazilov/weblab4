package com.dmitri.backend.controller;

import com.dmitri.backend.annotation.Secured;
import com.dmitri.backend.model.Hit;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/hits")
public class HitController {

    @Secured
    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveHit(@Context SecurityContext sc, Hit hit) {
        System.out.println(sc.getUserPrincipal().getName());
        return Response.ok().build();
    }

    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHits(@Context SecurityContext sc) {
        System.out.println(sc.getUserPrincipal().getName());
        return Response.ok().build();
    }

    @Secured
    @Path("/clear")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response clearHits(@Context SecurityContext sc) {
        return Response.ok().build();
    }
}
