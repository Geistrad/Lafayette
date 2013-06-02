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
package org.lafayette.server.http.digest;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Helper to generate HTTP digest authentication responses.
 *
 * Format is:
 * <pre>
 * $RESPONSE = md5(
 *                  md5( username + ":" + realm + ":" + password ) +    // User data.
 *                  ":" + nonce + ":" +
 *                  md5( httpMethod + ":" + requestedUri )              // Request data.
 *             )
 * </pre>
 *
 * Server response header:
 * <pre>
 * HTTP/1.1 401 Unauthorized
 * WWW-Authenticate: Digest real="Private Area", nonce="IrTfjizEdXmIdlwHwkDJx0"
 * ...
 * </pre>
 *
 * Client Request:
 * <pre>
 * GET / HTTP/1.1
 * Authorization: Digest username="Foo",
 *                       realm="Private Area",
 *                       nonce="IrTfjizEdXmIdlwHwkDJx0",
 * ,                     httpMethod="GET",
 *                       uri="/",
 *                       response="$RESPONSE"
 * ...
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Digest {

    private Digest() {
        super();
    }

    public static String digestUserData(final String username, final String password, final String realm) {
        return DigestUtils.md5Hex(String.format("%s:%s:%s", username, realm, password));
    }

    public static String digestRequestData(final String httpMethod, final String requestedUri) {
        return DigestUtils.md5Hex(String.format("%s:%s", httpMethod, requestedUri));
    }

    public static String digest(final String userData, final String nonce, final String requetsData) {
        return DigestUtils.md5Hex(String.format("%s:%s:%s", userData, nonce, requetsData));
    }

    public static String digest(final ResponseParameters params) {
        final String userData = digestUserData(params.getUsername(), params.getPassword(), params.getRealm());
        final String requestData = digestRequestData(params.getHttpMethod(), params.getRequestedUri());
        return digest(userData, params.getNonce(), requestData);
    }

}
