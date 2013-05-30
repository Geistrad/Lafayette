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

package org.lafayette.server.domain;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link User}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UserTest {

    @Test
    public void defaultConstructor() {
        final User u1 = new User();
        assertThat(u1.getId(), is(equalTo(BaseDomainObject.UNINITIALIZED_ID)));
    }

    @Test
    public void testHashCode() {
        final User u1 = new User(1, "foo", "bar");
        final User u2 = new User(1, "foo", "bar");
        final User u3 = new User(2, "snafu", "bar");

        assertThat(u1.hashCode(), is(equalTo(u1.hashCode())));
        assertThat(u1.hashCode(), is(equalTo(u2.hashCode())));
        assertThat(u2.hashCode(), is(equalTo(u1.hashCode())));
        assertThat(u2.hashCode(), is(equalTo(u2.hashCode())));

        assertThat(u1.hashCode(), is(not(equalTo(u3.hashCode()))));
        assertThat(u2.hashCode(), is(not(equalTo(u3.hashCode()))));

        final Role r1 = new Role(1, "foo", "bar");
        u1.addRole(r1);
        assertThat(u1.hashCode(), is(not(equalTo(u2.hashCode()))));
        u2.addRole(r1);
        assertThat(u1.hashCode(), is(equalTo(u2.hashCode())));
    }

    @Test
    @SuppressWarnings("IncompatibleEquals")
    public void testEquals() {
        final User u1 = new User(1, "foo", "bar");
        final User u2 = new User(1, "foo", "bar");
        final User u3 = new User(2, "snafu", "bar");

        //CHECKSTYLE:OFF
        assertThat(u1.equals(null), is(equalTo(false)));
        assertThat(u1.equals(""), is(equalTo(false)));
        //CHECKSTYLE:ON

        assertThat(u1.equals(u1), is(equalTo(true)));
        assertThat(u1.equals(u2), is(equalTo(true)));
        assertThat(u2.equals(u1), is(equalTo(true)));
        assertThat(u2.equals(u2), is(equalTo(true)));

        assertThat(u1.equals(u3), is(equalTo(false)));
        assertThat(u2.equals(u3), is(equalTo(false)));

        final Role r1 = new Role(1, "foo", "bar");
        u1.addRole(r1);
        assertThat(u1.equals(u2), is(equalTo(false)));
        u2.addRole(r1);
        assertThat(u1.equals(u2), is(equalTo(true)));
    }

    @Test
    public void testToString() {
        final User u1 = new User(1, "foo", "bar");
        assertThat(u1.toString(), is(equalTo("User{id=1, loginName=foo, hashedUserData=bar, roles=[]}")));

        final Role r1 = new Role(1, "foo", "bar");
        u1.addRole(r1);
        assertThat(u1.toString(), is(equalTo("User{id=1, loginName=foo, hashedUserData=bar, roles=["
                + "Role{id=1, name=foo, description=bar}"
                + "]}")));

        final Role r2 = new Role(2, "baz", "bar");
        u1.addRole(r2);
        assertThat(u1.toString(), is(equalTo("User{id=1, loginName=foo, hashedUserData=bar, roles=["
                + "Role{id=2, name=baz, description=bar}, "
                + "Role{id=1, name=foo, description=bar}"
                + "]}")));
    }

    @Test
    public void addGetAndRemoveRoles() {
        final User u1 = new User(1, "foo", "bar");
        assertThat(u1.getRoles(), hasSize(0));

        final Role r1 = new Role(1, "foo", "bar");
        assertThat(u1.hasRole(r1), is(equalTo(false)));
        u1.addRole(r1);
        assertThat(u1.hasRole(r1), is(equalTo(true)));
        assertThat(u1.getRoles(), hasSize(1));
        assertThat(u1.getRoles(), contains(r1));

        final Role r2 = new Role(2, "baz", "bar");
        assertThat(u1.hasRole(r2), is(equalTo(false)));
        u1.addRole(r2);
        assertThat(u1.hasRole(r1), is(equalTo(true)));
        assertThat(u1.hasRole(r2), is(equalTo(true)));
        assertThat(u1.getRoles(), hasSize(2));
        assertThat(u1.getRoles(), containsInAnyOrder(r1, r2));

        u1.removeRole(r1);
        assertThat(u1.hasRole(r1), is(equalTo(false)));
        assertThat(u1.getRoles(), hasSize(1));
        assertThat(u1.getRoles(), contains(r2));
    }

}
