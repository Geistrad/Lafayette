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

import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.lafayette.server.web.service.ServiceDescriptor;

/**
 * Tests for {@link DataService}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DataServiceTest {

    private final DataService sut = new DataService();

    @Test
    public void getPutAndDeleteData() {
        assertThat(sut.getData("foo", "1"), is(nullValue()));
        final JSONObject o1 = new JSONObject();
        assertThat(sut.putData("foo", "1", o1), is(nullValue()));
        assertThat(sut.getData("foo", "1"), is(sameInstance(o1)));
        sut.deleteData("foo", "1");
        assertThat(sut.getData("foo", "1"), is(nullValue()));
    }

    @Test
    public void getDescription() {
        final ServiceDescriptor desc = sut.getDescription();
        assertThat(desc.getServiceDescription(), is(equalTo("Service to store JSON data.")));
        assertThat(desc.getApiDescription(), containsInAnyOrder(
                "Get data by user name and unique id.",
                "Store data by user name and unique id.",
                "Delete data by user name and unique id."));
    }
}