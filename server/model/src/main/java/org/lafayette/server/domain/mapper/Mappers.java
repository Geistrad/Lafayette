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

import javax.sql.DataSource;
import org.lafayette.server.domain.Role;
import org.lafayette.server.domain.User;
import org.lafayette.server.domain.mapper.id.IntegerIdentityMap;

/**
 * Factory to retrieve domain object mappers from.
 *
 * This factory guaranty that all mappers for the same domain object type share the
 * same identity map, if the mappers are created by the same factory instance.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Mappers {

    /**
     * Used database connection.
     */
    private final DataSource dataSource;
    /**
     * Identity map for {@link User user ovjects}.
     */
    private final IntegerIdentityMap<User> userIdMap = new IntegerIdentityMap<User>();
    /**
     * Identity map for {@link Role role ovjects}.
     */
    private final IntegerIdentityMap<Role> roleIdMap = new IntegerIdentityMap<Role>();

    /**
     * Dedicated constructor.
     *
     * @param dataSource used database connection
     */
    public Mappers(final DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    /**
     * Create a new user mapper.
     *
     * @return always new instance, but w/ shared identity map
     */
    public UserMapper createUserMapper() {
        return new UserMapper(dataSource, userIdMap);
    }

    /**
     * Create a new role mapper.
     *
     * @return always new instance, but w/ shared identity map
     */
    public RoleMapper createRoleMapper() {
        return new RoleMapper(dataSource, roleIdMap);
    }


}
