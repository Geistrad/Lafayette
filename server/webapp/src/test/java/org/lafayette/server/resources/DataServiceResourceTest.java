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

import java.io.IOException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.msgpack.MessagePack;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DataServiceResourceTest {

    private final MessagePack mpack = new MessagePack();

    @Test
    public void testSomeMethod() throws JSONException, IOException {
        final JSONObject json = new JSONObject();
        json.put("foo", "bar");
        json.put("number1", 42);
        json.put("number2", 3.14);
        json.put("boolean", true);
        mpack.register(JSONObject.class);
//        assertThat(mpack.<JSONObject>write(json), is(new byte[] {}));
    }

}