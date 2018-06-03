#!/bin/sh
#Date:2017/06/05
#Version:1.0.0
#Author:daiwq

DEPLOY_SPLITE='========================================================'
ACCOUNT='/usr/local/account-api/misc'
INSTALL_DIR="$ACCOUNT/install"
MYSQL="$ACCOUNT/etc"
source $INSTALL_DIR/trycatch.sh

PRE_INSTALL_EXCEPTION=100
TOMCAT_INSTALL_EXCEPTION=101
MARIADB_INSTALL_EXCEPTION=102
JDK_INSTALL_EXCEPTION=103
REDIS_INSTALL_EXCEPTION=104
SUCCESS=0
MSG="An Exception was thrown, when install the "

function show_table ()
{
    mysql -u$USERNAME -p$PWD -e "

    CREATE DATABASE IF NOT EXISTS account DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
    use account;
    #echo '+++++++++++++++++++++++++++++++++++++++'
    show tables;
    #echo '+++++++++++++++++++++++++++++++++++++++'
    "
}

try
(
    dos2unix $INSTALL_DIR/*
    chattr -i /etc/group
    chattr -i /etc/gshadow
    chattr -i /etc/passwd
    chattr -i /etc/shadow

    chmod +x $INSTALL_DIR/*.sh
    cp $INSTALL_DIR/MariaDB.repo /etc/yum.repos.d/

    sh $INSTALL_DIR/install_pre.sh
    echo $DEPLOY_SPLITE
    sh $INSTALL_DIR/install_jdk.sh
    echo $DEPLOY_SPLITE
    if [[ ! -z $(rpm -qa | grep mariadb) ]];
    then
        echo "mariadb had existed."
    else
        sh $INSTALL_DIR/install_mariadb.sh
        echo $DEPLOY_SPLITE
        sh $INSTALL_DIR/install_mariadb_init.sh
        echo $DEPLOY_SPLITE
        sh $INSTALL_DIR/install_init_data.sh
    fi
    echo $DEPLOY_SPLITE

    sh $INSTALL_DIR/install_tomcat.sh

    sh $INSTALL_DIR/install_redis.sh

    echo 'source /etc/profile'
    show_table

    chattr +i /etc/group
    chattr +i /etc/gshadow
    chattr +i /etc/passwd
    chattr +i /etc/shadow

    cp $INSTALL_DIR/service-start.sh /etc/init.d/
    chmod +x /etc/init.d/service-start.sh
)
catch || {
    case $ex_code in
        $TOMCAT_INSTALL_EXCEPTION)
            echo "$MSG"tomcat
            throw $ex_code
            ;;
        $MARIADB_INSTALL_EXCEPTION)
            echo "$MSG"mariadb
            ;;
        $JDK_INSTALL_EXCEPTION)
            echo "$MSG"jdk
            throw $ex_code
            ;;
        $REDIS_INSTALL_EXCEPTION)
            echo "$MSG"redis
            throw $ex_code
            ;;
        $SUCCESS)
            echo "successfully"
            throw $ex_code
            ;;
        *)
            echo "An unexpected exception was thrown"
            throw $ex_code
            ;;
    esac
}

exit 0