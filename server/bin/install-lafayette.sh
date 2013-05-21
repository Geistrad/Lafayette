#!/bin/bash

echo "Copy defaults..."
cp ../etc/default/server.properties /etc/default/lafayette.properties

echo "Copy server config..."
mkdir -p /etc/lafayette
cp /etc/default/lafayette.properties /etc/lafayette/server.properties

echo "Create log directory..."
mkdir -p /var/log/lafayette
touch /var/log/lafayette/server.log
chown -R tomcat7:tomcat7 /var/log/lafayette

echo "Done :)"
