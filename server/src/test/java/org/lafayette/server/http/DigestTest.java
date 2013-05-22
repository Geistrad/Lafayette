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

import java.net.URI;
import java.net.URISyntaxException;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;

/**
 * Tests for {@link Digest}.
 * 
 * Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DigestTest {
    
    @Test
    public void testDigestUserData() {
        assertThat(Digest.digestUserData("foo", "bar", "baz"), 
                   is("b1925214b7e20b0fc95cae9f1d6de985"));
    }

    @Test
    public void testDigestRequestData() throws URISyntaxException {
        assertThat(Digest.digestRequestData(Digest.HttpMethod.GET, new URI("/foo")), 
                   is("435c145e5ba6c34b4ef332e7bab538d1"));
    }

    @Test @Ignore
    public void testDigest_3args() {
    }

    @Test @Ignore
    public void testDigest_DigestValues() {
    }
}