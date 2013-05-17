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

/**
 * Interface for domain object.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface DomainObject {

    /**
     * Get the unique id.
     *
     * @return unique id
     */
    int getId();
    /**
     * Set the unique id.
     *
     * Implementors must guaranty that the id is set only one time.
     *
     * @param id unique id
     */
    void setId(int id);

}
