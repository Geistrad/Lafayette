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

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeConverterBinding;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Default injector which does nothing.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NullInjector implements Injector {

    @Override
    public void injectMembers(Object instance) {

    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral) {
        return null;
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(Class<T> type) {
        return null;
    }

    @Override
    public Map<Key<?>, Binding<?>> getBindings() {
        return Collections.emptyMap();
    }

    @Override
    public Map<Key<?>, Binding<?>> getAllBindings() {
        return Collections.emptyMap();
    }

    @Override
    public <T> Binding<T> getBinding(Key<T> key) {
        return null;
    }

    @Override
    public <T> Binding<T> getBinding(Class<T> type) {
        return null;
    }

    @Override
    public <T> Binding<T> getExistingBinding(Key<T> key) {
        return null;
    }

    @Override
    public <T> List<Binding<T>> findBindingsByType(TypeLiteral<T> type) {
        return Collections.emptyList();
    }

    @Override
    public <T> Provider<T> getProvider(Key<T> key) {
        return null;
    }

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        return null;
    }

    @Override
    public <T> T getInstance(Key<T> key) {
        return null;
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        return null;
    }

    @Override
    public Injector getParent() {
        return this;
    }

    @Override
    public Injector createChildInjector(Iterable<? extends Module> modules) {
        return this;
    }

    @Override
    public Injector createChildInjector(Module... modules) {
        return this;
    }

    @Override
    public Map<Class<? extends Annotation>, Scope> getScopeBindings() {
        return Collections.emptyMap();
    }

    @Override
    public Set<TypeConverterBinding> getTypeConverterBindings() {
        return Collections.emptySet();
    }

}
