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
package org.lafayette.server.resources;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.apache.log4j.Logger;
import org.lafayette.server.Log;
import org.lafayette.server.http.MediaType;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Path("/")
public class IndexResource {

    private static final String NL = "\r\n";
    private final Logger log = Log.getLogger(this);

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String indexAsText() {
        final StringBuilder buffer = new StringBuilder("Lafayette Server");
        buffer.append(NL).append(NL).append("Hello World!");
        return buffer.toString();
    }

    @Produces(MediaType.TEXT_UR_ILIST)
    @GET
    public String indexAsUriList() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("# Available URIs:").append(NL)
                .append("/users").append(NL);
        return buffer.toString();
    }

    @Produces(MediaType.TEXT_PLAIN)
    @GET @Path("users")
    public String usersAsText() {
        final StringBuilder buffer = new StringBuilder();

        try {
            Class.forName("com.mysql.jdbc.Driver");

            final Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lafayette", "root", "");

            final Statement stmt = con.createStatement();
            final ResultSet rs = stmt.executeQuery("select * from user");
            String dbtime;
            while (rs.next()) {
                dbtime = rs.getString(1);
                buffer.append(rs.getString(1))
                        .append(' ')
                        .append(rs.getString(2))
                        .append(' ')
                        .append(rs.getString(3))
                        .append(' ')
                        .append(rs.getString(4))
                        .append(NL);
            }

            con.close();

        } catch (SQLException ex) {
            log.fatal(ex.toString());
        } catch (ClassNotFoundException ex) {
            log.fatal(ex.toString());
        }

        return buffer.toString();
    }
}
