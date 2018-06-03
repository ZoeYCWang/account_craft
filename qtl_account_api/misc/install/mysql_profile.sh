#!/bin/sh
#Date:2017/06/05
#Version:1.0.0
#Author:daiwq

ACCOUNT='/usr/local/account-api/misc'
INSTALL_DIR="$ACCOUNT/install"
MYSQL="$ACCOUNT/etc"

MYSQL_U_ROOT=
MYSQL_P_ROOT=
MYSQL_U_ACCOUNT=
MYSQL_P_ACCOUNT=
cat $MYSQL | while read LINE
do
    KEY=`echo $1 | grep  -oP '(?<=\=)(.*)'`;
    if [ $KEY == 'mysql_u_root' ];then
        MYSQL_U_ROOT=`echo $1 | awk -F '='  '{print $1}'`;
    elif [ $KEY == 'mysql_p_root' ];then
        MYSQL_P_ROOT=`echo $1 | awk -F '='  '{print $1}'`;
    elif [ $KEY == 'mysql_u_root' ];then
        MYSQL_U_ROOT=`echo $1 | awk -F '='  '{print $1}'`;
    elif [ $KEY == 'mysql_p_root' ];then
        MYSQL_P_ROOT=`echo $1 | awk -F '='  '{print $1}'`;
    fi
done