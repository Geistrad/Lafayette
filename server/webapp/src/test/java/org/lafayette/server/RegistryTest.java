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

import com.google.inject.Injector;
import de.weltraumschaf.commons.Version;
import java.security.NoSuchAlgorithmException;
import javax.sql.DataSource;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.lafayette.server.domain.db.NullDataSource;
import org.lafayette.server.domain.mapper.Mappers;
import org.lafayette.server.nonce.Nonce;
import org.lafayette.server.nonce.NonceFactory;
import org.lafayette.server.web.InitialServletParameters;

/**
 * Tests for {@link Registry}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RegistryTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final Registry sut = new Registry();

    public RegistryTest() throws NoSuchAlgorithmException {
        super();
    }

    @Test
    public void defaultReturnsNeverNullBut() {
        assertThat(sut.getDataSource(), is(notNullValue()));
        assertThat(sut.getInitParameters(), is(notNullValue()));
        assertThat(sut.getStage(), is(notNullValue()));
        assertThat(sut.getVersion(), is(notNullValue()));
        assertThat(sut.getMappers(), is(notNullValue()));
        assertThat(sut.getDependnecyInjector(), is(notNullValue()));
    }

    @Test
    public void setDatabase_throwsExceptionIfParameterIsNull() {
        thrown.expect(NullPointerException.class);
        sut.setDataSource(null);
    }

    @Test
    public void setInitParameters_throwsExceptionIfParameterIsNull() {
        thrown.expect(NullPointerException.class);
        sut.setInitParameters(null);
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
    public void setMappers_throwsExceptionIfParameterIsNull() {
        thrown.expect(NullPointerException.class);
        sut.setMappers(null);
    }

    @Test
    public void setNongeGenerator_throwsExceptionIfParameterIsNull() {
        thrown.expect(NullPointerException.class);
        sut.setNongeGenerator(null);
    }

    @Test
    public void setDependnecyInjector_throwsExceptionIfParameterIsNull() {
        thrown.expect(NullPointerException.class);
        sut.setDependnecyInjector(null);
    }

    @Test
    public void setDatabase() {
        final DataSource db = new NullDataSource();
        sut.setDataSource(db);
        assertThat(sut.getDataSource(), is(sameInstance(db)));
    }

    @Test
    public void setInitParameters() {
        final InitialServletParameters context = new InitialServletParameters();
        sut.setInitParameters(context);
        assertThat(sut.getInitParameters(), is(sameInstance(context)));
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

    @Test
    public void setMappers() {
        final Mappers mappers = new Mappers(new NullDataSource());
        sut.setMappers(mappers);
        assertThat(sut.getMappers(), is(sameInstance(mappers)));
    }

    @Test
    public void setNongeGenerator() throws NoSuchAlgorithmException {
        final Nonce nonce = NonceFactory.sha1();
        sut.setNongeGenerator(nonce);
        assertThat(sut.getNongeGenerator(), is(sameInstance(nonce)));
    }

    @Test
    public void getDependnecyInjector() {
        final Injector injector = new NullInjector();
        sut.setDependnecyInjector(injector);
        assertThat(sut.getDependnecyInjector(), is(sameInstance(injector)));
    }

}
