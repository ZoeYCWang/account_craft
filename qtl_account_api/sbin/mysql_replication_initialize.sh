#!/bin/bash
#
#
#Absolute path to this script  
SCRIPT=$(readlink -f $0)
#Absolute path this script is in
SCRIPTPATH=$(dirname $SCRIPT)
.   $SCRIPTPATH/../etc/common

function usage()
{
    echo "the correct command is: sync_initialize.sh -ip {op_ip}"
    echo "the database is mariadb."
}
if [ "$1" == "" ]
then
    usage
    exit
fi

while [ "$1" != "" ]
do
    case $1 in
        -ip | --ip )
            shift
            IP=$1
            ;;
        -h | --help )
            usage
            exit 1
            ;;
        * )
            usage
            exit 1
    esac
    shift
done

function start_mysql_master()
{
    if [ -f /etc/my.cnf ]
    then
        mv /etc/my.cnf /etc/my.cnf.bk
        cat /etc/my.cnf.bk | awk '{
        if ($0=="[mysqld]")
        {
            print $0 >> "/etc/my.cnf";
            print "server-id="ID >> "/etc/my.cnf";
            print "log-bin=mysql-bin" >> "/etc/my.cnf";
            print "log-bin=/var/lib/mysql/mysql-bin" >> "/etc/my.cnf"
            print "relay-log = mysql-relay-bin" >> "/etc/my.cnf";
            print "replicate-wild-ignore-table=mysql.%" >> "/etc/my.cnf";
            print "replicate-wild-ignore-table=information_schema.%" >> "/etc/my.cnf";
            print "skip_slave_start" >> "/etc/my.cnf"
            print "#log_slave_updates=1" >> "/etc/my.cnf"
        }
    else
        print $0 >> "/etc/my.cnf";
    }' ID=$RANDOM
    else
        echo "pls check the file /etc/my.cnf"
        exit 1
    fi
    #grant replication user
    /usr/bin/mysql -u $MYSQL_U_ROOT -p$MYSQL_P_ROOT -e "grant replication slave on *.* to '$MYSQL_REPL_USER'@'%' identified by '$MYSQL_REPL_USER_PASSWORD';" 2>> /dev/null 
    /usr/bin/mysql -u $MYSQL_U_ROOT -p$MYSQL_P_ROOT -e "grant all privileges on  *.* to '$MYSQL_REPL_USER'@'$IP' identified by '$MYSQL_REPL_USER_PASSWORD'" 2>> /dev/null
    /usr/bin/mysql -u $MYSQL_U_ROOT -p$MYSQL_P_ROOT -e "flush privileges;" 2>> /dev/null
    /usr/bin/mysql -u $MYSQL_U_ROOT -p$MYSQL_P_ROOT -e "set global innodb_flush_log_at_trx_commit=2;" 2>> /dev/null
    systemctl restart mariadb
}

MASTER_INFO=`mysql -u $MYSQL_U_ROOT -p$MYSQL_P_ROOT -e "show master status;"`
if [ -z "$MASTER_INFO" ]
then
    start_mysql_master
else
    echo "master have started."
fi
