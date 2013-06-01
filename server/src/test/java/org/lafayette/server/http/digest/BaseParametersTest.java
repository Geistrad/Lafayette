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

package org.lafayette.server.http.digest;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link BaseParameters}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BaseParametersTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final BaseParameters sut = new BaseParametersStung();

    @Test
    public void byDefaultAllPropertiesAreEmpty() {
        assertThat(sut.getHttpMethod(), is(equalTo("")));
        assertThat(sut.getNonce(), is(equalTo("")));
        assertThat(sut.getRealm(), is(equalTo("")));
        assertThat(sut.getRequestedUri(), is(equalTo("")));
        assertThat(sut.getUsername(), is(equalTo("")));
    }

    @Test
    public void setHttpMethod_throwsExcpetionIfPassedNullIn() {
        thrown.expect(NullPointerException.class);
        sut.setHttpMethod(null);
    }

    @Test
    public void setNonce_throwsExcpetionIfPassedNullIn() {
        thrown.expect(NullPointerException.class);
        sut.setNonce(null);
    }

    @Test
    public void setRealm_throwsExcpetionIfPassedNullIn() {
        thrown.expect(NullPointerException.class);
        sut.setRealm(null);
    }

    @Test
    public void setRequestedUri_throwsExcpetionIfPassedNullIn() {
        thrown.expect(NullPointerException.class);
        sut.setRequestedUri(null);
    }

    @Test
    public void setUsername_throwsExcpetionIfPassedNullIn() {
        thrown.expect(NullPointerException.class);
        sut.setUsername(null);
    }

    @Test @Ignore
    public void testToString() {
    }

    @Test @Ignore
    public void testHashCode() {
    }

    @Test @Ignore
    public void testEquals() {
    }

    private final class BaseParametersStung extends BaseParameters {

    }
}
