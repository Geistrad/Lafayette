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

package org.lafayette.server.web.http;

/**
 * Implementors must tell which {@link MediaType} it produces.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Typeable {

    /**
     * Get the media type.
     *
     * @return must not return {@code null}
     */
    String getMediaType();

}
