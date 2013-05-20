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
import com.google.common.collect.Maps;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.lafayette.server.ApplicationException;
import org.lafayette.server.domain.DomainObject;
import org.lafayette.server.log.Log;
import org.lafayette.server.log.Logger;

/**
 * Base implementation for database mappers.
 *
 * @param <T> type of mapped {@link DomainObject domain object}
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseMapper<T extends DomainObject> implements Mapper<T> {

    /**
     * Logging facility.
     */
    private static final Logger LOG = Log.getLogger(BaseMapper.class);
    /**
     * Used JDBC database connection.
     */
    protected final Connection db;
    /**
     * Caches domain object loaded from database in memory.
     *
     * Key is the {@link DomainObject#getId() primary key} of the domain object.
     */
    private final Map<Integer, T> loadedMap = Maps.newHashMap();

    /**
     * Dedicated constructor.
     *
     * @param db used database connection
     */
    public BaseMapper(final Connection db) {
        super();
        this.db = db;
    }

    /**
     * SQL statement to find a {@link DomainObject domain object} by it's ID.
     *
     * @return SQL prepared statement string
     */
    protected abstract String findByIdStatement();

    /**
     * SQL statement to find all {@link DomainObject domain object}.
     *
     * @param limit limit of result record sets
     * @param offset offset of result record sets
     * @return SQL prepared statement string
     */
    protected abstract String findAllStatement(final int limit, final int offset);

    /**
     * SQL statement to find the max ID.
     *
     * @return SQL prepared statement string
     */
    protected abstract String findMaxPrimaryKeyStatement();

    /**
     *
     * @return SQL prepared statement string
     */
    protected abstract String insertStatement();

    /**
     *
     * @return SQL prepared statement string
     */
    protected abstract String deleteStatement();

    protected abstract T doLoad(final int id, final ResultSet result) throws SQLException;

    protected abstract void doInsert(final T subject, final PreparedStatement insertStatement) throws SQLException;

    protected T abstractFind(final int id) {
        if (loadedMap.containsKey(id)) {
            return loadedMap.get(id);
        }

        try {
            final PreparedStatement findStatement = db.prepareStatement(findByIdStatement());
            findStatement.setInt(1, id);
            final ResultSet rs = findStatement.executeQuery();

            if (!rs.next()) {
                throw new ApplicationException(String.format("There is no record set whith primary key '%d'!", id));
            }

            final T result = load(rs);
            findStatement.close();
            return result;
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

    protected T load(final ResultSet rs) throws SQLException {
        final Integer id = Integer.valueOf(rs.getInt(1));

        if (loadedMap.containsKey(id)) {
            return loadedMap.get(id);
        }

        final T result = doLoad(id, rs);
        loadedMap.put(id, result);
        return result;
    }

    protected Collection<T> loadAll(final ResultSet rs) throws SQLException {
        final Collection<T> result = Lists.newArrayList();

        while (rs.next()) {
            result.add(load(rs));
        }

        return result;
    }

    public Collection<T> findAll(final int limit, final int offset) {
        return findMany(new StatementSource() {
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

    protected Collection<T> findMany(final StatementSource source) {
        final PreparedStatement stmt;

        try {
            final String sql = source.sql();
            stmt = db.prepareStatement(sql);

            if (null == stmt) {
                LOG.error("Can't prepare statement for SQL '%s'!", sql);
                return Collections.emptyList();
            }

            for (int i = 0; i < source.parameters().length; ++i) {
                stmt.setObject(i + 1, source.parameters()[i]);
            }

            final ResultSet rs = stmt.executeQuery();
            final Collection<T> result = loadAll(rs);
            stmt.close();

            return result;
        } catch (SQLException ex) {
            throw new ApplicationException(ex.getMessage() + " " + ex.getErrorCode() + " " + ex.getSQLState(), ex);
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
            loadedMap.put(subject.getId(), subject);
            insertStatement.close();
            return subject.getId();
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

    @Override
    public void delete(final T subject) {
        try {
            final PreparedStatement updateStatement = db.prepareStatement(deleteStatement());
            updateStatement.setInt(1, subject.getId());
            updateStatement.execute();
            loadedMap.remove(subject.getId());
            updateStatement.close();
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

    private int findNextDatabaseId() {
        try {
            final PreparedStatement findMaxPrimaryKeyStatement = db.prepareStatement(findMaxPrimaryKeyStatement());
            final ResultSet rs = findMaxPrimaryKeyStatement.executeQuery();
            findMaxPrimaryKeyStatement.close();
            rs.next();
            return rs.getInt(1) + 1;
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }
}
