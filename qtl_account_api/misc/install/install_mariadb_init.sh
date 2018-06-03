#!/bin/sh

cat > /etc/my.cnf << 'EOF'
[mysqld]
datadir=/var/lib/mysql
socket=/var/lib/mysql/mysql.sock
symbolic-links=0

[mysqld_safe]
log-error=/var/log/mariadb/mariadb.log
pid-file=/var/run/mariadb/mariadb.pid

!includedir /etc/my.cnf.d
EOF

/usr/bin/expect <<EOF
    spawn mysql_secure_installation
    expect {
        "Enter current password for root (enter for none):" {
             send "\r";
             exp_continue;
        }
        "Set root password*" {
            send "\r";
            exp_continue;
        }
        "New password*" {
            send "Quantil@456123\r";
            exp_continue;
        }
        "Re-enter new password*" {
            send "Quantil@456123\r";
            exp_continue;
        }
        "Remove anonymous users*" {
              send "\r";
              exp_continue;
        }        
        "Disallow root login remotely*" {
              send "\r";
              exp_continue;
        }
        "Remove test database and access to it*" {
               send "\r";
               exp_continue;
         }
        "Reload privilege tables now*" {
               send "\r";
               exp_continue;
        }
    }
    interact
EOF

systemctl restart mariadb

mysql -uroot -pQuantil@456123 -e "
create user quantil@localhost identified by 'quantil@123456';
grant all on *.* to quantil@localhost identified by 'quantil@123456';
grant all privileges on *.* to quantil@'%' identified by 'quantil@123456';
"
