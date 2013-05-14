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

package org.lafayette.server.config;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UnixStrategyTest {

    @Rule
    public final TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void createHomeConfigFileName_throwExceptionIfNullArgument() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Argument must not be null!");
        UnixStrategy.createHomeConfigFileName(null);
    }

    @Test
    public void createHomeConfigFileName_throwExcpetionIfEmptyArgument() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Argument must not be empty!");
        UnixStrategy.createHomeConfigFileName("");
    }

    @Test
    public void createHomeConfigFileName() {
        assertThat(UnixStrategy.createHomeConfigFileName("foobar"),
                   is("foobar/.lafayette/server.properties"));
    }

    @Test
    public void createSystemConfigFileName_nullArguemnt() {
        assertThat(UnixStrategy.createSystemConfigFileName(null),
                   is("/etc/lafayette/server.properties"));
    }

    @Test
    public void createSystemConfigFileName_emptyArguemnt() {
        assertThat(UnixStrategy.createSystemConfigFileName(""),
                   is("/etc/lafayette/server.properties"));
    }

    @Test
    public void createSystemConfigFileName() {
        assertThat(UnixStrategy.createSystemConfigFileName("foobar"),
                   is("foobar/etc/lafayette/server.properties"));
    }

    @Test @Ignore
    public void testHasFoundConfigWithNothingToFind() throws IOException {
        final File home = tempFolder.newFolder();
        final File prefix = tempFolder.newFolder();
        new File(prefix, UnixStrategy.SYSTEM_CONFIG_DIR).mkdirs();
        final UnixStrategy sut = new UnixStrategy(home.getAbsolutePath(), prefix.getAbsolutePath());
        assertThat(sut.hasFoundConfig(), is(false));
        assertThat(sut.getFoundConfig(), is(nullValue()));
        assertThat(sut.getFileList(), hasSize(2));
        // TODO test for strings in list
        sut.findConfig();
        assertThat(sut.hasFoundConfig(), is(false));
        assertThat(sut.getFoundConfig(), is(nullValue()));
        assertThat(sut.getFileList(), hasSize(2));
    }

    @Test @Ignore
    public void testGetFoundConfig() {
    }

    @Test @Ignore
    public void testFindConfig() {
    }

}