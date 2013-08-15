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

package org.lafayette.server.webapp.api.resources;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.lafayette.server.web.http.MediaType;
import org.lafayette.server.web.http.UriList;
import org.lafayette.server.web.service.data.DataService;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Path("/service/data")
public class DataServiceResource extends BaseResource {

    @GET
    @Path("/{user}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataAsJson(@PathParam("user") final String user, @PathParam("id") final String id) {
        JSONObject datum = getData(user, id);

        if (null == datum) {
            return Response.status(Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(datum).build();
    }

    @GET
    @Path("/{user}/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Object getDataAsXml(@PathParam("user") final String user, @PathParam("id") final String id) throws JSONException {
        JSONObject datum = getData(user, id);

        if (null == datum) {
            return Response.status(Status.NOT_FOUND).type(MediaType.APPLICATION_XML).build();
        }

        return Response.status(Status.OK).type(MediaType.APPLICATION_XML).entity(Converter.xml(datum)).build();
    }

    @GET
    @Path("/{user}/{id}")
    @Produces(MediaType.APPLICATION_X_MSGPACK)
    public Object getDataAsMessagePack(@PathParam("user") final String user, @PathParam("id") final String id) {
        return getData(user, id);
    }

    @PUT
    @Path("/{user}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Object putData(@PathParam("user") final String user, @PathParam("id") final String id, final JSONObject json) throws IOException {
        final DataService service = getDataService();
        final JSONObject datum = service.putData(user, id, json);
        final Response.ResponseBuilder response = Response.status(Status.CREATED);
        final UriBuilder uri = uriInfo().getBaseUriBuilder();
        response.type(MediaType.APPLICATION_XML).header("Location", uri.build(user, id).toString());

        if (null != datum) {
            response.entity(Converter.mpack(datum));
        }

        return response.build();
    }

    @DELETE
    @Path("/{user}/{id}")
    public void deleteData(@PathParam("user") final String user, @PathParam("id") final String id) {
        final DataService service = getDataService();
        service.deleteData(user, id);
    }

    @Override
    protected void addUrisToIndexList(UriList list) throws URISyntaxException {

    }

    private JSONObject getData(final String user, final String id) {
        final DataService service = getDataService();
        final JSONObject datum = service.getData(user, id);
        return datum;
    }

    private DataService getDataService() {
        return services().getApiServices().getDataService();
    }
}
