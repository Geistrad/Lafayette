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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
public class UserResource extends BaseResource {

    private static final int PAD_ID = 5;
    private static final int PAD_LOGIN_NAME = 16;
    private static final int PAD_HASHED_USER_DATA = 34;
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
        final UserFinder finder = finders().forUsers();
        buffer.append(StringUtils.rightPad("id", PAD_ID))
                .append(StringUtils.rightPad("loginName", PAD_LOGIN_NAME))
                .append(StringUtils.rightPad("hashedUserData", PAD_HASHED_USER_DATA))
                .append("salt")
                .append(Constants.NL);

        for (final User user : finder.findAll(LIMIT, 0)) {
            buffer.append(StringUtils.rightPad(String.valueOf(user.getId()), PAD_ID))
                    .append(StringUtils.rightPad(user.getLoginName(), PAD_LOGIN_NAME))
                    .append(StringUtils.rightPad(user.getHashedUserData(), PAD_HASHED_USER_DATA))
                    .append(Constants.NL);
        }

        return buffer.toString();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String userAsPlainText(@PathParam("id") String id) {
        final User user = findUserById(id);

        if (null == user) {
            raiseIdNotFoundError("user", id);
        }

        return user.toString();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public User userAsJsonOrXml(@PathParam("id") String id) {
        final User user = findUserById(id);

        if (null == user) {
            raiseIdNotFoundError("user", id);
        }

        return user;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_X_MSGPACK)
    public Response userAsMessagePack(@PathParam("id") String id) throws IOException {
        final User user = findUserById(id);

        if (null == user) {
            raiseIdNotFoundError("user", id);
        }

        return Response.ok(formatMessagePack(user), MediaType.APPLICATION_X_MSGPACK).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(JSONObject jsonEntity) throws JSONException, Exception {
        if (!jsonEntity.has("loginName")) {
            raiseMissingPropertyError("loginName");
        }

        final String loginName = jsonEntity.getString("loginName");
        final User newUser = new User(loginName, ""); // FIXME Set hashed user data (password).
        registry().getMappers().createUserMapper().insert(newUser);
        final URI uri = uriInfo().getAbsolutePathBuilder()
                .path(String.valueOf(newUser.getId()))
                .build();

        return Response.created(uri)
                .entity(newUser)
                .build();
    }

    /**
     * Find a user by it's id.
     *
     * @param id user's id
     * @return may be {@code null}, if user was not found
     * throws NumberFormatException if passed in user id is not parseable to integer
     */
    private User findUserById(String id) {
        final UserFinder finder = finders().forUsers();
        return finder.find(Integer.parseInt(id));
    }
}
