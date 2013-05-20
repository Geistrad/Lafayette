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

    /**
     * Decorated logger.
     */
    private final org.apache.log4j.Logger delegate;

    /**
     * Dedicated constructor.
     *
     * @param delegate decorated logger
     */
    public Logger(final org.apache.log4j.Logger delegate) {
        super();
        this.delegate = delegate;
    }

    /**
     * Creates new formatter.
     *
     * @see java.util.Formatter#format(java.lang.String, java.lang.Object[])
     * @param format format string
     * @param args variable number of arguments used in format
     * @return the new formatter
     */
    Formatter format(final String format, final Object... args) {
        return new Formatter().format(format, args);
    }

    /**
     * @see org.apache.log4j.Logger#setLevel(org.apache.log4j.Level)
     * @param level
     */
    public void setLevel(final Level level) {
        delegate.setLevel(level);
    }

    /**
     * @see org.apache.log4j.Logger#getLevel()
     * @return
     */
    public final Level getLevel() {
        return delegate.getLevel();
    }

    public void debug(final String format, final Object... args) {
        debug(new Formatter().format(format, args).toString());
    }

    /**
     * @see org.apache.log4j.Logger#debug(java.lang.Object)
     * @param message
     */
    public void debug(final Object message) {
        delegate.debug(message);
    }

    /**
     * @see org.apache.log4j.Logger#debug(java.lang.Object, java.lang.Throwable)
     * @param message
     * @param t
     */
    public void debug(final Object message, final Throwable t) {
        delegate.debug(message, t);
    }

    public void trace(final String format, final Object... args) {
        trace(format(format, args).toString());
    }

    /**
     * @see org.apache.log4j.Logger#trace(java.lang.Object)
     * @param message
     */
    public void trace(final Object message) {
        delegate.trace(message);
    }

    /**
     * @see org.apache.log4j.Logger#trace(java.lang.Object, java.lang.Throwable)
     * @param message
     * @param t
     */
    public void trace(final Object message, final Throwable t) {
        delegate.trace(message, t);
    }

    public void info(final String format, final Object... args) {
        info(format(format, args).toString());
    }

    /**
     * @see org.apache.log4j.Logger#info(java.lang.Object)
     * @param message
     */
    public void info(final Object message) {
        delegate.info(message);
    }

    /**
     * @see org.apache.log4j.Logger#info(java.lang.Object, java.lang.Throwable)
     * @param message
     * @param t
     */
    public void info(final Object message, final Throwable t) {
        delegate.info(message, t);
    }

    public void warn(final String format, final Object... args) {
        warn(format(format, args).toString());
    }

    /**
     * @see org.apache.log4j.Logger#warn(java.lang.Object)
     * @param message
     */
    public void warn(final Object message) {
        delegate.warn(message);
    }

    /**
     * @see org.apache.log4j.Logger#warn(java.lang.Object, java.lang.Throwable)
     * @param message
     * @param t
     */
    public void warn(final Object message, final Throwable t) {
        delegate.warn(message, t);
    }

    public void error(final String format, final Object... args) {
        error(format(format, args).toString());
    }

    /**
     * @see org.apache.log4j.Logger#error(java.lang.Object)
     * @param message
     */
    public void error(final Object message) {
        delegate.error(message);
    }

    /**
     * @see org.apache.log4j.Logger#error(java.lang.Object, java.lang.Throwable)
     * @param message
     * @param t
     */
    public void error(final Object message, final Throwable t) {
        delegate.error(message, t);
    }

    public void fatal(final String format, final Object... args) {
        fatal(format(format, args).toString());
    }

    /**
     * @see org.apache.log4j.Logger#fatal(java.lang.Object)
     * @param message
     */
    public void fatal(final Object message) {
        delegate.fatal(message);
    }

    /**
     * @see org.apache.log4j.Logger#fatal(java.lang.Object, java.lang.Throwable) 
     * @param message
     * @param t
     */
    public void fatal(final Object message, final Throwable t) {
        delegate.fatal(message, t);
    }

}
