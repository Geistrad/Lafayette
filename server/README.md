# Lafaiette Java Web Application

Maven project site is [here][1].

## Installation

Add to `.m2/settings.xml`

    <servers>
        ...
        <server>
            <id>SERVER_ID</id>
            <username>root</username>
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

## Todo

- https://vaadin.com/wiki/-/wiki/Main/Creating%20Secure%20Vaadin%20Applications%20using%20JEE6#section-Creating+Secure+Vaadin+Applications+using+JEE6-SecuringTheWebLayer
- Database mappers: Validate input (string length, id range etc.)

[1]: http://weltraumschaf.github.io/Lafayette/