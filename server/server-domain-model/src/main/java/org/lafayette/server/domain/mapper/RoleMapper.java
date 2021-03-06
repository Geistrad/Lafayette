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
import java.util.Collection;
import javax.sql.DataSource;
import org.lafayette.server.domain.DomainModelException;
import org.lafayette.server.domain.Role;
import org.lafayette.server.domain.Role.Names;
import org.lafayette.server.domain.RoleFinder;
import org.lafayette.server.domain.mapper.id.IntegerIdentityMap;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RoleMapper extends BaseMapper<Role> implements RoleFinder {

    /**
     * Insert SQL statement .
     */
    private static final String SQL_INSERT = "insert into %s values (?, ?, ?)";
    /**
     * Find by tableMame SQL statement .
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
     * Position of user id parameter in prepared statement.
     */
    private static final int INSERT_PARAM_USERID_POS = 2;
    /**
     * Position of tableMame parameter in prepared statement.
     */
    private static final int INSERT_PARAM_NAME_POS = 3;
    /**
     * Position of user id parameter in prepared statement.
     */
    private static final int UPDATE_PARAM_USERID_POS = 1;
    /**
     * Position of tableMame parameter in prepared statement.
     */
    private static final int UPDATE_PARAM_NAME_POS = 2;
    /**
     * Position of role id parameter in prepared statement.
     */
    private static final int UPDATE_PARAM_ID_POS = 3;
    /**
     * Position of user id row in result set.
     */
    private static final int LOAD_ROW_USERID_POS = 2;
    /**
     * Position of role tableMame row in result set.
     */
    private static final int LOAD_ROW_NAME = 3;

    /**
     * Dedicated constructor.
     *
     * @param dataSource database connection
     * @param idMap identity map
     */
    RoleMapper(final DataSource dataSource, final IntegerIdentityMap<Role> idMap) {
        super(dataSource, idMap, new RoleTableDescription());
    }

    @Override
    protected String findByIdStatement() {
        return findByIdStatement(columns(), tableMame(), primaryKeyColumn());
    }

    @Override
    protected String findAllStatement(int limit, int offset) {
        return findAllStatement(columns(), tableMame(), limit, offset);
    }

    @Override
    protected String findMaxPrimaryKeyStatement() {
        return findMaxPrimaryKeyStatement(primaryKeyColumn(), tableMame());
    }

    /**
     * SQL statement to find role by tableMame.
     *
     * @return SQL prepared statement string
     */
    private String findByNameStatement() {
        return String.format(SQL_FIND_BY_NAME, columns(), tableMame());
    }

    private String findByUserIdStatement() {
        return String.format(SQL_FIND_BY_USER_ID, columns(), tableMame());
    }

    /**
     * SQL statement to update role.
     *
     * @return SQL prepared statement string
     */
    private String updateStatement() {
        return String.format(SQL_UPDATE, tableMame(), primaryKeyColumn());
    }

    @Override
    protected String insertStatement() {
        return String.format(SQL_INSERT, tableMame());
    }

    @Override
    protected String deleteStatement() {
        return deleteStatement(tableMame(), primaryKeyColumn());
    }

    @Override
    protected Role doLoad(int id, ResultSet result) throws SQLException {
        final int userId = result.getInt(LOAD_ROW_USERID_POS);
        final String name = result.getString(LOAD_ROW_NAME);
        return new Role(id, userId, Role.Names.forName(name));
    }

    @Override
    protected void doInsert(final Role subject, final PreparedStatement insertStatement) throws SQLException {
        insertStatement.setInt(INSERT_PARAM_USERID_POS, subject.getUserId());
        insertStatement.setString(INSERT_PARAM_NAME_POS, subject.getName().toString());
    }

    @Override
    public void update(Role subject) {
        try {
            final Connection db = getConnection();
            final PreparedStatement updateStatement = db.prepareStatement(updateStatement());
            updateStatement.setInt(UPDATE_PARAM_USERID_POS, subject.getUserId());
            updateStatement.setString(UPDATE_PARAM_NAME_POS, subject.getName().toString());
            updateStatement.setInt(UPDATE_PARAM_ID_POS, subject.getId());
            updateStatement.execute();
            updateStatement.close();
            closeConnection(db);
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
        return findMany(new BaseStatementSource() {
            @Override
            public String sql() {
                return findByNameStatement();
            }

            @Override
            public Object[] parameters() {
                return new Object[] { name.toString() };
            }

        });
    }

    @Override
    public Collection<Role> findByUserId(final int userId) {
        return findMany(new BaseStatementSource() {
            @Override
            public String sql() {
                return findByUserIdStatement();
            }

            @Override
            public Object[] parameters() {
                return new Object[] { String.valueOf(userId) };
            }

        });
    }

    /**
     * Describes mapped database table.
     */
    private static final class RoleTableDescription implements TableDescription {

        @Override
        public String primaryKeyColumn() {
            return "id";
        }

        @Override
        public String tableName() {
            return "role";
        }

        @Override
        public String columns() {
            return primaryKeyColumn() + ", userId, name";
        }
    }
}
