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
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TextTable<T> extends BaseTable<T> {
    private static final int PAD = 4;

    @Override
    public String format(Collection<T> data) {
        // TODO Split in methods.
        Collection<String> names = null;
        int[] maxColumnLengths = new int[]{};

        for (final T datum : data) {
            if (null == names) {
                names = findNames(datum);
                maxColumnLengths = new int[names.size()];
                int i = 0;

                for (final String name : names) {
                    maxColumnLengths[i] = name.length();
                    ++i;
                }
            }

            final Map<String, Object> properties = findProperties(datum);
            int i = 0;

            for (final String name : names) {
                final String value = String.format("%s", properties.get(name));

                if (value.length() > maxColumnLengths[i]) {
                    maxColumnLengths[i] = value.length();
                }

                ++i;
            }
        }

        final StringBuilder buffer = new StringBuilder();

        {
            int i = 0;

            for (final String name : names) {
                buffer.append(StringUtils.rightPad(name, maxColumnLengths[i] + PAD));
                ++i;
            }
        }

        final int length = buffer.length();
        buffer.append('\n');

        for (int i = 0; i < length; ++i) {
            buffer.append('-');
        }

        buffer.append('\n');

        for (final T datum : data) {
            final Map<String, Object> properties = findProperties(datum);
            int i = 0;

            for (final String name : names) {
                final String value = String.format("%s", properties.get(name));
                buffer.append(StringUtils.rightPad(value, maxColumnLengths[i] + PAD));
                ++i;
            }

            buffer.append('\n');
        }

        return buffer.toString();
    }
}
