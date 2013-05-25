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

    private String name;
    private String description;

    public Role() {
        this("", "");
    }

    public Role(final String name, final String description) {
        this(UNINITIALIZED_ID, name, description);
    }

    public Role(final int id, final String name, final String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name, description);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Role)) {
            return false;
        }

        final Role other = (Role) obj;

        return super.equals(other)
                && Objects.equal(name, other.name)
                && Objects.equal(description, other.description);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(super.toString())
                .add("name", name)
                .add("description", description)
                .toString();
    }
}
