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

import java.util.Properties;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ServerConfig {

    private static final String JDBC_URI_FORMAT = "jdbc:%s://%s:%d/%s";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_PASSWORD = "db.passwprd";
    private static final String DB_USER = "db.user";
    private static final String DB_NAME = "db.name";
    private static final String DB_PORT = "db.port";
    private static final String DB_HOST = "db.host";
    private final Properties serverProperties;

    public ServerConfig(final Properties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public String getDbHost() {
        return serverProperties.getProperty(DB_HOST);
    }

    public int getDbPort() {
        return Integer.valueOf(serverProperties.getProperty(DB_PORT));
    }

    public String getDbName() {
        return serverProperties.getProperty(DB_NAME);
    }

    public String getDbUser() {
        return serverProperties.getProperty(DB_USER);
    }

    public String getDbPassword() {
        return serverProperties.getProperty(DB_PASSWORD);
    }

    public String getDbDriver() {
        return serverProperties.getProperty(DB_DRIVER);
    }

    public String generateJdbcUri() {
        return String.format(JDBC_URI_FORMAT, getDbDriver(), getDbHost(), getDbPort(), getDbName());
    }

}
