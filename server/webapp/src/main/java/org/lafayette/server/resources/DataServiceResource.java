/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package org.lafayette.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONObject;
import org.lafayette.server.http.MediaType;
import org.lafayette.server.web.service.data.DataStore;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Path("/service/data")
public class DataServiceResource {

    private static final DataStore<JSONObject> DATA = new DataStore<JSONObject>();

    @GET
    @Path("/{user}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataAsJson(@PathParam("user") final String user, @PathParam("id") final String id) {
        final JSONObject datum = DATA.get(user, id);

        if (null == datum) {
            return Response.status(Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(datum).build();
    }

    @GET
    @Path("/{user}/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Object getDataAsXml(@PathParam("user") final String user, @PathParam("id") final String id) {
        return DATA.get(user, id);
    }

    @GET
    @Path("/{user}/{id}")
    @Produces(MediaType.APPLICATION_X_MSGPACK)
    public Object getDataAsMessagePack(@PathParam("user") final String user, @PathParam("id") final String id) {
        return DATA.get(user, id);
    }

    @PUT
    @Path("/{user}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Object saveData(@PathParam("user") final String user, @PathParam("id") final String id, final JSONObject jsonEntity) {
        return DATA.set(user, id, jsonEntity);
    }

    @DELETE
    @Path("/{user}/{id}")
    public Object deleteData(@PathParam("user") final String user, @PathParam("id") final String id) {
        return DATA.remove(user, id);
    }
}
