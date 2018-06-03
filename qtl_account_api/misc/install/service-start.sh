#!/bin/sh
#Date:2017/06/05
#Version:1.0.0
#Author:daiwq

/usr/local/java/apache-tomcat-8.0.44/bin/startup.sh
systemctl start mariadb
/etc/init.d/redis_63791 start
