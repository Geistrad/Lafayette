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

    private static final String AUTH_HEADER_FORMAT = "Digest realm=\"%s\" nonce=\"%s\"";

    public UnauthorizedException(final ResponseParameters params) {
        this(params, "");
    }

    public UnauthorizedException(final ResponseParameters params, final String message) {
        super(Response.status(Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE,
                                                          createDigestHEaderValue(params))
                .entity(message).build());
    }

    static String createDigestHEaderValue(final ResponseParameters params) {
        return String.format(AUTH_HEADER_FORMAT, params.getRealm(), params.getNonce());
    }

}
