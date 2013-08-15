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
import java.util.Collection;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for {@link TextTable}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TextTableTest {

    private final TextTable<Data> sut = new TextTable<Data>();

    @Test
    public void format() {
        final Collection<Data> data = Lists.newArrayList();
        data.add(new Data("foo1", "bar1", "baz1"));
        data.add(new Data("foo2", "bar2", "baz2"));
        data.add(new Data("foo3", "bar3", "baz3"));
        assertThat(sut.format(data), is(equalTo(
                  "baz     foo     bar     \n"
                + "------------------------\n"
                + "baz1    foo1    bar1    \n"
                + "baz2    foo2    bar2    \n"
                + "baz3    foo3    bar3    \n")));
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
