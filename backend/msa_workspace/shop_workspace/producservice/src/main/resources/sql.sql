mysql> create table product_file(
    -> product_file_id int primary key auto_increment
       -> , filename varchar(25)
           -> , original_name varchar(25)
           -> , content_type varchar(20)
           -> , filepath varchar(100)
           -> , filesize int default 0
                                         -> , product_id int
           -> , constraint fk_product_product_file foreign key(product_id)
        -> references product(product_id)
        -> );

create table product(
    -> product_id int primary key auto_increment
       -> , product_name varchar(25)
           -> , brand varchar(25)
           -> , price int default 0
                                      -> , discount int default 0
                                                                    -> , detail text
           -> , subcategory_id int
           -> , constraint fk_subcategory_product foreign key(subcategory_id)
        -> references subcategory(subcategory_id)
        -> );

mysql> create table topcategory(
    -> topcategory_id int primary key auto_increment
       -> , topname varchar(20)
           -> );

mysql> create table subcategory(
    -> subcategory_id int primary key auto_increment
       -> , subname varchar(20)
           -> );