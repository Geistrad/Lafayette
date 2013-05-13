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

import org.apache.log4j.Logger;

/**
 * Get Log4J loggers.
 *
 * Usage:
 * <pre>
 * private final Logger log = Log.getLogger(this);
 * //...
 * log.trace("Trace");
 * log.debug("Debug");
 * log.info("Info");
 * log.warn("Warn");
 * log.error("Error");
 * log.fatal("Fatal");
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Log {

    private Log() {
        super();
    }

    public static Logger getLogger(final Object object) {
        return getLogger(object.getClass());
    }

    public static Logger getLogger(final Class<?> clazz) {
        return Logger.getLogger(clazz);
    }

}
