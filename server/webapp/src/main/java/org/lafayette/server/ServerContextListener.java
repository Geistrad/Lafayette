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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.lafayette.server.log.Log;
import de.weltraumschaf.commons.Version;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import org.lafayette.server.config.ConfigLoader;
import org.lafayette.server.config.ServerConfig;
import org.lafayette.server.db.JdbcDriver;
import org.lafayette.server.db.NullConnection;
import org.lafayette.server.log.Logger;
import org.lafayette.server.mapper.Mappers;

/**
 * Implements a servlet context listener.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ServerContextListener extends GuiceServletContextListener implements ServletContextListener {

    /**
     * Key for a {@link Registry registry object} servlet context attribute.
     */
    public static final String REGISRTY = "registry";
    /**
     * PAth to version file.
     */
    private static final String VERSION_FILE = "/org/lafayette/server/version.properties";
    /**
     * Logging facility.
     */
    private static final Logger LOG = Log.getLogger(ServerContextListener.class);
    private static final String JNDI_NAME_DATA_SOURCE = "java:/comp/env/jdbc/mysql";
    /**
     * Registry shared over the whole web application.
     */
    private final Registry reg = new Registry();

    /**
     * Dedicated constructor.
     *
     * @throws NoSuchAlgorithmException if registry can't initialize {@link Registry#nongeGenerator}
     */
    public ServerContextListener() throws NoSuchAlgorithmException {
        super();
    }

    @Override
    protected Injector getInjector() {
        // http://code.google.com/p/google-guice/wiki/ServletModule
        LOG.debug("Create Guice injector.");
        final Injector injector = Guice.createInjector(new ServerModule());
        reg.setDependnecyInjector(injector);
        return injector;
    }

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        LOG.debug("Context initialized. Execute listener...");
        loadVersion();
        loadStage();
        final ServerConfig config = loadConfig();
        final Connection con = openDatabaseConnection(config);
        createMappersFactory(con);
        createDataSource();
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
     * @return the opened connection
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

    /**
     * Create {@link Mappers mapper factory} and stores to registry.
     *
     * @param con open database connection
     */
    private void createMappersFactory(final Connection con) {
        reg.setMappers(new Mappers(con));
    }

    private void createDataSource() {
        try {
            LOG.info("Create data source.");
            final InitialContext initialContext = new InitialContext();
            final DataSource dataSource = (DataSource) initialContext.lookup(JNDI_NAME_DATA_SOURCE);

            if (null == dataSource) {
                LOG.error("Can't lookup data source via JNDI!");
            } else {
                reg.setDataSource(dataSource);
            }
        } catch (NamingException ex) {
            LOG.fatal(ex.getMessage());
        }
    }
}
