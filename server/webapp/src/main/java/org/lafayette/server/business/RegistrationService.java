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
import org.lafayette.server.domain.User;
import org.lafayette.server.domain.UserFinder;
import org.lafayette.server.http.digest.Digest;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RegistrationService extends ApplicationService {

    public RegistrationService(final Finders finders) {
        super(finders);
    }

    public void registerNewUser(final String loginName, final String password, final String realm, final String emailAddress) throws ServiceExcpetion {
        final UserFinder users = getUserFinder();

        if (null != users.findByLoginName(loginName)) {
            throw new ServiceExcpetion("User already exists!");
        }

        final String hasshedUserPassword = Digest.digestUserData(loginName, password, realm);
        final User user = new User(loginName, hasshedUserPassword);
        users.insert(user);
    }
}
