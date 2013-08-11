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

import java.util.Collection;

/**
 * Formats a collection of data objects into a Markdown table.
 *
 * Readable bean properties are used to generate the cells.
 *
 * <pre>
 * |Left align|Right align|Center align|
 * |:---------|:----------|:----------|
 * |This|This|This|
 * |column|column|column|
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MarkdownTable<T> implements Table<T> {

    @Override
    public String format(Collection<T> data) {
        return "";
    }

}
