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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Stage {

    private static final String ENV_VAR_NAME = "STAGE";
    private static final Stages DEFUALT = Stages.PRODUCTION;
    private final Stages stage;

    public Stage() {
        super();

        final String env = System.getenv(ENV_VAR_NAME);

        if (null == env || env.length() == 0) {
            stage = DEFUALT;
        } else {
            stage = Stages.map(env);
        }
    }

    @Override
    public String toString() {
        return stage.toString();
    }  

    public static enum Stages {
        DEVELOPMENT, PRODUCTION;

        @Override
        public String toString() {
            return name();
        }

        static Stages map(final String name) {
            if ("development".equalsIgnoreCase(name)) {
                return DEVELOPMENT;
            }

            return PRODUCTION;
        }
    }

}
