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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BaseDomainObjectTest {

    //CHECKSTYLE:OFF
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final BaseDomainObject sut = new BaseDomainObjectStub();

    @Test
    public void throwExceptionOnResettingId() {
        assertThat(sut.getId(), is(BaseDomainObject.UNINITIALIZED_ID));
        sut.setId(23);
        assertThat(sut.getId(), is(23));
        thrown.expect(IllegalStateException.class);
        sut.setId(42);
    }

    @Test
    public void throwExceptionIfIdIsZero() {
        thrown.expect(IllegalArgumentException.class);
        sut.setId(0);
    }

    @Test
    public void throwExceptionIfIdIsLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        sut.setId(-3);
    }

    @Test
    public void isIdInitialized() {
        assertThat(sut.isIdInitialized(), is(equalTo(false)));
        sut.setId(42);
        assertThat(sut.isIdInitialized(), is(equalTo(true)));
    }

    @Test
    public void testToString() {
        sut.setId(42);
        assertThat(sut.toString(), is(equalTo("id=42")));
    }

    @Test
    public void testHashCode() {
        final BaseDomainObject sut1 = new BaseDomainObjectStub();
        sut1.setId(42);
        final BaseDomainObject sut2 = new BaseDomainObjectStub();
        sut2.setId(42);
        final BaseDomainObject sut3 = new BaseDomainObjectStub();
        sut3.setId(23);

        assertThat(sut1.hashCode(), is(equalTo(sut1.hashCode())));
        assertThat(sut1.hashCode(), is(equalTo(sut2.hashCode())));
        assertThat(sut2.hashCode(), is(equalTo(sut1.hashCode())));
        assertThat(sut2.hashCode(), is(equalTo(sut2.hashCode())));

        assertThat(sut1.hashCode(), is(not(equalTo(sut3.hashCode()))));
        assertThat(sut2.hashCode(), is(not(equalTo(sut3.hashCode()))));
    }

    @Test
    @SuppressWarnings("IncompatibleEquals")
    public void testEquals() {
        final BaseDomainObject sut1 = new BaseDomainObjectStub();
        sut1.setId(42);
        final BaseDomainObject sut2 = new BaseDomainObjectStub();
        sut2.setId(42);
        final BaseDomainObject sut3 = new BaseDomainObjectStub();
        sut3.setId(23);

        //CHECKSTYLE:OFF
        assertThat(sut1.equals(null), is(equalTo(false)));
        assertThat(sut1.equals(""), is(equalTo(false)));
        //CHECKSTYLE:ON

        assertThat(sut1.equals(sut1), is(equalTo(true)));
        assertThat(sut1.equals(sut2), is(equalTo(true)));
        assertThat(sut2.equals(sut1), is(equalTo(true)));
        assertThat(sut2.equals(sut2), is(equalTo(true)));

        assertThat(sut1.equals(sut3), is(equalTo(false)));
        assertThat(sut2.equals(sut3), is(equalTo(false)));
    }

    private static final class BaseDomainObjectStub extends BaseDomainObject {
    }
}