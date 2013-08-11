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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for {@link HtmlTable}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class HtmlTableTest {

    private final HtmlTable<Data> sut = new HtmlTable<Data>();

    @Test
    public void findNames() {
        assertThat(sut.findNames(new Data("foo1", "bar1", "baz1")),
                containsInAnyOrder("foo", "bar", "baz"));
    }

    @Test
    public void formatHead() {
        assertThat(sut.formatHead(Arrays.asList("foo", "bar", "baz")),
                contains(
                "<thead>",
                "<tr>",
                "<td>foo</td>",
                "<td>bar</td>",
                "<td>baz</td>",
                "</tr>",
                "</thead>"));
    }

    @Test
    public void formatList_emptyOrNullList() {
        assertThat(sut.format(Collections.<Data>emptyList()), is(equalTo("<table/>")));
        assertThat(sut.format(null), is(equalTo("<table/>")));
    }

    @Test
    public void formatDatum() {
        assertThat(
                sut.formatDatum(
                new Data("one", "two", "three"),
                Arrays.asList("foo", "bar", "baz")),
                contains(
                "<tr>",
                "<td>one</td>",
                "<td>two</td>",
                "<td>three</td>",
                "</tr>"));
    }

    @Test
    public void formatDatum_nullData() {
        assertThat(
                sut.formatDatum(
                null,
                Arrays.asList("foo", "bar", "baz")),
                hasSize(0));
    }

    @Test
    public void formatDatum_emptyOrNullNames() {
        assertThat(
                sut.formatDatum(
                new Data("one", "two", "three"),
                Collections.<String>emptyList()),
                hasSize(0));
        assertThat(
                sut.formatDatum(
                new Data("one", "two", "three"),
                null),
                hasSize(0));
    }

    @Test
    public void formatData() {
    }

    @Test
    public void formatData_nullOrEmptyData() {
        assertThat(
                sut.formatData(null, Arrays.asList("foo", "bar", "baz")),
                hasSize(0));
        assertThat(
                sut.formatData(Collections.<Data>emptyList(), Arrays.asList("foo", "bar", "baz")),
                hasSize(0));
    }

    @Test
    public void formatData_emptyOrNullNames() {
        assertThat(
                sut.formatData(
                Arrays.asList(new Data("one", "two", "three"), new Data("four", "five", "six")),
                Arrays.asList("foo", "bar", "baz")),
                contains(
                "<tbody>",
                "<tr>",
                "<td>one</td>",
                "<td>two</td>",
                "<td>three</td>",
                "</tr>",
                "<tr>",
                "<td>four</td>",
                "<td>five</td>",
                "<td>six</td>",
                "</tr>",
                "</tbody>"));
    }

    @Test
    public void format_nullData() {
        assertThat(sut.format(null), is(equalTo("<table/>")));
    }

    @Test
    public void format_emptyData() {
        assertThat(sut.format(Collections.<Data>emptyList()), is(equalTo("<table/>")));
    }

    @Test
    public void format() {
        final Collection<Data> data = Lists.newArrayList();
        data.add(new Data("foo1", "bar1", "baz1"));
        data.add(new Data("foo2", "bar2", "baz2"));
        data.add(new Data("foo3", "bar3", "baz3"));
        assertThat(sut.format(data), is(equalTo("<table>\n"
                + "    <thead>\n"
                + "        <tr>\n"
                + "            <td>baz</td>\n"
                + "            <td>foo</td>\n"
                + "            <td>bar</td>\n"
                + "        </tr>\n"
                + "    </thead>\n"
                + "    <tbody>\n"
                + "        <tr>\n"
                + "            <td>baz1</td>\n"
                + "            <td>foo1</td>\n"
                + "            <td>bar1</td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td>baz2</td>\n"
                + "            <td>foo2</td>\n"
                + "            <td>bar2</td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td>baz3</td>\n"
                + "            <td>foo3</td>\n"
                + "            <td>bar3</td>\n"
                + "        </tr>\n"
                + "    </tbody>\n"
                + "</table>\n")));
    }

    public static final class Data {

        private String foo;
        private String bar;
        private String baz;

        public Data(final String foo, final String bar, final String baz) {
            super();
            this.foo = foo;
            this.bar = bar;
            this.baz = baz;
        }

        public String getFoo() {
            return this.foo;
        }

        public String getBar() {
            return this.bar;
        }

        public String getBaz() {
            return this.baz;
        }
    }
}
