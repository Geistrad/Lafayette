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
import java.sql.Statement;
import javax.sql.DataSource;
import org.junit.Test;
import org.lafayette.server.domain.DomainObject;
import org.lafayette.server.domain.mapper.id.IntegerIdentityMap;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link BaseMapper}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BaseMapperTest extends DbTestCase {

    @Override
    protected String tableSql() {
        return "table_base_mapper.sql";
    }

    @Override
    protected String testDataSql() {
        return "data_base_mapper.sql";
    }

    @Test
    public void findNextDatabaseId() throws SQLException {
        final BaseMapper sut = new BaseMapperStub(dataSource(), new IntegerIdentityMap<DomainObject>());
        assertThat(sut.findNextDatabaseId(), is(4));
        assertThat(sut.findNextDatabaseId(), is(4));
        final Connection db = db();
        Statement insertStatement = db.createStatement();
        insertStatement.execute("insert into base_mapper values (4, 'test1')");
        insertStatement.close();
        assertThat(sut.findNextDatabaseId(), is(5));
        insertStatement = db.createStatement();
        insertStatement.execute("insert into base_mapper values (7, 'test1')");
        insertStatement.close();
        assertThat(sut.findNextDatabaseId(), is(8));
        db.close();
    }

    /**
     * Stub to make abstract class testable.
     */
    private static final class BaseMapperStub extends BaseMapper<DomainObject> {

        /**
         * Dedicated constructor.
         *
         * @param dataSource used to get database connection
         * @param idMap maps identity map for mapped domain objects
         */
        public BaseMapperStub(DataSource dataSurce, IntegerIdentityMap<DomainObject> idMap) {
            super(dataSurce, idMap);
        }

        @Override
        protected String findByIdStatement() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected String findAllStatement(int limit, int offset) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected String findMaxPrimaryKeyStatement() {
            return findMaxPrimaryKeyStatement("id", "base_mapper");
        }

        @Override
        protected String insertStatement() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected String deleteStatement() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected DomainObject doLoad(int id, ResultSet result) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void doInsert(DomainObject subject, PreparedStatement insertStatement) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void update(DomainObject subject) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
