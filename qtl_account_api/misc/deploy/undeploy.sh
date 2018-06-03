#!/bin/sh

TOMCAT_HOME='/usr/local/java/apache-tomcat-8.0.44'
WORK_SPACE='/usr/local/account-api'

chmod +x $WORK_SPACE/misc/install/install_tomcat_check.sh

sh $WORK_SPACE/misc/install/install_tomcat_check.sh

if [ $? != 0 ]; then
    sh $TOMCAT_HOME/bin/shutdown.sh
    echo 'tomcat was shutdown'
fi

rm -rf $TOMCAT_HOME/logs;
mkdir $TOMCAT_HOME/logs;

rm -rf $WORK_SPACE
