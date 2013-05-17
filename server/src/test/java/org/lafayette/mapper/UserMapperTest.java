/*
 * LICENSE
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package org.lafayette.mapper;

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

/**
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UserMapperTest {

  private Connection db;

  @Before
  public void createDatabaseConnection() throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
    Class.forName("org.hsqldb.jdbcDriver");
    db = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "sa", "");
    Statement createTableStatement = db.createStatement();
    final String tableSql = FileUtils.readFileToString(new File(getClass()
      .getResource("/org/lafayette/server/sql/table_user.sql").toURI()));
    createTableStatement.execute(tableSql);
    final String dataSql = FileUtils.readFileToString(new File(getClass()
      .getResource("/org/lafayette/server/sql/data_user.sql").toURI()));
    Statement insertDataStatement = db.createStatement();
    insertDataStatement.execute(dataSql);
  }

  @After
  public void closeDatabaseConnection() throws SQLException {
    Statement shutdownStatement = db.createStatement();
    shutdownStatement.execute("shutdown");
    db.close();
  }

  @Test
  public void doSomething() {

  }

}
