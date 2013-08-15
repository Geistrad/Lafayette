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
 * Service layer for web applications.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class ApplicationService {

    /**
     * Finder factory.
     */
    private final Finders finders;

    /**
     * Dedicated constructor.
     *
     * @param finders finder factory
     */
    public ApplicationService(final Finders finders) {
        super();
        this.finders = finders;
    }

    /**
     * Get a user finder.
     *
     * @return never {@code null}
     */
    protected UserFinder getUserFinder() {
        return finders.forUsers();
    }

    /**
     * Get a role finder.
     *
     * @return never {@code null}
     */
    protected RoleFinder getRoleFinder() {
        return finders.forRoles();
    }

}
