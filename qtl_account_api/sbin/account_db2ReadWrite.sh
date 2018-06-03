#!/bin/sh
# 
#

#Aabsolute path to this script  
SCRIPT=$(readlink -f $0)
#Absolute path this script is in
SCRIPTPATH=$(dirname $SCRIPT)
.   $SCRIPTPATH/../etc/common
.   $SCRIPTPATH/state_change_common.sh

function usage()
{
    echo -e "${green} usage:${SCRIPT} -p|--p {port} ${nc}"
}

while [ "$1" != "" ]; do
    case $1 in
        -f | --force ) shift
                        FORCE=1
                        ;;
        -p | --port ) shift
                        PORTS=$1
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

function set_redis_read_write()
{
	PORT=$1
	/usr/local/bin/redis-cli -p $PORT -a $REDIS_PASSWORD slaveof NO ONE
	sed -i "s/\(^ *slaveof.*\)/# \1/" /etc/redis/redis_$PORT.conf
}

function set_mysql_read_write()
{
    mysql -u $MYSQL_U_REPL -p$MYSQL_P_REPL -e "stop slave;" 2>/dev/null 
}

if [ ! -f "${REDIS_STATUS_FILE}" ]
then
    generate_state_file
fi

echo -e "${blue} set mysql to read write. ${nc}"
set_mysql_read_write

echo -e "${blue} Set redis to read write. ${nc}"
for port in ${PORTS[@]}
do
	set_redis_read_write $port
done

echo -e "${blue} Set now state to read write state.. ${nc}"
echo "account-api-state=readwrite" > $REDIS_STATUS_FILE
echo -e "${green} Change the account-api state to read-write success! ${nc}"



