#!/bin/sh

if [ $# -eq 0 ];then
  echo "the correct command is: deploy -env {alpha}"
  exit 1
fi

while [ "$1" != "" ]; do
	case $1 in
        -dn | --domain )  shift
								domain=$1
                                ;;
		-env | --env )	shift
								env=$1
								;;
        -h | --help )           usage
                                exit
                                ;;
        * )                     usage
                                exit 1
    esac
    shift
done

WORK_SPACE="/usr/local/account-api"
TOMCAT_HOME="/usr/local/java/apache-tomcat-8.0.44"
rm -rf $TOMCAT_HOME/logs
mkdir $TOMCAT_HOME/logs
ln -s $WORK_SPACE/dist $TOMCAT_HOME/webapps/ROOT
chmod +x $WORK_SPACE/misc/install/install_tomcat_check.sh
sh $WORK_SPACE/misc/install/install_tomcat_check.sh
echo "platform=${env}" > $WORK_SPACE/dist/WEB-INF/classes/platform.conf.properties
if [ $? == 0 ]; then
    sh $TOMCAT_HOME/bin/shutdown.sh
    pIDa=`ps -ef | grep tomcat | awk '{print $2}'`
    if [ "$pIDa" != "" ];
    then
        ps -ef | grep tomcat | awk '{print $2}' | xargs kill -9
    fi
    echo "tomcat will be restart"
fi

sh $TOMCAT_HOME/bin/startup.sh;
exit 0