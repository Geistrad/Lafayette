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
 * Interface for identity mappers.
 *
 * @param <I> id type
 * @param <D> domain object type
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface IdentityMap<I, D extends DomainObject> {

    /**
     * Whether a domain object is mapped.
     *
     * @param domainObject asked domain object
     * @return {@code true} if this domain object is in the map., else {@code false}
     */
    boolean containsDomainObject(D domainObject);

    /**
     * Whether a identity is mapped.
     *
     * @param id asked identity
     * @return {@code true} if this identity is in the map., else {@code false}
     */
    boolean containsIdentity(I id);

    /**
     * Get the domain object with an identity.
     *
     * @param id identity of object
     * @return may return {@code null}, if no object mapped to given identity
     */
    D get(I id);

    /**
     * If the map is empty.
     *
     * @return {@code true} if {@link #isEmpty()} returns 0, else {@code false}
     */
    boolean isEmpty();

    /**
     * Put a domain object with an identity into the map.
     *
     * @param id domain object identity
     * @param domainObject domain object
     * @return previous mapped domain object
     */
    D put(final I id, final D domainObject);

    /**
     * Return how many identities are in the map.
     *
     * @return count of identities
     */
    int size();

}
