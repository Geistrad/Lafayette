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
import javax.servlet.ServletContext;

/**
 * Wraps a {@link GenericServlet} to get type safe configuration.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class InitialServletParameters {

    private static final String EMPTY_STRING = "";
    static final String PARAM_NAME_REALM = "realm";
    private final String realm;

    /**
     * Initializes all parameters with an empty string.
     */
    public InitialServletParameters() {
        this(null);
    }

    /**
     * Dedicated constructor.
     *
     * @param context to get parameters from
     */
    public InitialServletParameters(final ServletContext context) {
        super();
        realm = null == context ? EMPTY_STRING : context.getInitParameter(PARAM_NAME_REALM);
    }

    /**
     * Returns the realm.
     *
     * @return never {@code null} but maybe empty
     */
    public String getRealm() {
        return realm;
    }

}
