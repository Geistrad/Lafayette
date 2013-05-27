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

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Helper to generate HTTP digest duthentication responses.
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
 *                       uri="/",
 *                       response="$RESPONSE"
 * ...
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Digest {

    public static String digestUserData(final String username, final String password, final String realm) {
        return DigestUtils.md5Hex(String.format("%s:%s:%s", username, realm, password));
    }

    public static String digestRequestData(final HttpMethod httpMethod, final URI requestedUri) {
        return DigestUtils.md5Hex(String.format("%s:%s", httpMethod, requestedUri.getPath()));
    }

    public static String digest(final String userData, final String nonce, final String requetsData) {
        return DigestUtils.md5Hex(String.format("%s:%s:%s", userData, nonce, requetsData));
    }

    public static String digest(final Values v) {
        final String userData = digestUserData(v.getUsername(), v.getPassword(), v.getRealm());
        final String requestData = digestRequestData(v.getHttpMethod(), v.getRequestedUri());
        return digest(userData, v.getNonce(), requestData);
    }

    /**
     * TODO Duplicated to {@link AuthorizationHeaderParser.DigestParams}.
     */
    public static class Values {
        private String username = "";
        private String password = "";
        private String realm = "";
        private String nonce = "";
        private HttpMethod httpMethod = HttpMethod.GET;
        private URI requestedUri;

        public Values() {
            super();
            try {
                requestedUri = new URI("/");
            } catch (URISyntaxException ex) {
                // Does never happen for URI "/".
            }
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(final String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(final String password) {
            this.password = password;
        }

        public String getRealm() {
            return realm;
        }

        public void setRealm(final String realm) {
            this.realm = realm;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(final String nonce) {
            this.nonce = nonce;
        }

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(final HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        public URI getRequestedUri() {
            return requestedUri;
        }

        public void setRequestedUri(final URI requestedUri) {
            this.requestedUri = requestedUri;
        }

    }

    public static enum HttpMethod { GET, PUT, POST, DELETE, HEAD, OPTIONS }
}
