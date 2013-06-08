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

package org.lafayette.server.mapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import org.junit.After;
import org.junit.Before;
import org.lafayette.server.db.JdbcDriver;
import org.lafayette.server.db.SqlLoader;

/**
 * Provides a fresh HSQL in memory database for tests.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public abstract class DbTestCase {

    /**
     * JDBS URI.
     */
    private static final String JDBC_URI = "jdbc:hsqldb:mem:lafayette";
    /**
     * Default admin user.
     */
    private static final String DB_USER = "sa";
    /**
     * Default password.
     */
    private static final String DB_PASSWORD = "";
    /**
     * Connection to the test database.
     */
    private Connection db;

    /**
     * Getter for the connection.
     *
     * @return same instance
     */
    public Connection db() {
        return db;
    }

    /**
     * Start test database and insert test data.
     *
     * @throws ClassNotFoundException if JDBC driver not found
     * @throws SQLException if SQL error occurs
     * @throws IOException if SQL file read error occurs
     * @throws URISyntaxException if SQL file read error occurs
     */
    @Before
    public void startTestDatabase() throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
        startTestDatabase(true);
    }

    /**
     * Creates connection, executes the create table script from {@link #tableSql()} and insert
     * some test data from {@link #testDataSql()} if wanted.
     *
     * @param withData whether to create test data from {@link #testDataSql()}
     * @throws ClassNotFoundException if JDBC driver not found
     * @throws SQLException if SQL error occurs
     * @throws IOException if SQL file read error occurs
     * @throws URISyntaxException if SQL file read error occurs
     */
    public void startTestDatabase(final boolean withData) throws ClassNotFoundException, SQLException, IOException,
        URISyntaxException {
        createDatabaseConnection();
        createTable();

        if (withData) {
            insertTestData();
        }
    }

    /**
     * Shutdown and close test database connection.
     *
     * @throws SQLException if SQL error occurs
     */
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

    /**
     * Create database connection.
     *
     * @throws SQLException if SQL error occurs
     * @throws ClassNotFoundException if JDBC driver not found
     */
    @SuppressWarnings("DMI_EMPTY_DB_PASSWORD")
    private void createDatabaseConnection() throws SQLException, ClassNotFoundException {
        JdbcDriver.HSQLDB.load();
        db = DriverManager.getConnection(JDBC_URI, DB_USER, DB_PASSWORD);
    }

    /**
     * Create tables from SQL files.
     *
     * @throws SQLException if SQL error occurs
     * @throws IOException if SQL file read error occurs
     * @throws URISyntaxException if SQL file read error occurs
     */
    @SuppressWarnings("OBL_UNSATISFIED_OBLIGATION")
    private void createTable() throws SQLException, IOException, URISyntaxException {
        final String tableSql = SqlLoader.loadSql(tableSql());
        final Statement createTableStatement = db.createStatement();
        createTableStatement.execute(tableSql);
        createTableStatement.close();
    }

    /**
     * Must return file name relative to {@link SqlLoader#FIXTURE_BASE}.
     *
     * @return  must not return {@code nul} or empty string.
     */
    protected abstract String tableSql();

    /**
     * Insert test date from SQL files.
     *
     * @throws SQLException if SQL error occurs
     * @throws IOException if SQL file read error occurs
     * @throws URISyntaxException if SQL file read error occurs
     */
    @SuppressWarnings("OBL_UNSATISFIED_OBLIGATION")
    private void insertTestData() throws SQLException, URISyntaxException, IOException {
        final String dataSql = SqlLoader.loadSql(testDataSql());
        final Statement insertDataStatement = db.createStatement();
        insertDataStatement.execute(dataSql);
        insertDataStatement.close();
    }

    /**
     * Must return file name relative to {@link SqlLoader#FIXTURE_BASE}.
     *
     * @return  must not return {@code nul} or empty string.
     */
    protected abstract String testDataSql();

}
