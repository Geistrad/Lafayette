/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package org.lafayette.server.nonce;

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link NonceCache}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NonceCacheTest {

    //CHECKSTYLE:OFF
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void add_throwsExcpetionIfSameNonceAddedTwice() {
        final NonceCache sut = new NonceCache();
        sut.add("foo");
        thrown.expect(IllegalArgumentException.class);
        sut.add("foo");
    }

    public void size_isZeroOnNewCache() {
        final NonceCache sut = new NonceCache();
        assertThat(sut.size(), is(0));
    }

    @Test
    public void has() {
        final NonceCache sut = new NonceCache();
        assertThat(sut.has("foo"), is(false));
        sut.add("foo");
        assertThat(sut.has("foo"), is(true));
    }
    
    @Test
    public void add() {
        final NonceCache sut = new NonceCache();
        sut.add("foo");
        assertThat(sut.size(), is(1));
        sut.add("bar");
        assertThat(sut.size(), is(2));
        sut.add("baz");
        assertThat(sut.size(), is(3));
    }

    @Test @Ignore
    public void garbageCollect() {
        
    }
    
    @Test
    public void shouldDoGarbageCollection_throwsExceptionIfSizeLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        NonceCache.shouldDoGarbageCollection(-1, 100, 10);
    }

    @Test
    public void shouldDoGarbageCollection_throwsExceptionIfCapacityLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        NonceCache.shouldDoGarbageCollection(1, 0, 10);
    }

    @Test
    public void shouldDoGarbageCollection_throwsExceptionIfThresholdLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        NonceCache.shouldDoGarbageCollection(1, 100, 0);
    }

    @Test
    public void shouldDoGarbageCollection_throwsExcpetionIfThresholdIsEqualToCapacity() {
        thrown.expect(IllegalArgumentException.class);
        NonceCache.shouldDoGarbageCollection(1, 100, 100);
    }

    @Test
    public void shouldDoGarbageCollection_throwsExcpetionIfThresholdIsGreaterThanCapacity() {
        thrown.expect(IllegalArgumentException.class);
        NonceCache.shouldDoGarbageCollection(1, 100, 101);
    }

    @Test
    public void shouldDoGarbageCollection() {
        assertThat(NonceCache.shouldDoGarbageCollection(0, 100, 10), is(false));
        assertThat(NonceCache.shouldDoGarbageCollection(10, 100, 10), is(false));
        assertThat(NonceCache.shouldDoGarbageCollection(20, 100, 10), is(false));
        assertThat(NonceCache.shouldDoGarbageCollection(30, 100, 10), is(false));
        assertThat(NonceCache.shouldDoGarbageCollection(40, 100, 10), is(false));
        assertThat(NonceCache.shouldDoGarbageCollection(50, 100, 10), is(false));
        assertThat(NonceCache.shouldDoGarbageCollection(60, 100, 10), is(false));
        assertThat(NonceCache.shouldDoGarbageCollection(70, 100, 10), is(false));
        assertThat(NonceCache.shouldDoGarbageCollection(80, 100, 10), is(false));
        assertThat(NonceCache.shouldDoGarbageCollection(89, 100, 10), is(false));
        assertThat(NonceCache.shouldDoGarbageCollection(90, 100, 10), is(true));
        assertThat(NonceCache.shouldDoGarbageCollection(91, 100, 10), is(true));
        assertThat(NonceCache.shouldDoGarbageCollection(92, 100, 10), is(true));
        assertThat(NonceCache.shouldDoGarbageCollection(93, 100, 10), is(true));
        assertThat(NonceCache.shouldDoGarbageCollection(94, 100, 10), is(true));
        assertThat(NonceCache.shouldDoGarbageCollection(95, 100, 10), is(true));
        assertThat(NonceCache.shouldDoGarbageCollection(96, 100, 10), is(true));
        assertThat(NonceCache.shouldDoGarbageCollection(97, 100, 10), is(true));
        assertThat(NonceCache.shouldDoGarbageCollection(98, 100, 10), is(true));
        assertThat(NonceCache.shouldDoGarbageCollection(99, 100, 10), is(true));
        assertThat(NonceCache.shouldDoGarbageCollection(100, 100, 10), is(true));
    }

}
