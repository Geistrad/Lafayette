# Lafayette Server Appliance

Documentation for Lafayete Server Development appliance.

- OS: Debian Wheezy
- Java: OpenJDK 1.6.0_27 (`/usr/lib/jvm/java-6-openjdk-amd64`)
- Maven: 3.0.4 (`/usr/share/maven`)

Install Script: `wget https://raw.github.com/Weltraumschaf/Lafayette/master/server/bin/install-debian.sh`

## Import into Virtual Box

1. download and install [Virtual Box][1]. 
2. import appliance: __Menu__ -> __File__ -> __Import Aplliance__
3. change network from NAT to bridged
    1. Open virtual machne settings
    2. Navigate to network
    3. change `Attached to:` from `NAT` to `Bridged Adapter`
4. start virtual machine
5. chaneg hostname
	1. login as root
	2. edit /etc/hostname and change `lafayette-dev`: `$ nano /et/hostname`
	3. edit /htc/hosts and  change `lafayette-dev`: `$ nano /et/hosts`
	4. reboot machine: `$ shutdown -r now`
6. change Sonar URI in Jenkins
	1. login via Webbrowser to Jenkins with user `service-user` (see below): `http://YOURHOSTNAME:8080`
	2. go to `configure Jenkins`
	3. go to `configure system`
	4. search for `sonar`
	5. edit server URI to: `http://YOURHOSTNAME:9000`
	
## Users

- root: `mo3Shoo7`
- service-user: `ahf4Iedu` (sudoer)

## Services

* MySQL 5.5.31 (User: `root` - Password: `mo3Shoo7`)
* Apache Tomcat 7 (User: `root` - Password: `mo3Shoo7`)
* Jenkins (User: `root` - Password: `mo3Shoo7`)
* Sonar (User: `root` - Password: `mo3Shoo7`, DB: `sonar` - `Quizook1`) in `/usr/local/sonar`

### Tomcat

- Port 80: in `/etc/default/tomcat7` autobind=on
- MySQL JDBC connector: `/usr/share/tomcat7/lib/mysql.jar -> /usr/share/java/mysql.jar`
- see [Install Tomcat 7 on Ubuntu 11.10 for Mifos X][2]

Add in `/etc/defaults/tomcat`

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
[2]: https://mifosforge.jira.com/wiki/display/MIFOSX/Install+Tomcat+7+on+Ubuntu+11.10+for+Mifos+X
