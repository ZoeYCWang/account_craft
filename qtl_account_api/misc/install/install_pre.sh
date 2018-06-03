#!/bin/bash
#Date:2017/06/05
#Version:1.0.0
#Description:

installLogFile='/usr/local/account-api/misc/install/install.log';


if [[ ! -z $(rpm -qa | grep wget) ]];
then
   echo "wget had existed."
else
   yum -y install wget
fi

if [[ ! -z $(rpm -qa | grep unzip) ]];
then
   echo "unzip had existed."
else
   yum -y install unzip
fi

if [[ ! -z $(rpm -qa | grep e2fsprogs) ]];
then
   echo "e2fsprogs had existed."
else
   yum -y install e2fsprogs
fi

if [[ ! -z $(rpm -qa | grep lsof) ]];
then
   echo "lsof had existed."
else
   yum -y install lsof
fi

if [[ ! -z $(rpm -qa | grep make) ]];
then
   echo "make had existed."
else
   yum -y install make
fi

if [[ ! -z $(rpm -qa | grep gcc) ]];
then
   echo "gcc had existed."
else
   yum -y install gcc
fi

if [[ ! -z $(rpm -qa | grep cc) ]];
then
   echo "cc had existed."
else
   yum -y install cc
fi

# myDir
if [ ! -f "$installLogFile" ];
then
 touch "$installLogFile"
fi
