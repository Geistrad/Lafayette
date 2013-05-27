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

/**
 * Implementors of this interface generated random cryptographic strength NONCE strings.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Nonce {

    /**
     * Return the next nonce.
     *
     * This method guarantees that a nonce is never returned twice.
     *
     * @return Nonce string.
     */
    String getNext();

    /**
     * Calculates a random nonce.
     *
     * This method may return an already calculated nonce twice.
     *
     * @return Nonce string.
     */
    String calculateNonce();

    /**
     * SUpported algorithms.
     */
    public enum RandonAlgorithm {

        /**
         * SHA-1 based Pseudo Random Number Generator.
         *
         * See http://en.wikipedia.org/wiki/SHA-1
         */
        SHA1_PRNG ("SHA1PRNG"),

        /**
         * Yarrow based Pseudo Random Number Generator.
         *
         * See http://www.schneier.com/yarrow.html
         */
        YARROW_PRNG ("YarrowPRNG"),

        /**
         * Native is /dev/random or /dev/urandom based Pseudo Random Number Generator.
         *
         * Only available on systems providing these files.
         */
        NATIVE_PRNG ("NativePRNG");

        /**
         * Name of algorithm.
         */
        private final String name;

        /**
         * Default constructor.
         *
         * @param name Name of algorithm.
         */
        private RandonAlgorithm(final String name) {
            this.name = name;
        }

        /**
         * Getter for name of algorithm.
         *
         * @return Name of algorithm.
         */
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    public enum DigestAlgorithm {
        MD5, SHA1;
    }

}
