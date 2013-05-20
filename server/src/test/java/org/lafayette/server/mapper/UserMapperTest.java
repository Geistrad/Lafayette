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
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lafayette.server.db.JdbcDriver;
import org.lafayette.server.domain.User;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.Matchers.*;
import org.lafayette.server.ApplicationException;

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
        startTestDatabase(true);
    }

    public void startTestDatabase(final boolean withData) throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
        createDatabaseConnection();
        createTable();

        if (withData) {
            insertTestData();
        }
    }

    @After
    public void destroyTestDatabase() throws SQLException {
        try {
            final Statement shutdownStatement = db.createStatement();
            shutdownStatement.execute("shutdown");
            shutdownStatement.close();
            db.close();
        } catch (SQLNonTransientConnectionException ex) {
            // Ignore if database already removed.
        }
    }

    private void createDatabaseConnection() throws SQLException, ClassNotFoundException {
        JdbcDriver.HSQLDB.load();
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
        assertThat(user.getId(), is(1));
        assertThat(user.getLoginName(), is("Foo"));
        assertThat(user.getHashedPassword(), is("b9f46238b289f23ba807973840655032"));
        assertThat(user.getSalt(), is("Oih0mei7"));

        user = sut.find(2);
        assertThat(user.getId(), is(2));
        assertThat(user.getLoginName(), is("Bar"));
        assertThat(user.getHashedPassword(), is("043bd227eaa879d438e7c1dfea568bc9"));
        assertThat(user.getSalt(), is("AiZuur1Y"));
    }

    @Test
    public void findUserById_caches() {
        final UserMapper sut = new UserMapper(db);
        final User user = sut.find(1);
        assertThat(user, is(sameInstance(sut.find(1))));
    }

    @Test
    public void findByLoginName() {
        final UserMapper sut = new UserMapper(db);
        final User user = sut.findByLoginName("Baz");
        assertThat(user.getId(), is(3));
        assertThat(user.getLoginName(), is("Baz"));
        assertThat(user.getHashedPassword(), is("aa82cc74b4a932c06d4ea5a9ac38cf5e"));
        assertThat(user.getSalt(), is("Eng7ovej"));
    }

    @Test
    public void findByLoginName_caches() {
        final UserMapper sut = new UserMapper(db);
        final User user = sut.findByLoginName("Baz");
        assertThat(user, is(sameInstance(sut.findByLoginName("Baz"))));
    }

    @Test
    public void findAll() {
        final UserMapper sut = new UserMapper(db);

        for (final User user : sut.findAll(10, 0)) {
            final int userId = user.getId();

            switch (userId) {
                case 1:
                    assertThat(user.getLoginName(), is("Foo"));
                    assertThat(user.getHashedPassword(), is("b9f46238b289f23ba807973840655032"));
                    assertThat(user.getSalt(), is("Oih0mei7"));
                    break;
                case 2:
                    assertThat(user.getLoginName(), is("Bar"));
                    assertThat(user.getHashedPassword(), is("043bd227eaa879d438e7c1dfea568bc9"));
                    assertThat(user.getSalt(), is("AiZuur1Y"));
                    break;
                case 3:
                    assertThat(user.getLoginName(), is("Baz"));
                    assertThat(user.getHashedPassword(), is("aa82cc74b4a932c06d4ea5a9ac38cf5e"));
                    assertThat(user.getSalt(), is("Eng7ovej"));
                    break;
                default:
                    fail("Unexpected user id: " + userId);
            }
        }
    }

    @Test
    public void findAll_emptyTable() throws SQLException, ClassNotFoundException, IOException, URISyntaxException {
        destroyTestDatabase();
        startTestDatabase(false);
        final UserMapper sut = new UserMapper(db);
        final Collection<User> users = sut.findAll(10, 0);
        assertThat(users, is(empty()));
    }

    @Test
    public void insert() {
        UserMapper sut = new UserMapper(db);
        final String loginName = "snafu";
        final String hashedPassword = "snafupw";
        final String salt = "snafusalt";
        User user = new User(loginName, hashedPassword, salt);
        final int id = sut.insert(user);

        sut = new UserMapper(db);
        user = sut.find(id);
        assertThat(user.getLoginName(), is(loginName));
        assertThat(user.getHashedPassword(), is(hashedPassword));
        assertThat(user.getSalt(), is(salt));
    }

    @Test
    public void update() {
        UserMapper sut = new UserMapper(db);
        User user = sut.find(2);
        assertThat(user.getId(), is(2));
        assertThat(user.getLoginName(), is("Bar"));
        assertThat(user.getHashedPassword(), is("043bd227eaa879d438e7c1dfea568bc9"));
        assertThat(user.getSalt(), is("AiZuur1Y"));

        user.setLoginName("snafu");
        user.setHashedPassword("snafupw");
        user.setSalt("snafusalt");
        sut.update(user);

        user = sut.find(2); // get from cache
        assertThat(user.getId(), is(2));
        assertThat(user.getLoginName(), is("snafu"));
        assertThat(user.getHashedPassword(), is("snafupw"));
        assertThat(user.getSalt(), is("snafusalt"));

        sut = new UserMapper(db);
        user = sut.find(2);
        assertThat(user.getId(), is(2));
        assertThat(user.getLoginName(), is("snafu"));
        assertThat(user.getHashedPassword(), is("snafupw"));
        assertThat(user.getSalt(), is("snafusalt"));
    }

    @Test
    public void delete() {
        final UserMapper sut = new UserMapper(db);
        final User user = sut.find(2);
        assertThat(user.getId(), is(2));
        assertThat(user.getLoginName(), is("Bar"));
        assertThat(user.getHashedPassword(), is("043bd227eaa879d438e7c1dfea568bc9"));
        assertThat(user.getSalt(), is("AiZuur1Y"));

        sut.delete(user);

        try {
            sut.find(2);
            fail("Expected exception not thrown!");
        } catch (ApplicationException ex) {
            assertThat(ex.getMessage(), is("There is no record set whith primary key '2'!"));
        }
    }
}
