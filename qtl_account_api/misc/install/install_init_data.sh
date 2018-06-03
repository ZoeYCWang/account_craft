#!/bin/sh
#Date:2017/06/05
#Version:1.0.0
#Author:daiwq

mysql -uquantil -pquantil@123456 -e "

CREATE DATABASE IF NOT EXISTS account DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
use account;
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS account;

CREATE TABLE account (
  id varchar(36) NOT NULL,
  username varchar(32) NOT NULL,
  password varchar(128) NOT NULL,
  email varchar(128) DEFAULT NULL,
  tenant_id varchar(36) DEFAULT NULL,
  status int(11) NOT NULL,
  customization text,
  last_login_at datetime DEFAULT NULL,
  last_login_ip varchar(20) DEFAULT NULL,
  description varchar(512) DEFAULT NULL,
  deleted int(11) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(36) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(36) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO account VALUES ('c9089f3a41bb11e7827f782bcb048924', 'admin', '8fb3ec03d5775ec8654b7ff8966b5f53', 'admin@quantil.com', 'gslb', '0', '{\"timezone\":\"AUTO\"}', '2017-12-12 03:49:22', '120.41.4.231', null, '1', '2017-05-26 10:44:56', '', '2017-12-12 03:49:22', '');
INSERT INTO account VALUES ('f594f36e41bd11e7827f782b3b048724', 'quantil', '31dca14797d9399e92f3ca15bc531252', 'quantil@test.com', 'gslb', '0', '{\"timezone\":\"AUTO\"}', '2017-12-14 16:21:15', '127.0.0.1', 'just for test.', '1', '2017-05-29 21:51:11', '', '2017-12-14 16:21:15', '');

DROP TABLE IF EXISTS account_info;
CREATE TABLE account_info (
  id varchar(36) NOT NULL,
  account_id varchar(35) NOT NULL,
  nickname varchar(32) DEFAULT NULL,
  real_name varchar(64) DEFAULT NULL,
  email varchar(64) DEFAULT NULL,
  telephone varchar(32) DEFAULT NULL,
  corporation varchar(128) DEFAULT NULL,
  others text,
  deleted int(11) NOT NULL,
  description varchar(512) DEFAULT NULL,
  created_at datetime NOT NULL,
  created_by varchar(36) NOT NULL,
  updated_at datetime NOT NULL,
  updated_by varchar(36) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO account_info VALUES ('d1661ffa412911e7827f782bcb048924', 'beebf56c412811e7827f782bcb048924', '', '', '', '22', '222', '22', '0', '', '2017-04-21 18:33:26', 'c9089f3a41bb11e7827f782bcb048924', '2017-04-27 18:33:32', 'c9089f3a41bb11e7827f782bcb048924');
INSERT INTO account_info VALUES ('9cf8e87c41bc11e7827f782bcb048924', 'c9089f3a41bb11e7827f782bcb048924', null, null, null, null, null, null, '0', null, '2017-05-26 10:40:23', 'c9089f3a41bb11e7827f782bcb048924', '2017-05-26 10:48:31', 'c9089f3a41bb11e7827f782bcb048924');
INSERT INTO account_info VALUES ('a662a95641bd11e7827f782bcb048924', '1c2f775a41bd11e7827f782bcb048924', null, null, null, null, null, null, '0', null, '2017-05-26 10:48:10', 'c9089f3a41bb11e7827f782bcb048924', '2017-05-26 10:48:28', 'c9089f3a41bb11e7827f782bcb048924');
INSERT INTO account_info VALUES ('ab5c81de41bd11e7827f782bcb048924', '208e460a41bd11e7827f782bcb048924', null, null, null, null, null, null, '0', null, '2017-05-26 10:48:14', 'c9089f3a41bb11e7827f782bcb048924', '0000-00-00 00:00:00', 'c9089f3a41bb11e7827f782bcb048924');

DROP TABLE IF EXISTS account_role;
CREATE TABLE account_role (
  id varchar(36) NOT NULL,
  role_id varchar(36) NOT NULL,
  account_id varchar(36) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS account_role;
CREATE TABLE account_role (
  id varchar(36) NOT NULL,
  role_id varchar(36) NOT NULL,
  account_id varchar(36) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO account_role VALUES ('60e5cefe41bc11e7827f782bcb048924', '6b3b229241bb11e7827f782bcb048924', 'c9089f3a41bb11e7827f782bcb048924');
INSERT INTO account_role VALUES ('edacab7c41bd11e7827f782bcb06671', '3ceb389c412911e7827f782bcb048924', 'f594f36e41bd11e7827f782b3b048724');


DROP TABLE IF EXISTS account_tenant;
CREATE TABLE account_tenant (
  id varchar(36) NOT NULL,
  account_id varchar(36) NOT NULL,
  tenant_id varchar(36) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO account_tenant VALUES ('15968fe441bf11e7827f782bcb048924', 'c9089f3a41bb11e7827f782bcb048924', 'e3c359de41be11e7827f782bcb048924');

DROP TABLE IF EXISTS permission;
CREATE TABLE permission (
  id varchar(36) NOT NULL,
  url varchar(512) DEFAULT NULL,
  method varchar(64) NOT NULL,
  type varchar(64) DEFAULT NULL,
  description varchar(512) DEFAULT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(36) DEFAULT NULL,
  updated_by varchar(36) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  deleted int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO permission VALUES ('5fab3b48fa6169eeee25138054daca35', '/api/0.1/account/systems/version', 'get', 'API', '', '2017-11-27 04:34:03', 'f594f36e41bd11e7827f782b3b048724', '', '2017-11-27 04:34:03', '0');
INSERT INTO permission VALUES ('bdac56998340af0da9701e9f5647180e', '/api/0.1/account/systems/version', 'get', 'account1', 'test1', '2017-11-27 03:35:19', 'f594f36e41bd11e7827f782b3b048724', 'quantil', '2017-11-27 03:35:19', '0');

DROP TABLE IF EXISTS permission_role;
CREATE TABLE permission_role (
  id varchar(36) NOT NULL,
  role_id varchar(36) NOT NULL,
  permission_id varchar(36) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS role;
CREATE TABLE role (
  id varchar(36) NOT NULL,
  code varchar(64) DEFAULT NULL,
  name varchar(64) NOT NULL,
  as_default bit(1) DEFAULT NULL,
  description varchar(512) DEFAULT NULL,
  parent_id varchar(36) DEFAULT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(36) DEFAULT NULL,
  updated_by varchar(36) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  deleted int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


INSERT INTO role VALUES ('3ceb389c412911e7827f782bcb048924', null, 'admin', 0, 'Administrator', null, '2017-10-16 03:29:09', '', '', '2017-10-16 03:29:32', '1');
INSERT INTO role VALUES ('6b3b229241bb11e7827f782bcb048924', null, 'root', 0, 'Super administrator', null, null, null, null, null, '1');
INSERT INTO role VALUES ('f88dec8241bc11e7827f782bcb048924', null, 'user', 0, 'Regular user', null, '2017-11-28 21:55:54', '', '', '2017-11-28 21:55:54', '1');
INSERT INTO role VALUES ('843beece9dcd11e7b3299286e2e6d4a7', null, 'op', 0, 'DevOps', null, '2017-10-16 03:28:30', '', '', '2017-12-11 04:31:25', '1');
INSERT INTO role VALUES ('aa78468e9dce11e7b3299286e2e6d4a7', null, 'watch', 1, 'Readonly', null, '2017-10-16 03:38:32', '', '', '2017-11-28 21:55:54', '1');
INSERT INTO role VALUES ('fb0d21541ee34d3acf8b620bcde904ea', null, 'Quantil Portal Control', 0, 'Control API access for Quantil portal, DO NOT assign to GSLB Portal users', null, '2017-12-12 02:35:15', 'f594f36e41bd11e7827f782b3b048724', '', '2017-12-12 03:27:55', '1');

DROP TABLE IF EXISTS system_config;
CREATE TABLE system_config (
  id varchar(36) NOT NULL,
  name varchar(24) NOT NULL,
  value text NOT NULL,
  catalog int(11) DEFAULT NULL,
  data_type varchar(64) DEFAULT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(36) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(36) DEFAULT NULL,
  deleted int(1) NOT NULL,
  description varchar(512) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO system_config VALUES ('347c776c6fd8889076c87d1f276a6885', 'permission_types', '[\"PAGE\",\"ACTION\",\"API_ACCOUNT\",\"API_RDC\",\"API_GSLB\"]', null, 'json', '2017-12-07 02:44:02', 'f594f36e41bd11e7827f782b3b048724', '2017-12-07 03:02:10', '', '1', null);
INSERT INTO system_config VALUES ('a93606b628996dcb41a457ed8625e632', 'restful_methods', '[\"get\",\"post\",\"put\",\"patch\",\"delete\"]', null, 'json', '2017-12-07 15:17:47', 'c9089f3a41bb11e7827f782bcb048924', '2017-12-07 02:48:38', '', '1', null);
INSERT INTO system_config VALUES ('ff839d89a58911e7a99c0a002700000b', 'custom', '{\"timezone\":\"AUTO\"}', '1', 'json', '2017-09-30 10:49:00', '', '2017-10-23 04:23:59', '', '1', '');


DROP TABLE IF EXISTS tenant;
CREATE TABLE tenant (
  id varchar(36) NOT NULL,
  name varchar(128) NOT NULL,
  description varchar(512) DEFAULT NULL,
  admin varchar(36) NOT NULL,
  deleted int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


INSERT INTO tenant VALUES ('rdc', 'rdc', null, 'beebf56c412811e7827f782bcb048924', '1');
INSERT INTO tenant VALUES ('gslb', 'gslb', null, 'beebf56c412811e7827f782bcb048924', '1');
INSERT INTO tenant VALUES ('#', '#', null, 'c9089f3a41bb11e7827f782bcb048924', null);
"
