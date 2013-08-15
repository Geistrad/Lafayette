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

import org.junit.Test;
import static org.junit.Assert.*;
import org.lafayette.server.Stage.Stages;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Stage}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class StageTest {

    @Test
    public void mapNameToEnum() {
        assertThat(Stages.map("development"), is(Stages.DEVELOPMENT));
        assertThat(Stages.map("production"), is(Stages.PRODUCTION));
        assertThat(Stages.map("foobar"), is(Stages.PRODUCTION));
        assertThat(Stages.map(""), is(Stages.PRODUCTION));
    }

    @Test
    public void defaultConstruction() {
        assertThat(new Stage().getStage(), is(Stages.PRODUCTION));
    }

    @Test
    public void convenienceConstruction() {
        assertThat(new Stage("development").getStage(), is(Stages.DEVELOPMENT));
        assertThat(new Stage("production").getStage(), is(Stages.PRODUCTION));
        assertThat(new Stage("foobar").getStage(), is(Stages.PRODUCTION));
        assertThat(new Stage("").getStage(), is(Stages.PRODUCTION));
    }

    @Test
    public void testToString() {
        assertThat(new Stage("development").toString(), is("DEVELOPMENT"));
        assertThat(new Stage("production").toString(), is("PRODUCTION"));
    }
}
