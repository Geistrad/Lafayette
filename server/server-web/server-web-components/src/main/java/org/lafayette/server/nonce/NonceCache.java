/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package org.lafayette.server.nonce;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * Auto cleaning cache for nonce.
 *
 * XXX Consider using byte[] instead of string.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class NonceCache {

    /**
     * Default capacity.
     */
    private static final int DEFAULT_CAPACITY = 1000;
    /**
     * Default threshold.
     */
    private static final int DEFAULT_THRESHOLD = 10;
    /**
     * Maximum number of nonces to cache.
     */
    private final int capacity;
    /**
     * If size() >= capacity - capacity / threshold then garbage collect.
     */
    private final int threshold;
    /**
     * Collects the nonce strings.
     */
    private final List<String> nonces;

    /**
     * Initializes capacity with {@link #DEFAULT_CAPACITY} and threshold with {@link #DEFAULT_CAPACITY}.
     */
    public NonceCache() {
        this(DEFAULT_CAPACITY, DEFAULT_THRESHOLD);
    }

    /**
     * Dedicated constructor.
     *
     * @param capacity maximum number of cached NONCEs, must not be less than 1
     * @param threshold used determine if garbage collection is needed, must not be less than 1 and must be less than
     *                  capacity
     */
    public NonceCache(final int capacity, final int threshold) {
        super();

        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must not be less than one!");
        }

        if (threshold < 1) {
            throw new IllegalArgumentException("Threshold must not be less than one!");
        }

        if (threshold >= capacity) {
            throw new IllegalArgumentException("Threshold must be less than capacity!");
        }

        this.capacity = capacity;
        this.threshold = threshold;
        this.nonces = Lists.newArrayListWithCapacity(capacity);
    }

    /**
     * Whether the caches has a NONCE value or not.
     *
     * @param nonce asked for
     * @return {@code true} if the cache has this value, else {@code false}
     */
    public boolean has(final String nonce) {
        synchronized (nonces) {
            return nonces.contains(nonce);
        }
    }

    /**
     * Add a NONCE to the cache.
     *
     * Throws {@link IllegalArgumentException} if a NONCE was already added to the cache. Also calls
     * {@link #garbageCollection()} as last operation.
     *
     * @param nonce value to cache
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if you try to add a nonce twice
     * CHECKSTYLE:ON
     */
    public void add(final String nonce) {
        synchronized (nonces) {
            if (has(nonce)) {
                throw new IllegalArgumentException(String.format("Nonce '%s' already added to cache!", nonce));
            }

            nonces.add(nonce);
        }

        garbageCollection();
    }

    /**
     * Return number of cached NONCEs.
     *
     * @return never les than 0
     */
    public int size() {
        synchronized (nonces) {
            return nonces.size();
        }
    }

    /**
     * Determine if garbage collection is necessary, and if, delete old NONCEs from cache.
     */
    private void garbageCollection() {
        synchronized (nonces) {
            if (!shouldDoGarbageCollection(nonces.size(), capacity, threshold)) {
                return;
            }

            final List<String> retained = Lists.newArrayList(nonces.subList(capacity / threshold, nonces.size()));
            nonces.clear();
            nonces.addAll(retained);
        }
    }

    /**
     * Calculates if it is necessary to run garbage collection.
     *
     * Garbage collection is done if:
     * <pre>
     * size >= capacity - capacity / threshold
     * </pre>
     *
     * @param size current size of cache, must not be less than 0
     * @param capacity current capacity of cache, must not be less than 1
     * @param threshold current threshold of cache, must not be less than 1 and must nut be greater or equal capacity
     * @return {@code true} if garbage collection should be run, else {@code false}
     */
    static boolean shouldDoGarbageCollection(final int size, final int capacity, final int threshold) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must not be less than zero!");
        }

        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must not be less than one!");
        }

        if (threshold < 1) {
            throw new IllegalArgumentException("Threshold must not be less than one!");
        }

        if (threshold >= capacity) {
            throw new IllegalArgumentException("Threshold must be less than capacity!");
        }

        return size >= capacity - capacity / threshold;
    }
}
