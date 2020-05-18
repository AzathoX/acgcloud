drop table if exists tb_
create table if not exists tb_(
  id bigint primary key not null auto_increment,
  /**业务字段start**/
   /**业务字段end**/
  remark   varchar(255) null,
  is_del bigint not null default 0,
  create_time datetime default current_timestamp,
  modified_time datetime default current_timestamp,
  unique (id,is_del)
)engine=innodb,charset=utf8;