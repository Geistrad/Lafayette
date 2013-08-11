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
import java.sql.SQLException;

/**
 * Base implementation for a statement source.
 *
 * Prepare a statement source:
 * <pre>
 * executePreparedStatement(new BaseStatementSource() {
 *
 *      &#064;Override public String sql() {
 *          return "select foo, bar, baz from snafu where id = ?";
 *      }
 *
 *      &#064;Override public Object[] parameters() {
 *          return new Object[]{ Integer.valueOf(23) };
 *      }
 * });
 * </pre>
 *
 * Use a statement source:
 * <pre>
 * PreparedStatement stmt = source.prepare(db);
 * ResultSet rs = stmt.executeQuery();
 * </pre>
 *
 * The parameters will be set in the same order on the prepared statement as
 * they occurs in the {@link #parameters() parameters array}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseStatementSource implements StatementSource {

    /**
     * Default parameters.
     */
    private static final Object[] ENMPTY_PARAMS = new Object[]{};

    @Override
    public final PreparedStatement prepare(final Connection db) throws SQLException {
        final PreparedStatement stmt = db.prepareStatement(sql());
        final Object[] parameters = parameters();

        if (parameters.length > 0) {
            for (int i = 0; i < parameters.length; ++i) {
                stmt.setObject(i + 1, parameters[i]);
            }
        }

        return stmt;
    }

    /**
     * Return by default an empty array.
     *
     * Override this method to provide parameters for prepared statement.
     *
     * @return always return same empty array.
     */
    @Override
    public Object[] parameters() {
        return ENMPTY_PARAMS;
    }
}
