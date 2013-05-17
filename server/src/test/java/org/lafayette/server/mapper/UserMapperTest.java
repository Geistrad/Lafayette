/*
 * LICENSE
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package org.lafayette.server.mapper;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lafayette.server.db.JdbcDriver;
import org.lafayette.server.domain.User;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UserMapperTest {
    private static final String FIXTURE_BASE = "/org/lafayette/server";
    private static final String JDBC_URI = "jdbc:hsqldb:mem:lafayette";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private Connection db;

    @Before
    public void startTestDatabase() throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
        createDatabaseConnection();
        createTable();
        insertTestData();
    }

    @After
    public void destroyTestDatabase() throws SQLException {
        final Statement shutdownStatement = db.createStatement();
        shutdownStatement.execute("shutdown");
        shutdownStatement.close();
        db.close();
    }

    private void createDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName(JdbcDriver.HSQLDB.toString());
        db = DriverManager.getConnection(JDBC_URI, DB_USER, DB_PASSWORD);
    }

    private void createTable() throws SQLException, IOException, URISyntaxException {
        final String tableSql = loadSql("table_user.sql");
        final Statement createTableStatement = db.createStatement();
        createTableStatement.execute(tableSql);
        createTableStatement.close();
    }

    private void insertTestData() throws SQLException, URISyntaxException, IOException {
        final String dataSql = loadSql("data_user.sql");
        final Statement insertDataStatement = db.createStatement();
        insertDataStatement.execute(dataSql);
        insertDataStatement.close();
    }

    private String loadSql(final String fileName) throws URISyntaxException, IOException {
        return FileUtils.readFileToString(new File(getClass()
                .getResource(FIXTURE_BASE + "/sql/" + fileName).toURI()));
    }
    @Test
    public void findUserById() {
        final UserMapper sut = new UserMapper(db);
        User user = sut.find(1);
        assertThat(user.getId(), is(Long.valueOf(1)));
        assertThat(user.getLoginName(), is("Foo"));
        assertThat(user.getHashedPassword(), is("b9f46238b289f23ba807973840655032"));
        assertThat(user.getSalt(), is("Oih0mei7"));

        user = sut.find(Long.valueOf(2));
        assertThat(user.getId(), is(Long.valueOf(2)));
        assertThat(user.getLoginName(), is("Bar"));
        assertThat(user.getHashedPassword(), is("043bd227eaa879d438e7c1dfea568bc9"));
        assertThat(user.getSalt(), is("AiZuur1Y"));
    }

}
