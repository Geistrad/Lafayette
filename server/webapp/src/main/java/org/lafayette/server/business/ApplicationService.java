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

import org.lafayette.server.domain.Finders;
import org.lafayette.server.domain.RoleFinder;
import org.lafayette.server.domain.UserFinder;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class ApplicationService {

    private final Finders finders;

    public ApplicationService(Finders finders) {
        super();
        this.finders = finders;
    }

    protected UserFinder getUserFinder() {
        return finders.forUsers();
    }

    protected RoleFinder getRoleFinder() {
        return finders.forRoles();
    }

}
