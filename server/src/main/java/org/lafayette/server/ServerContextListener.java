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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.lafayette.server.db.JdbcDriver;

/**
 * Implements a servlet context listener.
 *
 * Creates and provides an instance of a Neo4j database.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ServerContextListener implements ServletContextListener {

    public static String REGISRTY = "registry";
    private final Logger log = Log.getLogger(this);

    public ServerContextListener() {
        super();
    }

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        log.debug("Context initialized. Execute listener...");
        final Registry reg = new Registry();

        try {
            final Version version = new Version("/org/lafayette/server/version.properties");
            version.load();
            reg.setVersion(version);
        } catch (IOException ex) {
            log.fatal(ex.toString());
        }

        reg.setStage(new Stage());

        try {
            JdbcDriver.MYSQL.load(); // FIXME USe configuration.
            final Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafayette", "root", "");
            reg.setDatabase(con);
        } catch (SQLException ex) {
            log.fatal(ex.toString());
        } catch (ClassNotFoundException ex) {
            log.fatal(ex.toString());
        }

        sce.getServletContext().setAttribute(REGISRTY, reg);

    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        log.debug("Context destroyed. Execute listener...");
        final Registry reg = (Registry) sce.getServletContext().getAttribute(REGISRTY);
        try {
            reg.getDatabase().close();
        } catch (SQLException ex) {
            log.fatal(ex.toString());
        }
    }

}
