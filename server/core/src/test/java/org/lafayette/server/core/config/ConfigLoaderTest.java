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

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConfigLoaderTest {

    @Test
    @SuppressWarnings("OBL_UNSATISFIED_OBLIGATION")
    public void loadConfig() throws IOException {
        final ConfigLoader sut = ConfigLoader.create(new TestStrategy());
        final Properties prop = new Properties();
        final InputStream input = new FileInputStream(sut.getFile());
        prop.load(input);
        input.close();

        final ServerConfig config = new ServerConfig(prop);
        assertThat(config.getDbHost(), is("//localhost"));
        assertThat(config.getDbPort(), is(3306));
        assertThat(config.getDbName(), is("lafayette"));
        assertThat(config.getDbUser(), is("root"));
        assertThat(config.getDbPassword(), is("lkwe23r"));
        assertThat(config.getDbDriver(), is("mysql"));
    }

}
