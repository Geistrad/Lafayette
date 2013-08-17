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

package org.lafayette.server.web.servlet;

import de.weltraumschaf.commons.Version;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.lafayette.server.core.EnvVars;
import org.lafayette.server.core.Stage;
import org.lafayette.server.core.log.Log;
import org.lafayette.server.core.log.Logger;
import org.lafayette.server.web.Registry;
import static org.lafayette.server.web.servlet.ExtendedContextListener.ATTRIBUTE_NAME_REGISRTY;

/**
 * Only initializes {@link Registry} with base information provided to the web app.
 *
 * Provided information:
 * <ul>
 * <li>stage information</li>
 * <li>version information</li>
 * </ul>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BaseContextListener implements ServletContextListener {

    /**
     * Key for a {@link Registry registry object} servlet context attribute.
     */
    public static final String ATTRIBUTE_NAME_REGISRTY = "registry";

    /**
     * PAth to version file.
     */
    private static final String VERSION_FILE = "/org/lafayette/server/version.properties";
    /**
     * Logging facility.
     */
    private static final Logger LOG = Log.getLogger(ExtendedContextListener.class);

    /**
     * Registry shared over the whole web application.
     */
    private final Registry registry = new Registry();

    /**
     * Dedicated constructor.
     *
     * @throws NoSuchAlgorithmException if registry can't initialize {@link Registry#nongeGenerator}
     */
    public BaseContextListener() throws NoSuchAlgorithmException {
        super();
    }

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        LOG.debug("Context initialized. Execute base listener...");
        sce.getServletContext().setAttribute(ATTRIBUTE_NAME_REGISRTY, registry);
        loadVersion();
        loadStage();

    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        LOG.debug("Context destroyed. Execute base listener...");
    }

    Registry getRegistry() {
        return registry;
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
            registry.setVersion(version);
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
        registry.setStage(stage);
    }
}
