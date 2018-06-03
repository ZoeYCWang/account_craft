#!/bin/sh

#Absolute path to this script  
SCRIPT=$(readlink -f $0)
#Absolute path this script is in
SCRIPTPATH=$(dirname $SCRIPT)
.   $SCRIPTPATH/../etc/common
.   $SCRIPTPATH/state_change_common.sh


function usage()
{
    echo -e "${green} usage:${SCRIPT} [-p|--port <port>] ${nc}"
}

function set_redis_read_only()
{
    PORT=$1
    /usr/local/bin/redis-cli -p $PORT -a $REDIS_PASSWORD slaveof "readonly" 1
    sed -i "s/^#* *slaveof.*/slaveof readonly 1/" /etc/redis/redis_$PORT.conf
    sed -i "s/^#* *masterauth.*/#masterauth /" /etc/redis/redis_$PORT.conf
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

echo -e "${blue} Check now state file"
if [ ! -f "${REDIS_STATUS_FILE}" ]
then
    generate_state_file
fi

NOW_STATE=`sed '/^account-api-state=/!d;s/.*=//' $REDIS_STATUS_FILE`
echo -e "${blue} Now state is ${NOW_STATE}. ${nc}"

echo -e "${blue} Set redis to read only. ${nc}"
for PORT in ${PORTS[@]}
do
    set_redis_read_only $PORT
done

echo -e "${blue} Set now state to read only state.. ${nc}"
echo "account-api-state=readonly" >$REDIS_STATUS_FILE
echo -e "${green} Change the account-api state to read-only success! ${nc}"