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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class BaseDomainObject implements DomainObject {

    private Long id;

    public BaseDomainObject(final Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof BaseDomainObject)) {
            return false;
        }

        final BaseDomainObject other = (BaseDomainObject) obj;

        return id == other.id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }

}
