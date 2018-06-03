#!/bin/sh
#Date:2017/06/05
#Version:1.0.0
#Author:daiwq


LOG_FILE='/usr/local/account-api/misc/install/install.log'
PACKAGES_DIR=/tmp/packages
MYSQL_ROOT_PASSWORD="Quantil@456321"
MYSQL_USER_PASSWORD="Quantil@456321"


# install mysql 5.6+
function install_mysql()
{
	# install mysql
	output "Installing mysql..."
	# mysql_version=`mysql --version 2>>$LOG_FILE`
	if [[ $mysql_version =~ "5.7" ]]; then
		output "mysql $mysql_version already installed."
	else
		chattr -i /etc/group
		chattr -i /etc/gshadow
		chattr -i /etc/passwd
		chattr -i /etc/shadow
		cd $PACKAGES_DIR
		wget http://dev.mysql.com/get/mysql57-community-release-el7-8.noarch.rpm --no-check-certificate
		yum -y localinstall mysql57-community-release-el7-8.noarch.rpm
		yum -y install mysql-community-server
		systemctl start mysqld
		if [ $? -eq 0 ];then
		     output "mysqld is successfully started"
		     # install mysql-python dependency
		else
		     output "mysqld failed to start."
		     exit
		fi
		systemctl enable mysqld
		systemctl daemon-reload
		# update default root password for mysql
		default_password=`grep 'temporary password' /var/log/mysqld.log | awk '{print $NF}'`
		# echo "skip-grant-tables" >> /etc/my.cnf
		# service mysqld restart
		# sleep 3s
		mysql -uroot -p$default_password --connect-expired-password -e "ALTER USER 'root'@'localhost' IDENTIFIED BY '$MYSQL_ROOT_PASSWORD';"
		# ALTER USER 'root'@'localhost' IDENTIFIED BY 'Quantil@456321';
		# SET PASSWORD FOR 'root'@'localhost' = PASSWORD('Quantil@456321');
		if [ $? -eq 0 ];then
		     output "Default root password $default_password is successfully reset."
		     # install mysql-python dependency
		else
		     output "Failed to reset default root password."
		     exit
		fi

		mysql -uroot -p$MYSQL_ROOT_PASSWORD -e "create user 'quantil'@'localhost' identified by '$MYSQL_USER_PASSWORD';grant all privileges on *.* to 'quantil'@'localhost';"

		if [ $? -eq 0 ];then
		     output "Created db user quantil successfully."
		     # install mysql-python dependency
		else
		     output "Failed to create db user quantil."
		     exit
		fi
	fi
}
install_mysql;

