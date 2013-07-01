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

import com.google.common.collect.Lists;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Formats a collection of data objects into a HTML table.
 *
 * Readable bean properties are used to generate the cells.
 *
 * @param <T> type of formatted object
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class HtmlTable<T> implements Table<T> {
    /**
     * Opening row tag.
     */
    private static final String TAG_TR_OPEN = "<tr>";
    /**
     * Closing row tag.
     */
    private static final String TAG_TR_CLOSE = "</tr>";
    /**
     * Opening data tag.
     */
    private static final String TAG_TD_OPEN = "<td>";
    /**
     * Closing data tag.
     */
    private static final String TAG_TD_CLOSE = "</td>";
    /**
     * Opening table head tag.
     */
    private static final String TAG_THEAD_OPEN = "<thead>";
    /**
     * Closing table head tag.
     */
    private static final String TAG_THEAD_CLOSE = "</thead>";
    /**
     * Empty table tag.
     */
    private static final String TAG_TABLE_EMPTY = "<table/>";
    /**
     * Opening table tag.
     */
    private static final String TAG_TABLE_OPEN = "<table>";
    /**
     * Closing table tag.
     */
    private static final String TAG_TABLE_CLOSE = "</table>";
    /**
     * Opening table body tag.
     */
    private static final String TAG_TBODY_OPEN = "<tbody>";
    /**
     * Closing table body tag.
     */
    private static final String TAG_TBODY_CLOSE = "</tbody>";

    private final int baseIndentation;

    public HtmlTable() {
        this(0);
    }

    public HtmlTable(final int baseIndentation) {
        super();
        this.baseIndentation = baseIndentation;
    }

    @Override
    public String format(final Collection<T> data) {
        if (null == data || data.isEmpty()) {
            return TAG_TABLE_EMPTY;
        }

        final Collection<String> lines = Lists.newArrayList();
        lines.add(TAG_TABLE_OPEN);
        final Collection<String> names = findNames(data.iterator().next());
        lines.addAll(formatHead(names));
        lines.addAll(formatData(data, names));
        lines.add(TAG_TABLE_CLOSE);
        return prettyPrint(lines);
    }

    private Map<String, Object> findProperties(final T object) {
        try {
            final Map<String, Object> properties = BeanUtils.describe(object);
            properties.remove("class");
            return properties;
        } catch (IllegalAccessException ex) {
            return Collections.emptyMap();
        } catch (InvocationTargetException ex) {
            return Collections.emptyMap();
        } catch (NoSuchMethodException ex) {
            return Collections.emptyMap();
        }
    }

    Collection<String> findNames(final T object) {
        return Lists.newArrayList(findProperties(object).keySet());
    }

    Collection<String> formatHead(final Collection<String> names) {
        final Collection<String> lines = Lists.newArrayList();
        lines.add(TAG_THEAD_OPEN);
        lines.add(TAG_TR_OPEN);

        for (final String name : names) {
            lines.add(String.format("%s%s%s", TAG_TD_OPEN, name, TAG_TD_CLOSE));
        }

        lines.add(TAG_TR_CLOSE);
        lines.add(TAG_THEAD_CLOSE);
        return lines;
    }

    Collection<String> formatData(final Collection<T> data, final Collection<String> names) {
        if (null == data || data.isEmpty()) {
            return Collections.emptyList();
        }

        if (null == names || names.isEmpty()) {
            return Collections.emptyList();
        }

        final Collection<String> lines = Lists.newArrayList();
        lines.add(TAG_TBODY_OPEN);

        for (final T datum : data) {
            lines.addAll(formatDatum(datum, names));
        }

        lines.add(TAG_TBODY_CLOSE);
        return lines;
    }

    Collection<String> formatDatum(final T datum, final Collection<String> names) {
        if (null == datum) {
            return Collections.emptyList();
        }

        if (null == names || names.isEmpty()) {
            return Collections.emptyList();
        }

        final Collection<String> lines = Lists.newArrayList();
        final Map<String, Object> properties = findProperties(datum);
        lines.add(TAG_TR_OPEN);

        for (final String name : names) {
            lines.add(String.format("%s%s%s", TAG_TD_OPEN, properties.get(name), TAG_TD_CLOSE));
        }

        lines.add(TAG_TR_CLOSE);
        return lines;
    }

    private String prettyPrint(final Collection<String> lines) {
        final StringBuilder buffer = new StringBuilder();
        int indent = 0;

        for (final String line : lines) {
            if (baseIndentation > 0) {
                buffer.append(indention(baseIndentation));
            }

            if (TAG_TABLE_CLOSE.equals(line) || TAG_THEAD_CLOSE.equals(line)
                || TAG_TBODY_CLOSE.equals(line) || TAG_TR_CLOSE.equals(line)) {
                --indent;
            }

            buffer.append(indention(indent)).append(line).append('\n');

            if (TAG_TABLE_OPEN.equals(line) || TAG_THEAD_OPEN.equals(line)
                || TAG_TBODY_OPEN.equals(line) || TAG_TR_OPEN.equals(line)) {
                ++indent;
            }

        }

        return buffer.toString();
    }

    private String indention(final int count) {
        final StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            buffer.append("    ");
        }
        return buffer.toString();
    }
}
