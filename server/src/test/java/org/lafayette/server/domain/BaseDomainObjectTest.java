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

    @Rule public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwExceptionOnResettingId() {
        final BaseDomainObject sut = new BaseDomainObjectStub();
        assertThat(sut.getId(), is(BaseDomainObject.UNINITIALIZED_ID));
        sut.setId(23);
        assertThat(sut.getId(), is(23));
        thrown.expect(IllegalStateException.class);
        sut.setId(42);
    }

    private static final class BaseDomainObjectStub extends BaseDomainObject {

    }
}