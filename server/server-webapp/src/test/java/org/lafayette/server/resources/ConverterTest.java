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

    @Test
    public void fromJsonToMessagePack_null() throws JSONException, IOException {
        assertThat(Converter.mpack(null), is(equalTo(new byte[]{
            (byte) 0x80, // map w/ 0 elements
        })));
    }

    @Test
    public void fromJsonToMessagePack_empty() throws JSONException, IOException {
        assertThat(Converter.mpack(new JSONObject()), is(equalTo(new byte[]{
            (byte) 0x80, // map w/ 0 elements
        })));
    }

    private static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    @Test
    public void fromJsonToMessagePack_simple() throws JSONException, IOException {
        final JSONObject json = new JSONObject();
        json.put("foo", "bar");
        json.put("number1", 42);
        json.put("number2", 3.14);
        json.put("boolean", true);

        assertThat(Converter.mpack(json), is(equalTo(new byte[]{
            (byte) 0x84, (byte) 0xa3, (byte) 0x66, (byte) 0x6f, (byte) 0x6f, (byte) 0xa3, (byte) 0x62,
            (byte) 0x61, (byte) 0x72, (byte) 0xa7, (byte) 0x6e, (byte) 0x75, (byte) 0x6d, (byte) 0x62, (byte) 0x65,
            (byte) 0x72, (byte) 0x31, (byte) 0x2a, (byte) 0xa7, (byte) 0x6e, (byte) 0x75, (byte) 0x6d, (byte) 0x62,
            (byte) 0x65, (byte) 0x72, (byte) 0x32, (byte) 0xcb, (byte) 0x40, (byte) 0x09, (byte) 0x1e, (byte) 0xb8,
            (byte) 0x51, (byte) 0xeb, (byte) 0x85, (byte) 0x1f, (byte) 0xa7, (byte) 0x62, (byte) 0x6f, (byte) 0x6f,
            (byte) 0x6c, (byte) 0x65, (byte) 0x61, (byte) 0x6e, (byte) 0xc3,
        })));
    }

    @Test
    public void fromJsonToMessagePack_complex() throws JSONException, IOException {
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

        assertThat(Converter.mpack(json), is(equalTo(new byte[]{
            (byte) 0x84, (byte) 0xa3, (byte) 0x66, (byte) 0x6f, (byte) 0x6f, (byte) 0xa3, (byte) 0x62,
            (byte) 0x61, (byte) 0x72, (byte) 0xa7, (byte) 0x6e, (byte) 0x75, (byte) 0x6d, (byte) 0x62, (byte) 0x65,
            (byte) 0x72, (byte) 0x31, (byte) 0x2a, (byte) 0xa3, (byte) 0x6f, (byte) 0x62, (byte) 0x6a, (byte) 0x91,
            (byte) 0x82, (byte) 0xa7, (byte) 0x6e, (byte) 0x75, (byte) 0x6d, (byte) 0x62, (byte) 0x65, (byte) 0x72,
            (byte) 0x32, (byte) 0xcb, (byte) 0x40, (byte) 0x09, (byte) 0x1e, (byte) 0xb8, (byte) 0x51, (byte) 0xeb,
            (byte) 0x85, (byte) 0x1f, (byte) 0xa7, (byte) 0x62, (byte) 0x6f, (byte) 0x6f, (byte) 0x6c, (byte) 0x65,
            (byte) 0x61, (byte) 0x6e, (byte) 0xc3, (byte) 0xa3, (byte) 0x61, (byte) 0x72, (byte) 0x72, (byte) 0x91,
            (byte) 0x93, (byte) 0xa3, (byte) 0x66, (byte) 0x6f, (byte) 0x6f, (byte) 0xa3, (byte) 0x62, (byte) 0x61,
            (byte) 0x72, (byte) 0xa3, (byte) 0x62, (byte) 0x61, (byte) 0x7a,
        })));
    }
}
