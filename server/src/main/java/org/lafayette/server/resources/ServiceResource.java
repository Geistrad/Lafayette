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
import javax.ws.rs.Path;
import javax.ws.rs.core.UriBuilder;
import org.lafayette.server.http.UriList;

/**
 * Serves the service resource.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Path("/service")
public class ServiceResource extends BaseResource {

    @Override
    protected void addUrisToIndexList(final UriList indexUriList) throws URISyntaxException {
        final UriBuilder ub = uriInfo().getAbsolutePathBuilder();
        ub.path("/file");
        indexUriList.add(ub.build());
    }

}
