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

package org.lafayette.server.http;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.lafayette.server.http.digest.ResponseParameters;

/**
 * Used to signal authorization required.
 *
 * See  http://zcox.wordpress.com/2009/06/22/use-exceptions-to-send-error-responses-in-jersey/
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UnauthorizedException extends WebApplicationException {

    /**
     * Format string for HTTP authorization header.
     */
    private static final String AUTH_HEADER_FORMAT = "Digest realm=\"%s\" nonce=\"%s\"";

    /**
     * Initializes the response with empty message.
     *
     * @param params response parameters used to get realm and nonce from
     */
    public UnauthorizedException(final ResponseParameters params) {
        this(params, "");
    }

    /**
     * Dedicated constructor.
     *
     * @param params response parameters used to get realm and nonce from
     * @param message response message
     */
    public UnauthorizedException(final ResponseParameters params, final String message) {
        super(Response.status(Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE,
                                                          createDigestHeaderValue(params))
                .entity(message).build());
    }

    /**
     * Formats the HTTP authorization header.
     *
     * @param params response parameters used to get realm and nonce from
     * @return formatted string
     */
    static String createDigestHeaderValue(final ResponseParameters params) {
        return String.format(AUTH_HEADER_FORMAT, params.getRealm(), params.getNonce());
    }

}
