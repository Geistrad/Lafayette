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

import java.net.URI;
import java.util.HashSet;

/**
 * Represents a set of URIs used for repsonses of {@link MediaType#TEXT_UR_ILIST type URI-list}.
 * 
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UriList extends HashSet<URI> implements Typeable {

    @Override
    public String getMediaType() {
        return MediaType.TEXT_URI_LIST;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        
        for (final URI uri : this) {
            buffer.append(uri).append(Constants.NL);
        }
        
        return buffer.toString();
    }
    
}
