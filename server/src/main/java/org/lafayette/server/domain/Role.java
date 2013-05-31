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
import javax.xml.bind.annotation.XmlRootElement;
import org.msgpack.annotation.Message;

/**
 * Represents the roles a {@link User} has.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@XmlRootElement @Message
public class Role extends BaseDomainObject {

    private int userId;
    /**
     * Name of role.
     *
     * One of {@link Role.Names}.
     *
     * TODO use {@link Role.Names} as type.
     */
    private String name;

    public Role() {
        this(0, "");
    }

    public Role(final int userId, final String name) {
        this(UNINITIALIZED_ID, userId, name);
    }

    public Role(final int id, final int userId, final String name) {
        super(id);
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), userId, name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Role)) {
            return false;
        }

        final Role other = (Role) obj;

        return super.equals(other)
                && Objects.equal(userId, other.userId)
                && Objects.equal(name, other.name);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(super.toString())
                .add("userId", userId)
                .add("name", name)
                .toString();
    }

    public enum Names {
        ANONYMOUS, USER, ADMINISTRATOR;
    }
}
