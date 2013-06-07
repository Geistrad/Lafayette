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
 * Used environment variables.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum EnvVars {

    /**
     * Environment variable $STAGE.
     */
    STAGE,
    /**
     * Environment variable $HOME.
     */
    HOME;

    /**
     * Retrieves the environment variable from the system.
     *
     * @return always returns string, may be an empty string but never {@code null}
     */
    public String getFromSystem() {
        final String env = System.getenv(name());
        return null == env ? "" : env;
    }

}
