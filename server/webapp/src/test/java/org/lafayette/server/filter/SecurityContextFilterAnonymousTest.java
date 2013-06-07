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
import java.security.Principal;
import javax.ws.rs.core.SecurityContext;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.lafayette.server.domain.Role;
import org.lafayette.server.domain.User;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link SecurityContextFilterAnonymous}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SecurityContextFilterAnonymousTest {

    private final SecuirityContextFilter sut = new SecurityContextFilterAnonymous();

    @Test
    public void getRequestFilter() {
        assertThat(sut, is(instanceOf(ContainerRequestFilter.class)));
        assertThat(sut.getRequestFilter(), is(sameInstance((ContainerRequestFilter) sut)));
    }

    @Test
    public void getResponseFilter() {
        assertThat(sut, is(instanceOf(ContainerResponseFilter.class)));
        assertThat(sut.getResponseFilter(), is(sameInstance((ContainerResponseFilter) sut)));
    }

    @Test
    public void filterRequest_alwaysInjectAnonymousUser() {
        final ContainerRequest req = new ContainerRequest(
                mock(WebApplication.class),
                null, null, null,
                new InBoundHeaders(), null);
        assertThat(sut.filter(req), is(sameInstance(req)));
        final SecurityContext securityContext = req.getSecurityContext();
        assertThat(securityContext.isSecure(), is(false));
        assertThat(securityContext.getAuthenticationScheme(), is(equalTo(SecurityContext.DIGEST_AUTH)));
        assertThat(securityContext.isUserInRole(Role.Names.ADMINISTRATOR.toString()), is(false));
        assertThat(securityContext.isUserInRole(Role.Names.USER.toString()), is(false));
        assertThat(securityContext.isUserInRole(Role.Names.ANONYMOUS.toString()), is(true));
        assertThat(securityContext.getUserPrincipal(), is(sameInstance((Principal) User.ANONYMOUS)));
    }

    @Test
    public void filterResponse_alwaysReturnSameInstanceAsPassedIn() {
        final ContainerRequest req = new ContainerRequest(
                mock(WebApplication.class),
                null, null, null,
                new InBoundHeaders(), null);
        final ContainerResponse res = new ContainerResponse(mock(WebApplication.class), req, null);
        assertThat(sut.filter(req, res), is(sameInstance(res)));
    }
}
