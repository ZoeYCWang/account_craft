#!/bin/sh

#Absolute path to this script  
SCRIPT=$(readlink -f $0)
#Absolute path this script is in
SCRIPTPATH=$(dirname $SCRIPT)
.   $SCRIPTPATH/../etc/common
.   $SCRIPTPATH/state_change_common.sh

function usage()
{
    echo -e "${green} usage:${SCRIPT} -m|--master-ip <master-ip> [-p|--port <port> -a | --auth <master-auth> ] ${nc}"
}

MASTER_IP=""
DEFAULT_MASTER_AUTH=$REDIS_PASSWORD
NEED_RESTART=0
MASTER_AUTH=""

function check_params()
{
    echo -e "${blue} Check params which user input${nc}"

    if [ "$MASTER_IP" = "" ]
    then
        usage
        exit 1
    fi

    if [ "$MASTER_AUTH" = "" ]
    then
        MASTER_AUTH=$DEFAULT_MASTER_AUTH
    elif [ "$MASTER_AUTH" != "$DEFAULT_MASTER_AUTH" ]
    then
        NEED_RESTART=1
    fi
}

function set_redis_read_only_replication()
{
    PORT=$1
    /usr/local/bin/redis-cli -p $PORT -a $REDIS_PASSWORD slaveof "$MASTER_IP" $PORT
    /usr/local/bin/redis-cli -p $PORT -a $REDIS_PASSWORD config set masterauth "$MASTER_AUTH"
    sed -i "s/^#* *slaveof.*/slaveof $MASTER_IP $PORT/" /etc/redis/redis_$PORT.conf
    sed -i "s/^#* *masterauth.*/masterauth $MASTER_AUTH/" /etc/redis/redis_$PORT.conf
}

function set_mysql_read_only_replication()
{
    #set innodb
    mysql -u $MYSQL_U_REPL -p$MYSQL_P_REPL -e "set global innodb_flush_log_at_trx_commit=2;" 2>/dev/null
    # get master database status
    MASTER_INFO=`mysql -u $MYSQL_U_REPL -h $MASTER_IP -p$MYSQL_P_REPL -e "show master status;" 2>/dev/null`
    LOG_FILE=`echo $MASTER_INFO | awk '{print $6}'`
    LOG_POS=`echo $MASTER_INFO | awk '{print $7}'`
    # add master information on slave
    mysql -u $MYSQL_U_REPL -p$MYSQL_P_REPL -e "change master to master_host='$MASTER_IP',master_user='$MYSQL_REPL_USER',master_password='$MYSQL_U_REPL',master_log_file='$LOG_FILE',master_log_pos=$LOG_POS;"
    mysql -u $MYSQL_U_REPL -p$MYSQL_P_REPL -e "start slave;" 2>/dev/null
    # get slave database status
    SLAVE_INFO=`mysql -u $MYSQL_U_REPL -p$MYSQL_P_REPL -e "show slave status\G;" 2>/dev/null | grep Running`
    SLAVE_IO_STATUS=`echo $SLAVE_INFO | awk '{print $2}'`
    SLAVE_SQL_STATUS=`echo $SLAVE_INFO | awk '{print $4}'`
    if [[ "$SLAVE_IO_STATUS" == 'Yes' && "$SLAVE_SQL_STATUS" == 'Yes' ]]
    then
        echo -e "${green} Mysql database sync is successfuly! ${nc}"
    else
        echo $SLAVE_INFO
    fi
}

while [ "$1" != "" ]
do
    case $1 in
        -f | --force ) shift
                        FORCE=1
                        ;;
        -m | --master-ip ) shift
                        MASTER_IP=$1
                        ;;
        -p | --port ) shift
                        PORTS=$1
                        ;;
        -a | --auth ) shift
                        MASTER_AUTH=$1
                        ;;
        -h | --help ) 
                        usage
                        exit
                        ;;
        * )             usage
                        exit 1
    esac
    shift
done

check_params

echo -e "${blue} Check now state file"

if [ ! -f "${REDIS_STATUS_FILE}" ]
then
    generate_state_file
fi

NOW_STATE=`sed '/^account-api-state=/!d;s/.*=//' $REDIS_STATUS_FILE`
echo -e "${blue} Now state is ${NOW_STATE}. ${nc}"


echo -e "${blue} set mysql to read only replication. ${nc}"
set_mysql_read_only_replication

echo -e "${blue} Set redis to read only replication. ${nc}"
for port in ${PORTS[@]}
do
    set_redis_read_only_replication $port
    if [ $NEED_RESTART -eq 1 ]
    then
        /usr/local/bin/redis-cli -p $port -a $REDIS_PASSWORD shutdown
        /etc/init.d/redis_$port start
    fi
done


echo -e "${blue} Set now state to read only replication state.. ${nc}"
echo "account-api-state=readonlyreplication" >$REDIS_STATUS_FILE
echo -e "${green} Change the account-api state to read-only-replication success. ${nc}"