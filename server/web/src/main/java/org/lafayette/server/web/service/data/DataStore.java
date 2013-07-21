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

package org.lafayette.server.web.service.data;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * In memory store to store user data.
 *
 * The internal data structures are thread safe.
 *
 * @param <T> type of stored user data
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class DataStore<T> {

    /**
     * Maps the user data to the user name.
     *
     * Is a concurrent map.
     */
    private final Map<String, UserStore<T>> data = Maps.newConcurrentMap();

    public T get(final String user, final String id) {
        if (data.containsKey(user)) {
            return data.get(user).get(id);
        }

        return null;
    }

    public T set(final String user, final String id, final T datum) {
        if (!data.containsKey(user)) {
            data.put(user, new UserStore<T>());
        }

        return data.get(user).set(id, datum);
    }

    public T remove(final String user, final String id) {
        if (data.containsKey(user)) {
            return data.get(user).remove(id);
        }

        return null;
    }

    private final class UserStore<T> {
        private final Map<String, T> data = Maps.newConcurrentMap();

        public T get(final String id) {
            return data.get(id);
        }

        public T set(final String id, final T datum) {
            return data.put(id, datum);
        }

        public T remove(final String id) {
            return data.remove(id);
        }
    }

}
