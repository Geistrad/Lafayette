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
package org.lafayette.server.systemtests;

import org.apache.catalina.startup.Tomcat;
import org.junit.Before;
import org.junit.Test;

/**
 * System tests against the data service.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DataServiceTest {

    /**
     * The tomcat instance.
     */
    private Tomcat mTomcat;
    /**
     * The temporary directory in which Tomcat and the app are deployed.
     */
    private String mWorkingDir = System.getProperty("java.io.tmpdir");

    @Before
    public void setup() throws Throwable {
        mTomcat = new Tomcat();
        mTomcat.setPort(0);
        mTomcat.setBaseDir(mWorkingDir);
        mTomcat.getHost().setAppBase(mWorkingDir);
        mTomcat.getHost().setAutoDeploy(true);
        mTomcat.getHost().setDeployOnStartup(true);
    }

    @Test
    public void createAndGetJsonResource() {
    }
}
