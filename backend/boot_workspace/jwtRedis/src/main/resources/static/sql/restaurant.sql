-- mysql restaurant db구축
-- user chef / 1234
-- 권한할
CREATE DATABASE IF NOT EXISTS restaurant;

CREATE USER 'chef'@'%' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON restaurant.* TO 'chef'@'%';
FLUSH PRIVILEGES;

create table role(
role_id int primary key auto_increment
, role_name varchar(10) -- ADMIN, STORE, USER
);

insert into role(role_name)
values(admin), (store), (user);

create table member(
    member_id int primary key auto_increment
    , id varchar(20)
    , password varchar(64)
    , name varchar(20)
    , role_id int
    , email varchar(100)
    , constraint fk_role_member foreign key (role_id) references role(role_id)
);