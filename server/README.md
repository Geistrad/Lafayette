# Lafaiette Java Web Application

## Installation

Add to `.m2/settings.xml`

    <servers>
        ...
        <server>
            <id>SERVER_ID</id>
            <username>admin</username>
            <password>PASSWORD</password>
        </server>
        ...
    </servers>

    ...

    <profiles>
        ...
        <profile>
            <id>PROFILE_ID</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <tomcat-server>SERVER_ID</tomcat-server>
                <tomcat-url>http://HOSTNAME/manager/text</tomcat-url>
                <tomcat-port>80</tomcat-port>
            </properties>
        </profile>
        ...
    </profiles>

Tomcat user configured in `.m2/settings.xml` for deploying needs user role `manager-script` in Tomcats user config.

Build

    $ mvn clean package

Deploy

    $ mvn tomcat7:deploy
