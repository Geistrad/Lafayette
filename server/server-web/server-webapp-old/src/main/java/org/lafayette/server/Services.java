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

import org.lafayette.server.business.BusinessServiceProvider;
import org.lafayette.server.domain.Finders;
import org.lafayette.server.web.service.ApiServiceProvider;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Services {

    private final ApiServiceProvider apiServiceProvider = new ApiServiceProvider();
    private final BusinessServiceProvider businessServiceProvider;

    public Services(final Finders finders) {
        super();
        businessServiceProvider = new BusinessServiceProvider(finders);
    }

    public ApiServiceProvider getApiServices() {
        return apiServiceProvider;
    }

    public BusinessServiceProvider getBusinessServices() {
        return businessServiceProvider;
    }

}
