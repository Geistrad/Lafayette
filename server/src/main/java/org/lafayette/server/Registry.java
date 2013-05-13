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

package org.lafayette.server;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Registry {

    private final Map<Key, Item<?>> store = Maps.newHashMap();

    public Registry() {
        super();
    }

    public void setItem(final Key key, final Item<?> item) {
        store.put(key, item);
    }

    public boolean hasKey(final Key key) {
        return store.containsKey(key);
    }

    public Item<?> getItem(final Key key) {
        return store.get(key);
    }

    public Item<?> getVersion() {
        return getItem(Key.VERSION);
    }

    public Item<?> getStage() {
        return getItem(Key.STAGE);
    }

    @Override
    public String toString() {
        return  store.toString();
    }

    public static enum Key {
        STAGE, VERSION;
    }

    public static final class Item<T> {
        private final T item;

        public Item(final T item) {
            this.item = item;
        }

        public T getItem() {
            return item;
        }

        @Override
        public String toString() {
            return item.toString();
        }

    }
}
