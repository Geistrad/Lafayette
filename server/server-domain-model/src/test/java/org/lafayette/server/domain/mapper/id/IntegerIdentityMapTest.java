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
package org.lafayette.server.domain.mapper.id;

import org.junit.Test;
import org.lafayette.server.domain.DomainObject;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link IntegerIdentity}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class IntegerIdentityMapTest {

    private final IntegerIdentityMap<DomainObjectStub> sut = new IntegerIdentityMap<DomainObjectStub>();

    @Test
    public void testSize() {
        assertThat(sut.size(), is(0));
        sut.put(1, new DomainObjectStub());
        assertThat(sut.size(), is(1));
        sut.put(2, new DomainObjectStub());
        assertThat(sut.size(), is(2));
        sut.put(1, new DomainObjectStub());
        assertThat(sut.size(), is(2));
    }

    @Test
    public void testIsEmpty() {
        assertThat(sut.isEmpty(), is(true));
        sut.put(1, new DomainObjectStub());
        assertThat(sut.isEmpty(), is(false));
    }

    @Test
    public void testContainsIdentity_integerObject() {
        assertThat(sut.containsIdentity(Integer.valueOf(1)), is(false));
        assertThat(sut.containsIdentity(Integer.valueOf(2)), is(false));
        sut.put(1, new DomainObjectStub());
        assertThat(sut.containsIdentity(Integer.valueOf(1)), is(true));
        assertThat(sut.containsIdentity(Integer.valueOf(2)), is(false));
        sut.put(2, new DomainObjectStub());
        assertThat(sut.containsIdentity(Integer.valueOf(1)), is(true));
        assertThat(sut.containsIdentity(Integer.valueOf(2)), is(true));
    }

    @Test
    public void testContainsIdentity_nativeInt() {
        assertThat(sut.containsIdentity(1), is(false));
        assertThat(sut.containsIdentity(2), is(false));
        sut.put(1, new DomainObjectStub());
        assertThat(sut.containsIdentity(1), is(true));
        assertThat(sut.containsIdentity(2), is(false));
        sut.put(2, new DomainObjectStub());
        assertThat(sut.containsIdentity(1), is(true));
        assertThat(sut.containsIdentity(2), is(true));
    }

    @Test
    public void testContainsDomainObject() {
        final DomainObjectStub obj1 = new DomainObjectStub();
        final DomainObjectStub obj2 = new DomainObjectStub();
        assertThat(sut.containsDomainObject(obj1), is(false));
        assertThat(sut.containsDomainObject(obj2), is(false));
        sut.put(4, obj1);
        assertThat(sut.containsDomainObject(obj1), is(true));
        assertThat(sut.containsDomainObject(obj2), is(false));
        sut.put(4, obj2);
        assertThat(sut.containsDomainObject(obj1), is(false));
        assertThat(sut.containsDomainObject(obj2), is(true));
        sut.put(3, obj1);
        assertThat(sut.containsDomainObject(obj1), is(true));
        assertThat(sut.containsDomainObject(obj2), is(true));
    }

    @Test
    public void testGet_integerObject() {
        final DomainObjectStub obj1 = new DomainObjectStub();
        final DomainObjectStub obj2 = new DomainObjectStub();
        assertThat(sut.get(Integer.valueOf(1)), is(nullValue()));
        assertThat(sut.get(Integer.valueOf(2)), is(nullValue()));
        sut.put(Integer.valueOf(1), obj1);
        assertThat(sut.get(Integer.valueOf(1)), is(sameInstance(obj1)));
        assertThat(sut.get(Integer.valueOf(2)), is(nullValue()));
        sut.put(Integer.valueOf(2), obj2);
        assertThat(sut.get(Integer.valueOf(1)), is(sameInstance(obj1)));
        assertThat(sut.get(Integer.valueOf(2)), is(sameInstance(obj2)));
    }

    @Test
    public void testGet_nativeInt() {
        final DomainObjectStub obj1 = new DomainObjectStub();
        final DomainObjectStub obj2 = new DomainObjectStub();
        assertThat(sut.get(1), is(nullValue()));
        assertThat(sut.get(2), is(nullValue()));
        sut.put(1, obj1);
        assertThat(sut.get(1), is(sameInstance(obj1)));
        assertThat(sut.get(2), is(nullValue()));
        sut.put(2, obj2);
        assertThat(sut.get(1), is(sameInstance(obj1)));
        assertThat(sut.get(2), is(sameInstance(obj2)));
    }

    @Test
    public void testPut_integerObject() {
        final DomainObjectStub obj1 = new DomainObjectStub();
        final DomainObjectStub obj2 = new DomainObjectStub();
        assertThat(sut.get(Integer.valueOf(1)), is(nullValue()));
        assertThat(sut.put(Integer.valueOf(1), obj1), is(nullValue()));
        assertThat(sut.get(Integer.valueOf(1)), is(sameInstance(obj1)));
        assertThat(sut.put(Integer.valueOf(1), obj2), is(sameInstance(obj1)));
        assertThat(sut.get(Integer.valueOf(1)), is(sameInstance(obj2)));
    }

    @Test
    public void testPut_nativeInt() {
        final DomainObjectStub obj1 = new DomainObjectStub();
        final DomainObjectStub obj2 = new DomainObjectStub();
        assertThat(sut.get(1), is(nullValue()));
        assertThat(sut.put(1, obj1), is(nullValue()));
        assertThat(sut.get(1), is(sameInstance(obj1)));
        assertThat(sut.put(1, obj2), is(sameInstance(obj1)));
        assertThat(sut.get(1), is(sameInstance(obj2)));
    }

    /**
     * Stubbed domain objerct for tests.
     */
    private static final class DomainObjectStub implements DomainObject {

        private int id;

        @Override
        public int getId() {
            return id;
        }

        @Override
        public void setId(final int id) {
            this.id = id;
        }

    }
}
