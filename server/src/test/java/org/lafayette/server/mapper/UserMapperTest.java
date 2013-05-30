/*
 * LICENSE
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package org.lafayette.server.mapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.Test;
import org.lafayette.server.domain.User;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.Matchers.*;
import org.lafayette.server.ApplicationException;
import org.lafayette.server.mapper.id.IntegerIdentityMap;

/**
 * Tests for {@link UserMapper}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UserMapperTest extends DbTestCase {

    @Override
    protected String tableSql() {
        return "table_user.sql";
    }

    @Override
    protected String testDataSql() {
        return "data_user.sql";
    }

    @Test
    public void findUserById() {
        final UserMapper sut = new UserMapper(db(), new IntegerIdentityMap<User>());
        User user = sut.find(1);
        assertThat(user.getId(), is(1));
        assertThat(user.getLoginName(), is("Foo"));
        assertThat(user.getHashedUserData(), is("b9f46238b289f23ba807973840655032"));

        user = sut.find(2);
        assertThat(user.getId(), is(2));
        assertThat(user.getLoginName(), is("Bar"));
        assertThat(user.getHashedUserData(), is("043bd227eaa879d438e7c1dfea568bc9"));
    }

    @Test
    public void findUserById_caches() {
        final UserMapper sut = new UserMapper(db(), new IntegerIdentityMap<User>());
        final User user = sut.find(1);
        assertThat(user, is(sameInstance(sut.find(1))));
    }

    @Test
    public void findByLoginName() {
        final UserMapper sut = new UserMapper(db(), new IntegerIdentityMap<User>());
        final User user = sut.findByLoginName("Baz");
        assertThat(user.getId(), is(3));
        assertThat(user.getLoginName(), is("Baz"));
        assertThat(user.getHashedUserData(), is("aa82cc74b4a932c06d4ea5a9ac38cf5e"));
    }

    @Test
    public void findByLoginName_caches() {
        final UserMapper sut = new UserMapper(db(), new IntegerIdentityMap<User>());
        final User user = sut.findByLoginName("Baz");
        assertThat(user, is(sameInstance(sut.findByLoginName("Baz"))));
    }

    @Test
    public void findAll() {
        final UserMapper sut = new UserMapper(db(), new IntegerIdentityMap<User>());

        for (final User user : sut.findAll(10, 0)) {
            final int userId = user.getId();

            switch (userId) {
                case 1:
                    assertThat(user.getLoginName(), is("Foo"));
                    assertThat(user.getHashedUserData(), is("b9f46238b289f23ba807973840655032"));
                    break;
                case 2:
                    assertThat(user.getLoginName(), is("Bar"));
                    assertThat(user.getHashedUserData(), is("043bd227eaa879d438e7c1dfea568bc9"));
                    break;
                case 3:
                    assertThat(user.getLoginName(), is("Baz"));
                    assertThat(user.getHashedUserData(), is("aa82cc74b4a932c06d4ea5a9ac38cf5e"));
                    break;
                default:
                    fail("Unexpected user id: " + userId);
            }
        }
    }

    @Test
    public void findAll_emptyTable() throws SQLException, ClassNotFoundException, IOException, URISyntaxException {
        destroyTestDatabase();
        startTestDatabase(false);
        final UserMapper sut = new UserMapper(db(), new IntegerIdentityMap<User>());
        final Collection<User> users = sut.findAll(10, 0);
        assertThat(users, is(empty()));
    }

    @Test
    public void insert() {
        final String loginName = "snafu";
        final String hashedUserData = "snafupw";
        User user = new User(loginName, hashedUserData);
        UserMapper sut = new UserMapper(db(), new IntegerIdentityMap<User>());
        final int id = sut.insert(user);

        // avoid cache
        sut = new UserMapper(db(), new IntegerIdentityMap<User>());
        user = sut.find(id);
        assertThat(user.getLoginName(), is(loginName));
        assertThat(user.getHashedUserData(), is(hashedUserData));
    }

    @Test
    public void update() {
        UserMapper sut = new UserMapper(db(), new IntegerIdentityMap<User>());
        User user = sut.find(2);
        assertThat(user.getId(), is(2));
        assertThat(user.getLoginName(), is("Bar"));
        assertThat(user.getHashedUserData(), is("043bd227eaa879d438e7c1dfea568bc9"));

        user.setLoginName("snafu");
        user.setHashedUserData("snafupw");
        sut.update(user);

        user = sut.find(2); // get from cache
        assertThat(user.getId(), is(2));
        assertThat(user.getLoginName(), is("snafu"));
        assertThat(user.getHashedUserData(), is("snafupw"));

        sut = new UserMapper(db(), new IntegerIdentityMap<User>());
        user = sut.find(2);
        assertThat(user.getId(), is(2));
        assertThat(user.getLoginName(), is("snafu"));
        assertThat(user.getHashedUserData(), is("snafupw"));
    }

    @Test
    public void delete() {
        UserMapper sut = new UserMapper(db(), new IntegerIdentityMap<User>());
        final User user = sut.find(2);
        assertThat(user.getId(), is(2));
        assertThat(user.getLoginName(), is("Bar"));
        assertThat(user.getHashedUserData(), is("043bd227eaa879d438e7c1dfea568bc9"));

        sut.delete(user);

        try {
            sut.find(2);
            fail("Expected exception not thrown!");
        } catch (ApplicationException ex) {
            assertThat(ex.getMessage(), is("There is no record set whith primary key '2'!"));
        }

        // avoid cache
        sut = new UserMapper(db(), new IntegerIdentityMap<User>());

        try {
            sut.find(2);
            fail("Expected exception not thrown!");
        } catch (ApplicationException ex) {
            assertThat(ex.getMessage(), is("There is no record set whith primary key '2'!"));
        }
    }
}
