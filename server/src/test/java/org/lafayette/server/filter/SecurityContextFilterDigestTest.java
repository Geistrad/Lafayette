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

import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.WebApplication;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.HttpHeaders;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.lafayette.server.Registry;
import org.lafayette.server.ServerContextListener;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link SecurityContextFilterDigest}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SecurityContextFilterDigestTest {

    private final ServletContext sc = mock(ServletContext.class);
    private final HttpHeaders headers = mock(HttpHeaders.class);

    @Test
    public void getRequestFilter() {
        final SecurityContextFilterDigest sut = new SecurityContextFilterDigest(sc, headers);
        assertThat(sut, is(instanceOf(ContainerRequestFilter.class)));
        assertThat(sut.getRequestFilter(), is(sameInstance((ContainerRequestFilter) sut)));
    }

    @Test
    public void getResponseFilter() {
        final SecurityContextFilterDigest sut = new SecurityContextFilterDigest(sc, headers);
        assertThat(sut, is(instanceOf(ContainerResponseFilter.class)));
        assertThat(sut.getResponseFilter(), is(sameInstance((ContainerResponseFilter) sut)));
    }

    @Test
    public void registry() throws NoSuchAlgorithmException {
        final Registry registry = new Registry();
        when(sc.getAttribute(ServerContextListener.REGISRTY)).thenReturn(registry);
        final SecurityContextFilterDigest sut = new SecurityContextFilterDigest(sc, headers);
        assertThat(sut.registry(), is(sameInstance(registry)));
    }

    @Test
    public void filterResponse_alwaysReturnSameInstanceAsPassedIn() {
        final ContainerRequest req = new ContainerRequest(
                mock(WebApplication.class),
                null, null, null,
                new InBoundHeaders(), null);
        final ContainerResponse res = new ContainerResponse(mock(WebApplication.class), req, null);
        final SecurityContextFilterDigest sut = new SecurityContextFilterDigest(sc, headers);
        assertThat(sut.filter(req, res), is(sameInstance(res)));
    }

    @Test
    @Ignore
    public void filterRequest_throwsUnauthorizedExceptionIfHeaderEmpty() {
    }

    @Test
    @Ignore
    public void filterRequest_throwsForbiddenExceptionIfAuthenticationNotValid() {
    }

    @Test
    @Ignore
    public void filterRequest_returnRequestWithSecurityContextIfValidAuthentication() {

    }
}
