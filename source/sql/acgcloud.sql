drop database if exists acgcloud;
create database if not exists acgcloud charset utf8;

use acgcloud;

drop table if exists tb_prartition
create table if not exists tb_prartition(
  id bigint primary key not null auto_increment,
  /**业务字段start**/
  filesys varchar(255) not null,
  vp_name char(128) not null ,
  vp_hash_name char(255) not null unique,
  vp_size double(5,2) default 0.00,
   /**业务字段end**/
  remark   varchar(255) null,
  remark1  varchar(255) null,
  remark2  varchar(255) null,
  remark3  varchar(255) null,
  remark4  varchar(255)  null,
  remark5  varchar(255)  null,
  remark6  varchar(255) null,
  remark7  varchar(255)  null,
  is_del bigint not null default 0,
  create_time datetime default current_timestamp,
  modified_time datetime default current_timestamp,
  unique (id,is_del)
)engine=innodb,charset=utf8;

drop table if exists tb_logic_catalog;
create table if not exists tb_logic_catalog
(
  id bigint primary key not null auto_increment,
  /**业务字段start**/
  prartition_id bigint not null,
  catalog_name char(128) not null ,
  catalog_hash_name char(255) not null unique,
  /**业务字段end**/
  remark   varchar(255) null,
  remark1  varchar(255) null,
  remark2  varchar(255) null,
  remark3  varchar(255) null,
  remark4  varchar(255)  null,
  remark5  varchar(255)  null,
  remark6  varchar(255) null,
  remark7  varchar(255)  null,
  is_del bigint not null default 0,
  create_time datetime default current_timestamp,
  modified_time datetime default current_timestamp,
  unique (id,is_del)
)engine=innodb,charset=utf8;


drop table if exists tb_cloud_flodler;
create table if not exists tb_cloud_flodler
(
  id bigint primary key not null auto_increment,
  /**业务字段start**/
  prartition_id bigint not null,
  filesys varchar(255) not null,
  logic_path varchar(255) not null,
  catalog_id bigint not null,
  parent_id bigint null,
  isfile boolean default false,
  suffix char(10) null,
  name char(128) not null,
  hash_name char(255) not null unique,
  vp_size double(5,2) default 0.00,
  /**业务字段end**/
  remark   varchar(255) null,
  remark1  varchar(255) null,
  remark2  varchar(255) null,
  is_del bigint not null default 0,
  create_time datetime default current_timestamp,
  modified_time datetime default current_timestamp,
  unique (id,is_del)
)engine=innodb,charset=utf8;


drop table if exists tb_cloud_wkstation;
create table if not exists tb_cloud_wkstation(
  id bigint primary key not null auto_increment,
  /**业务字段start**/
  user_id bigint not null default 0,
  flodler_id bigint not null,
  logic_path varchar(255) not null,
  is_admin boolean default false,
  /**业务字段end**/
  is_del bigint not null default 0,
  remark   varchar(255) null,
  remark1  varchar(255) null,
  remark2  varchar(255) null,
  remark3  varchar(255) null,
  remark4  varchar(255)  null,
  remark5  varchar(255)  null,
  create_time datetime default current_timestamp,
  modified_time datetime default current_timestamp,
  unique (id,is_del)
)engine=innodb,charset=utf8;
