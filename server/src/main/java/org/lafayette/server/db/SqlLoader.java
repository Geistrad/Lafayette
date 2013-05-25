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

package org.lafayette.server.db;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.commons.io.FileUtils;

/**
 * Utility class to load sql files from resources.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class SqlLoader {

    /**
     * Base package where SQL files are stored in resources.
     */
    private static final String RESOURCE_BASE = "/org/lafayette/server/sql/";

    /**
     * Hidden for pure static class.
     */
    private SqlLoader() {
        super();
    }

    /**
     * Load a SQL file and returns content as string.
     *
     * @param fileName relative to {@value #RESOURCE_BASE}
     * @return file content
     * @throws URISyntaxException if file name is not valid resource URI
     * @throws IOException if I/O errors happened
     */
    public static String loadSql(final String fileName) throws URISyntaxException, IOException {
        final File file = new File(SqlLoader.class.getResource(RESOURCE_BASE + fileName).toURI());
        return FileUtils.readFileToString(file);
    }

}
