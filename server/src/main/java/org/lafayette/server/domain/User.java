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
import com.google.common.collect.Sets;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import org.msgpack.annotation.Message;

/**
 * Represents an user.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@XmlRootElement @Message
public class User extends BaseDomainObject {

    /**
     * Unique login name of the user.
     */
    private String loginName;
    /**
     * Hashed password.
     *
     * hash = PBKDF2( password + salt)
     *
     * see http://stackoverflow.com/questions/8674018/pbkdf2-with-bouncycastle-in-java
     */
    private String hashedPassword;
    /**
     * Unique salt for password hash.
     */
    private String salt;
    private final Collection<Role> roles = Sets.newHashSet();

    /**
     * No argument constructor for necessary for Jackson XML generation.
     */
    public User() {
        this("", "", "");
    }

    /**
     * Initializes the {@link BaseDomainObject#id} with {@link BaseDomainObject#UNINITIALIZED_ID}.
     *
     * This constructor should be used for user objects which were not yet saved in the database.
     *
     * @param loginName the login name
     * @param hashedPassword the hashed password
     * @param salt the password hash salt
     */
    public User(final String loginName, final String hashedPassword, final String salt) {
        this(UNINITIALIZED_ID, loginName, hashedPassword, salt);
    }

    /**
     * Dedicated constructor.
     *
     * @param id the primary key
     * @param loginName the login name
     * @param hashedPassword the hashed password
     * @param salt the password hash salt
     */
    public User(final int id, final String loginName, final String hashedPassword, final String salt) {
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
                .add("salt", salt)
                .toString();
    }
}
