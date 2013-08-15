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

/**
 * Factory to create nonce generators.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class NonceFactory {

    /**
     * Format string for {2link NoSuchAlgorithmException exceptions}.
     */
    private static final String ERROR_FORMAT = "Algorithm %s not implemented!";

    /**
     * Hide constructor for pure static factory class.
     */
    private NonceFactory() {
        super();
    }

    /**
     * Creates a nonce generator based on the passed algorithm type.
     *
     * @param a Type of algorithm to use.
     * @return A new created instance.
     * @throws NoSuchAlgorithmException If the obtained algorithm is not provided by the runtime.
     */
    static Nonce createDigest(final Nonce.RandonAlgorithm a) throws NoSuchAlgorithmException {
        Nonce n;

        switch (a) {
            case SHA1_PRNG:
                n = new Sha1();
                break;
            case YARROW_PRNG:
                throw new NoSuchAlgorithmException(String.format(ERROR_FORMAT, Nonce.RandonAlgorithm.YARROW_PRNG));
            case NATIVE_PRNG:
                throw new NoSuchAlgorithmException(String.format(ERROR_FORMAT, Nonce.RandonAlgorithm.NATIVE_PRNG));
            default:
                throw new NoSuchAlgorithmException(String.format(ERROR_FORMAT, a));
        }

        return n;
    }

    /**
     * Creates a SHA-1 based nonce generator.
     *
     * @return A new created instance.
     * @throws NoSuchAlgorithmException If the SHA-1 algorithm is not provided by the runtime.
     */
    public static Nonce sha1() throws NoSuchAlgorithmException {
        return createDigest(Nonce.RandonAlgorithm.SHA1_PRNG);
    }

    /**
     * Creates a SHA-1 based nonce generator.
     *
     * @return A new created instance.
     * @throws NoSuchAlgorithmException If the SHA-1 algorithm is not provided by the runtime.
     */
    public static Nonce yarrow() throws NoSuchAlgorithmException {
        return createDigest(Nonce.RandonAlgorithm.YARROW_PRNG);
    }

    /**
     * Creates a native /dev/random based nonce generator.
     *
     * The name is deliberately misspelled because 'native' is a Java keyword.
     *
     * @return A new created instance.
     * @throws NoSuchAlgorithmException If the SHA-1 algorithm is not provided by the runtime.
     */
    public static Nonce nativ() throws NoSuchAlgorithmException {
        return createDigest(Nonce.RandonAlgorithm.NATIVE_PRNG);
    }

}
