/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package org.lafayette.server.nonce;

import java.security.NoSuchAlgorithmException;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link Sha1}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Sha1Test {

    @Test @Ignore
    public void createDefault() throws NoSuchAlgorithmException {
        final Sha1 sut = new Sha1();
        assertThat(sut.getSalt(), is(BaseNonce.DEFAULT_SALT));
//        assertThat(sut.getDigest() instanceof Md5, is(true));
    }

    @Test @Ignore
    public void createWithSalt() throws NoSuchAlgorithmException {
        final Sha1 sut = new Sha1("foobar");
        assertThat(sut.getSalt(), is("foobar"));
//        assertThat(sut.getDigest() instanceof Md5, is(true));
    }

    @Test @Ignore
    public void createWithDigest() throws NoSuchAlgorithmException {
//        final Sha1 sut = new Sha1(Digest.Algorithm.SHA1);
//        assertThat(sut.getSalt(), is(BaseNonce.DEFAULT_SALT));
//        assertThat(sut.getDigest() instanceof de.weltraumschaf.maconha.core.security.digest.Sha1, is(true));
    }

}
