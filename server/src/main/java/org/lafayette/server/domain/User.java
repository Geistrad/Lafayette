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
public class User extends BaseDomainObject {


    private String loginName;
    private String hashedPassword;
    private String salt;

    public User(final String loginName, final String hashedPassword, final String salt) {
        this(Long.valueOf(0), loginName, hashedPassword, salt);
    }

    public User(final Long id, final String loginName, final String hashedPassword, final String salt) {
        super(id);
        this.loginName = loginName;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
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
        return Objects.hashCode(super.hashCode(), loginName, hashedPassword, salt);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }

        final User other = (User) obj;

        return super.equals(other)
                && Objects.equal(loginName, other.loginName)
                && Objects.equal(hashedPassword, other.hashedPassword)
                && Objects.equal(salt, other.salt);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(super.toString())
                .add("loginName", loginName)
                .add("hashedPassword", hashedPassword)
                .add("salt", salt).toString();
    }
}
