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
import java.util.Collection;
import org.lafayette.server.DomainModelException;
import org.lafayette.server.domain.Role;
import org.lafayette.server.domain.Role.Names;
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
    private static final String COLUMNS = PK_FIELD_NAME + ", userId, name";
    /**
     * Insert SQL statement .
     */
    private static final String SQL_INSERT = "insert into %s values (?, ?, ?)";
    /**
     * Find by name SQL statement .
     */
    private static final String SQL_FIND_BY_NAME = "select %s from %s where name like ?";
    /**
     * Find by user id SQL statement .
     */
    private static final String SQL_FIND_BY_USER_ID = "select %s from %s where userId like ?";
    /**
     * Update SQL statement .
     */
    private static final String SQL_UPDATE = "update %s set userId = ?, name = ? where %s = ?";

    /**
     * Dedicated constructor.
     *
     * @param db database connection
     * @param idMap identity map
     */
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
        return findMaxPrimaryKeyStatement(PK_FIELD_NAME, TABLE_NAME);
    }

    /**
     * SQL statement to find role by name.
     *
     * @return SQL prepared statement string
     */
    private String findByNameStatement() {
        return String.format(SQL_FIND_BY_NAME, COLUMNS, TABLE_NAME);
    }

    /**
     * SQL statement to update role.
     *
     * @return SQL prepared statement string
     */
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
        final int userId = result.getInt(2);
        final String name = result.getString(3);
        return new Role(id, userId, Role.Names.forName(name));
    }

    @Override
    protected void doInsert(final Role subject, final PreparedStatement insertStatement) throws SQLException {
        insertStatement.setInt(2, subject.getUserId());
        insertStatement.setString(3, subject.getName().toString());
    }

    @Override
    public void update(Role subject) {
        try {
            final PreparedStatement updateStatement = db.prepareStatement(updateStatement());
            updateStatement.setInt(1, subject.getUserId());
            updateStatement.setString(2, subject.getName().toString());
            updateStatement.setInt(3, subject.getId());
            updateStatement.execute();
            updateStatement.close();
        } catch (SQLException ex) {
            throw new DomainModelException(ex);
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
    public Collection<Role> findByName(final Names name) {
        // TODO Implement
        throw new UnsupportedOperationException("Not implemented yet!");
//        try {
//            final PreparedStatement findStatement = db.prepareStatement(findByNameStatement());
//            findStatement.setString(1, name);
//            final ResultSet rs = findStatement.executeQuery();
//
//            if (!rs.next()) {
//                throw new DomainModelException(String.format("There is no role with name %s!", name));
//            }
//
//            final Role result = load(rs);
//            findStatement.close();
//            return result;
//        } catch (SQLException ex) {
//            throw new DomainModelException(ex);
//        }
    }

    @Override
    public Collection<Role> findByUserId(int userId) {
        // TODO Implement
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
