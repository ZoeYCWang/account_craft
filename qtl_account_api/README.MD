## account-api

Account is project authority certification center that provides account-related management, and one user in the time one token verifying.
Also there have role, tenant and maybe menu management.
 
 
## Installation
To successfully install and deploy Account API, there are following prerequirements:
- JDK = 1.8.0_65
- MySql = 5.7.18
- Tomcat = apache-tomcat-8.0.44

## Step 1: Download package.
 
Download from software repository and uncompress tar file to directory /usr/local.  
   
```
cd /usr/local 
```
Check the software repo [http://softrepo.qtlcdn.com/account-api/](http://softrepo.qtlcdn.com/account-api/) and select a version to install.
```
## you can modifiy if project name is not account-api
PROJECT_NAME=account-api
BRANCH_NAME=develop

http://softrepo.qtlcdn.com/account-api/$BRANCH_NAME/$PROJECT_NAME-1.0.0-$BRANCH_NAME.tar.gz 
tar -zxvf $PROJECT_NAME-$BRANCH_NAME.tar.gz
```


## Step 2: Install basic dependency package

```
sh /usr/local/$PROJECT_NAME/misc/install/install.sh 
```

## Step 3 deploy project

```
sh /usr/local/$PROJECT_NAME/deploy/deploy.sh -env $BRANCH_NAME
```

## Step 4: Post Check

### 1.  Check mysql DB.

A DB user quantil with initial password quantil@123456 should be successfully created. To check this user's existence:
mysql -uquantil -p'quantil@123456' 

A database named account and its tables should be successfully created. To check this, in mysql command line:


```
show databases;
use account;
show tables;
```


### 2.Check jdk

JDK Environment is basic for java, so make sure it is running, the command line:

```
java -version
```
It will show the follow info

```
java version "1.8.0_131"
Java(TM) SE Runtime Environment (build 1.8.0_131-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.131-b11, mixed mode)

```

### 3.Check tomcat
Tomcat are war package running host, account.war will be deployed in path "/usr/local/java/apache-tomcat-8.0.44/webapps", So can check if it exists.
``` 
 sh /usr/local/account-api/misc/install/install_tomcat_check.sh
```

### 4.Check online SwaggerUI

After all installations and configurations, to check if API is working, please visit:http://{domain_name}/docs or http://{ip}/docs. A swagger UI will show and you can try out APIs after you login successfully.




