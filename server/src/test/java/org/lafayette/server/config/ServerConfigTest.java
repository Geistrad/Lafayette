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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ServerConfigTest {

    private ServerConfig sut;

    @Before
    public void initSubject() throws IOException {
        final Properties prop = new Properties();
        final InputStream input = getClass().getResourceAsStream("/org/lafayette/server/config/server.properties");
        prop.load(input);
        input.close();
        sut = new ServerConfig(prop);
    }

    @Test
    public void getDbHost() {
        assertThat(sut.getDbHost(), is("localhost"));
    }

    @Test
    public void getDbPort() {
        assertThat(sut.getDbPort(), is(3306));
    }

    @Test
    public void getDbName() {
        assertThat(sut.getDbName(), is("lafayette"));
    }

    @Test
    public void getDbUser() {
        assertThat(sut.getDbUser(), is("root"));
    }

    @Test
    public void getDbPassword() {
        assertThat(sut.getDbPassword(), is("lkwe23r"));
    }

    @Test
    public void getDbDriver() {
        assertThat(sut.getDbDriver(), is("mysql"));
    }

    @Test @Ignore
    public void generateJdbcUri() {
    }
}