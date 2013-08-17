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

import javax.servlet.ServletContext;

/**
 * Get parameters from servlet context (declared in WEB-INf/web.xml.
 *
 * Example:
 * <code>
 * &lt;context-param&gt;
 *      &lt;param-name&gt;realm&lt;/param-name&gt;
 *      &lt;param-value&gt;Lafayette restricted area.&lt;/param-value&gt;
 * &lt;/context-param&gt;
 * </code>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class InitialServletParameters {

    /**
     * Parameter name for realm.
     */
    static final String PARAM_NAME_REALM = "realm";
    /**
     * Default value returned if a parameter from the context or context itself is {@code null}.
     */
    private static final String EMPTY_STRING = "";
    /**
     * Loaded value from context.
     *
     * Must not be {@code null}.
     */
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
