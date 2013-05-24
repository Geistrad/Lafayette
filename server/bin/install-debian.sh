#!/bin/bash

#
# Install all necessary packages for debian.
#

# Add Jenkins (http://pkg.jenkins-ci.org/debian/)
echo "Add Jenkins sources to apt sources..."
wget -q -O - http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key | apt-key add -
echo "" >> /etc/apt/sources.list
echo "# Jenkins source" >> /etc/apt/sources.list
echo "deb http://pkg.jenkins-ci.org/debian binary/" >> /etc/apt/sources.list

# Update sources
echo "Update apt sources..."
apt-get update
echo "Upgrade packages..."
apt-get -y upgrade 

# Install packages.
echo "Install packages..."
apt-get -y install \
    apt-file \
    etckeeper \
    git \
    htop \
    jenkins \
    libmysql-java \
    maven \
    mysql-server-5.5 \
    openjdk-6-jdk \
    screen \
    sudo \
    tomcat7 \
    tomcat7-admin \
    tree \
    unzip \

# Update apt files.
echo "Update apt-file..."
apt-file update

echo "Udpate /etc/environment"
echo "JAVA_HOME=/usr/lib/jvm/java-6-openjdk-amd64" >> /etc/environment
echo "M2_HOME=/usr/local/apache-maven/" >> /etc/environment
echo "MAVEN_HOME=/usr/local/apache-maven" >> /etc/environment
echo "M2=/usr/local/apache-maven/bin" >> /etc/environment
echo "STAGE=development" >> /etc/environment

echo "Done :)"
