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
public interface UserFinder {

    User find(final int id);
    User find(final Integer id);
    User findByLoginName(String loginName);
    Collection<User> findAll(int limit, int offset);
    /**
     * Insert {@link User} into database.
     *
     * @param subject domain object to insert
     * @return primary key id of inserted record set
     */
    int insert(User subject);
    /**
     * updates {@link User} from database.
     *
     * @param subject domain object to update
     */
    void update(User subject);
    /**
     * Deletes {@link User} from database.
     *
     * @param subject domain object to delete
     */
    void delete(User subject);

}
