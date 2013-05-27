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

package org.lafayette.server.http;

import org.junit.Ignore;
import org.junit.Test;
import org.lafayette.server.http.AuthorizationHeaderParser.DigestParams;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link AuthorizationHeaderParser.DigestParams}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DigestParamsTest {

    @Test
    public void testToString() {
        final DigestParams sut = new DigestParams("name", "realm", "nonce", "GET", "uri", "response");
        assertThat(sut.toString(), is(equalTo("DigestParams{username=name, realm=realm, nonce=nonce, httpMethod=GET, "
                + "requestedUri=uri, response=response}")));
    }

    @Test @Ignore
    public void testHashCide() {

    }

    @Test @Ignore
    public void testEquals() {

    }

    @Test
    public void isValid_defultIsInvalid() {
        assertThat(AuthorizationHeaderParser.DEFAULT_DIGEST_PARAMAS.isValid(), is(false));
    }

    @Test
    public void isValid_trueIfAllParamsNotEmpty() {
        final DigestParams sut = new DigestParams("name", "realm", "nonce", "GET", "uri", "response");
        assertThat(sut.isValid(), is(true));
    }

    @Test
    public void isValid_falseIfAtLeastOneParamIsEmpty() {
        DigestParams sut = new DigestParams("", "realm", "nonce", "GET", "uri", "response");
        assertThat(sut.isValid(), is(false));
        sut = new DigestParams("name", "", "nonce", "GET", "uri", "response");
        assertThat(sut.isValid(), is(false));
        sut = new DigestParams("name", "realm", "", "GET", "uri", "response");
        assertThat(sut.isValid(), is(false));
        sut = new DigestParams("name", "realm", "nonce", "", "uri", "response");
        assertThat(sut.isValid(), is(false));
        sut = new DigestParams("name", "realm", "nonce", "GET", "", "response");
        assertThat(sut.isValid(), is(false));
        sut = new DigestParams("name", "realm", "nonce", "GET", "uri", "");
        assertThat(sut.isValid(), is(false));
    }
}
