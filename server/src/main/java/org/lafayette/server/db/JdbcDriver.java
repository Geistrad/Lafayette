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

package org.lafayette.server.db;

/**
 * Full qualified names of JDBC driver classes.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum JdbcDriver {

    /**
     * JDBC driver class for MySQL.
     */
    MYSQL("com.mysql.jdbc.Driver", "mysql"),
    /**
     * JDBC driver class for HSQLDB.
     */
    HSQLDB("org.hsqldb.jdbc.JDBCDriver", "hsqldb");

    /**
     * Full qualified class name.
     */
    private final String driverClassName;
    /**
     * Typical JDBC name.
     */
    private final String name;

    /**
     * Dedicated constructor
     *
     * @param driverClassName full qualified class name
     * @param name typical JDBC name
     */
    private JdbcDriver(final String driverClassName, final String name) {
        this.driverClassName = driverClassName;
        this.name = name;
    }

    /**
     * Load the driver class.
     *
     * @throws ClassNotFoundException if class was not found
     */
    public void load() throws ClassNotFoundException {
        Class.forName(driverClassName);
    }

    @Override
    public String toString() {
        return driverClassName;
    }

    /**
     * Get driver for a JDBC name such as 'mysql' or 'hsqldb'.
     *
     * @param name case does not matter, throws {@link IllegalArgumentException exception} if no driver for name exist
     * @return never {@code null}
     */
    public static JdbcDriver getFor(final String name) {
        if (MYSQL.name.equalsIgnoreCase(name)) {
            return MYSQL;
        } else if (HSQLDB.name.equalsIgnoreCase(name)) {
            return HSQLDB;
        } else {
            throw new IllegalArgumentException(String.format("Can not find driver for '%s'!", name));
        }
    }

}
