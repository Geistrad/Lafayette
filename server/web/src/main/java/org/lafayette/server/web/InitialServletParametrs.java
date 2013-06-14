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

package org.lafayette.server.web;

import javax.servlet.GenericServlet;

/**
 * Wraps a {@link GenericServlet} to get type safe configuration.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class InitialServletParametrs {

    /**
     * Name of the realm parameter.
     */
    static final String PARMA_NAME_REALM = "REALM";
    private static final String EMPTY_STRING = "";

    /**
     * To get parameters from.
     */
    private final GenericServlet servlet;

    /**
     * Dedicated constructor.
     *
     * @param servlet to get parameters from
     */
    public InitialServletParametrs(final GenericServlet servlet) {
        super();
        this.servlet = servlet;
    }

    /**
     * Returns the realm.
     *
     * @return never {@code null} but maybe empty
     */
    public String getRealm() {
        return getInitParameter(PARMA_NAME_REALM);
    }

    /**
     * Generic getter which avoids {@code null}.
     *
     * @param name name of property
     * @return never {@code null} but maybe empty
     */
    String getInitParameter(final String name) {
        final String value = servlet.getInitParameter(name);
        return null == value ? EMPTY_STRING : value;
    }
}
