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

import com.google.common.collect.Lists;
import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Strategy to find configuration file.
 *
 * 1. look for $HOME/.lafayette/server.properties 2. look for /etc/lafayette/server.properties
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class UnixStrategy implements LoaderStrategie {

    private static final String VENDOR = "lafayette";
    private static final String FILE = "server.properties";
    private static final String SYSTEM_CONFIG_DIR = "etc";
    private static final String DIR_SEP = "/";
    private final List<String> fileList = Lists.newArrayList();
    private File foundConfig;

    public UnixStrategy(final String home) {
        this(home, "");
    }

    public UnixStrategy(final String home, final String prefix) {
        super();
        fileList.add(createHomeConfigFileName(home));
        fileList.add(createSystemConfigFileName(prefix));
    }

    public static String createHomeConfigFileName(final String home) {
        if (null == home) {
            throw new IllegalArgumentException("Argument must not be null!");
        }

        if (home.length() == 0) {
            throw new IllegalArgumentException("Argument must not be empty!");
        }

        return home + "/." + VENDOR + DIR_SEP + FILE;
    }

    public static String createSystemConfigFileName(final String prefix) {
        if (null != prefix && prefix.length() > 0) {
            return prefix + DIR_SEP + SYSTEM_CONFIG_DIR + DIR_SEP + VENDOR + DIR_SEP + FILE;
        }
        return DIR_SEP + SYSTEM_CONFIG_DIR + DIR_SEP + VENDOR + DIR_SEP + FILE;
    }

    @Override
    public boolean hasFoundConfig() {
        return foundConfig != null;
    }

    @Override
    public File getFoundConfig() {
        return foundConfig;
    }

    @Override
    public void findConfig() {
        if (hasFoundConfig()) {
            return;
        }

        for (final String fileName : fileList) {
            final File file = new File(fileName);

            if (!file.exists()) {
                continue;
            }

            if (!file.isFile()) {
                continue;
            }

            if (!file.canRead()) {
                continue;
            }

            foundConfig = file;
            return;
        }
    }

    Collection<String> getFileList() {
        return Lists.newArrayList(fileList);
    }
}
