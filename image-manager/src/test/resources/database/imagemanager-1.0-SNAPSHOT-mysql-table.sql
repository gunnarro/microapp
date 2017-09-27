------------------------------------------------------------------------------------------
-- DB name		 : SportsTeam DB
-- DB Component	 : Tables
-- Release date	 : 07.09.2014 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

-- Turn off fk check
SET FOREIGN_KEY_CHECKS=0;

-- Table: users
DROP TABLE IF EXISTS users;
CREATE TABLE users(id 						INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP,
                   username    	        	VARCHAR(25),
                   password		           	VARCHAR(100),-- long length because of encryption
                   email    	        	VARCHAR(200),
                   enabled		           	INTEGER DEFAULT 1,
                   UNIQUE(username));

-- Table: users
DROP TABLE IF EXISTS roles;
CREATE TABLE roles(id 						INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP,
                   username    	        	VARCHAR(25),
                   role			           	VARCHAR(24));
 

-- Table: image_details
DROP TABLE IF EXISTS image_details;
CREATE TABLE image_details(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						image_date_time			TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id				INTEGER,
						image_name				VARCHAR(50),
						image_title				VARCHAR(100),
						image_path				VARCHAR(100),
						image_mapped_absolute_path	VARCHAR(100),
						image_size_byte			INTEGER,
						image_geo_location		VARCHAR(100),
						image_description	VARCHAR(500),
						image_type	VARCHAR(25),
						UNIQUE(image_name,image_path));

-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;
