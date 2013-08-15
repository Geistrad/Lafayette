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

package org.lafayette.server.domain.mapper.id;

import org.lafayette.server.domain.DomainObject;

/**
 * Implements identity mapping for integer ids.
 *
 * @param <D> domain object type
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class IntegerIdentityMap<D extends DomainObject> extends BaseIdentityMap<Integer, D> {

    /**
     * Whether a id is mapped.
     *
     * @param id asked identity
     * @return {@code true} if this identity is in the map., else {@code false}
     */
    public boolean containsIdentity(final int id) {
        return containsIdentity(Integer.valueOf(id));
    }

    /**
     * Get the domain object with an id.
     *
     * @param id identity of object
     * @return may return {@code null}, if no object mapped to given identity
     */
    public D get(final int id) {
        return get(Integer.valueOf(id));
    }

    /**
     * Put a domain object with an id into the map.
     *
     * @param id domain object identity
     * @param domainObject domain object
     * @return previous mapped domain object
     */
    public D put(final int id, final D domainObject) {
        return put(Integer.valueOf(id), domainObject);
    }

}
