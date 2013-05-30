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
 *                       httpMethod="GET",
 *                       uri="/",
 *                       response="$RESPONSE"
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class AuthorizationHeaderParser {

    static final RequestParameters DEFAULT_DIGEST_PARAMAS = new RequestParameters("", "", "", "", "", "");
    private static final Logger LOG = Log.getLogger(AuthorizationHeaderParser.class);

    private AuthorizationHeaderParser() {
        super();
    }

    public static RequestParameters parseDigestHeaderValue(final String headerValue) {
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

        return new RequestParameters(username, realm, nonce, httpMethod, requestedUri, response);
    }

}
