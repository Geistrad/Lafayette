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
package org.lafayette.server.http;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * Extends {@link javax.ws.rs.core.MediaType MediaType from WS Core}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@SuppressWarnings("NM_SAME_SIMPLE_NAME_AS_SUPERCLASS")
public class MediaType extends javax.ws.rs.core.MediaType {

    /**
     * Media type for plain text URI lists.
     *
     * See http://amundsen.com/hypermedia/urilist/
     */
    public static final String TEXT_URI_LIST = "text/uri-list";
    /**
     * Media type for MessagePack.
     *
     * See http://msgpack.org/
     */
    public static final String APPLICATION_X_MSGPACK = "application/x-msgpack";
}
