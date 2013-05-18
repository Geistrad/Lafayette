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
package org.lafayette.server.log;

import java.util.Formatter;
import org.apache.log4j.Level;

/**
 * Decorator for {@link org.apache.log4j.Logger Log4J logger}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Logger {

    private final org.apache.log4j.Logger delegate;

    public Logger(final org.apache.log4j.Logger delegate) {
        super();
        this.delegate = delegate;
    }

    public void setLevel(Level level) {
        delegate.setLevel(level);
    }

    public final Level getLevel() {
        return delegate.getLevel();
    }

    public void debug(final String format, final Object... args) {
        debug(new Formatter().format(format, args).toString());
    }

    public void debug(Object message) {
        delegate.debug(message);
    }

    public void debug(Object message, Throwable t) {
        delegate.debug(message, t);
    }

    public void trace(final String format, final Object... args) {
        trace(new Formatter().format(format, args).toString());
    }

    public void trace(Object message) {
        delegate.trace(message);
    }

    public void trace(Object message, Throwable t) {
        delegate.trace(message, t);
    }

    public void info(final String format, final Object... args) {
        info(new Formatter().format(format, args).toString());
    }

    public void info(Object message) {
        delegate.info(message);
    }

    public void info(Object message, Throwable t) {
        delegate.info(message, t);
    }

    public void warn(final String format, final Object... args) {
        warn(new Formatter().format(format, args).toString());
    }

    public void warn(Object message) {
        delegate.warn(message);
    }

    public void warn(Object message, Throwable t) {
        delegate.warn(message, t);
    }

    public void error(final String format, final Object... args) {
        error(new Formatter().format(format, args).toString());
    }

    public void error(Object message) {
        delegate.error(message);
    }

    public void error(Object message, Throwable t) {
        delegate.error(message, t);
    }

    public void fatal(final String format, final Object... args) {
        fatal(new Formatter().format(format, args).toString());
    }

    public void fatal(Object message) {
        delegate.fatal(message);
    }

    public void fatal(Object message, Throwable t) {
        delegate.fatal(message, t);
    }

}
