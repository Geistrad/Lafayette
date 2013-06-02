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

package org.lafayette.server.filter;

import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.core.HttpHeaders;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AuthenticationResourceFilters.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class AuthenticationResourceFiltersTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final ResourceFilterFactory sut = new AuthenticationResourceFilters();

    @Test
    public void create_forMethodWithAuthenticationAnnotation() {
        ((AuthenticationResourceFilters) sut).setHeaders(mock(HttpHeaders.class));
        ((AuthenticationResourceFilters) sut).setServlet(mock(ServletContext.class));
        //CHECKSTYLE:OFF
        final AbstractMethod method = mock(AbstractMethod.class);
        //CHECKSTYLE:ON
        when(method.isAnnotationPresent(Authentication.class)).thenReturn(Boolean.TRUE);
        final List<ResourceFilter> result = sut.create(method);
        assertThat(result, hasSize(1));
        assertThat(result.get(0), is(instanceOf(SecurityContextFilterDigest.class)));
    }

    @Test
    public void create_forMethodWithoutAuthenticationAnnotation() {
        final List<ResourceFilter> result = sut.create(mock(AbstractMethod.class));
        assertThat(result, hasSize(1));
        assertThat(result.get(0), is(instanceOf(SecurityContextFilterAnonymous.class)));
    }

    @Test
    public void setServlet_throwsExceptionIfNullPassedIn() {
        thrown.expect(NullPointerException.class);
        ((AuthenticationResourceFilters) sut).setServlet(null);
    }

    @Test
    public void setHeaders_throwsExceptionIfNullPassedIn() {
        thrown.expect(NullPointerException.class);
        ((AuthenticationResourceFilters) sut).setHeaders(null);
    }
}