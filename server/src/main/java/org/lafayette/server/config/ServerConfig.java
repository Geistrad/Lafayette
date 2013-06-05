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
 * Central configuration for the web application.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ServerConfig {

    /**
     * Format of JDBC URIs.
     */
    static final String JDBC_URI_FORMAT = "jdbc:%s://%s:%d/%s";
    /**
     * Property name for database driver.
     */
    static final String DB_DRIVER = "db.driver";
    /**
     * Property name for database password.
     */
    static final String DB_PASSWORD = "db.passwprd";
    /**
     * Property name for database user.
     */
    static final String DB_USER = "db.user";
    /**
     * Property name for database name.
     */
    static final String DB_NAME = "db.name";
    /**
     * Property name for database port.
     */
    static final String DB_PORT = "db.port";
    /**
     * Property name for database host.
     */
    static final String DB_HOST = "db.host";
    /**
     * Realm for HTTP authorization.
     */
    static final String SECURITY_REALM = "security.realm";
    /**
     * Holds the properties from configuration file.
     */
    private final Properties serverProperties;

    /**
     * Dedicated constructor.
     *
     * @param serverProperties properties loaded from file
     */
    public ServerConfig(final Properties serverProperties) {
        super();
        this.serverProperties = serverProperties;
    }

    /**
     * Get the database host.
     *
     * @return never {@code null}
     */
    public String getDbHost() {
        return serverProperties.getProperty(DB_HOST, "");
    }

    /**
     * Get the database port.
     *
     * @return integer greater equal 0
     */
    public int getDbPort() {
        try {
            final Integer port = Integer.valueOf(serverProperties.getProperty(DB_PORT, ""));
            return port < 0 ? 0 : port;
        } catch (final NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * Get the database name.
     *
     * @return never {@code null}
     */
    public String getDbName() {
        return serverProperties.getProperty(DB_NAME, "");
    }

    /**
     * Get the database user.
     *
     * @return never {@code null}
     */
    public String getDbUser() {
        return serverProperties.getProperty(DB_USER, "");
    }

    /**
     * Get the database  password.
     *
     * @return never {@code null}
     */
    public String getDbPassword() {
        return serverProperties.getProperty(DB_PASSWORD, "");
    }

    /**
     * Get the database driver.
     *
     * @return never {@code null}
     */
    public String getDbDriver() {
        return serverProperties.getProperty(DB_DRIVER, "");
    }

    /**
     * Get the realm string for authorization.
     *
     * @return never {@code null}
     */
    public String getSecurotyRealm() {
        return serverProperties.getProperty(SECURITY_REALM, "");
    }

    /**
     * Generates JDBC URI from configuration parameters.
     *
     * @return never {@code null}
     */
    public String generateJdbcUri() {
        final StringBuilder buffer = new StringBuilder("jdbc:");
        buffer.append(getDbDriver())
              .append(':')
              .append(getDbHost());

        if (getDbPort() > 0) {
            buffer.append(':').append(getDbPort());
        }

        buffer.append('/').append(getDbName());
        return buffer.toString();
    }

}
