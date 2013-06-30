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

package org.lafayette.server.web.resource;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class TextResource implements Resource {

    private final String text;

    public TextResource() {
        this("");
    }

    public TextResource(final String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof TextResource)) {
            return false;
        }

        final TextResource other = (TextResource) obj;
        return text.equals(other.text);
    }

    @Override
    public String toString() {
        return text;
    }

}
