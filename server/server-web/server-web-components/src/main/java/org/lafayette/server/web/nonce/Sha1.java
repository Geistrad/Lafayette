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
package org.lafayette.server.web.nonce;

import java.security.NoSuchAlgorithmException;

/**
 * SHA-1 based nonce generator.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Sha1 extends BaseNonce {

    /**
     * Use {@link BaseNonce#DEFAULT_SALT} and {@link BaseNonce#DEFAULT_DIGEST}.
     *
     * @throws NoSuchAlgorithmException On unsupported algorithm.
     */
    public Sha1() throws NoSuchAlgorithmException {
        this(DEFAULT_SALT, DEFAULT_DIGEST);
    }

    /**
     * Use {@link BaseNonce#DEFAULT_DIGEST}.
     *
     * @param salt salt Used hash salt.
     * @throws NoSuchAlgorithmException On unsupported algorithm.
     */
    public Sha1(final String salt) throws NoSuchAlgorithmException {
        this(salt, DEFAULT_DIGEST);
    }

    /**
     * Use {@link BaseNonce#DEFAULT_SALT}.
     *
     * @param digest Used hashing algorithm.
     * @throws NoSuchAlgorithmException On unsupported algorithm.
     */
    public Sha1(Nonce.DigestAlgorithm digest) throws NoSuchAlgorithmException {
        this(DEFAULT_SALT, digest);
    }

    /**
     * Dedicated constructor.
     *
     * @param salt Used hash salt.
     * @param digest Used hashing algorithm.
     * @throws NoSuchAlgorithmException On unsupported algorithm.
     */
    public Sha1(String salt, Nonce.DigestAlgorithm digest) throws NoSuchAlgorithmException {
        super(Nonce.RandonAlgorithm.SHA1_PRNG, salt, digest);
    }

}
