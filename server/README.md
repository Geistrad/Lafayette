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

## Response Media Types

    /r              text/uri-list, text/plain, text/html
        /user
            /{id}   text/plain, text/html, application/json, application/xml, application/x-msgpack
        /service    text/uri-list, text/plain, text/html, application/json, application/xml, application/x-msgpack
            /{name} text/plain, text/html, application/json, application/xml, application/x-msgpack

## Classes

### org.lafayette.server.http

    UriList > Set<URI>

## Todo

- Add security http://stackoverflow.com/questions/2902427/user-authentication-on-a-jersey-rest-service
- Configure site plugin in POM.
