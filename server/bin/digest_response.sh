#!/bin/bash

if [ "${1}" == "-h" ] ; then
	echo "usage: ${0} username realm password nonce httpMethod requestedUri"
	exit 0
fi

username="${1}"
realm="${2}" 
password="${3}"
nonce="${4}"
httpMethod="${5}"
requestedUri="${6}"

userData=$(md5 -qs "${username}:${realm}:${password}")
requestData=$(md5 -qs "${httpMethod}:${requestedUri}")
response=$(md5 -qs "${userData}:${nonce}:$requestData")

echo "Response is: ${response}"