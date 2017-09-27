------------------------------------------------------------------------------------------
-- DB name		 : TaskManager DB
-- DB Component	 : Schema
-- Release date	 : 09.06.2016 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 31.07.2016	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

DROP DATABASE taskmanager_unit_test;
CREATE DATABASE IF NOT EXISTS taskmanager;
USE taskmanager;

CREATE USER 'unittest'@'localhost' IDENTIFIED BY 'test1234';
GRANT ALL PRIVILEGES ON taskmanager_unit_test.* TO 'unittest'@'127.9.127.2';

CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin4321';
GRANT ALL PRIVILEGES ON taskmanager.* TO 'admin'@'127.9.127.2';

CREATE USER 'web'@'localhost' IDENTIFIED BY 'wEbt0t3';
GRANT ALL PRIVILEGES ON taskmanager.* TO 'web'@'127.9.127.2';

--GRANT CREATE, DROP, DELETE, INSERT, SELECT, UPDATE, ON sportsteam_unit_test.* TO 'unittest'@'localhost�;
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