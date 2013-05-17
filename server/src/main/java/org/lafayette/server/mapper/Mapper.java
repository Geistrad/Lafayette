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

import org.lafayette.server.domain.DomainObject;

/**
 * Interface for mapper which maps {@link DomainObject domain objects} to the database.
 *
 * @param <T> concrete type of domain object
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Mapper<T extends DomainObject> {

    /**
     * Insert domain object into database.
     *
     * @param subject domain object to insert
     * @return primary key id of inserted record set
     */
    Long insert(T subject);
    /**
     * updates domain object from database.
     *
     * @param subject domain object to update
     */
    void update(final T subject);
    /**
     * Deletes domain object from database.
     *
     * @param subject domain object to delete
     */
    void delete(final T subject);

}
