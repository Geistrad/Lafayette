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
package org.lafayette.server.domain;

import com.google.common.base.Objects;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class User implements DomainObject {

    private Long id;
    private String loginName;
    private String hashedPassword;
    private String salt;

    public User(final Long id, final String loginName, final String hashedPassword, final String salt) {
        super();
        this.id = id;
        this.loginName = loginName;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, loginName, hashedPassword, salt);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }

        final User other = (User) obj;

        return Objects.equal(id, other.id)
                && Objects.equal(loginName, other.loginName)
                && Objects.equal(hashedPassword, other.hashedPassword)
                && Objects.equal(salt, other.salt);
    }
}
