#!/bin/sh

if [ $# -eq 0 ];then
  echo "the correct command is: build.sh -env {alpha}"
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
        -mvn | --mvn )   shift
                                 mvn=$1
                                 ;;
        * )                     usage
                                exit 1
    esac
    shift
done

#change the config

#building
$mvn/bin/mvn clean package sonar:sonar -Dsonar.language=java -Dsonar.sourceEncoding=UTF-8 -U
unzip -o account-web/target/account-api.war -d dist
cp -r sbin dist/
cp -r etc dist/
cp VERSION dist/

