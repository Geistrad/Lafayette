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

    private static final DigestParams DEFAULT_DIGEST_PARAMAS = new DigestParams("", "", "", "", "");

    private AuthorizationHeaderParser() {
        super();
    }

    public static DigestParams parseDigestHeaderValue(final String headerValue) {
        Validate.notEmpty(headerValue, "Header value must not be empty!");
        final String trimmedValue = headerValue.trim();
        final int firstWhiteSpace = trimmedValue.indexOf(' ');

        if (!"Digest".equalsIgnoreCase(trimmedValue.substring(0, firstWhiteSpace))) {
            return DEFAULT_DIGEST_PARAMAS;
        }

        String username = "";
        String realm = "";
        String nonce = "";
        String uri = "";
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
            } else if ("uri".equalsIgnoreCase(key)) {
                uri = value;
            } else if ("response".equalsIgnoreCase(key)) {
                response = value;
            }
        }

        return new DigestParams(username, realm, nonce, uri, response);
    }

    public static class DigestParams {
        private final String username;
        private final String realm;
        private final String nonce;
        private final String uri;
        private final String response;

        DigestParams(final String username, final String realm, final String nonce, final String uri, final String response) {
            super();
            this.username = username;
            this.realm = realm;
            this.nonce = nonce;
            this.uri = uri;
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

        public String getUri() {
            return uri;
        }

        public String getResponse() {
            return response;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(username, realm, nonce, uri, response);
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof DigestParams)) {
                return false;
            }

            final DigestParams other = (DigestParams) obj;
            return Objects.equal(username, other.username) &&
                    Objects.equal(realm, other.realm) &&
                    Objects.equal(nonce, other.nonce) &&
                    Objects.equal(uri, other.uri) &&
                    Objects.equal(response, other.response);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this).toString();
        }

    }
}
