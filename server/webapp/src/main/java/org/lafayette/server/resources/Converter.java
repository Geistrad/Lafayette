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
import java.util.Iterator;
import java.util.Map;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.msgpack.MessagePack;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Converter {

    private static final Converter INSTACE = new Converter();
    private final Json2Xml json2xml = new Json2Xml();
    private final Json2MessagePack json2macp = new Json2MessagePack();

    public String toXml(final JSONObject json) throws JSONException {
        return json2xml.convert(json);
    }

    public static String xml(final JSONObject json) throws JSONException {
        return INSTACE.toXml(json);
    }

    public byte[] toMessagePack(final JSONObject json) throws IOException {
        return json2macp.convert(json);
    }

    public static byte[] mpack(final JSONObject json) throws IOException {
        return INSTACE.toMessagePack(json);
    }

    private static final class Json2Xml {

        private static final String OPEN_TAG = "<%s>";
        private static final String CLOSE_TAG = "</%s>";
        private static final String EMPTY_TAG = "<%s/>";
        private static final char NL = '\n';
        private static final String TAB = "    ";
        private static final String ROOT_TAG_NAME = "object";
        private int indentation = 0;

        private static String opneTag(final String name) {
            return String.format(OPEN_TAG, name);
        }

        private static String closeTag(final String name) {
            return String.format(CLOSE_TAG, name);
        }

        private static String emptyTag(final String name) {
            return String.format(EMPTY_TAG, name);
        }

        private void indent() {
            ++indentation;
        }

        private void exdent() {
            --indentation;

            if (indentation < 0) {
                indentation = 0;
            }
        }

        private String indentation() {
            final StringBuilder buffer = new StringBuilder();

            for (int i = 0; i < indentation; ++i) {
                buffer.append(TAB);
            }

            return buffer.toString();
        }

        public String convert(final JSONObject json) throws JSONException {
            final StringBuilder buffer = new StringBuilder();

            if (json.length() == 0) {
                return buffer.append(emptyTag(ROOT_TAG_NAME)).append(NL).toString();
            }

            formatObject(buffer, ROOT_TAG_NAME, json);
            return buffer.toString();
        }

        private void formatObject(final StringBuilder buffer, final String name, final JSONObject json) throws JSONException {
            buffer.append(indentation()).append(opneTag(name)).append(NL);
            indent();
            final Iterator<String> it = json.keys();

            while (it.hasNext()) {
                final String key = it.next();
                final Object value = json.get(key);

                if (value.getClass() == JSONObject.class) {
                    formatObject(buffer, key, (JSONObject) value);
                } else if (value.getClass() == JSONArray.class) {
                    formatArray(buffer, key, (JSONArray) value);
                } else {
                    formatPrimitve(buffer, key, value.toString());
                }
            }

            exdent();
            buffer.append(indentation()).append(closeTag(name)).append(NL);
        }

        private void formatArray(final StringBuilder buffer, final String name, final JSONArray array) throws JSONException {
            buffer.append(indentation()).append(opneTag(name)).append(NL);
            indent();

            for (int i = 0, length = array.length(); i < length; ++i) {
                final String key = String.valueOf(i);
                final Object value = array.get(i);

                if (value.getClass() == JSONObject.class) {
                    formatObject(buffer, key, (JSONObject) value);
                } else if (value.getClass() == JSONArray.class) {
                    formatArray(buffer, key, (JSONArray) value);
                } else {
                    formatPrimitve(buffer, key, value.toString());
                }
            }

            exdent();
            buffer.append(indentation()).append(closeTag(name)).append(NL);
        }

        private void formatPrimitve(final StringBuilder buffer, final String key, final String value) {
            buffer.append(indentation())
                            .append(opneTag(key))
                            .append(value)
                            .append(closeTag(key))
                            .append(NL);
        }
    }

    private static final class Json2MessagePack {
        private final MessagePack mpack = new MessagePack();

        public Json2MessagePack() {
            super();
            mpack.register(JSONObject.class);
            mpack.register(JSONArray.class);
        }

        public byte[] convert(final JSONObject json) throws IOException {
            return mpack.<Map>write(new JsonObject(json).values());
        }
    }
}
