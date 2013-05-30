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

import com.sun.jersey.api.NotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.lafayette.server.Registry;
import org.lafayette.server.ServerContextListener;
import org.lafayette.server.domain.DomainObject;
import org.lafayette.server.domain.Finders;
import org.lafayette.server.http.MediaType;
import org.lafayette.server.http.UriList;
import org.lafayette.server.log.Log;
import org.lafayette.server.log.Logger;
import org.msgpack.MessagePack;

/**
 * Common functionality for all resource classes.
 *
 * XXX Consider package private.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public abstract class BaseResource {

    /**
     * Logger facility.
     */
    protected final Logger log = Log.getLogger(this);
    /**
     * List of URIs available from a resource's index.
     */
    private final UriList indexUriList;
    /**
     * Used to format message pack format.
     */
    private final MessagePack msgpack = new MessagePack();
    /**
     * Servlet context injected by Jersey.
     */
    @Context private ServletContext servlet;
    /**
     * Security context injected by Jersey.
     */
    @Context private HttpHeaders headers;
    /**
     * URI info injected by Jersey.
     */
    @Context private UriInfo uriInfo;

    public BaseResource() {
        indexUriList = new UriList();
        indexUriList.setComment("# Available URIs:");
    }

    protected abstract void addUrisToIndexList(UriList indexUriList) throws URISyntaxException;

    protected Registry registry() {
        return (Registry) servlet.getAttribute(ServerContextListener.REGISRTY);
    }

    protected ServletContext servlet() {
        return servlet;
    }

    protected HttpHeaders headers() {
        return headers;
    }

    protected UriInfo uriInfo() {
        return uriInfo;
    }
    private Finders finders = null;

    protected synchronized Finders finders() {
        if (null == finders) {
            finders = new Finders(registry().getMappers());
        }

        return finders;
    }

    protected Response createErrorResponse(String message) {
        return Response.serverError()
                .entity(message)
                .build();
    }

    protected void raiseIdNotFoundError(String resource, String id) throws WebApplicationException {
        final String message = String.format("Can't find '%s' with id '%s'!", resource, id);
        throw new NotFoundException(message);
    }

    protected void raiseMissingPropertyError(String name) throws WebApplicationException {
        final String message = String.format("Property '%s' missing!", name);
        throw new WebApplicationException(createErrorResponse(message));
    }

    protected byte[] formatMessagePack(final DomainObject object) throws IOException {
        return msgpack.write(object);
    }

    @GET
    @Produces(MediaType.TEXT_URI_LIST)
    public String indexAsUriList() throws URISyntaxException {
        log.info("Respond with URI list for %s", servlet.getRealPath(""));
        addUrisToIndexList(indexUriList);
        return indexUriList.toString();
    }
}
