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

package org.lafayette.server.domain;

import org.lafayette.server.mapper.Mappers;

/**
 * Create finder objects with shared identity map.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Finders {

    /**
     * Shares the mappers for all finders.
     */
    private final Mappers mappers;

    /**
     * Dedicated constructor.
     *
     * @param m mapper factory shared for all finders.
     */
    public Finders(final Mappers m) {
        super();
        mappers = m;
    }

    /**
     * Crete a new user finder.
     *
     * @return new instance
     */
    public UserFinder forUsers() {
        return mappers.createUserMapper();
    }

    /**
     * Crete a new role finder.
     *
     * @return new instance
     */
    public RoleFinder forRoles() {
        return mappers.createRoleMapper();
    }

}
