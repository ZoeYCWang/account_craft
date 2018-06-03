#!/bin/sh
#Date:2017/06/05
#Version:1.0.0
#Author:daiwq

TOMCATPATH=/usr/local/java/apache-tomcat-8.0.44
TOMCATPORT=80
cd $TOMCATPATH
pIDa=`/usr/sbin/lsof -i :$TOMCATPORT|grep -v "PID" | awk '{print $2}'`
if [ "$pIDa" != "" ];
then
  echo 'tomcat had started successfully.'
  echo "running in port:"$TOMCATPORT
  echo "the process ID:"$pIDa
  exit 0
else
  echo 'Tomcat had not started,please check again.'
  exit 1
fi
