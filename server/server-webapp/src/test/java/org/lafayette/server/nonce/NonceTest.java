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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for {@link Nonce}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NonceTest {

    @Test
    public void assertAlgorithmGetName() {
        assertThat(Nonce.RandonAlgorithm.NATIVE_PRNG.getName(), is("NativePRNG"));
        assertThat(Nonce.RandonAlgorithm.SHA1_PRNG.getName(), is("SHA1PRNG"));
        assertThat(Nonce.RandonAlgorithm.YARROW_PRNG.getName(), is("YarrowPRNG"));
    }

    @Test
    public void assertAlgorithmToString() {
        assertThat(Nonce.RandonAlgorithm.NATIVE_PRNG.toString(), is("NativePRNG"));
        assertThat(Nonce.RandonAlgorithm.SHA1_PRNG.toString(), is("SHA1PRNG"));
        assertThat(Nonce.RandonAlgorithm.YARROW_PRNG.toString(), is("YarrowPRNG"));
    }

}
