#!/bin/bash

git pull origin master && mvn clean package && mvn tomcat7:redeploy

