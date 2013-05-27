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

import com.google.common.base.Objects;
import org.apache.commons.lang3.Validate;
import org.lafayette.server.log.Log;
import org.lafayette.server.log.Logger;

/**
 * Parses the "Authorization" header field from a HTTP request.
 *
 * Header for digest authentication.
 * <pre>
 * Authorization: Digest username="Foo",
 *                       realm="Private Area",
 *                       nonce="IrTfjizEdXmIdlwHwkDJx0",
 *                       uri="/",
 *                       response="$RESPONSE"
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class AuthorizationHeaderParser {

    static final DigestParams DEFAULT_DIGEST_PARAMAS = new DigestParams("", "", "", "", "", "");
    private static final Logger LOG = Log.getLogger(AuthorizationHeaderParser.class);

    private AuthorizationHeaderParser() {
        super();
    }

    public static DigestParams parseDigestHeaderValue(final String headerValue) {
        Validate.notNull(headerValue, "Header value must not be null!");
        final String trimmedValue = headerValue.trim();

        if (trimmedValue.isEmpty()) {
            LOG.info("Authorization header is empty! Return empty default params.");

            return DEFAULT_DIGEST_PARAMAS;
        }

        final int firstWhiteSpace = trimmedValue.indexOf(' ');

        if (!"Digest".equalsIgnoreCase(trimmedValue.substring(0, firstWhiteSpace))) {
            LOG.info("Authorization header is not of type 'Digest'! Return empty default params.");
            return DEFAULT_DIGEST_PARAMAS;
        }

        String username = "";
        String realm = "";
        String nonce = "";
        String httpMethod = "";
        String requestedUri = "";
        String response = "";

        for (final String keyValuePair : trimmedValue.substring(firstWhiteSpace).split(",")) {
            final String[] keyValueParts = keyValuePair.split("=");
            final String key = keyValueParts[0].trim();
            final String value = keyValueParts[1].trim().replace("\"", "");

            if ("username".equalsIgnoreCase(key)) {
                username = value;
            } else if ("realm".equalsIgnoreCase(key)) {
                realm = value;
            } else if ("nonce".equalsIgnoreCase(key)) {
                nonce = value;
            } else if ("httpMethod".equalsIgnoreCase(key)) {
                httpMethod = value;
            } else if ("uri".equalsIgnoreCase(key)) {
                requestedUri = value;
            } else if ("response".equalsIgnoreCase(key)) {
                response = value;
            }
        }

        return new DigestParams(username, realm, nonce, httpMethod, requestedUri, response);
    }

    /**
     * Encapsulates the parameters from a client's authentication header.
     */
    public static class DigestParams {

        /**
         * Users login name.
         */
        private final String username;
        /**
         * Parameter from server retrieved from previous 401 response.
         */
        private final String realm;
        /**
         * Parameter from server retrieved from previous 401 response.
         */
        private final String nonce;
        /**
         * Used HTTP method.
         */
        private final String httpMethod;
        /**
         * Uri to resource which want to be accessed.
         */
        private final String requestedUri;
        /**
         * Response calculated by client.
         *
         * @see Digest#digest(org.lafayette.server.http.Digest.Values)
         */
        private final String response;

        DigestParams(final String username, final String realm, final String nonce, final String httpMethod, final String uri, final String response) {
            super();
            this.username = username;
            this.realm = realm;
            this.nonce = nonce;
            this.httpMethod = httpMethod;
            this.requestedUri = uri;
            this.response = response;
        }

        public String getUsername() {
            return username;
        }

        public String getRealm() {
            return realm;
        }

        public String getNonce() {
            return nonce;
        }

        public String getRequestedUri() {
            return requestedUri;
        }

        public String getResponse() {
            return response;
        }

        public String getHttpMethod() {
            return httpMethod;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(username, realm, nonce, httpMethod, requestedUri, response);
        }

        /**
         * The parameter object is only valid if all parameters are not empty strings
         * because all parameters are required for digest authentication.
         *
         * @return {@code true} if and only if all properties are not empty
         */
        public boolean isValid() {
            return !(username.isEmpty()
                    || realm.isEmpty()
                    || nonce.isEmpty()
                    || httpMethod.isEmpty()
                    || requestedUri.isEmpty()
                    || response.isEmpty());
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof DigestParams)) {
                return false;
            }

            final DigestParams other = (DigestParams) obj;
            return Objects.equal(username, other.username)
                    && Objects.equal(realm, other.realm)
                    && Objects.equal(nonce, other.nonce)
                    && Objects.equal(httpMethod, other.httpMethod)
                    && Objects.equal(requestedUri, other.requestedUri)
                    && Objects.equal(response, other.response);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("username", username)
                    .add("realm", realm)
                    .add("nonce", nonce)
                    .add("httpMethod", httpMethod)
                    .add("requestedUri", requestedUri)
                    .add("response", response)
                    .toString();
        }
    }
}
