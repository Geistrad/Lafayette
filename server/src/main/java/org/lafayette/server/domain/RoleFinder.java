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

import java.util.Collection;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface RoleFinder {

    Role find(final int id);
    Role find(final Integer id);
    Collection<Role> findByUserId(int userId);
    Collection<Role> findByName(String loginName);
    Collection<Role> findAll(int limit, int offset);
    /**
     * Insert {@link Role} into database.
     *
     * @param subject domain object to insert
     * @return primary key id of inserted record set
     */
    int insert(Role subject);
    /**
     * updates {@link Role} from database.
     *
     * @param subject domain object to update
     */
    void update(Role subject);
    /**
     * Deletes {@link Role} from database.
     *
     * @param subject domain object to delete
     */
    void delete(Role subject);
}
