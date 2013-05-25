/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package org.lafayette.server;

import org.lafayette.server.log.Log;
import de.weltraumschaf.commons.Version;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.lafayette.server.config.ConfigLoader;
import org.lafayette.server.config.ServerConfig;
import org.lafayette.server.db.JdbcDriver;
import org.lafayette.server.db.NullConnection;
import org.lafayette.server.db.SqlLoader;
import org.lafayette.server.log.Logger;
import org.lafayette.server.mapper.Mappers;

/**
 * Implements a servlet context listener.
 *
 * Creates and provides an instance of a Neo4j database.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ServerContextListener implements ServletContextListener {

    /**
     * Key for a {@link Registry registry object} servlet context attribute.
     */
    public static String REGISRTY = "registry";
    /**
     * PAth to version file.
     */
    private static final String VERSION_FILE = "/org/lafayette/server/version.properties";
    /**
     * Logging facility.
     */
    private final static Logger LOG = Log.getLogger(ServerContextListener.class);
    /**
     * Registry shared over the whole web application.
     */
    private final Registry reg = new Registry();

    /**
     * Dedicated constructor.
     */
    public ServerContextListener() {
        super();
    }

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        LOG.debug("Context initialized. Execute listener...");
        loadVersion();
        loadStage();
        final ServerConfig config = loadConfig();
        final Connection con = openDatabaseConnection(config);
        createMappersFactory(con);
        sce.getServletContext().setAttribute(REGISRTY, reg);
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        LOG.debug("Context destroyed. Execute listener...");
        closeDatabaseConnection();
    }

    /**
     * Add version information to the {@link Registry registry}.
     */
    private void loadVersion() {
        try {
            LOG.info("Load version from file %s.", VERSION_FILE);
            final Version version = new Version(VERSION_FILE);
            version.load();
            LOG.info("Loaded version %s.", version.getVersion());
            reg.setVersion(version);
        } catch (IOException ex) {
            LOG.fatal("Error loading version: %s", ex.toString());
        }
    }

    /**
     * Add stage information to the {@link Registry registry}.
     */
    private void loadStage() {
        final String envStage = EnvVars.STAGE.getFromSystem();
        LOG.debug("Use $STAGE=%s as stage.", envStage);
        final Stage stage = new Stage(envStage);
        LOG.info("Loaded stage %s.", stage.toString());
        reg.setStage(stage);
    }

    /**
     * Open and add database connection to the {@link Registry registry}.
     *
     * @param config server configuration
     */
    private Connection openDatabaseConnection(final ServerConfig config) {
        try {
            LOG.debug("Load JDBC driver class for '%s'.", config.getDbDriver());
            JdbcDriver.getFor(config.getDbDriver()).load();
            LOG.info("Open database connection to %s.", config.generateJdbcUri());
            final Connection con = DriverManager.getConnection(
                    config.generateJdbcUri(),
                    config.getDbUser(),
                    config.getDbPassword());
            LOG.info("Database connection to %s established.", config.generateJdbcUri());
            reg.setDatabase(con);
            return con;
        } catch (SQLException ex) {
            LOG.fatal("Error opening database connection: %s", ex.toString());
        } catch (ClassNotFoundException ex) {
            LOG.fatal("Error loading JDBC driver: %s", ex.toString());
        } catch (IllegalArgumentException ex) {
            LOG.fatal("Error loading JDBC driver: %s", ex.toString());
        }

        return new NullConnection();
    }

    /**
     * Add server configuration to the {@link Registry registry}.
     *
     * @return loaded server configuration
     */
    private ServerConfig loadConfig() {
        final Properties configProperties = new Properties();
        final ServerConfig serverConfig = new ServerConfig(configProperties);
        reg.setServerConfig(serverConfig);
        FileInputStream input = null;

        try {
            final ConfigLoader loader = ConfigLoader.create();
            loader.load();

            if (loader.hasConfig()) {
                final File configFile = loader.getFile();
                LOG.info("Read server config from file '%s'!", configFile.getAbsolutePath());
                input = new FileInputStream(configFile);
                configProperties.load(input);
            } else {
                LOG.warn("No server config found!");
            }
        } catch (IOException ex) {
            LOG.fatal("Error reading server config: %s", ex.toString());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                LOG.fatal("Error closing config file: %s", ex.toString());
            }
        }

        return serverConfig;
    }

    /**
     * Close database connection.
     */
    private void closeDatabaseConnection() {
        try {
            LOG.info("Close database connection.");
            reg.getDatabase().close();
        } catch (SQLException ex) {
            LOG.fatal("Error closing database connection: %s", ex.toString());
        }
    }

    private void createMappersFactory(final Connection con) {
        reg.setMappers(new Mappers(con));
    }

}
