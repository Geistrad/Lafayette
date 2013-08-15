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
import org.apache.commons.lang3.Validate;

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

    /**
     * Hidden for pure static class.
     */
    private Digest() {
        super();
    }

    /**
     * Generates md5 hash in hex from user data.
     *
     * Format: <kbd>md5( username : realm : password )</kbd>
     *
     * @param username must not be {@code null}
     * @param password must not be {@code null}
     * @param realm must not be {@code null}
     * @return never {@code null} or empty
     */
    public static String digestUserData(final String username, final String password, final String realm) {
        Validate.notNull(username, "Username must not be null!");
        Validate.notNull(password, "Password must not be null!");
        Validate.notNull(realm, "Realm must not be null!");
        return DigestUtils.md5Hex(String.format("%s:%s:%s", username, realm, password));
    }

    /**
     * Generates md5 hash in hex from request data.
     *
     * Format: <kbd>md5( httpMethod : requestedUri)</kbd>
     *
     * @param httpMethod must not be {@code null}
     * @param requestedUri must not be {@code null}
     * @return never {@code null} or empty
     */
    public static String digestRequestData(final String httpMethod, final String requestedUri) {
        Validate.notNull(httpMethod, "HttpMethod must not be null!");
        Validate.notNull(requestedUri, "RequestedUri must not be null!");
        return DigestUtils.md5Hex(String.format("%s:%s", httpMethod, requestedUri));
    }

    /**
     * Generates md5 hash in hex (authentication digest) from {@link #digestUserData(java.lang.String, java.lang.String,
     * java.lang.String) hashed userdata}, NONCE, and {@link #digestRequestData(java.lang.String, java.lang.String)
     * hashed request data}.
     *
     * Format: <kbd>md5( userData : NONCE : requetsData)</kbd>
     *
     * @param userData must not be {@code null}
     * @param nonce must not be {@code null}
     * @param requetsData must not be {@code null}
     * @return never {@code null} or empty
     */
    public static String digest(final String userData, final String nonce, final String requetsData) {
        Validate.notNull(userData, "UserData must not be null!");
        Validate.notNull(nonce, "NONCE must not be null!");
        Validate.notNull(requetsData, "RequetsData must not be null!");
        return DigestUtils.md5Hex(String.format("%s:%s:%s", userData, nonce, requetsData));
    }

    /**
     * Generates authentication digest from request parameters.
     *
     * @param params must not be {@code null}
     * @return never {@code null}
     */
    public static String digest(final ResponseParameters params) {
        Validate.notNull(params, "Request parameters mut not be null!");
        final String userData = digestUserData(params.getUsername(), params.getPassword(), params.getRealm());
        final String requestData = digestRequestData(params.getHttpMethod(), params.getRequestedUri());
        return digest(userData, params.getNonce(), requestData);
    }

}
