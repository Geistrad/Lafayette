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
import java.util.List;
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
import org.lafayette.server.http.AuthorizationHeaderParser;
import org.lafayette.server.http.AuthorizationHeaderParser.DigestParams;
import org.lafayette.server.http.MediaType;
import org.lafayette.server.http.UriList;
import org.lafayette.server.log.Log;
import org.lafayette.server.log.Logger;
import org.msgpack.MessagePack;

/**
 * Common functionality for all resource classes.
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
    @Context private ServletContext context;
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
        return (Registry) context.getAttribute(ServerContextListener.REGISRTY);
    }

    protected ServletContext servlet() {
        return context;
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

    protected String formatMessagePack(final DomainObject object) throws IOException {
        return new String(msgpack.write(object));
    }
    
    protected String getAuthorizationHeader() {
       final List<String> authHeader = headers().getRequestHeader(HttpHeaders.AUTHORIZATION); 
       
       if (authHeader == null || authHeader.isEmpty()) {
           log.debug("No authorization header sent by client.");
           return "";
       }
       
       if (authHeader.size() > 1) {
           log.warn("More than one authorization header sent by client! Using first one and ignore others.");
       }
       
       return authHeader.get(0);
    }
    
    @GET
    @Produces(MediaType.TEXT_URI_LIST)
    public String indexAsUriList() throws URISyntaxException {
        log.info("Respond with URI list for %s", context.getRealPath(""));
        addUrisToIndexList(indexUriList);
        return indexUriList.toString();
    }
}
