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

package org.lafayette.server;

/**
 * Generic application runtime exception.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DomainModelException extends RuntimeException {

    /**
     * Initializes previous causing exception but empty message.
     *
     * @param cause previous exception
     */
    public DomainModelException(final Throwable cause) {
        this("", cause);
    }

    /**
     * Initializes exception with message.
     *
     * @param message error message
     */
    public DomainModelException(final String message) {
        this(message, null);
    }

    /**
     * Dedicated constructor.
     *
     * @param message error message
     * @param cause previous exception.
     */
    public DomainModelException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
