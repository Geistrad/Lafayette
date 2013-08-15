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

/**
 * Describes a mapped database table.
 *
 * Mostly implemented as private inner class of a mapper.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface TableDescription {

    /**
     * Get name of primary key column.
     *
     * @return never {@code null} or empty
     */
    String primaryKeyColumn();
    /**
     * Get the name of mapped table.
     *
     * @return never {@code null} or empty
     */
    String tableName();
    /**
     * Names of all columns (inclusive primary key column) separated with comma.
     *
     * @return never {@code null} or empty
     */
    String columns();

}
