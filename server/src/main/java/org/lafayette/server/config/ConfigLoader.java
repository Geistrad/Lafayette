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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ConfigLoader {

    private final LoaderStrategie loaderStrategy;

    private ConfigLoader(final LoaderStrategie loaderStrategy) {
        super();
        this.loaderStrategy = loaderStrategy;
    }

    public static ConfigLoader create() {
        final boolean isWindows = false;;

        if (isWindows) {
            return create(new WindowsStrategy());
        } else {
            return create(new UnixStrategy(System.getProperty("HOME", ".")));
        }
    }

    public static ConfigLoader create(final LoaderStrategie loaderStrategy) {
        return new ConfigLoader(loaderStrategy);
    }

    public void load() {
        loaderStrategy.findConfig();
    }

    public boolean hasConfig() {
        return loaderStrategy.hasFoundConfig();
    }

    public File getFile() {
        return loaderStrategy.getFoundConfig();
    }

}
