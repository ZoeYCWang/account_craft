#!/bin/sh
#Date:2017/06/05
#Version:1.0.0
#Author:daiwq
PACKAGES_DIR='/tmp/packages'
REDIS='redis-3.2.10'
INSTALL_DIR='/usr/local/account-api/misc/install'
PORT=63791

function install_redis()
{
	echo "Installing Redis..."
	#cd $PACKAGES_DIR
	if [ ! -d "$REDIS" ];
    then
       wget http://softrepo.qtlcdn.com/third_party/redis/$REDIS.tar.gz
       tar xvzf $REDIS.tar.gz
    fi

	cd $REDIS
	make
	make MALLOC=libc
	make install

	if [ ! -d /etc/redis ]; then
        mkdir /etc/redis
    fi

	cp -f $INSTALL_DIR/redis_$PORT.conf /etc/redis/

    if [ ! -d /var/redis/redis_$PORT ]; then
        mkdir -p /var/redis/redis_$PORT
    fi
	cp $INSTALL_DIR/redis_$PORT /etc/init.d/
	chmod +x /etc/init.d/redis_$PORT

	# we need to set up 1 redis instances
	/etc/init.d/redis_$PORT start
	if [ $? -eq 0 ]; then
        echo "redis $PORT is successfully started"
    else
        echo "redis $PORT failed to start, please check log for more details"
    fi

	echo "Redis is successfully installed and configured."
}

function check_redis()
{
	redis_version=`redis-server --version 2>>$log_file`
	if [[ $redis_version =~ "Redis server v=3.2" ]];then
		output "Redis server 3.2 is already installed."
		# check if the configs and 63791 is up and running
		return 1
	else
		output "None or other redis are installed."
		return 0
	fi
}

#setup the redis

cat > /usr/lib/systemd/system/redis.service << EOF
[root@b61f106f09a9 system]# cat redis.service
[Unit]
Description=Redis
After=network.target remote-fs.target nss-lookup.target

[Service]
Type=forking
ExecStart=/etc/init.d/redis_$PORT start

[Install]
WantedBy=multi-user.target
EOF


redis_version=`redis-server --version`
if [[ $redis_version =~ "Redis server v=3.2" ]];then
	echo "Redis server 3.2 is already installed."
else
   install_redis
fi