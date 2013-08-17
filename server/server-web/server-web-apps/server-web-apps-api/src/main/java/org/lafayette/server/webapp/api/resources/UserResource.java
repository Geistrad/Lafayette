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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.lafayette.server.domain.service.UserService;
import org.lafayette.server.domain.User;
import org.lafayette.server.web.http.MediaType;
import org.lafayette.server.web.http.UriList;
import org.lafayette.server.web.fmt.HtmlDocument;
import org.lafayette.server.web.fmt.HtmlTable;
import org.lafayette.server.web.fmt.TextTable;

/**
 * Serves the user resource.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Path("/user")
public class UserResource extends BaseResource {

    /**
     * Limit for all user query.
     */
    private static final int LIMIT = 25;

    @Override
    protected void addUrisToIndexList(final UriList indexUriList) throws URISyntaxException {
        final UriBuilder ub = uriInfo().getAbsolutePathBuilder();
        ub.path("/{id}");
        indexUriList.add(ub.build());
    }

    private Collection<User> findAllUsers(final int limit, final int offset) {
        final UserService service = services().getBusinessServices().getUserService();
        return service.getAllUsers(limit, 0);
    }
    /**
     * Get all users as plain text.
     *
     * @return plain text string
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String usersAsText() {
        return new TextTable<User>().format(findAllUsers(LIMIT, 0));
    }

    /**
     * Get all users as HTML.
     *
     * @return HTML string
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String usersAsHtml() {
        final HtmlDocument doc = new HtmlDocument();
        doc.setTitle("All users");
        doc.setBody(new HtmlTable<User>(2).format(findAllUsers(LIMIT, 0)));
        return doc.format();
    }

    /**
     * Get user as plain text.
     *
     * @param id must be not {@code null}, not empty and a parseable int.
     * @return plain text string
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String userAsPlainText(@PathParam("id") final String id) {
        final User user = findUserById(validateUserId(id));

        if (null == user) {
            raiseIdNotFoundError("user", id);
        }

        return user.toString();
    }

    /**
     * Get a user as JSON or XML.
     *
     * @param id must be not {@code null}, not empty and a parseable int.
     * @return domain object
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public User userAsJsonOrXml(@PathParam("id") final String id) {
        final User user = findUserById(validateUserId(id));

        if (null == user) {
            raiseIdNotFoundError("user", id);
        }

        return user;
    }

    /**
     * Get a user as message pack formatted byte sequence.
     *
     * @param id must be not {@code null}, not empty and a parseable int.
     * @return never {@code null}
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_X_MSGPACK)
    public Response userAsMessagePack(@PathParam("id") String id) {
        final User user = findUserById(validateUserId(id));

        if (null == user) {
            raiseIdNotFoundError("user", id);
        }

        return Response.ok(formatMessagePack(user), MediaType.APPLICATION_X_MSGPACK).build();
    }

    /**
     * Create a user from given JSON entity.
     *
     * @param jsonEntity must not be {@code null} and contain properties 'loginName' and 'password'
     * @return response for newly created user.
     * @throws JSONException if get property fails
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(final JSONObject jsonEntity) throws JSONException {
        if (null == jsonEntity) {
            raiseClientError("No JSON data given!");
        }

        if (!jsonEntity.has("loginName")) {
            raiseMissingPropertyError("loginName");
        }

        if (!jsonEntity.has("password")) {
            raiseMissingPropertyError("password");
        }

        final String loginName = jsonEntity.getString("loginName");
        final User newUser = new User(loginName, ""); // FIXME Set hashed user data (password).
        registry().getMappers()
                .createUserMapper()
                .insert(newUser);
        final URI uri = uriInfo().getAbsolutePathBuilder()
                .path(String.valueOf(newUser.getId()))
                .build();

        return Response.created(uri)
                .entity(newUser)
                .build();
    }

    /**
     * Validates that the given user id is parsable to an integer.
     *
     * raises missing property error if id is null or empty.
     * raises client error if id can not be parsed to integer.
     *
     * @param id id to verify
     * @return id as integer
     */
    private int validateUserId(final String id) {
        if (id == null || id.isEmpty()) {
            raiseMissingPropertyError("id");
        }

        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            raiseClientError(String.format("Not a valid user id '%s!", id));
            return -1;
        }
    }

    /**
     * Find a user by it's id.
     *
     * @param id user's id
     * @return may be {@code null}
     */
    private User findUserById(final int id) {
        return services().getBusinessServices().getUserService().findById(id);
    }
}
