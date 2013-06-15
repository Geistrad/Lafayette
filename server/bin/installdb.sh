#!/bin/bash

if [ "${1}" == "" ] ; then
	echo "Error: Give MySQL root password as first argument!"
	exit 1
else
	PASSWORD="${1}"	
fi

SQL_DIR="../model/src/main/resources/org/lafayette/server/sql"
mysql -uroot -p "${PASSWORD}" < "${SQL_DIR}/setup_db_user.sql"

DB_USER="lafayette"
DB_PASSWORD="ooleiTh1"
DB_TABLES="table_user table_role"
DB_DATA="data_user data_role"

for file in ${DB_TABLES}
do
		mysql -u"${DB_USER}" -p"${DB_PASSWORD}" < "${SQL_DIR}/${file}.sql"
done

for file in ${DB_DATA}
do
		mysql -u"${DB_USER}" -p"${DB_PASSWORD}" < "${SQL_DIR}/${file}.sql"
done
