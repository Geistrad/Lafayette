#!/bin/bash

#
# Install all necessary packages for debian.
#

# Add Jenkins (http://pkg.jenkins-ci.org/debian/)
wget -q -O - http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key | sudo apt-key add -
echo "" > /etc/apt/sources.list
echo "# Jenkins source" > /etc/apt/sources.list
echo "deb http://pkg.jenkins-ci.org/debian binary/" > /etc/apt/sources.list

# Update sources
apt-get update

# Install packages.
apt-get install \
    apt-file \
    etckeeper \
    git \
    htop \
    jenkins \
    mysql-server-5.5 \
    openjdk-6-jdk \
    screen \
    sudo \
    tomcat7 \
    tomcat7-admin \
    tree \
    unzip \

# Update apt files.
apt-file update
