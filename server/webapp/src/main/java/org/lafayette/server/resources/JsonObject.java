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
package org.lafayette.server.resources;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class JsonObject {

    private final JSONObject delegate;

    public JsonObject(JSONObject delegate) {
        this.delegate = delegate;
    }

    public Map values() {
        try {
            final Field f = delegate.getClass().getDeclaredField("myHashMap");
            f.setAccessible(true);
            final Map values = (Map) f.get(delegate);
            return null == values ? Collections.emptyMap() : values;
        } catch (NullPointerException ex) {
            return Collections.emptyMap();
        } catch (IllegalArgumentException ex) {
            return Collections.emptyMap();
        } catch (IllegalAccessException ex) {
            return Collections.emptyMap();
        } catch (NoSuchFieldException ex) {
            return Collections.emptyMap();
        } catch (SecurityException ex) {
            return Collections.emptyMap();
        }
    }
}
