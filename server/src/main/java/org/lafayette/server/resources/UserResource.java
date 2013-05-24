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

import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.lang3.StringUtils;
import org.lafayette.server.Registry;
import org.lafayette.server.ServerContextListener;
import org.lafayette.server.domain.Finders;
import org.lafayette.server.domain.User;
import org.lafayette.server.domain.UserFinder;
import org.lafayette.server.http.Constants;
import org.lafayette.server.http.MediaType;
import org.lafayette.server.http.UriList;

/**
 * Serves the user resource.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Path("/user")
public class UserResource extends BaseResurce {

    private static final int PAD_ID = 5;
    private static final int PAD_LOGIN_NAME = 16;
    private static final int PAD_HASHED_PASSWORD = 34;
    private static final int LIMIT = 25;

    @Override
    protected void addUrisToIndexList(final UriList indexUriList) throws URISyntaxException {
        final UriBuilder ub = uriInfo().getAbsolutePathBuilder();
        ub.path("/{id}");
        indexUriList.add(ub.build());
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String usersAsText() {
        final StringBuilder buffer = new StringBuilder();
        final UserFinder finder = Finders.forUsers(registry().getMappers());
        buffer.append(StringUtils.rightPad("id", PAD_ID))
              .append(StringUtils.rightPad("loginName", PAD_LOGIN_NAME))
              .append(StringUtils.rightPad("hashedPassword", PAD_HASHED_PASSWORD))
              .append("salt")
              .append(Constants.NL);

        for (final User user : finder.findAll(LIMIT, 0)) {
            buffer.append(StringUtils.rightPad(String.valueOf(user.getId()), PAD_ID))
                  .append(StringUtils.rightPad(user.getLoginName(), PAD_LOGIN_NAME))
                  .append(StringUtils.rightPad(user.getHashedPassword(), PAD_HASHED_PASSWORD))
                  .append(user.getSalt()).append(Constants.NL);
        }

        return buffer.toString();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String userAsTest(@PathParam("id") String id) {
        final UserFinder finder = Finders.forUsers(registry().getMappers());
        final User user = finder.find(Integer.parseInt(id));
        return user.toString();
    }

}
