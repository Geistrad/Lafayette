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
 * Parameter needed for authentication response.
 *
 * XXX Consider if necessary.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ResponseParameters  extends BaseParameters {

    /**
     * The users plain text password.
     */
    private String password;

    /**
     * Initializes all parameters with an empty string.
     */
    public ResponseParameters() {
        this("", "", "", "", "", "");
    }

    /**
     * Dedicated constructor.
     *
     * @param username must not be {@code null}
     * @param realm must not be {@code  null}
     * @param password must not be {@code  null}
     * @param nonce must not be {@code  null}
     * @param httpMethod must not be {@code  null}
     * @param requestedUri must not be {@code  null}
     */
    public ResponseParameters(final String username, final String realm, final String password, final String nonce,
        final String httpMethod, final String requestedUri) {
        super(username, realm, nonce, httpMethod, requestedUri);
        this.password = password;
    }

    /**
     * Get the plain text password.
     *
     * @return never {@code null}
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the plain text password.
     *
     * @param password must not be {code null}
     */
    public void setPassword(final String password) {
        Validate.notNull(password);
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), password);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        final ResponseParameters other = (ResponseParameters) obj;
        return Objects.equal(password, other.password);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(super.toString())
                .add("password", password)
                .toString();
    }

}
