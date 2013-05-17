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
import java.util.Map;
import org.lafayette.server.ApplicationException;
import org.lafayette.server.domain.DomainObject;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseMapper<T extends DomainObject> implements Mapper<T> {

    /**
     * Caches domain object loaded from database in memory.
     *
     * Key is the {@link DomainObject#getId() primary key} of the domain object.
     */
    private final Map<Long, T> loadedMap = Maps.newHashMap();
    /**
     * Used JDBC database connection.
     */
    protected final Connection db;

    public BaseMapper(final Connection db) {
        super();
        this.db = db;
    }

    protected abstract String findStatement();

    protected abstract String findAllStatement(final int limit, final int offset);

    protected abstract String findMaxPrimaryKeyStatement();

    protected abstract String insertStatement();
    protected abstract String deleteStatement();

    protected abstract T doLoad(final Long id, final ResultSet result) throws SQLException;

    protected abstract void doInsert(final T subject, final PreparedStatement insertStatement) throws SQLException;

    protected T abstractFind(final Long id) {
        if (loadedMap.containsKey(id)) {
            return loadedMap.get(id);
        }

        try {
            final PreparedStatement findStatement = db.prepareStatement(findStatement());
            findStatement.setLong(1, id.longValue());
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
        final Long id = new Long(rs.getLong(1));

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
    public Long insert(final T subject) {
        try {
            final PreparedStatement insertStatement = db.prepareStatement(insertStatement());
            subject.setId(findNextDatabaseId());
            insertStatement.setLong(1, subject.getId().longValue());
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
            updateStatement.setLong(1, subject.getId());
            updateStatement.execute();
            loadedMap.remove(subject.getId());
            updateStatement.close();
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

    private Long findNextDatabaseId() {
        try {
            final PreparedStatement findMaxPrimaryKeyStatement = db.prepareStatement(findMaxPrimaryKeyStatement());
            final ResultSet rs = findMaxPrimaryKeyStatement.executeQuery();
            findMaxPrimaryKeyStatement.close();
            rs.next();
            return rs.getLong(1) + 1;
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }
}
