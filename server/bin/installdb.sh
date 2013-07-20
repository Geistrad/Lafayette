#!/bin/bash

SQL_DIR="../model/src/main/resources/org/lafayette/server/sql"
mysql -h "localhost" < "${SQL_DIR}/setup_db_user.sql"

DB_USER="lafayette"
DB_PASSWORD="ooleiTh1"
DB_TABLES="table_user table_role"
DB_DATA="data_user data_role"
DB_HOST="localhost"
DB_NAME="lafayette"

for file in ${DB_TABLES}
do
		mysql -h "${DB_HOST}" -u "${DB_USER}" -p"${DB_PASSWORD}" "${DB_NAME}" < "${SQL_DIR}/${file}.sql"
done

for file in ${DB_DATA}
do
		mysql -h "${DB_HOST}" -u "${DB_USER}" -p"${DB_PASSWORD}" "${DB_NAME}" < "${SQL_DIR}/${file}.sql"
done
