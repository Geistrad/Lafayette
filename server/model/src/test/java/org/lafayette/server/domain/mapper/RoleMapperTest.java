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

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collection;
import org.junit.Test;
import org.lafayette.server.domain.Role;
import org.lafayette.server.domain.mapper.id.IntegerIdentityMap;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.lafayette.server.domain.DomainModelException;
import org.lafayette.server.domain.Role.Names;

/**
 * Tests for {@link RoleMapper}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RoleMapperTest extends DbTestCase {

    @Override
    protected String tableSql() {
        return "table_role.sql";
    }

    @Override
    protected String testDataSql() {
        return "data_role.sql";
    }

    @Test
    public void findRoleById() {
        final RoleMapper sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());
        Role role = sut.find(1);
        assertThat(role.getId(), is(1));
        assertThat(role.getUserId(), is(1));
        assertThat(role.getName(), is(equalTo(Names.USER)));

        role = sut.find(2);
        assertThat(role.getId(), is(2));
        assertThat(role.getUserId(), is(1));
        assertThat(role.getName(), is(equalTo(Names.ADMINISTRATOR)));
    }

    @Test
    public void findRoleById_caches() {
        final RoleMapper sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());
        final Role role = sut.find(1);
        assertThat(role, is(sameInstance(sut.find(1))));
    }

    @Test
    public void findByName() {
        final RoleMapper sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());
        final Collection<Role> role = sut.findByName(Names.USER);
        assertThat(role, hasSize(2));
        assertThat(role, contains(new Role(1, 1, Names.USER), new Role(3, 2, Names.USER)));
    }

    @Test
    public void findByUserId() {
        final RoleMapper sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());
        final Collection<Role> role = sut.findByUserId(1);
        assertThat(role, contains(new Role(1, 1, Names.USER), new Role(2, 1, Names.ADMINISTRATOR)));
    }

    @Test
    public void findAll() {
        final RoleMapper sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());

        for (final Role role : sut.findAll(10, 0)) {
            final int roleId = role.getId();

            switch (roleId) {
                case 1:
                    assertThat(role.getUserId(), is(1));
                    assertThat(role.getName(), is(equalTo(Names.USER)));
                    break;
                case 2:
                    assertThat(role.getUserId(), is(1));
                    assertThat(role.getName(), is(equalTo(Names.ADMINISTRATOR)));
                    break;
                case 3:
                    assertThat(role.getUserId(), is(2));
                    assertThat(role.getName(), is(equalTo(Names.USER)));
                    break;
                default:
                    fail("Unexpected role id: " + roleId);
            }
        }
    }

    @Test
    public void findAll_emptyTable() throws SQLException, ClassNotFoundException, IOException, URISyntaxException {
        destroyTestDatabase();
        startTestDatabase(false);
        final RoleMapper sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());
        final Collection<Role> roles = sut.findAll(10, 0);
        assertThat(roles, is(empty()));
    }

    @Test
    public void insert() {
        final int userId = 5;
        Role role = new Role(userId, Names.ANONYMOUS);
        RoleMapper sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());
        final int id = sut.insert(role);

        // avoid cache
        sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());
        role = sut.find(id);
        assertThat(role.getUserId(), is(userId));
        assertThat(role.getName(), is(Names.ANONYMOUS));
    }

    @Test
    public void update() {
        RoleMapper sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());
        Role role = sut.find(2);
        assertThat(role.getId(), is(2));
        assertThat(role.getUserId(), is(1));
        assertThat(role.getName(), is(equalTo(Names.ADMINISTRATOR)));

        role.setUserId(3);
        role.setName(Names.ANONYMOUS);
        sut.update(role);

        role = sut.find(2); // get from cache
        assertThat(role.getId(), is(2));
        assertThat(role.getUserId(), is(3));
        assertThat(role.getName(), is(equalTo(Names.ANONYMOUS)));

        sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());
        role = sut.find(2);
        assertThat(role.getId(), is(2));
        assertThat(role.getUserId(), is(3));
        assertThat(role.getName(), is(equalTo(Names.ANONYMOUS)));
    }

    @Test
    public void delete() {
        RoleMapper sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());
        final Role role = sut.find(2);
        assertThat(role.getId(), is(2));
        assertThat(role.getUserId(), is(1));
        assertThat(role.getName(), is(equalTo(Names.ADMINISTRATOR)));

        sut.delete(role);

        try {
            sut.find(2);
            fail("Expected exception not thrown!");
        } catch (DomainModelException ex) {
            assertThat(ex.getMessage(), is("There is no record set whith primary key '2'!"));
        }

        // avoid cache
        sut = new RoleMapper(dataSource(), new IntegerIdentityMap<Role>());

        try {
            sut.find(2);
            fail("Expected exception not thrown!");
        } catch (DomainModelException ex) {
            assertThat(ex.getMessage(), is("There is no record set whith primary key '2'!"));
        }
    }
}
