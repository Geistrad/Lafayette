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
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.lafayette.server.config.ConfigLoader;
import org.lafayette.server.config.ServerConfig;
import org.lafayette.server.db.JdbcDriver;
import org.lafayette.server.log.Logger;

/**
 * Implements a servlet context listener.
 *
 * Creates and provides an instance of a Neo4j database.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ServerContextListener implements ServletContextListener {

    public static String REGISRTY = "registry";
    private static final String VERSION_FILE = "/org/lafayette/server/version.properties";
    private final Logger log = Log.getLogger(this);
    private final Registry reg = new Registry();

    public ServerContextListener() {
        super();
    }

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        log.debug("Context initialized. Execute listener...");
        loadVersion();
        loadStage();
//        final ServerConfig config = loadConfig();
//        openDatabaseConnection(config);
        sce.getServletContext().setAttribute(REGISRTY, reg);
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        log.debug("Context destroyed. Execute listener...");
        closeDatabaseConnection();
    }

    private void loadVersion() {
        try {
            log.info("Load version from file %s.", VERSION_FILE);
            final Version version = new Version(VERSION_FILE);
            version.load();
            log.info("Loaded version %s.", version.getVersion());
            reg.setVersion(version);
        } catch (IOException ex) {
            log.fatal("Error loading version: %s", ex.toString());
        }
    }

    private void loadStage() {
        final Stage stage = new Stage();
        log.info("Loaded stage %s.", stage.toString());
        reg.setStage(stage);
    }

    private void openDatabaseConnection(final ServerConfig config) {
        try {
            log.info("Load JDBC driver class for '%s'.", config.getDbDriver());
            JdbcDriver.getFor(config.getDbDriver()).load();
            log.info("Open database connection to %s.", config.generateJdbcUri());
            final Connection con = DriverManager.getConnection(
                    config.generateJdbcUri(),
                    config.getDbUser(),
                    config.getDbPassword());
            log.info("Database connection to %s established.", config.generateJdbcUri());
            reg.setDatabase(con);
        } catch (SQLException ex) {
            log.fatal("Error opening database connection: %s", ex.toString());
        } catch (ClassNotFoundException ex) {
            log.fatal("Error loading JDBC driver: %s", ex.toString());
        }
    }

    private ServerConfig loadConfig() {
        final Properties configProperties = new Properties();
        final ServerConfig serverConfig = new ServerConfig(configProperties);
        reg.setServerConfig(serverConfig);
        FileInputStream input = null;

        try {
            final ConfigLoader loader = ConfigLoader.create();
            final File configFile = loader.getFile();
            log.info("Read server config from file '%s'!", configFile.getAbsolutePath());
            input = new FileInputStream(configFile);
            configProperties.load(input);
        } catch (IOException ex) {
            log.fatal("Error reading server config: %s", ex.toString());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                log.fatal("Error closing config file: %s", ex.toString());
            }
        }

        return serverConfig;
    }

    private void closeDatabaseConnection() {
        try {
            log.info("Close database connection.");
            reg.getDatabase().close();
        } catch (SQLException ex) {
            log.fatal("Error closing database connection: %s", ex.toString());
        }
    }
}
