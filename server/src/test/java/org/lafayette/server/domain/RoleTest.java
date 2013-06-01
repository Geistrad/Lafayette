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
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.lafayette.server.domain.Role.Names;

/**
 * Tests for {@link Role}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RoleTest {

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void setUserId_throwsExceptionIfLesthanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("User id must not be leass than 0!");
        new Role().setUserId(-1);
    }

    @Test
    public void setName_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Name must not be null!");
        new Role().setName(null);
    }

    @Test
    public void defaultConstructor() {
        final Role r1 = new Role();
        assertThat(r1.getId(), is(equalTo(BaseDomainObject.UNINITIALIZED_ID)));
        assertThat(r1.getUserId(), is(equalTo(0)));
        assertThat(r1.getName(), is(equalTo(Names.ANONYMOUS)));
    }

    @Test
    public void testHashCode() {
        final Role r1 = new Role(1, 1, Names.USER);
        final Role r2 = new Role(1, 1, Names.USER);
        final Role r3 = new Role(2, 2, Names.USER);

        assertThat(r1.hashCode(), is(equalTo(r1.hashCode())));
        assertThat(r1.hashCode(), is(equalTo(r2.hashCode())));
        assertThat(r2.hashCode(), is(equalTo(r1.hashCode())));
        assertThat(r2.hashCode(), is(equalTo(r2.hashCode())));

        assertThat(r1.hashCode(), is(not(equalTo(r3.hashCode()))));
        assertThat(r2.hashCode(), is(not(equalTo(r3.hashCode()))));
    }

    @Test
    @SuppressWarnings("IncompatibleEquals")
    public void testEquals() {
        final Role r1 = new Role(1, 1, Names.USER);
        final Role r2 = new Role(1, 1, Names.USER);
        final Role r3 = new Role(2, 2, Names.USER);

        //CHECKSTYLE:OFF
        assertThat(r1.equals(null), is(equalTo(false)));
        assertThat(r1.equals("foobar"), is(equalTo(false)));
        //CHECKSTYLE:ON

        assertThat(r1.equals(r1), is(equalTo(true)));
        assertThat(r1.equals(r2), is(equalTo(true)));
        assertThat(r2.equals(r1), is(equalTo(true)));
        assertThat(r2.equals(r2), is(equalTo(true)));

        assertThat(r1.equals(r3), is(equalTo(false)));
        assertThat(r3.equals(r1), is(equalTo(false)));
        assertThat(r2.equals(r3), is(equalTo(false)));
        assertThat(r3.equals(r2), is(equalTo(false)));
    }

    @Test
    public void testToString() {
        assertThat(new Role(1, 2, Names.USER).toString(), is(equalTo("Role{id=1, userId=2, name=USER}")));
    }
}
