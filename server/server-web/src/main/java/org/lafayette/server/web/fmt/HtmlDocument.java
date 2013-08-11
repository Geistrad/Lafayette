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

package org.lafayette.server.web.fmt;

/**
 * Formats a HTML document.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class HtmlDocument {

    private String title = "";
    private String body = "";

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public String format() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("<!DOCTYPE html>\n")
              .append("<html>\n")
              .append("    <head>\n")
              .append("        <title>").append(title).append("</title>\n")
              .append("    </head>\n")
              .append("    <body>\n")
              .append(body)
              .append("    </body>\n");
        buffer.append("</html>\n");
        return buffer.toString();
    }
}
