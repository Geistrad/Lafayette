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

import com.google.common.collect.Lists;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.lafayette.server.DomainModelException;
import org.lafayette.server.domain.DomainObject;
import org.lafayette.server.mapper.id.IntegerIdentityMap;

/**
 * Base implementation for database mappers.
 *
 * @param <T> type of mapped {@link DomainObject domain object}
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseMapper<T extends DomainObject> implements Mapper<T> {

    /**
     * Generic find by id query.
     *
     * First format parameter are the colum names.
     * Second format parameter is he table name.
     * Third format parameter is the primary key field name.
     * First prepared statement parameter is the id.
     */
    private static final String SQL_FIND_BY_ID = "select %s from %s where %s = ?";
    /**
     * Generic find all query.
     *
     * First format parameter are the colum names.
     * Second format parameter is he table name.
     * Third format parameter is the limit.
     * Fourth format parameter is the offset.
     */
    private static final String SQL_FIND_ALL = "select %s from %s limit %d offset %s";
    /**
     * Generic query to find the maximum id used.
     *
     * First format parameter is the primary key field name.
     * Second format parameter is the table name.
     */
    private static final String SQL_MAX_PK = "select max(%s) from %s";
    /**
     * Generic query to delete by id.
     *
     * First format parameter is the table name.
     * Second format parameter is the primary key field name.
     * First prepared statement parameter is the id.
     */
    private static final String SQL_DELETE = "delete from %s where %s = ?";

    /**
     * Used JDBC database connection.
     */
    protected final Connection db;
    /**
     * Caches domain object loaded from database in memory.
     *
     * Key is the {@link DomainObject#getId() primary key} of the domain object.
     */
    private final IntegerIdentityMap<T> idMap;

    /**
     * Dedicated constructor.
     *
     * @param db used database connection
     * @param idMap maps identity map for mapped domain objects
     */
    public BaseMapper(final Connection db, final IntegerIdentityMap<T> idMap) {
        super();
        this.db = db;
        this.idMap = idMap;
    }

    /**
     * SQL statement to find a {@link DomainObject domain object} by it's ID.
     *
     * @return SQL prepared statement string
     */
    protected abstract String findByIdStatement();

    /**
     * Generates the find by id SQL statement.
     *
     * @param columns comma separated column names
     * @param table table name
     * @param pk primary key field name
     * @return SQL prepared statement string
     */
    protected String findByIdStatement(final String columns, final String table, final String pk) {
        return String.format(SQL_FIND_BY_ID, columns, table, pk);
    }

    /**
     * SQL statement to find all {@link DomainObject domain object}.
     *
     * @param limit limit of result record sets
     * @param offset offset of result record sets
     * @return SQL prepared statement string
     */
    protected abstract String findAllStatement(final int limit, final int offset);

    /**
     * Generates the find all SQL statement.
     *
     * @param columns comma separated column names
     * @param table table name
     * @param limit limit parameter
     * @param offset offset parameter
     * @return SQL prepared statement string
     */
    protected String findAllStatement(final String columns, final String table, final int limit, final int offset) {
        return String.format(SQL_FIND_ALL, columns, table, limit, offset);
    }

    /**
     * SQL statement to find the max ID.
     *
     * @return SQL prepared statement string
     */
    protected abstract String findMaxPrimaryKeyStatement();

    /**
     * SQL statement to find the max ID.
     *
     * @param pk primary key field name
     * @param table table name
     * @return SQL prepared statement string
     */
    protected String findfindMaxPrimaryKeyStatement(final String pk, final String table) {
        return String.format(SQL_MAX_PK, pk, table);
    }

    /**
     * SQL statement to insert record set.
     *
     * @return SQL prepared statement string
     */
    protected abstract String insertStatement();

    /**
     * SQL statement to delete record set.
     *
     * @return SQL prepared statement string
     */
    protected abstract String deleteStatement();

    /**
     * SQL statement to delete record set.
     *
     * @param table table name
     * @param pk primary key field name
     * @return SQL prepared statement string
     */
    protected String deleteStatement(final String table, final String pk) {
        return String.format(SQL_DELETE, table, pk);
    }

    /**
     * Mapper specific implementation for creating a domain object from result set.
     *
     * @param id identity of domain object
     * @param result data source
     * @return may return {@code null} if no results in result set
     * @throws SQLException if any SQL error occurs
     */
    protected abstract T doLoad(final int id, final ResultSet result) throws SQLException;

    /**
     * Mapper specific implementation for inserting a domain object.
     *
     * @param subject domain object to persist
     * @param insertStatement statement for insert
     * @throws SQLException if any SQL error occurs
     */
    protected abstract void doInsert(final T subject, final PreparedStatement insertStatement) throws SQLException;

    /**
     * Generic find method.
     *
     * @param id primary key id of domain object.
     * @return found domain object
     */
    protected T abstractFind(final int id) {
        if (idMap.containsIdentity(id)) {
            return idMap.get(id);
        }

        try {
            final PreparedStatement findStatement = db.prepareStatement(findByIdStatement());
            findStatement.setInt(1, id);
            final ResultSet rs = findStatement.executeQuery(); // NOPMD NExt is checked in next line.

            if (!rs.next()) {
                throw new DomainModelException(String.format("There is no record set whith primary key '%d'!", id));
            }

            final T result = load(rs);
            findStatement.close();
            return result;
        } catch (SQLException ex) {
            throw new DomainModelException(ex);
        }
    }

    /**
     * Mapper unspecific general implementation for creating a domain object from result set.
     *
     * @param rs data source
     * @return may return {@code null} if no results in result set
     * @throws SQLException if any SQL error occurs
     */
    protected T load(final ResultSet rs) throws SQLException {
        final Integer id = Integer.valueOf(rs.getInt(1));

        if (idMap.containsIdentity(id)) {
            return idMap.get(id);
        }

        final T result = doLoad(id, rs);
        idMap.put(id, result);
        return result;
    }

    /**
     * Mapper unspecific general implementation for creating a list of domain object from result set.
     *
     * @param rs data source
     * @return never {@code null}, maybe empty collection
     * @throws SQLException if any SQL error occurs
     */
    protected Collection<T> loadAll(final ResultSet rs) throws SQLException {
        final Collection<T> result = Lists.newArrayList();

        while (rs.next()) {
            result.add(load(rs));
        }

        return result;
    }

    /**
     * Mapper unspecific general implementation to find all domain objects.
     *
     * @param limit number of maximum objects
     * @param offset shift of list start
     * @return never {@code null}, maybe empty collection
     */
    public Collection<T> findAll(final int limit, final int offset) {
        return findMany(new BaseStatementSource() {
            @Override
            public String sql() {
                return findAllStatement(limit, offset);
            }

            @Override
            public Object[] parameters() {
                return new Object[]{};
            }
        });
    }

    /**
     * General mapper unspecific implementation to find many objects.
     *
     * @param source statement source to get prepared statement from
     * @return never {@code null}, maybe empty collection
     */
    protected Collection<T> findMany(final StatementSource source) {
        try {
            final PreparedStatement stmt = source.prepare(db);
            final ResultSet rs = stmt.executeQuery(); // NOPMD Next is invoked on loadAll(rs).
            final Collection<T> result = loadAll(rs);
            stmt.close();

            return result;
        } catch (SQLException ex) {
            throw new DomainModelException(ex.getMessage() + " " + ex.getErrorCode() + " " + ex.getSQLState(), ex);
        }
    }

    @Override
    public int insert(final T subject) {
        try {
            final PreparedStatement insertStatement = db.prepareStatement(insertStatement());
            subject.setId(findNextDatabaseId());
            insertStatement.setInt(1, subject.getId());
            doInsert(subject, insertStatement);
            insertStatement.execute();
            idMap.put(subject.getId(), subject);
            insertStatement.close();
            return subject.getId();
        } catch (SQLException ex) {
            throw new DomainModelException(ex);
        }
    }

    @Override
    public void delete(final T subject) {
        try {
            final PreparedStatement updateStatement = db.prepareStatement(deleteStatement());
            updateStatement.setInt(1, subject.getId());
            updateStatement.execute();
            idMap.remove(subject.getId());
            updateStatement.close();
        } catch (SQLException ex) {
            throw new DomainModelException(ex);
        }
    }

    /**
     * General implementation to find next free identity for a table.
     *
     * @return always greater 0
     */
    private int findNextDatabaseId() {
        try {
            final PreparedStatement findMaxPrimaryKeyStatement = db.prepareStatement(findMaxPrimaryKeyStatement());
            final ResultSet rs = findMaxPrimaryKeyStatement.executeQuery();
            findMaxPrimaryKeyStatement.close();

            if (!rs.next()) {
                throw new DomainModelException("Can't determine next id!");
            }

            return rs.getInt(1) + 1;
        } catch (SQLException ex) {
            throw new DomainModelException(ex);
        }
    }
}
