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
package org.lafayette.server.web.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.lafayette.server.core.log.Log;
import java.security.NoSuchAlgorithmException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import org.lafayette.server.domain.db.NullDataSource;
import org.lafayette.server.core.log.Logger;
import org.lafayette.server.domain.mapper.Mappers;
import org.lafayette.server.web.InitialServletParameters;
import org.lafayette.server.web.Registry;
import org.lafayette.server.web.ServerModule;

/**
 * Implements a servlet context listener.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ExtendedContextListener extends GuiceServletContextListener implements ServletContextListener {

    /**
     * Key for a {@link Registry registry object} servlet context attribute.
     */
    public static final String ATTRIBUTE_NAME_REGISRTY = BaseContextListener.ATTRIBUTE_NAME_REGISRTY;

    /**
     * Logging facility.
     */
    private static final Logger LOG = Log.getLogger(ExtendedContextListener.class);
    /**
     * JNDI name for main data base data source.
     */
    private static final String JNDI_NAME_DATA_SOURCE = "java:/comp/env/jdbc/mysql";
    private final BaseContextListener base = new BaseContextListener();

    /**
     * Dedicated constructor.
     *
     * @throws NoSuchAlgorithmException if registry can't initialize {@link Registry#nongeGenerator}
     */
    public ExtendedContextListener() throws NoSuchAlgorithmException {
        super();
    }

    @Override
    protected Injector getInjector() {
        // http://code.google.com/p/google-guice/wiki/ServletModule
        LOG.debug("Create Guice injector.");
        final Injector injector = Guice.createInjector(new ServerModule());
        base.getRegistry().setDependnecyInjector(injector);
        return injector;
    }

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        base.contextInitialized(sce);
        LOG.debug("Context initialized. Execute extended listener...");
        final DataSource dataSource = createDataSource();
        createMappersFactory(dataSource);
        final ServletContext servletContext = sce.getServletContext();
        base.getRegistry().setInitParameters(new InitialServletParameters(servletContext));
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        base.contextDestroyed(sce);
        LOG.debug("Context destroyed. Execute extended listener...");
    }

    /**
     * Create {@link Mappers mapper factory} and stores to registry.
     *
     * @param dataSource open database connection
     */
    private void createMappersFactory(final DataSource dataSource) {
        base.getRegistry().setMappers(new Mappers(dataSource));
    }

    /**
     * Creates data source provided by container via JNDI and sets to {@link Registry}.
     *
     * @return created data source
     */
    private DataSource createDataSource() {
        try {
            LOG.info("Create data source.");
            final InitialContext initialContext = new InitialContext();
            final DataSource dataSource = (DataSource) initialContext.lookup(JNDI_NAME_DATA_SOURCE);

            if (null == dataSource) {
                LOG.error("Can't lookup data source via JNDI!");
            } else {
                base.getRegistry().setDataSource(dataSource);
                return dataSource;
            }
        } catch (NamingException ex) {
            LOG.fatal(ex.getMessage());
        }

        return new NullDataSource();
    }
}
