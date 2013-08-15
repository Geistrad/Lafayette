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

package org.lafayette.server.webapp.api.filter;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
//CHECKSTYLE:OFF
import javax.ws.rs.core.SecurityContext;
//CHECKSTYLE:ON
import org.lafayette.server.domain.User;

/**
 * Always inject {@link User#ANONYMOUS} into the requests {@link SecurityContext security context}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class SecurityContextFilterAnonymous implements SecuirityContextFilter {

    @Override
    public ContainerRequestFilter getRequestFilter() {
        return this;
    }

    @Override
    public ContainerResponseFilter getResponseFilter() {
        return this;
    }

    @Override
    @edu.umd.cs.findbugs.annotations.SuppressWarnings("CFS_CONFUSING_FUNCTION_SEMANTICS")
    public ContainerRequest filter(final ContainerRequest request) {
        request.setSecurityContext(new SecurityContextImpl(User.ANONYMOUS));
        return request;
    }

    @Override
    public ContainerResponse filter(final ContainerRequest request, final ContainerResponse response) {
        return response;
    }

}
