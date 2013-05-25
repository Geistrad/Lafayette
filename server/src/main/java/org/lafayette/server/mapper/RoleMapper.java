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
import org.lafayette.server.domain.Role;
import org.lafayette.server.domain.RoleFinder;
import org.lafayette.server.mapper.id.IntegerIdentityMap;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RoleMapper extends BaseMapper<Role> implements RoleFinder {

    /**
     * Name of database table.
     */
    private static final String TABLE_NAME = "role";
    /**
     * Name of primary key field.
     */
    private static final String PK_FIELD_NAME = "id";
    /**
     * Field of database table.
     */
    private static final String COLUMNS = PK_FIELD_NAME + ", name, description";
    private static final String SQL_INSERT = "insert into %s values (?, ?, ?)";
    private static final String SQL_FIND_BY_NAME = "select %s from %s where name like ?";
    private static final String SQL_UPDATE = "update %s set name = ?, description = ? where %s = ?";

    RoleMapper(final Connection db, final IntegerIdentityMap<Role> idMap) {
        super(db, idMap);
    }

    @Override
    protected String findByIdStatement() {
        return findByIdStatement(COLUMNS, TABLE_NAME, PK_FIELD_NAME);
    }

    @Override
    protected String findAllStatement(int limit, int offset) {
        return findAllStatement(COLUMNS, TABLE_NAME, limit, offset);
    }

    @Override
    protected String findMaxPrimaryKeyStatement() {
        return findfindMaxPrimaryKeyStatement(PK_FIELD_NAME, TABLE_NAME);
    }

    private String findByNameStatement() {
        return String.format(SQL_FIND_BY_NAME, COLUMNS, TABLE_NAME);
    }

    private String updateStatement() {
        return String.format(SQL_UPDATE, TABLE_NAME, PK_FIELD_NAME);
    }

    @Override
    protected String insertStatement() {
        return String.format(SQL_INSERT, TABLE_NAME);
    }

    @Override
    protected String deleteStatement() {
        return deleteStatement(TABLE_NAME, PK_FIELD_NAME);
    }

    @Override
    protected Role doLoad(int id, ResultSet result) throws SQLException {
        final String name = result.getString(2);
        final String description = result.getString(3);
        return new Role(id, name, description);
    }

    @Override
    protected void doInsert(final Role subject, final PreparedStatement insertStatement) throws SQLException {
        insertStatement.setString(2, subject.getName());
        insertStatement.setString(3, subject.getDescription());
    }

    @Override
    public void update(Role subject) {
        try {
            final PreparedStatement updateStatement = db.prepareStatement(updateStatement());
            updateStatement.setString(1, subject.getName());
            updateStatement.setString(2, subject.getDescription());
            updateStatement.setInt(3, subject.getId());
            updateStatement.execute();
            updateStatement.close();
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

    @Override
    public Role find(final int id) {
        return (Role) abstractFind(id);
    }

    @Override
    public Role find(final Integer id) {
        return find(id.intValue());
    }

    @Override
    public Role findByName(final String name) {
        try {
            final PreparedStatement findStatement = db.prepareStatement(findByNameStatement());
            findStatement.setString(1, name);
            final ResultSet rs = findStatement.executeQuery();
            rs.next();
            final Role result = load(rs);
            findStatement.close();
            return result;
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

}