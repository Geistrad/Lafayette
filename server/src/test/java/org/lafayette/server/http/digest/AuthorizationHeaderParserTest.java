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
import org.lafayette.server.http.digest.AuthorizationHeaderParser.DigestParams;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link AuthorizationHeaderParser}.
 *
 * TODO Move into ResponseParameters
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class AuthorizationHeaderParserTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void parseDigestHeaderValue_returnDefualtIfNotDigest() {
        final String header = "Foobar username=\"Foo\", realm=\"Private Area\", nonce=«\"IrTfjizEdXmIdlwHwkDJx0\", "
                + "uri=\"/\", response=\"$RESPONSE\"";
        final DigestParams params = AuthorizationHeaderParser.parseDigestHeaderValue(header);
        assertThat(params.getUsername(), is(equalTo("")));
        assertThat(params.getRealm(), is(equalTo("")));
        assertThat(params.getNonce(), is(equalTo("")));
        assertThat(params.getRequestedUri(), is(equalTo("")));
        assertThat(params.getResponse(), is(equalTo("")));
    }

    @Test
    public void parseDigestHeaderValue() {
        final String header = "Digest username=\"Foo\", realm=\"Private Area\", nonce=\"IrTfjizEdXmIdlwHwkDJx0\", "
                + "uri=\"/\", response=\"$RESPONSE\"";
        final DigestParams params = AuthorizationHeaderParser.parseDigestHeaderValue(header);
        assertThat(params.getUsername(), is(equalTo("Foo")));
        assertThat(params.getRealm(), is(equalTo("Private Area")));
        assertThat(params.getNonce(), is(equalTo("IrTfjizEdXmIdlwHwkDJx0")));
        assertThat(params.getRequestedUri(), is(equalTo("/")));
        assertThat(params.getResponse(), is(equalTo("$RESPONSE")));
    }

    @Test
    public void parseDigestHeaderValue_throwsExcpetionIfNull() {
        thrown.expect(NullPointerException.class);
        AuthorizationHeaderParser.parseDigestHeaderValue(null);
    }

    @Test
    public void parseDigestHeaderValue_returnDefualtIfEmpty() {
        final DigestParams params = AuthorizationHeaderParser.parseDigestHeaderValue("");
        assertThat(params.getUsername(), is(equalTo("")));
        assertThat(params.getRealm(), is(equalTo("")));
        assertThat(params.getNonce(), is(equalTo("")));
        assertThat(params.getRequestedUri(), is(equalTo("")));
        assertThat(params.getResponse(), is(equalTo("")));
    }

}