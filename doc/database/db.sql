USE test;

DROP TABLE IF EXISTS wo_user;

CREATE TABLE `wo_user`( 
   `id` INT(10) NOT NULL  AUTO_INCREMENT, 
   `userName` VARCHAR(50) NOT NULL , 
   `passWord` VARCHAR(50) NOT NUll, 
   PRIMARY KEY (`id`)
 );