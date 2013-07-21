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

import org.lafayette.server.web.service.DescribableService;
import org.lafayette.server.web.service.Service;
import org.lafayette.server.web.service.ServiceApi;
import org.lafayette.server.web.service.ServiceDescriptor;
import org.codehaus.jettison.json.JSONObject;

/**
 * Service to store data.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Service("Service to store JSON data.")
public class DataService implements DescribableService {

    private static final ServiceDescriptor DESCRIPTOR = new ServiceDescriptorImpl(DataService.class);

    /**
     * Stores the data.
     */
    private final DataStore<JSONObject> store = new DataStore<JSONObject>();

    @ServiceApi("Get data by user name and unique id.")
    public JSONObject getData(final String user, final String id) {
        return store.get(user, id);
    }

    @ServiceApi("Store data by user name and unique id.")
    public JSONObject putData(final String userName, final String id, final JSONObject data) {
        return store.set(userName, id, data);
    }

    @ServiceApi("Delete data by user name and unique id.")
    public void deleteData(final String userName, final String id) {
        store.remove(userName, id);
    }

    @Override
    public ServiceDescriptor getDescription() {
        return DESCRIPTOR;
    }

}
