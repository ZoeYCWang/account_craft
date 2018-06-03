#!/bin/sh

PORTS=(63791)
FORCE=0

function generate_state_file()
{
    echo -e "${blue} generate state file.${nc}"
    master_host=`/usr/local/bin/redis-cli -p ${PORTS[1]} -a $REIDS_PASSWORD info replication | grep master_host | awk -F ":" '{print $2}'`
    if [ "$master_host" = "readonly" ]
    then
        echo -e "${blue} master host is readonly. ${nc}"
        echo "account-api-state=readonly" >$REDIS_STATUS_FILE
    elif [ "$master_host" = "" ]
    then
        echo -e "${blue} master host is empty, account-api is in master state. ${nc}"
        echo "account-api-state=readwrite" >$REDIS_STATUS_FILE
    else
        echo -e "${blue} account-api is slave of ${master_host}. ${nc}"
        echo "account-api-state=readonlyreplication" >$REDIS_STATUS_FILE
    fi
}

