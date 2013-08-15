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
package org.lafayette.server.web.http.digest;

import com.google.common.base.Objects;
import org.apache.commons.lang3.Validate;

/**
 * Base values needed for HTTP digest authentication.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseParameters {

    /**
     * Authenticated user's name.
     */
    private String username;
    /**
     * Realm of the authenticated resource.
     */
    private String realm;
    /**
     * Server generated unique random nonce.
     */
    private String nonce;
    /**
     * HTTP method of the request.
     */
    private String httpMethod;
    /**
     * URI to the requested resource.
     */
    private String requestedUri;

    /**
     * Default constructor.
     *
     * Initializes all properties with an empty string.
     */
    public BaseParameters() {
        this("", "", "", "", "");
    }

    /**
     * Dedicated constructor.
     *
     * @param username must not be {@code null}
     * @param realm must not be {@code  null}
     * @param nonce must not be {@code  null}
     * @param httpMethod must not be {@code  null}
     * @param requestedUri must not be {@code  null}
     */
    public BaseParameters(final String username, final String realm, final String nonce, final String httpMethod,
        final String requestedUri) {
        super();
        setUsername(username);
        setRealm(realm);
        setNonce(nonce);
        setHttpMethod(httpMethod);
        setRequestedUri(requestedUri);
    }

    /**
     * Get the user name.
     *
     * @return never {@code null}
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the user name.
     *
     * @param username must not be {@code null}
     */
    public final void setUsername(final String username) {
        Validate.notNull(username);
        this.username = username;
    }

    /**
     * Get the realm.
     *
     * @return never {@code null}
     */
    public String getRealm() {
        return realm;
    }

    /**
     * Set the realm.
     *
     * @param realm must not be {@code null}
     */
    public final void setRealm(final String realm) {
        Validate.notNull(realm);
        this.realm = realm;
    }

    /**
     * Get the NONCE.
     *
     * @return never {@code null}
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * Set the NONCE.
     *
     * @param nonce must not be {@code null}
     */
    public final void setNonce(final String nonce) {
        Validate.notNull(nonce);
        this.nonce = nonce;
    }

    /**
     * Get the HTTP method.
     *
     * @return never {@code null}
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * Set the HTTP method.
     *
     * @param httpMethod must not be {@code null}
     */
    public final void setHttpMethod(final String httpMethod) {
        Validate.notNull(httpMethod);
        this.httpMethod = httpMethod;
    }

    /**
     * Get the requested URI.
     *
     * @return never {@code null}
     */
    public final String getRequestedUri() {
        return requestedUri;
    }

    /**
     * Set the requested URI.
     *
     * @param requestedUri must not be {@code null}
     */
    public final void setRequestedUri(final String requestedUri) {
        Validate.notNull(requestedUri);
        this.requestedUri = requestedUri;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, realm, nonce, httpMethod, requestedUri);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof BaseParameters)) {
            return false;
        }

        final BaseParameters other = (BaseParameters) obj;
        return Objects.equal(username, other.username)
                && Objects.equal(realm, other.realm)
                && Objects.equal(nonce, other.nonce)
                && Objects.equal(httpMethod, other.httpMethod)
                && Objects.equal(requestedUri, other.requestedUri);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("username=").append(username)
                .append(", realm=").append(realm)
                .append(", nonce=").append(nonce)
                .append(", httpMethod=").append(httpMethod)
                .append(", requestedUri=").append(requestedUri)
                .toString();
    }
}
