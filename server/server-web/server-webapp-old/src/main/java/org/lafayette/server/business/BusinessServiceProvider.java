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

package org.lafayette.server.business;

import org.jsoup.helper.Validate;
import org.lafayette.server.domain.Finders;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BusinessServiceProvider {

    private final RegistrationService regsitrationService;
    private final UserService userService;

    public BusinessServiceProvider(final Finders finders) {
        Validate.notNull(finders, "Finders parameter must not be null!");
        regsitrationService = new  RegistrationService(finders);
        userService = new UserService(finders);
    }

    public RegistrationService getRegistrationService() {
        return regsitrationService;
    }

    public UserService getUserService() {
        return userService;
    }
}
