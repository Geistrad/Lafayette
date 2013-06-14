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

package org.lafayette.server.web;

import javax.servlet.GenericServlet;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class InitialServletParametrsTest {
    private static final String FOO_PARAM = "foo param";
    private static final String BAR_PARAM = "bar param";
    private static final String BAZ_PARAM = "baz param";
    private static final String REALM = "This is the realm.";

    private InitialServletParametrs sut;

    @Before
    public void createSut() {
        final GenericServlet servlet = mock(GenericServlet.class);
        when(servlet.getInitParameter("foo")).thenReturn(FOO_PARAM);
        when(servlet.getInitParameter("bar")).thenReturn(BAR_PARAM);
        when(servlet.getInitParameter("baz")).thenReturn(BAZ_PARAM);
        when(servlet.getInitParameter(InitialServletParametrs.PARMA_NAME_REALM)).thenReturn(REALM);
        when(servlet.getInitParameter("notexists")).thenReturn(null);
        sut = new InitialServletParametrs(servlet);
    }

    @Test
    public void testGetRealm() {
        assertThat(sut.getRealm(), is(REALM));
    }

    @Test
    public void testGetInitParameter() {
        assertThat(sut.getInitParameter("foo"), is(FOO_PARAM));
        assertThat(sut.getInitParameter("bar"), is(BAR_PARAM));
        assertThat(sut.getInitParameter("baz"), is(BAZ_PARAM));
        assertThat(sut.getInitParameter("notexists"), is(""));
    }

}