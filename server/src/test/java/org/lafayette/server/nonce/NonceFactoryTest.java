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
import org.junit.Test;

/**
 * Tests for {@link NonceFactory}.
 * 
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NonceFactoryTest {

    @Test
    public void createSha() throws NoSuchAlgorithmException {
        assertThat(NonceFactory.sha1() instanceof Sha1, is(true));
    }

    @Test(expected=NoSuchAlgorithmException.class)
    public void createYarrow() throws NoSuchAlgorithmException {
        NonceFactory.yarrow();
    }

    @Test(expected=NoSuchAlgorithmException.class)
    public void createNative() throws NoSuchAlgorithmException {
        NonceFactory.nativ();
    }

}
