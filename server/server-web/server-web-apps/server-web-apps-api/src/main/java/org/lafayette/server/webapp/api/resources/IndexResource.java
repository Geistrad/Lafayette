/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package org.lafayette.server.webapp.api.resources;

import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.lafayette.server.webapp.api.filter.Authentication;
import org.lafayette.server.web.http.Constants;
import org.lafayette.server.web.http.MediaType;
import org.lafayette.server.web.http.UriList;

/**
 * Serves the root/index resource.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Path("/")
public class IndexResource extends BaseResource {

    @Override
    protected void addUrisToIndexList(UriList indexUriList) throws URISyntaxException {
        indexUriList.add(new URI(servlet().getRealPath("/user")));
        indexUriList.add(new URI(servlet().getRealPath("/service")));
    }

    /**
     * Returns the index as plain text.
     *
     * @return never {@code null}
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String indexAsText() {
        final StringBuilder buffer = new StringBuilder("Lafayette Server");
        buffer.append(Constants.NL).append(Constants.NL).append("Hello World!");
        return buffer.toString();
    }

    //CHECKSTYLE:OFF
    @GET
    @Path("test/")
    @Produces(MediaType.TEXT_PLAIN)
    public Object test() {
        // XXX Remove this.
        return security().getUserPrincipal().toString()  + "\r\n";
    }

    @GET
    @Path("auth/")
    @Produces(MediaType.TEXT_PLAIN)
    @Authentication
    public Object auth() {
        // XXX Remove this.
        return security().getUserPrincipal().toString()  + "\r\n";
    }
    //CHECKSTYLE:ON
}
