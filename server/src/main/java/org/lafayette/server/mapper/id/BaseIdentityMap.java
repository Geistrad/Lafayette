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

import com.google.common.collect.Maps;
import java.util.Map;
import org.lafayette.server.domain.DomainObject;

/**
 * Base implementation for identity maps.
 *
 * Mostly delegates to a {@link java.util.Map map}.
 *
 * @param <I> id type
 * @param <D> domain object type
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class BaseIdentityMap<I, D extends DomainObject> implements IdentityMap<I, D> {

    /**
     * Holds mapping concurrent.
     */
    protected final Map<I, D> map = Maps.newConcurrentMap();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsIdentity(final I id) {
        return map.containsKey(id);
    }

    @Override
    public boolean containsDomainObject(final D domainObject) {
        return map.containsValue(domainObject);
    }

    @Override
    public D get(final I id) {
        return map.get(id);
    }

    @Override
    public D put(final I id, final D domainObject) {
        return map.put(id, domainObject);
    }

    @Override
    public D remove(final I id) {
        return map.remove(id);
    }

}
