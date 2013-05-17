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
import java.util.List;
import java.util.Map;
import org.lafayette.server.ApplicationException;
import org.lafayette.server.domain.DomainObject;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseMapper<T extends DomainObject> implements Mapper<T> {

    private final Map<Long, T> loadedMap = Maps.newHashMap();
    protected final Connection db;

    public BaseMapper(final Connection db) {
        this.db = db;
    }

    protected abstract String findStatement();
    protected abstract String insertStatement();
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
            rs.next();
            final T result =  load(rs);
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

    protected List<T> loadAll(final ResultSet rs) throws SQLException {
        List<T> result = Lists.newArrayList();

        while (rs.next()) {
            result.add(load(rs));
        }

        return result;
    }

    protected List<T> findMany(final StatementSource source) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = db.prepareStatement(source.sql());

            for (int i = 0; i < source.parameters().length; ++i) {
                stmt.setObject(i + 1, source.parameters()[i]);
            }

            rs = stmt.executeQuery();
            List<T> result = loadAll(rs);
            stmt.close();

            return result;
        } catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

    @Override
    public Long insert(final T subject) {
        PreparedStatement insertStatement = null;

        try {
            insertStatement = db.prepareStatement(insertStatement());
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

    private Long findNextDatabaseId() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
