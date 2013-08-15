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
package org.lafayette.server.web;

import org.lafayette.server.core.Stage;
import com.google.inject.Injector;
import de.weltraumschaf.commons.Version;
import java.security.NoSuchAlgorithmException;
import javax.sql.DataSource;
import org.apache.commons.lang3.Validate;
import org.lafayette.server.domain.Finders;
import org.lafayette.server.domain.db.NullDataSource;
import org.lafayette.server.domain.mapper.Mappers;
import org.lafayette.server.web.nonce.Nonce;
import org.lafayette.server.web.nonce.NonceFactory;
import org.lafayette.server.web.InitialServletParameters;

/**
 * Registers shared core resources.
 *
 * Shared resources:
 * <ul>
 * <li>{@link Version version}
 * <li>{@link Stage stage}
 * <li>{@link DataSource database connection}
 * <li>{@link Injector depeendcy injector}
 * <li>{@link InitialServletParameters servlet context parameters}
 * <li>{@link Mappers data mappers}
 * <li>{@link NonceFactory nonces}
 * </ul>
 *
 * All resources are initialized with default values so the registry never returns {@code null}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Registry {

    /**
     * Version of server.
     */
    private Version version = new Version("");
    /**
     * Stage of the server.
     */
    private Stage stage = new Stage();
    /**
     * Used to generate server nonce for authentication.
     */
    private Nonce nongeGenerator = NonceFactory.sha1();
    /**
     * Manages dependencies.
     */
    private Injector dependnecyInjector = new NullInjector();
    /**
     * Manages database connections.
     */
    private DataSource dataSource = new NullDataSource();
    /**
     * Mapper factory.
     */
    private Mappers mappers = new Mappers(dataSource);
    /**
     * Holds initial servlet parameters.
     */
    private InitialServletParameters initParameters = new InitialServletParameters();
    private Services services = new Services(new Finders(mappers));

    /**
     * Dedicated constructor.
     *
     * @throws NoSuchAlgorithmException if SHA1 algorithm is not present
     */
    public Registry() throws NoSuchAlgorithmException {
        super();
    }

    /**
     * Get the version.
     *
     * @return never {@code null}
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Set the version.
     *
     * @param v must not be null
     */
    public void setVersion(final Version v) {
        Validate.notNull(v, "Version must not be null!");
        this.version = v;
    }

    /**
     * Get the stage.
     *
     * @return never {@code null}
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Set the stage.
     *
     * @param s must not be null
     */
    public void setStage(final Stage s) {
        Validate.notNull(s, "Stage must not be null!");
        this.stage = s;
    }

    /**
     * Get database mapper factory.
     *
     * @return never {@code null}
     */
    public Mappers getMappers() {
        return mappers;
    }

    /**
     * Set database mapper factory.
     *
     * @param m must not be null
     */
    public void setMappers(final Mappers m) {
        Validate.notNull(m, "Mappers must not be null!");
        this.mappers = m;
    }

    /**
     * Get server nonce generator.
     *
     * @return never {@code null}
     */
    public Nonce getNongeGenerator() {
        return nongeGenerator;
    }

    /**
     * Set server nonce generator.
     *
     * @param n must not be {@code null}
     */
    public void setNongeGenerator(final Nonce n) {
        Validate.notNull(n, "Nonce generator must not be null!");
        this.nongeGenerator = n;
    }

    /**
     * Set injector which manages dependency injection.
     *
     * @param i must not be {@code null}
     */
    public void setDependnecyInjector(final Injector i) {
        Validate.notNull(i, "Injector must not be null!");
        this.dependnecyInjector = i;
    }

    /**
     * Get dependency injector.
     *
     * @return never {@code null}
     */
    public Injector getDependnecyInjector() {
        return dependnecyInjector;
    }

    /**
     * Get data source.
     *
     * @return never {@code null}
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Set data source.
     *
     * @param dataSource must not be {@code null}
     */
    public void setDataSource(final DataSource dataSource) {
        Validate.notNull(dataSource, "Data source must not be null!");
        this.dataSource = dataSource;
    }

    /**
     * Get servlet parameters.
     *
     * @return never {@code null}
     */
    public InitialServletParameters getInitParameters() {
        return initParameters;
    }

    /**
     * Set initial servlet parameters.
     *
     * @param initParameters must not be {@code null}
     */
    public void setInitParameters(InitialServletParameters initParameters) {
        Validate.notNull(initParameters, "Init parameters must not be null!");
        this.initParameters = initParameters;
    }

    public void getServices(final Services s) {
        Validate.notNull(s, "Service provider must not be null!");
        this.services = s;
    }
    public Services getServices() {
        return this.services;
    }

}
