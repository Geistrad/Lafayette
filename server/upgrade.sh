#!/bin/bash

#
# Update source and recompile web app and deploys it to tomcta.
#

git pull origin master && mvn clean package tomcat7:redeploy
