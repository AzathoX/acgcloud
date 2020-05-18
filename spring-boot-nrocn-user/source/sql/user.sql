create database if not exists user charset utf8;
drop database if exists user;
use user;
drop table if exists tb_account
create table if not exists tb_account(
  id bigint primary key not null auto_increment,
  /**业务字段start**/
  tel char(11) null unique,
  mail char(64) not null unique,
  username char(32) not null unique,
  password varchar(255) null,
  password_salt varchar(255) not null,
  /**业务字段end**/
  remark    varchar(255) null,
  remark1   varchar(255) null,
  remark2   varchar(255) null,
  remark3   varchar(255) null,
  is_del bigint not null default 0,
  create_time datetime default current_timestamp,
  modified_time datetime default current_timestamp,
  unique (id,is_del)
)engine=innodb,charset=utf8;


drop table if exists tb_user
create table if not exists tb_user(
  id bigint primary key not null auto_increment,
  account_id bigint not null unique,
  /**业务字段start**/
  username char(32) not null unique,
  myname varchar(255) null,
  /**业务字段end**/
  remark   varchar(255) null,
  remark1   varchar(255) null,
  remark2   varchar(255) null,
  remark3   varchar(255) null,
  remark4   varchar(255) null,
  remark5   varchar(255) null,
  remark6   varchar(255) null,
  remark7   varchar(255) null,
  remark8   varchar(255) null,
  remark9   varchar(255) null,
  remark10   varchar(255) null,
  remark11   varchar(255) null,
  remark12   varchar(255) null,
  remark13   varchar(255) null,
  remark14   varchar(255) null,
  remark15   varchar(255) null,
  remark16   varchar(255) null,
  remark17   varchar(255) null,
  remark18   varchar(255) null,
  remark19   varchar(255) null,
  remark20   varchar(255) null,
  remark21   varchar(255) null,
  remark22   varchar(255) null,
  remark23   varchar(255) null,
  remark24   varchar(255) null,
  remark25   varchar(255) null,
  is_del bigint not null default 0,
  create_time datetime default current_timestamp,
  modified_time datetime default current_timestamp,
  unique (id,is_del)
)engine=innodb,charset=utf8;