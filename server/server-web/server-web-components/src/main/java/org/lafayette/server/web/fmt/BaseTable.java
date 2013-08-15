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

package org.lafayette.server.web.fmt;

import com.google.common.collect.Lists;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseTable<T> implements Table<T> {

    protected Map<String, Object> findProperties(final T object) {
        try {
            final Map<String, Object> properties = BeanUtils.describe(object);
            properties.remove("class");
            return properties;
        } catch (IllegalAccessException ex) {
            return Collections.emptyMap();
        } catch (InvocationTargetException ex) {
            return Collections.emptyMap();
        } catch (NoSuchMethodException ex) {
            return Collections.emptyMap();
        }
    }

    Collection<String> findNames(final T object) {
        return Lists.newArrayList(findProperties(object).keySet());
    }

}
