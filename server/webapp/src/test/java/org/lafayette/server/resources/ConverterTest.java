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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.json.simple.JSONArray;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConverterTest {

    @Test
    public void fromJsonToXml_Empty() throws JSONException {
        assertThat(Converter.xml(new JSONObject()), is(equalTo("<object/>\n")));
    }

    @Test
    public void fromJsonToXml_simple() throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("foo", "bar");
        json.put("number1", 42);
        json.put("number2", 3.14);
        json.put("boolean", true);
        assertThat(Converter.xml(json), is(equalTo(
                "<object>\n"
                + "    <foo>bar</foo>\n"
                + "    <number1>42</number1>\n"
                + "    <number2>3.14</number2>\n"
                + "    <boolean>true</boolean>\n"
                + "</object>\n")));
    }

    @Test
    public void fromJsonToXml_complex() throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("foo", "bar");
        json.put("number1", 42);
        final JSONObject obj1 = new JSONObject();
        obj1.put("number2", 3.14);
        obj1.put("boolean", true);
        json.put("obj", obj1);
        final JSONArray arr = new JSONArray();
        arr.add("foo");
        arr.add("bar");
        arr.add("baz");
        json.put("arr", arr);

        assertThat(Converter.xml(json), is(equalTo(
                "<object>\n"
                + "    <foo>bar</foo>\n"
                + "    <number1>42</number1>\n"
                + "    <obj>\n"
                + "        <number2>3.14</number2>\n"
                + "        <boolean>true</boolean>\n"
                + "    </obj>\n"
                + "    <arr>\n"
                + "        <0>foo</0>\n"
                + "        <1>bar</1>\n"
                + "        <2>baz</2>\n"
                + "    </arr>\n"
                + "</object>\n")));
    }
}