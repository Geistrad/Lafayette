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
package org.lafayette.server.domain.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.lafayette.server.domain.DomainModelException;
import org.lafayette.server.domain.User;
import org.lafayette.server.domain.UserFinder;
import org.lafayette.server.domain.mapper.id.IntegerIdentityMap;

/**
 * Maps {@link User user domain objects} to the database.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UserMapper extends BaseMapper<User> implements UserFinder {

    /**
     * Name of database table.
     */
    private static final String TABLE_NAME = "user";
    /**
     * Name of primary key field.
     */
    private static final String PK_FIELD_NAME = "id";
    /**
     * Field of database table.
     */
    private static final String COLUMNS = PK_FIELD_NAME + ", loginName, hashedUserData";
    /**
     * Insert SQL statement.
     */
    private static final String SQL_INSERT = "insert into %s values (?, ?, ?)";
    /**
     * Find by login name SQL statement.
     */
    private static final String SQL_FIND_BY_LOGIN_NAME = "select %s from %s where loginName like ?";
    /**
     * Update SQL statement.
     */
    private static final String SQL_UPDATE = "update %s set loginName = ?, hashedUserData = ? where %s = ?";
    /**
     * Position of user id parameter in prepared statement.
     */
    private static final int INSERT_PARAM_USERID_POS = 2;
    /**
     * Position of hashed user data parameter in prepared statement.
     */
    private static final int INSERT_PARAM_HASHEDUSERDATA_POS = 3;
    /**
     * Position of login name parameter in prepared statement.
     */
    private static final int UPDATE_PARAM_LOGINNAME_POS = 1;
    /**
     * Position of hashed user data parameter in prepared statement.
     */
    private static final int UPDATE_PARAM_HASHEDUSERDATA_POS = 2;
    /**
     * Position of user id parameter in prepared statement.
     */
    private static final int UPDATE_PARAM_USERID_POS = 3;
    /**
     * Position of login name row in result set.
     */
    private static final int LOAD_ROW_LOGINNAME_POS = 2;
    /**
     * Position of hashed user data row in result set.
     */
    private static final int LOAD_ROW_HASHEDUSERDATA_POS = 3;

    /**
     * Created by {@link Mappers factory}.
     *
     * @param dataSource database connection
     * @param idMap all mappers must share same identity map
     */
    UserMapper(final DataSource dataSource, final IntegerIdentityMap<User> idMap) {
        super(dataSource, idMap);
    }

    @Override
    protected String findByIdStatement() {
        return findByIdStatement(COLUMNS, TABLE_NAME, PK_FIELD_NAME);
    }

    @Override
    protected String findAllStatement(final int limit, final int offset) {
        return findAllStatement(COLUMNS, TABLE_NAME, limit, offset);
    }

    @Override
    protected String findMaxPrimaryKeyStatement() {
        return findMaxPrimaryKeyStatement(PK_FIELD_NAME, TABLE_NAME);
    }

    /**
     * Creates SQL prepared statement to find a user by login name.
     *
     * @return never {@code null}
     */
    private String findByLoginNameStatement() {
        return String.format(SQL_FIND_BY_LOGIN_NAME, COLUMNS, TABLE_NAME);
    }

    /**
     * Creates SQL prepared statement to update a user.
     *
     * @return never {@code null}
     */
    private String updateStatement() {
        return String.format(SQL_UPDATE, TABLE_NAME, PK_FIELD_NAME);
    }

    @Override
    protected String deleteStatement() {
        return deleteStatement(TABLE_NAME, PK_FIELD_NAME);
    }

    @Override
    protected String insertStatement() {
        return String.format(SQL_INSERT, TABLE_NAME);
    }

    @Override
    protected User doLoad(final int id, final ResultSet result) throws SQLException {
        final String loginName = result.getString(LOAD_ROW_LOGINNAME_POS);
        final String hashedUserData = result.getString(LOAD_ROW_HASHEDUSERDATA_POS);
        return new User(id, loginName, hashedUserData);
    }

    @Override
    protected void doInsert(final User subject, final PreparedStatement insertStatement) throws SQLException {
        insertStatement.setString(INSERT_PARAM_USERID_POS, subject.getLoginName());
        insertStatement.setString(INSERT_PARAM_HASHEDUSERDATA_POS, subject.getHashedUserData());
    }

    @Override
    public User find(final int id) {
        return (User) abstractFind(id);
    }

    @Override
    public User find(final Integer id) {
        return find(id.intValue());
    }

    @Override
    public User findByLoginName(final String loginName) {
        try {
            final Connection db = getConnection();
            final PreparedStatement findStatement = db.prepareStatement(findByLoginNameStatement());
            findStatement.setString(1, loginName);
            final ResultSet rs = findStatement.executeQuery();

            if (!rs.next())  {
                findStatement.close();
                return null;
            }

            final User result = load(rs);
            findStatement.close();
            closeConnection(db);
            return result;
        } catch (SQLException ex) {
            throw new DomainModelException(ex);
        }
    }

    @Override
    public void update(final User subject) {
        try {
            final Connection db = getConnection();
            final PreparedStatement updateStatement = db.prepareStatement(updateStatement());
            updateStatement.setString(UPDATE_PARAM_LOGINNAME_POS, subject.getLoginName());
            updateStatement.setString(UPDATE_PARAM_HASHEDUSERDATA_POS, subject.getHashedUserData());
            updateStatement.setInt(UPDATE_PARAM_USERID_POS, subject.getId());
            updateStatement.execute();
            updateStatement.close();
            closeConnection(db);
        } catch (SQLException ex) {
            throw new DomainModelException(ex);
        }
    }
}
