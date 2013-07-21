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

package org.lafayette.server.web.service;

import java.util.Collection;

/**
 * Describes a service.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface ServiceDescriptor {

    /**
     * Get human readable description for service.
     *
     * @return must not be {@code null}
     */
    String getServiceDescription();
    /**
     * Get a list of descriptions for each available API method.
     *
     * @return must not be {@code null}
     */
    Collection<String> getApiDescription();

}
