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

import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.ext.Provider;

/**
 * Filters the request/response for authentication.
 *
 * Filter is only applied to resource methods with annotation {@link Authentication}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Provider
public class AuthenticationResourceFilterFactory implements ResourceFilterFactory {

    @Override
    public List<ResourceFilter> create(final AbstractMethod am) {
        if (am.isAnnotationPresent(Authentication.class)) {
            return Collections.<ResourceFilter>singletonList(new SecurityContextFilter());
        }

        return Collections.emptyList();
    }

}
