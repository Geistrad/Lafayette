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
class WindowsStrategy implements LoaderStrategie {

    @Override
    public boolean hasFoundConfig() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public File getFoundConfig() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void findConfig() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
