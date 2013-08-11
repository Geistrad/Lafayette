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
import java.net.URISyntaxException;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link UriList}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UriListTest {

    private final UriList sut = new UriList();

    @Test
    public void getMediaType() {
        assertThat(sut.getMediaType(), is(MediaType.TEXT_URI_LIST));
    }

    @Test
    public void testToStringWithComment() throws URISyntaxException {
        sut.add(new URI("/foo"));
        sut.add(new URI("/bar"));
        sut.setComment("snafu");
        assertThat(sut.toString(), is("# snafu\r\n/bar\r\n/foo\r\n"));
    }

    @Test
    public void testToStringWithoutComment() throws URISyntaxException {
        sut.add(new URI("/bar"));
        sut.add(new URI("/foo"));
        assertThat(sut.toString(), is("/bar\r\n/foo\r\n"));
    }

}
