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

import com.google.common.base.Objects;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseParameters {

    private String username;
    private String realm;
    private String nonce;
    private String httpMethod;
    private String requestedUri;

    public BaseParameters() {
        this("", "", "", "", "");
    }

    public BaseParameters(final String username, final String realm, final String nonce, final String httpMethod, final String requestedUri) {
        super();
        this.username = username;
        this.realm = realm;
        this.nonce = nonce;
        this.httpMethod = httpMethod;
        this.requestedUri = requestedUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getRequestedUri() {
        return requestedUri;
    }

    public void setRequestedUri(String requestedUri) {
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
                    .append("realm=").append(realm)
                    .append("nonce=").append(nonce)
                    .append("httpMethod=").append(httpMethod)
                    .append("requestedUri=").append(requestedUri)
                    .toString();
        }
}
