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

package org.lafayette.server.filter;

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;
import org.lafayette.server.domain.User;

/**
 * Implementation of security context.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SecurityContextImpl implements SecurityContext {

    /**
     * The authenticated principal.
     */
    private final User user;

    /**
     * Dedicated constructor.
     *
     * @param user authenticated principal
     */
    public SecurityContextImpl(final User user) {
        super();
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(final String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.DIGEST_AUTH;
    }

}
