#!/bin/sh
#Date:2017/06/05
#Version:1.0.0
#Author:daiwq

export JAVA_HOME=/usr/local/java/jdk1.8.0_131
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$JAVA_HOME/bin:$PATH

TOMCAT_HOME='/usr/local/java/apache-tomcat-8.0.44'
JAVA_PATH='/usr/local/java/'
TOMCAT_NAME='apache-tomcat-8.0.44.tar.gz'
function install_tomcat()
{
    if [ ! -d "$TOMCAT_HOME" ];
    then
        if [ ! -f "$TOMCAT_NAME" ];
        then
            wget http://softrepo.qtlcdn.com/third_party/java/$TOMCAT_NAME
        fi
        tar -zxvf "$TOMCAT_NAME" -C "$JAVA_PATH"
        sh $TOMCAT_HOME"/bin/startup.sh"
        echo "tomcat had started...."
    else
        echo "tomcat had existed"
        sh $TOMCAT_HOME"/bin/shutdown.sh"
        sh $TOMCAT_HOME/"bin/startup.sh"
    fi

    rm -rf "$TOMCAT_HOME"/webapps/docs
    rm -rf $TOMCAT_HOME/webapps/ROOT;

}

if [ ! -d "$TOMCAT_HOME" ];
then
   install_tomcat
fi

cat > /usr/lib/systemd/system/tomcat.service << EOF
[root@b61f106f09a9 system]# cat redis.service
[Unit]
Description=Tomcat
After=network.target remote-fs.target nss-lookup.target

[Service]
Type=forking
ExecStart=/usr/local/java/apache-tomcat-8.0.44/bin/startup.sh

[Install]
WantedBy=multi-user.target
EOF

