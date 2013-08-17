#!/bin/bash

#
# Generate multi module site and stages it in ${baseDir}/target/staged-site
#

TARGET="${1}"

mvn clean site:site 
mkdir -p "${TARGET}"
mvn site:stage -DstagingDirectory="${TARGET}"
echo "Site generated in ${TARGET}."
