------------------------------------------------------------------------------------------
-- DB name		 : DietManager DB
-- DB Component	 : Tables
-- Release date	 : 09.06.2016 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 31.07.2016	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

-- Turn off fk check
SET FOREIGN_KEY_CHECKS=0;

-- Table: users
DROP TABLE IF EXISTS users;
CREATE TABLE users(id 						INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   username    	        	VARCHAR(25),
                   password		           	VARCHAR(100),-- long length because of encryption
                   email    	        	VARCHAR(200),
                   enabled		           	INTEGER DEFAULT 1,
                   UNIQUE(username));

-- Table: users
DROP TABLE IF EXISTS roles;
CREATE TABLE roles(id 						INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   username    	        	VARCHAR(25),
                   role			           	VARCHAR(24));
 
						
-- Table: event_log
DROP TABLE IF EXISTS event_log;
CREATE TABLE event_log(id            	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id				 INTEGER,
						level					 VARCHAR(25),
						title					 VARCHAR(50),
						content   				 VARCHAR(4096));
-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;
