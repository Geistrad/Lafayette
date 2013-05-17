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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.lafayette.server.ApplicationException;
import org.lafayette.server.domain.User;
import org.lafayette.server.domain.UserFinder;

/**
 * Maps {@link User user domain objects} to the database.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class UserMapper extends BaseMapper<User> implements UserFinder {

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
    private static final String COLUMNS = PK_FIELD_NAME + ", loginName, hashedPassword, salt";

    public UserMapper(final Connection db) {
        super(db);
    }

    @Override
    protected String findStatement() {
        return String.format("select %s from %s where %s = ?", COLUMNS, TABLE_NAME, PK_FIELD_NAME);
    }

    @Override
    protected String findAllStatement(final int limit, final int offset) {
        return String.format("select %s from %s limit %d offset %s", COLUMNS, TABLE_NAME, limit, offset);
    }

    @Override
    protected String findMaxPrimaryKeyStatement() {
        return String.format("select max(%s) from %s", PK_FIELD_NAME, TABLE_NAME);
    }

    private String findLoginNameStatement() {
        return String.format("select %s from %s where loginName like ?", COLUMNS, TABLE_NAME);
    }

    private String updateStatement() {
        return String.format("update %s set loginName = ?, hashedPassword = ?, salt = ? where %s = ?",
                TABLE_NAME, PK_FIELD_NAME);
    }

    @Override
    protected String deleteStatement() {
        return String.format("delete from %s where %s = ?", TABLE_NAME, PK_FIELD_NAME);
    }

    @Override
    protected String insertStatement() {
        return String.format("insert into %s values (?, ?, ?, ?)", TABLE_NAME);
    }

    @Override
    protected User doLoad(final int id, final ResultSet result) throws SQLException {
        final String loginName = result.getString(2);
        final String hashedPassword = result.getString(3);
        final String salt = result.getString(4);
        return new User(id, loginName, hashedPassword, salt);
    }

    @Override
    protected void doInsert(final User subject, final PreparedStatement insertStatement) throws SQLException {
        insertStatement.setString(2, subject.getLoginName());
        insertStatement.setString(3, subject.getHashedPassword());
        insertStatement.setString(4, subject.getSalt());
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
            final PreparedStatement findStatement = db.prepareStatement(findLoginNameStatement());
            findStatement.setString(1, loginName);
            final ResultSet rs = findStatement.executeQuery();
            rs.next();
            final User result = load(rs);
            findStatement.close();
            return result;
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

    @Override
    public void update(final User subject) {
        try {
            final PreparedStatement updateStatement = db.prepareStatement(updateStatement());
            updateStatement.setString(1, subject.getLoginName());
            updateStatement.setString(2, subject.getHashedPassword());
            updateStatement.setString(3, subject.getSalt());
            updateStatement.setInt(4, subject.getId());
            updateStatement.execute();
            updateStatement.close();
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

}
