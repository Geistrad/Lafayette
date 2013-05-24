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

import java.net.URISyntaxException;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.lafayette.server.Registry;
import org.lafayette.server.ServerContextListener;
import org.lafayette.server.http.MediaType;
import org.lafayette.server.http.UriList;
import org.lafayette.server.log.Log;
import org.lafayette.server.log.Logger;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public abstract class BaseResurce {

    protected final Logger log = Log.getLogger(this);

    private final UriList indexUriList;
    @Context private ServletContext context;
    @Context private UriInfo uriInfo;

    public BaseResurce() {
        indexUriList = new UriList();
        indexUriList.setComment("# Available URIs:");
    }

    protected abstract void addUrisToIndexList(UriList indexUriList) throws URISyntaxException;

    protected Registry registry() {
        return (Registry) context.getAttribute(ServerContextListener.REGISRTY);
    }

    protected ServletContext context() {
        return context;
    }

    protected UriInfo uriInfo() {
        return uriInfo;
    }

    @GET
    @Produces(MediaType.TEXT_URI_LIST)
    public String indexAsUriList() throws URISyntaxException {
        log.info("Respond with URI list for %s", context.getRealPath(""));
        addUrisToIndexList(indexUriList);
        return indexUriList.toString();
    }

}
