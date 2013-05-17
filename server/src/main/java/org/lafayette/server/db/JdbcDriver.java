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
    MYSQL("com.mysql.jdbc.Driver"),
    /**
     * JDBC driver class for HSQLDB.
     */
    HSQLDB("org.hsqldb.jdbc.JDBCDriver");

    /**
     * Full qualified class name.
     */
    private final String driverClassName;

    /**
     * Dedicated constructor
     *
     * @param driverClassName full qualified class name
     */
    private JdbcDriver(final String driverClassName) {
        this.driverClassName = driverClassName;
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

}
