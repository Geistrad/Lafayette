# Lafayette-Dev

- OS: Debian Wheezy
- Java: OpenJDK 1.6.0_27 (`/usr/lib/jvm/java-6-openjdk-amd64`)
- Maven: 3.0.4 (``)

Install Script: `wget https://raw.github.com/Weltraumschaf/Lafayette/master/server/bin/install-debian.sh`

## Import into Virtual Box

1. Download and install [Virtual Box][1]. 
2. Import appliance: `Menu -> File -> Import Aplliance
3. Change network from NAT to bridged
    1. Open virtual machne settings
    2. Navigate to network
    3. Change `Attached to:` from `NAT` to `Bridged Adapter`
4. Start virtual machine

## Users

    root: mo3Shoo7
    service-user: ahf4Iedu (sudoer)

## Services

* MySQL 5.5.31 (User: root - Password: mo3Shoo7)
* Apache Tomcat 7 (User: root - Password: mo3Shoo7)
* Jenkins (User: root - Password: mo3Shoo7)

### Tomcat

- Port 80: in `/etc/default/tomcat7` autobind=on
- MySQL JDBC connector: `/usr/share/tomcat7/lib/mysql.jar -> /usr/share/java/mysql.jar`
- see https://mifosforge.jira.com/wiki/display/MIFOSX/Install+Tomcat+7+on+Ubuntu+11.10+for+Mifos+X

in `/etc/defaults/tomcat`

    export STAGE=development
    
    ...

    # Java memory options
    MAX_PERM_SIZE=128m
    HEAP_SPACE_START=128m
    HEAP_SPACE_MAX=256m

    ...

    JAVA_OPTS="-Djava.awt.headless=true \
    -Xmx128m \
    -XX:+UseConcMarkSweepGC \
    -XX:+CMSClassUnloadingEnabled \
    -XX:+UseParNewGC \
    -XX:MaxPermSize=${MAX_PERM_SIZE} \
    -Xms${HEAP_SPACE_START} -Xmx${HEAP_SPACE_MAX}"

[1]: https://www.virtualbox.org/wiki/Downloads