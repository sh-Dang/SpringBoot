create database electroshop;

-- 유저 shop2를 1234 비밀번호로 생성
create user 'shop2'@'%' identified by '1234';

-- shop2에게 electroshop에 대한 모든 권한 부여
grant all privileges on electroshop.* to 'shop2'@'%' with grant option;

flush privileges;

-- topcategory 테이블 생성
create table topcategory(
    topcategory_id int primary key auto_increment
    , topcategory varchar(20) not null
);

-- top category 테이블 인서트
insert into topcategory(topcategory)
values ('그래픽카드'),('CPU'),('메모리'),('키보드');

-- store table 생성
create table store(
    store_id int primary key auto_increment
    , business_id varchar(30)
    , password varchar(30)
    , store_name varchar(30)
);

-- admin table 생성
create table admin(
    admin_id int primary key auto_increment
    , admin varchar(20)
    , secure_id varchar(20)
    , password varchar(30)
    , name varchar(20)
);

-- product table 생성
create table product
(
    product_id   int primary key auto_increment,
    product_name varchar(20) not null,
    price        int         not null,
    brand        varchar(20) not null,
    store_id     int,
    constraint fk_product_store_id
        foreign key (store_id)
            references store (store_id)
);

-- product table 인서트
insert into product(product_name, price, brand, store_id)
values ('가지','1000', '여주밭', 1)
, ('핸드백', '1000000', '마루마루', 4)
, ('순대모듬', '20000', '용인순대', 5);
