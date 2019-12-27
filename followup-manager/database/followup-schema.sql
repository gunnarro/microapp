-- ----------------------------------------------------------------------------------------
-- DB name		 : followup DB
-- DB Component	 : Schema
-- Release date	 : 16.06.2019 
-- Schema Version: 1.0-SNAPSHOT
--
-- create schema:
--	mysql -u admin -p -v  < schema.sql
--
-- create tables:
-- mysql -u admin -p -v followup < tables.sql
--
-- export schema
-- mysqldump -u admin -p -v followup > schema-backup.sql
--
-- Modification History
-- Date			Modified by		Task 
-- ----------------------------------------------------------------------------------------
-- 16.06.2019	gunnarro 		Initial version 1.0-SNAPSHOT
-- ----------------------------------------------------------------------------------------

-- DROP DATABASE followup;
DROP DATABASE IF EXISTS followup;
CREATE DATABASE IF NOT EXISTS followup;
USE followup;

SET SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO';
SET time_zone = "+00:00";
SET GLOBAL time_zone = "+00:00";

-- CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY 'abCD_2o1o';
GRANT ALL PRIVILEGES ON followup.* TO 'admin'@'127.9.127.2';

-- CREATE USER IF NOT EXISTS 'web'@'localhost' IDENTIFIED BY 'abCD-2o1o';
GRANT ALL PRIVILEGES ON followup.* TO 'web'@'127.9.127.2';

FLUSH PRIVILEGES;
