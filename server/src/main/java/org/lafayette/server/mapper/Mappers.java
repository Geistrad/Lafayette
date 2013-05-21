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

import java.sql.Connection;
import org.lafayette.server.domain.User;
import org.lafayette.server.mapper.id.IntegerIdentityMap;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Mappers {

    private final Connection db;
    private final IntegerIdentityMap<User> userIdMap = new IntegerIdentityMap<User>();

    public Mappers(final Connection db) {
        this.db = db;
    }

    public UserMapper createUserMapper() {
        return new UserMapper(db, userIdMap);
    }
}
