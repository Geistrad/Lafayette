#!/bin/bash

git pull origin master && mvn clean package tomcat7:redeploy
