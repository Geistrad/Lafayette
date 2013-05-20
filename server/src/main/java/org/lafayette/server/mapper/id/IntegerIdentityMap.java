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

package org.lafayette.server.mapper.id;

import org.lafayette.server.domain.DomainObject;

/**
 * Implements identity mapping for integer ids.
 *
 * @param <D> domain object type
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class IntegerIdentityMap<D extends DomainObject> extends BaseIdentityMap<Integer, D> {

    public boolean containsIdentity(final int id) {
        return containsIdentity(Integer.valueOf(id));
    }

    public D get(final int id) {
        return get(Integer.valueOf(id));
    }

    public D put(final int id, final D domainObject) {
        return put(Integer.valueOf(id), domainObject);
    }

}
