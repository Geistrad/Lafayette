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
package org.lafayette.server;

import de.weltraumschaf.commons.Version;
import java.sql.Connection;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.lafayette.server.config.ServerConfig;
import org.lafayette.server.db.NullConnection;

/**
 * Tests for {@link Registry}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RegistryTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private final Registry sut = new Registry();

    @Test
    public void defaultReturnsNeverNull() {
        assertThat(sut.getDatabase(), is(notNullValue()));
        assertThat(sut.getServerConfig(), is(notNullValue()));
        assertThat(sut.getStage(), is(notNullValue()));
        assertThat(sut.getVersion(), is(notNullValue()));
    }

    @Test
    public void setDatabase_throwsExceptionIfParameterIsNull() {
        thrown.expect(NullPointerException.class);
        sut.setDatabase(null);
    }

    @Test
    public void setServerConfig_throwsExceptionIfParameterIsNull() {
        thrown.expect(NullPointerException.class);
        sut.setServerConfig(null);
    }

    @Test
    public void setStage_throwsExceptionIfParameterIsNull() {
        thrown.expect(NullPointerException.class);
        sut.setStage(null);
    }

    @Test
    public void setVersion_throwsExceptionIfParameterIsNull() {
        thrown.expect(NullPointerException.class);
        sut.setVersion(null);
    }

    @Test
    public void setDatabase() {
        final Connection db = new NullConnection();
        sut.setDatabase(db);
        assertThat(sut.getDatabase(), is(sameInstance(db)));
    }

    @Test
    public void setServerConfig() {
        final ServerConfig config = new ServerConfig(new Properties());
        sut.setServerConfig(config);
        assertThat(sut.getServerConfig(), is(sameInstance(config)));
    }

    @Test
    public void setStage() {
        final Stage stage = new Stage();
        sut.setStage(stage);
        assertThat(sut.getStage(), is(sameInstance(stage)));
    }

    @Test
    public void setVersion() {
        final Version version = new Version("");
        sut.setVersion(version);
        assertThat(sut.getVersion(), is(sameInstance(version)));
    }

}