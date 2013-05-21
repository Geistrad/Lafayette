#!/bin/bash

#
# Install all necessary packages for debian.
#

# Add Jenkins (http://pkg.jenkins-ci.org/debian/)

wget -q -O - http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key | sudo apt-key add -

echo "# Jenkins source" > /etc/apt/sources.list
echo "deb http://pkg.jenkins-ci.org/debian binary/" > /etc/apt/sources.list

sudo apt-get update
sudo apt-get install \
    jenkins \
    screen \
    tree \
    htop \
    java \
    tomcat \
    mysql \
    etckeeper \
    sudo
