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

package org.lafayette.server.core.config;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UnixStrategyTest {

    @Rule
    //CHECKSTYLE:OFF
    public final TemporaryFolder tempFolder = new TemporaryFolder();
    //CHECKSTYLE:ON

    @Rule
    //CHECKSTYLE:OFF
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

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

    @Test
    public void testFoundConfig_WithNothingToFind() throws IOException {
        final File home = tempFolder.newFolder();
        final File prefix = tempFolder.newFolder();
        assertThat(new File(prefix, "etc").mkdirs(), is(true));

        final UnixStrategy sut = new UnixStrategy(home.getAbsolutePath(), prefix.getAbsolutePath());
        assertThat(sut.hasFoundConfig(), is(false));
        assertThat(sut.getFoundConfig(), is(nullValue()));
        assertThat(sut.getFileList(), hasSize(2));
        assertThat(sut.getFileList(), contains(
            UnixStrategy.createHomeConfigFileName(home.getAbsolutePath()),
            UnixStrategy.createSystemConfigFileName(prefix.getAbsolutePath())
        ));
        sut.findConfig();
        assertThat(sut.hasFoundConfig(), is(false));
        assertThat(sut.getFoundConfig(), is(nullValue()));
        assertThat(sut.getFileList(), hasSize(2));
    }

    @Test
    public void testFoundConfig_homeConfigAvailable() throws IOException {
        final File home = tempFolder.newFolder();
        final File homeConfigDir = new File(home, ".lafayette");
        assertThat(homeConfigDir.mkdirs(), is(true));
        final File homeConfig = new File(homeConfigDir, "server.properties");
        assertThat(homeConfig.createNewFile(), is(true));
        final File prefix = tempFolder.newFolder();

        final UnixStrategy sut = new UnixStrategy(home.getAbsolutePath(), prefix.getAbsolutePath());
        assertThat(sut.hasFoundConfig(), is(false));
        assertThat(sut.getFoundConfig(), is(nullValue()));
        assertThat(sut.getFileList(), hasSize(2));
        assertThat(sut.getFileList(), contains(
            UnixStrategy.createHomeConfigFileName(home.getAbsolutePath()),
            UnixStrategy.createSystemConfigFileName(prefix.getAbsolutePath())
        ));

        sut.findConfig();
        assertThat(sut.hasFoundConfig(), is(true));
        assertThat(sut.getFoundConfig().getAbsolutePath(), is(homeConfig.getAbsolutePath()));
        assertThat(sut.getFileList(), hasSize(2));
    }

    @Test
    public void testFoundConfig_systemConfigAvailable() throws IOException {
        final File home = tempFolder.newFolder();
        final File prefix = tempFolder.newFolder();
        final File systemConfigDir = new File(prefix, "etc/lafayette");
        assertThat(systemConfigDir.mkdirs(), is(true));
        final File systemConfig = new File(systemConfigDir, "server.properties");
        assertThat(systemConfig.createNewFile(), is(true));

        final UnixStrategy sut = new UnixStrategy(home.getAbsolutePath(), prefix.getAbsolutePath());
        assertThat(sut.hasFoundConfig(), is(false));
        assertThat(sut.getFoundConfig(), is(nullValue()));
        assertThat(sut.getFileList(), hasSize(2));
        assertThat(sut.getFileList(), contains(
            UnixStrategy.createHomeConfigFileName(home.getAbsolutePath()),
            UnixStrategy.createSystemConfigFileName(prefix.getAbsolutePath())
        ));

        sut.findConfig();
        assertThat(sut.hasFoundConfig(), is(true));
        assertThat(sut.getFoundConfig().getAbsolutePath(), is(systemConfig.getAbsolutePath()));
        assertThat(sut.getFileList(), hasSize(2));
    }

    @Test
    public void testFoundConfig_homeAndSystemConfigAvailable() throws IOException {
        final File home = tempFolder.newFolder();
        final File homeConfigDir = new File(home, ".lafayette");
        assertThat(homeConfigDir.mkdirs(), is(true));
        final File homeConfig = new File(homeConfigDir, "server.properties");
        assertThat(homeConfig.createNewFile(), is(true));
        final File prefix = tempFolder.newFolder();
        final File systemConfigDir = new File(prefix, "etc/lafayette");
        assertThat(systemConfigDir.mkdirs(), is(true));
        final File systemConfig = new File(systemConfigDir, "server.properties");
        assertThat(systemConfig.createNewFile(), is(true));

        final UnixStrategy sut = new UnixStrategy(home.getAbsolutePath(), prefix.getAbsolutePath());
        assertThat(sut.hasFoundConfig(), is(false));
        assertThat(sut.getFoundConfig(), is(nullValue()));
        assertThat(sut.getFileList(), hasSize(2));
        assertThat(sut.getFileList(), contains(
            UnixStrategy.createHomeConfigFileName(home.getAbsolutePath()),
            UnixStrategy.createSystemConfigFileName(prefix.getAbsolutePath())
        ));

        sut.findConfig();
        assertThat(sut.hasFoundConfig(), is(true));
        assertThat(sut.getFoundConfig().getAbsolutePath(), is(homeConfig.getAbsolutePath()));
        assertThat(sut.getFileList(), hasSize(2));
    }

}
