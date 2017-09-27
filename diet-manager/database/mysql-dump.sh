#!/bin/bash
MYSQL_BACKUP_FILE_NAME=$(date +'%d_%m_%Y')_mysql_dietmanager_backup.sql
mysqldump --password=${OPENSHIFT_MYSQL_DB_PASSWORD} -u ${OPENSHIFT_MYSQL_DB_USERNAME} -h ${OPENSHIFT_MYSQL_DB_HOST} -P ${OPENSHIFT_MYSQL_DB_PORT} --databases dietmanager > ${OPENSHIFT_DATA_DIR}/backup/${MYSQL_BACKUP_FILE_NAME}