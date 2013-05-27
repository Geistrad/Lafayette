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
 * Tests for {@link RequestParameters}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RequestParametersTest {

    //CHECKSTYLE:OFF
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final RequestParameters sut = new RequestParameters();

    @Test
    public void byDefaultAllPropertiesAreEmpty() {
        assertThat(sut.getHttpMethod(), is(equalTo("")));
        assertThat(sut.getNonce(), is(equalTo("")));
        assertThat(sut.getRealm(), is(equalTo("")));
        assertThat(sut.getRequestedUri(), is(equalTo("")));
        assertThat(sut.getUsername(), is(equalTo("")));
        assertThat(sut.getResponse(), is(equalTo("")));
    }

    @Test
    public void setResponse_throwsExcpetionIfPassedNullIn() {
        thrown.expect(NullPointerException.class);
        sut.setResponse(null);
    }

    @Test
    public void isValid_defultIsInvalid() {
        assertThat(AuthorizationHeaderParser.DEFAULT_DIGEST_PARAMAS.isValid(), is(false));
    }

    @Test
    public void isValid_trueIfAllParamsNotEmpty() {
        final RequestParameters sut = new RequestParameters("name", "realm", "nonce", "GET", "uri", "response");
        assertThat(sut.isValid(), is(true));
    }

    @Test
    public void isValid_falseIfAtLeastOneParamIsEmpty() {
        RequestParameters sut = new RequestParameters("", "realm", "nonce", "GET", "uri", "response");
        assertThat(sut.isValid(), is(false));
        sut = new RequestParameters("name", "", "nonce", "GET", "uri", "response");
        assertThat(sut.isValid(), is(false));
        sut = new RequestParameters("name", "realm", "", "GET", "uri", "response");
        assertThat(sut.isValid(), is(false));
        sut = new RequestParameters("name", "realm", "nonce", "", "uri", "response");
        assertThat(sut.isValid(), is(false));
        sut = new RequestParameters("name", "realm", "nonce", "GET", "", "response");
        assertThat(sut.isValid(), is(false));
        sut = new RequestParameters("name", "realm", "nonce", "GET", "uri", "");
        assertThat(sut.isValid(), is(false));
    }

    @Test
    public void testToString() {
        final RequestParameters sut = new RequestParameters("name", "realm", "nonce", "GET", "uri", "response");
        assertThat(sut.toString(), is(equalTo("RequestParameters{username=name, realm=realm, nonce=nonce, httpMethod=GET, "
                + "requestedUri=uri, response=response}")));
    }

    @Test
    @Ignore
    public void testHashCode() {
    }

    @Test
    @Ignore
    public void testEquals() {
    }
}