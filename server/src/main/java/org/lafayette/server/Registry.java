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

import de.weltraumschaf.commons.Version;
import java.sql.Connection;
import java.util.Properties;
import org.apache.commons.lang3.Validate;
import org.lafayette.server.config.ServerConfig;
import org.lafayette.server.db.NullConnection;
import org.lafayette.server.mapper.Mappers;

/**
 * Registers shared core resources.
 *
 * Shared resources:
 * <ul>
 * <li>{@link Version version}
 * <li>{@link Stage stage}
 * <li>{@link Connection database connection}
 * <li>{@link ServerConfig server configuration}
 * </ul>
 *
 * All resources are initialized with default values so the registry never returns {@code null}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Registry {

    /**
     * Version of server.
     */
    private Version version = new Version("");
    /**
     * Stage of the server.
     */
    private Stage stage = new Stage();
    /**
     * JDBC database connection.
     */
    private Connection database = new NullConnection();
    /**
     * Service configuration.
     */
    private ServerConfig serverConfig = new ServerConfig(new Properties());
    private Mappers mappers;

    /**
     * Dedicated constructor.
     */
    public Registry() {
        super();
    }

    /**
     * Get the version.
     *
     * @return never {@code null}
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Set the version.
     *
     * @param v must not be null
     */
    public void setVersion(final Version v) {
        Validate.notNull(v, "Version must not be null!");
        this.version = v;
    }

    /**
     * Get the stage.
     *
     * @return never {@code null}
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Set the stage.
     *
     * @param s must not be null
     */
    public void setStage(final Stage s) {
        Validate.notNull(s, "Stage must not be null!");
        this.stage = s;
    }

    /**
     * Get the database connection.
     *
     * @return never {@code null}
     */
    public Connection getDatabase() {
        return database;
    }

    /**
     * Set the database connection.
     *
     * @param c must not be null
     */
    public void setDatabase(final Connection c) {
        Validate.notNull(c, "Connection must not be null!");
        this.database = c;
    }

    /**
     * Get the server configuration.
     *
     * @return never {@code null}
     */
    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    /**
     * Set the server configuration.
     *
     * @param sc must not be null
     */
    public void setServerConfig(final ServerConfig sc) {
        Validate.notNull(sc, "Server configuration must not be null!");
        this.serverConfig = sc;
    }

    public Mappers getMappers() {
        return mappers;
    }

    public void setMappers(final Mappers m) {
        Validate.notNull(m, "Mappers must not be null!");
        this.mappers = m;
    }

}
