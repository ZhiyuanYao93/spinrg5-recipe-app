-- connect to mySql and run as root USER

-- create database
CREATE DATABASE sb_dev;
CREATE DATABASE sb_prod;

-- create database access acounts
CREATE USER 'sb_dev_user'@'localhost' IDENTIFIED BY 'dev';
CREATE USER 'sb_prod_user'@'localhost' IDENTIFIED BY 'prod';

-- grant privelieges to users
GRANT SELECT ON sb_dev.* to 'sb_dev_user'@'localhost';
GRANT INSERT ON sb_dev.* to 'sb_dev_user'@'localhost';
GRANT DELETE ON sb_dev.* to 'sb_dev_user'@'localhost';
GRANT UPDATE ON sb_dev.* to 'sb_dev_user'@'localhost';

GRANT SELECT ON sb_prod.* to 'sb_prod_user'@'localhost';
GRANT INSERT ON sb_prod.* to 'sb_prod_user'@'localhost';
GRANT DELETE ON sb_prod.* to 'sb_prod_user'@'localhost';
GRANT UPDATE ON sb_prod.* to 'sb_prod_user'@'localhost';

-- For docker use
-- GRANT SELECT ON sb_dev.* to 'sb_dev_user'@'%';
-- GRANT INSERT ON sb_dev.* to 'sb_dev_user'@'%';
-- GRANT DELETE ON sb_dev.* to 'sb_dev_user'@'%';
-- GRANT UPDATE ON sb_dev.* to 'sb_dev_user'@'%';
--
-- GRANT SELECT ON sb_prod.* to 'sb_prod_user'@'%';
-- GRANT INSERT ON sb_prod.* to 'sb_prod_user'@'%';
-- GRANT DELETE ON sb_prod.* to 'sb_prod_user'@'%';
-- GRANT UPDATE ON sb_prod.* to 'sb_prod_user'@'%';
