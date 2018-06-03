#!/bin/sh
#Date:2017/06/05
#Version:1.0.0
#Author:daiwq


function install_mariadb ()
{
    cat > /etc/yum.repos.d/MariaDB.repo << 'EOF'
[mariadb]
name = MariaDB
baseurl = http://yum.mariadb.org/10.3.0/centos/7.2/x86_64/
gpgkey=https://yum.mariadb.org/RPM-GPG-KEY-MariaDB
gpgcheck=1
EOF
    if [[ ! -z $(rpm -qa | grep expect) ]];
    then
       echo "expect had existed."
    else
       yum -y install expect
    fi

    if [[ ! -z $(rpm -qa | grep mariadb) ]];
    then
       echo "mariadb had existed."
    else
       yum -y install mariadb mariadb-server
       systemctl start mariadb
       systemctl enable mariadb
    fi
}

install_mariadb;
