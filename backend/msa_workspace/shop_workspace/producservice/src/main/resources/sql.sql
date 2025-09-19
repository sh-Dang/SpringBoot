mysql> create table product_file(
product_file_id int primary key auto_increment
, filename varchar(25)
, original_name varchar(25)
, content_type varchar(20)
, filepath varchar(100)
, filesize int default 0
, product_id int
, constraint fk_product_product_file foreign key(product_id)
references product(product_id)
);

create table product(
 product_id int primary key auto_increment
, product_name varchar(25)
, brand varchar(25)
, price int default 0
, discount int default 0
, detail text
, subcategory_id int
, constraint fk_subcategory_product foreign key(subcategory_id)
references subcategory(subcategory_id)
);

mysql> create table topcategory(
topcategory_id int primary key auto_increment
, topname varchar(20)
);

mysql> create table subcategory(
subcategory_id int primary key auto_increment
, subname varchar(20)
, topcategory_id int
, constraint fk_subcategory_topcategory foreign key (topcategory_id)
                               references topcategory(topcategory_id)
);

-- TopCategory 더미 데이터
INSERT INTO topcategory (topname) VALUES ('상의');
INSERT INTO topcategory (topname) VALUES ('하의');
INSERT INTO topcategory (topname) VALUES ('아우터');
INSERT INTO topcategory (topname) VALUES ('신발');

-- SubCategory 더미 데이터
-- 상의
INSERT INTO subcategory (subname, topcategory_id) VALUES ('티셔츠', 1);
INSERT INTO subcategory (subname, topcategory_id) VALUES ('셔츠', 1);
INSERT INTO subcategory (subname, topcategory_id) VALUES ('블라우스', 1);
-- 하의
INSERT INTO subcategory (subname, topcategory_id) VALUES ('청바지', 2);
INSERT INTO subcategory (subname, topcategory_id) VALUES ('슬랙스', 2);
INSERT INTO subcategory (subname, topcategory_id) VALUES ('스커트', 2);
-- 아우터
INSERT INTO subcategory (subname, topcategory_id) VALUES ('자켓', 3);
INSERT INTO subcategory (subname, topcategory_id) VALUES ('코트', 3);
INSERT INTO subcategory (subname, topcategory_id) VALUES ('패딩', 3);
-- 신발
INSERT INTO subcategory (subname, topcategory_id) VALUES ('운동화', 4);
INSERT INTO subcategory (subname, topcategory_id) VALUES ('구두', 4);
INSERT INTO subcategory (subname, topcategory_id) VALUES ('부츠', 4);

-- Product 더미 데이터
-- 티셔츠
INSERT INTO product (product_name, brand, price, discount, detail, subcategory_id) VALUES ('베이직 라운드 티셔츠', 'BrandA', 25000, 10, '부드러운 면 소재의 기본 티셔츠입니다.', 1);
INSERT INTO product (product_name, brand, price, discount, detail, subcategory_id) VALUES ('스트라이프 티셔츠', 'BrandB', 32000, 15, '경쾌한 느낌의 스트라이프 패턴 티셔츠입니다.', 1);
-- 셔츠
INSERT INTO product (product_name, brand, price, discount, detail, subcategory_id) VALUES ('옥스포드 셔츠', 'BrandC', 55000, 0, '클래식한 디자인의 옥스포드 셔츠입니다.', 2);
-- 청바지
INSERT INTO product (product_name, brand, price, discount, detail, subcategory_id) VALUES ('슬림핏 데님 팬츠', 'BrandD', 78000, 20, '다리 라인을 잡아주는 슬림핏 청바지입니다.', 4);
INSERT INTO product (product_name, brand, price, discount, detail, subcategory_id) VALUES ('와이드 데님 팬츠', 'BrandE', 85000, 10, '트렌디한 와이드 핏의 청바지입니다.', 4);
-- 자켓
INSERT INTO product (product_name, brand, price, discount, detail, subcategory_id) VALUES ('오버핏 블레이저', 'BrandF', 120000, 5, '어떤 코디에도 잘 어울리는 오버핏 블레이저입니다.', 7);
-- 운동화
INSERT INTO product (product_name, brand, price, discount, detail, subcategory_id) VALUES ('데일리 스니커즈', 'BrandG', 99000, 10, '매일 신기 좋은 편안한 스니커즈입니다.', 10);
