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

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.lafayette.server.domain.User;
import static org.hamcrest.Matchers.*;
import org.lafayette.server.domain.Role;

/**
 * Tests for {@link SecurityContextImpl}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SecurityContextImplTest {

    @Test
    public void isSecure_falseByDefault() {
        final SecurityContext sut = new SecurityContextImpl(new User());
        assertThat(sut.isSecure(), is(false));
    }

    @Test
    public void isSecure_falseByArgument() {
        final SecurityContext sut = new SecurityContextImpl(new User(), false);
        assertThat(sut.isSecure(), is(false));
    }

    @Test
    public void isSecure_trueByArgument() {
        final SecurityContext sut = new SecurityContextImpl(new User(), true);
        assertThat(sut.isSecure(), is(true));
    }

    @Test
    public void getUserPrincipal() {
        final User user = new User();
        final SecurityContext sut = new SecurityContextImpl(user);
        assertThat(sut.getUserPrincipal(), is(sameInstance((Principal) user)));
    }

    @Test
    public void getAuthenticationScheme() {
        final SecurityContext sut = new SecurityContextImpl(new User());
        assertThat(sut.getAuthenticationScheme(), is(equalTo("DIGEST")));
    }

    @Test
    public void isUserInRole_userHasNoRoles() {
        final SecurityContext sut = new SecurityContextImpl(new User());
        assertThat(sut.isUserInRole(Role.Names.ADMINISTRATOR.toString()), is(false));
        assertThat(sut.isUserInRole(Role.Names.USER.toString()), is(false));
        assertThat(sut.isUserInRole(Role.Names.ANONYMOUS.toString()), is(false));
    }

    @Test
    public void isUserInRole_userHasUserRole() {
        final User user = new User();
        user.addRole(new Role(1, Role.Names.USER));
        final SecurityContext sut = new SecurityContextImpl(user);
        assertThat(sut.isUserInRole(Role.Names.ADMINISTRATOR.toString()), is(false));
        assertThat(sut.isUserInRole(Role.Names.USER.toString()), is(true));
        assertThat(sut.isUserInRole(Role.Names.ANONYMOUS.toString()), is(false));
    }

    @Test
    public void isUserInRole_userHasUserAndAdministratorRoles() {
        final User user = new User();
        user.addRole(new Role(1, Role.Names.USER));
        user.addRole(new Role(1, Role.Names.ADMINISTRATOR));
        final SecurityContext sut = new SecurityContextImpl(user);
        assertThat(sut.isUserInRole(Role.Names.ADMINISTRATOR.toString()), is(true));
        assertThat(sut.isUserInRole(Role.Names.USER.toString()), is(true));
        assertThat(sut.isUserInRole(Role.Names.ANONYMOUS.toString()), is(false));
    }
}