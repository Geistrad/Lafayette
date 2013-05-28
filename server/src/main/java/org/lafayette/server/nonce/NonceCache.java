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
    private final List<String> nonces;

    public NonceCache() {
        this(DEFAULT_CAPACITY, DEFAULT_THRESHOLD);
    }

    public NonceCache(final int capacity, final int threshold) {
        super();
        this.capacity = capacity;
        this.threshold = threshold;
        this.nonces = Lists.newArrayListWithCapacity(capacity);
    }

    public boolean has(final String nonce) {
        synchronized (nonces) {
            return nonces.contains(nonce);
        }
    }

    public void add(final String nonce) {
        synchronized (nonces) {
            if (has(nonce)) {
                throw new IllegalArgumentException(String.format("Nonce '%s' already added to cache!", nonce));
            }

            nonces.add(nonce);
        }

        garbageCollection();
    }

    public int size() {
        synchronized (nonces) {
            return nonces.size();
        }
    }

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
