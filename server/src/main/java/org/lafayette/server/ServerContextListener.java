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

//import de.weltraumschaf.citer.tpl.SiteLayout;
//import freemarker.template.Configuration;
//import freemarker.template.ObjectWrapper;
import de.weltraumschaf.commons.Version;
import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.lafayette.server.Registry.Item;
import org.apache.log4j.Logger;

/**
 * Implements a servlet context listener.
 *
 * Creates and provides an instance of a Neo4j database.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ServerContextListener implements ServletContextListener {

    private final Logger log = Log.getLogger(this);
//    private static final String TEMPLATE_PREFIX = "/org/lafayette/server/resources";

    public ServerContextListener() {
        super();
    }

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        final Registry reg = new Registry();

        try {
            final Version version = new Version("/org/lafayette/server/version.properties");
            version.load();
            reg.setItem(Registry.Key.VERSION, new Item<Version>(version));
        } catch (IOException ex) {
            log.fatal(ex.toString());
        }

        reg.setItem(Registry.Key.STAGE, new Item<Stage>(new Stage()));
        sce.getServletContext().setAttribute(Key.REGISRTY.toString(), reg);

    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        // nothing to do here yet
    }

    public static enum Key {

        REGISRTY;
    }
}
