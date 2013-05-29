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

package org.lafayette.server;

import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class AuthenticationResourceFilterFactory implements ResourceFilterFactory {

    @Context private SecurityContext sc;
    @Context private ServletContext context;

    @Override
    public List<ResourceFilter> create(AbstractMethod am) {
        if (am.isAnnotationPresent(Authentication.class)) {
            return Collections.<ResourceFilter>singletonList(new Filter());
        }

        return null;
    }

    private class Filter implements ResourceFilter, ContainerRequestFilter {

        @Override
        public ContainerRequestFilter getRequestFilter() {
            return this;
        }

        @Override
        public ContainerResponseFilter getResponseFilter() {
            return null;
        }

        @Override
        public ContainerRequest filter(final ContainerRequest request) {
            return request;
        }

    }
}
