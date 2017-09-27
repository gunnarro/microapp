------------------------------------------------------------------------------------------
-- DB name		 : SportsTeam DB
-- DB Component	 : Schema
-- Release date	 : 07.09.2014 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

DROP DATABASE sportsteam_unit_test;
CREATE DATABASE IF NOT EXISTS sportsteam_unit_test;
USE sportsteam_unit_test;

CREATE USER 'unittest'@'localhost' IDENTIFIED BY 'test1234';
GRANT ALL PRIVILEGES ON sportsteam_unit_test.* TO 'unittest'@'127.9.127.2';

CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin4321';
GRANT ALL PRIVILEGES ON sportsteam.* TO 'admin'@'127.9.127.2';

CREATE USER 'web'@'localhost' IDENTIFIED BY 'wEbt0t3';
GRANT ALL PRIVILEGES ON sportsteam.* TO 'web'@'127.9.127.2';

--GRANT CREATE, DROP, DELETE, INSERT, SELECT, UPDATE, ON sportsteam_unit_test.* TO 'unittest'@'localhost’;
FLUSH PRIVILEGES;
exit;

-----------------------------------------------------
--WHENEVER SQLERROR EXIT SQL.SQLCODE

--DROP USER quest CASCADE;
--DROP USER admin CASCADE;

--WHENEVER SQLERROR EXIT FAILURE

--CREATE USER quest IDENTIFIED BY quest default tablespace quest_data;
--GRANT CONNECT, RESOURCE, CREATE DATABASE LINK, CREATE SYNONYM, CREATE VIEW TO quest;

--CREATE USER admin IDENTIFIED BY admin default tablespace admin_data;
--GRANT CONNECT, RESOURCE, CREATE DATABASE LINK, CREATE SYNONYM, CREATE VIEW TO admin;

--exit