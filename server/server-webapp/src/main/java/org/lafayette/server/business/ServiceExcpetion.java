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
package org.lafayette.server.business;

/**
 * Runtime exceptions thrown by service layer.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ServiceExcpetion extends RuntimeException {

    /**
     * Dedicated constructor.
     *
     * @param message exception message
     */
    public ServiceExcpetion(String message) {
        super(message);
    }
}
