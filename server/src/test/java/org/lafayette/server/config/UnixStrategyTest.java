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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UnixStrategyTest {

    @Rule
    public final TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
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
        assertThat(sut.getFileList(), is(emptyCollectionOf(String.class)));
        assertThat(sut.hasFoundConfig(), is(false));
        assertThat(sut.getFoundConfig(), is(nullValue()));
        assertThat(sut.getFileList(), hasSize(2));
    }

    @Test
    public void testGetFoundConfig() {
    }

    @Test
    public void testFindConfig() {
    }

}