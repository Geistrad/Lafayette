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
import org.apache.commons.lang3.Validate;
import org.msgpack.annotation.Message;

/**
 * Represents the roles a {@link User} has.
 *
 * This class represents the record sets which describes which user has what role.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@XmlRootElement @Message
public class Role extends BaseDomainObject {

    /**
     * Id of the {@link User} which has this role.
     */
    private int userId;
    /**
     * Name of role.
     *
     * One of {@link Role.Names}.
     */
    private Names name;

    /**
     * Initializes id with {@link #UNINITIALIZED_ID}, userId with {@code 0} and name with {@code Names.ANONYMOUS}.
     */
    public Role() {
        this(0, Names.ANONYMOUS);
    }

    /**
     * Initializes id with {@link #UNINITIALIZED_ID}.
     *
     * @param userId id of user which has the role
     * @param name name of role the user has
     */
    public Role(final int userId, final Names name) {
        this(UNINITIALIZED_ID, userId, name);
    }

    /**
     * Dedicated constructor.
     *
     * @param id the record set unique id
     * @param userId id of user which has the role
     * @param name name of role the user has
     */
    public Role(final int id, final int userId, final Names name) {
        super(id);
        this.userId = userId;
        this.name = name;
    }

    /**
     * Get user id.
     *
     * @return always positive integer
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set user id
     *
     * @param userId must not be less than 0
     */
    public void setUserId(final int userId) {
        Validate.isTrue(userId >= 0, "User id must not be leass than 0!");
        this.userId = userId;
    }

    /**
     * Get the role name.
     *
     * @return never {@code null}
     */
    public Names getName() {
        return name;
    }

    /**
     * Set the role name.
     *
     * @param name must not be {@code null}
     */
    public void setName(final Names name) {
        Validate.notNull(name, "Name must not be null!");
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

    /**
     * Roles a {@link User} can have.
     */
    public static enum Names {
        /**
         * Unauthorized anonymous users.
         */
        ANONYMOUS,
        /**
         * Authorized usual users.
         */
        USER,
        /**
         * Full privileged authorized administrative users.
         */
        ADMINISTRATOR;

        /**
         * Look up a given name and returns the according name enum.
         *
         * @param name case insensitive
         * @return one of the enums
         * //CHECKSTYLE:OFF
         * @throws IllegalArgumentException if there is no matching enum
         * //CHECKSTYLE:ON
         */
        public static Names forName(final String name) {
            if (ANONYMOUS.toString().equalsIgnoreCase(name)) {
                return ANONYMOUS;
            } else if (USER.toString().equalsIgnoreCase(name)) {
                return USER;
            } else if (ADMINISTRATOR.toString().equalsIgnoreCase(name)) {
                return ADMINISTRATOR;
            } else {
                throw new IllegalArgumentException(String.format("Unknown role name '%s'!", name));
            }
        }
    }
}
