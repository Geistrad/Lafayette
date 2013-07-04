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

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.lafayette.server.web.service.data.DataStore;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Path("/service/data")
public class DataServiceResource {

    private final DataStore<Object> dataStore = new DataStore<Object>();

    @GET
    @Path("/{user}/{id}")
    public Object getData(@PathParam("user") final String user, @PathParam("user") final String id) {
        return dataStore.get(user, id);
    }

    @PUT
    @Path("/{user}/{id}")
    public Object saveData(@PathParam("user") final String user, @PathParam("user") final String id) {
        return dataStore.set(user, id, new Object());
    }

    @DELETE
    @Path("/{user}/{id}")
    public Object deleteData(@PathParam("user") final String user, @PathParam("user") final String id) {
        return dataStore.remove(user, id);
    }
}
