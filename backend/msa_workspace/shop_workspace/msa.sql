-- MSA 프로젝트 DB만들기
-- 서로 REST API로 통신
create database productdb;

create database orderdb;

create database memberdb;

-- 유저 만들기
create user 'msa'@'%' identified by '1234';

grant all privileges on productdb.* to 'msa'@'%' with grant option;

grant all privileges on orderdb.* to 'msa'@'%' with grant option;

grant all privileges on memberdb.* to 'msa'@'%' with grant option;