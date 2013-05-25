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

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collection;
import org.junit.Test;
import org.lafayette.server.domain.Role;
import org.lafayette.server.mapper.id.IntegerIdentityMap;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.lafayette.server.ApplicationException;
import org.lafayette.server.domain.User;

/**
 * Tests for {@link RoleMapper}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RoleMapperTest extends DbTestCase {

    private RoleMapper sut = new RoleMapper(db(), new IntegerIdentityMap<Role>());

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
        Role role = sut.find(1);
        assertThat(role.getId(), is(1));
        assertThat(role.getName(), is("admin"));
        assertThat(role.getDescription(), is("Administrative user with all privileges."));

        role = sut.find(2);
        assertThat(role.getId(), is(2));
        assertThat(role.getName(), is("user"));
        assertThat(role.getDescription(), is("Standart user with limited privileges."));
    }

    @Test
    public void findRoleById_caches() {
        final Role role = sut.find(1);
        assertThat(role, is(sameInstance(sut.find(1))));
    }

    @Test
    public void findByName() {
        final Role role = sut.findByName("user");
        assertThat(role.getId(), is(2));
        assertThat(role.getName(), is("user"));
        assertThat(role.getDescription(), is("Standart user with limited privileges."));
    }

    @Test
    public void findByName_caches() {
        final Role user = sut.findByName("user");
        assertThat(user, is(sameInstance(sut.findByName("Baz"))));
    }

    @Test
    public void findAll() {
        for (final Role role : sut.findAll(10, 0)) {
            final int roleId = role.getId();

            switch (roleId) {
                case 1:
                    assertThat(role.getName(), is("admin"));
                    assertThat(role.getDescription(), is("Administrative user with all privileges."));
                    break;
                case 2:
                    assertThat(role.getName(), is("user"));
                    assertThat(role.getDescription(), is("Standart user with limited privileges."));
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
        final Collection<Role> roles = sut.findAll(10, 0);
        assertThat(roles, is(empty()));
    }

    @Test
    public void insert() {
        final String name = "snafu";
        final String desc = "snafupw";
        final String salt = "snafusalt";
        Role user = new Role(name, desc);
        final int id = sut.insert(user);

        // avoid cache
        sut = new RoleMapper(db(), new IntegerIdentityMap<Role>());
        user = sut.find(id);
        assertThat(user.getName(), is(name));
        assertThat(user.getDescription(), is(desc));
    }

    @Test
    public void update() {
        Role role = sut.find(2);
        assertThat(role.getId(), is(2));
        assertThat(role.getName(), is("user"));
        assertThat(role.getDescription(), is("Standart user with limited privileges."));

        role.setName("fpp");
        role.setDescription("bar");
        sut.update(role);

        role = sut.find(2); // get from cache
        assertThat(role.getId(), is(2));
        assertThat(role.getName(), is("foo"));
        assertThat(role.getDescription(), is("bar"));

        sut = new RoleMapper(db(), new IntegerIdentityMap<Role>());
        role = sut.find(2);
        assertThat(role.getId(), is(2));
        assertThat(role.getName(), is("foo"));
        assertThat(role.getDescription(), is("bar"));
    }

    @Test
    public void delete() {
        final Role role = sut.find(2);
        assertThat(role.getId(), is(2));
        assertThat(role.getName(), is("user"));
        assertThat(role.getDescription(), is("Standart user with limited privileges."));

        sut.delete(role);

        try {
            sut.find(2);
            fail("Expected exception not thrown!");
        } catch (ApplicationException ex) {
            assertThat(ex.getMessage(), is("There is no record set whith primary key '2'!"));
        }
    }
}
