# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table task (
  group_number              varchar(255),
  day                       varchar(255),
  hours                     varchar(255),
  lection                   varchar(255),
  teacher                   varchar(255),
  room                      varchar(255))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists task;

SET REFERENTIAL_INTEGRITY TRUE;

