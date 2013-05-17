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

import com.google.common.base.Objects;

/**
 * Base implementation for domain objects.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class BaseDomainObject implements DomainObject {

    /**
     * Default value used to signal that {@link #id} is not initialized yet.
     */
    final static int UNINITIALIZED_ID = -1;

    /**
     * Unique id.
     */
    private int id = UNINITIALIZED_ID;

    public BaseDomainObject() {
        this(UNINITIALIZED_ID);
    }

    /**
     * Dedicated constructor.
     *
     * @param id unique id
     */
    public BaseDomainObject(final int id) {
        super();
        setId(id);
    }

    @Override
    public final int getId() {
        return id;
    }

    /**
     * Set the unique id.
     *
     * @param id unique id
     * CHECKSTYLE:OFF
     * @throws IllegalStateException if {@link #getId()} != {@value #UNINITIALIZED_ID}
     * CHECKSTYLE:ON
     */
    @Override
    public final void setId(final int id) {
        if (UNINITIALIZED_ID != getId()) {
            throw new IllegalStateException(String.format("Id already initalized with value '%d'!", getId()));
        }

        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof BaseDomainObject)) {
            return false;
        }

        final BaseDomainObject other = (BaseDomainObject) obj;

        return Objects.equal(id, other.id);
    }

    @Override
    public String toString() {
        return "id=" + id;
    }

}
