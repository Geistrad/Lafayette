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

package org.lafayette.server.config;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ServerConfig {
    private static final String JDBC_URI_FORMAT = "jdbc:%s://%s:%d/%s";

    public String getDbHost() {
        return "";
    }

    public int getDbPort() {
        return 0;
    }

    public String getDbName() {
        return "";
    }

    public String getDbUser() {
        return "";
    }

    public String getDbPassword() {
        return "";
    }

    public String getDbDriver() {
        return "";
    }

    public String generateJdbcUri() {
        return String.format(JDBC_URI_FORMAT, getDbDriver(), getDbHost(), getDbPort(), getDbName());
    }

}
