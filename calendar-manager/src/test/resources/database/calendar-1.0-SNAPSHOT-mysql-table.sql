------------------------------------------------------------------------------------------
-- DB name		 : Calendar DB
-- DB Component	 : Tables
-- Release date	 : 09.06.2016 
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
 
-- Table: addresses
DROP TABLE IF EXISTS addresses;
CREATE TABLE addresses(id 					   INTEGER  PRIMARY KEY AUTO_INCREMENT,
                       created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       last_modified_date_time TIMESTAMP,
                       street_name             VARCHAR(100),
                       street_number           VARCHAR(6),
                       street_number_postfix   VARCHAR(5),
                       zip_code                VARCHAR(8),
                       city                    VARCHAR(50),
                       post_code               VARCHAR(8),
                       post_box                VARCHAR(50),
                       country                 VARCHAR(50));

 -- Table: persons
DROP TABLE IF EXISTS persons;
CREATE TABLE persons(id 					 INTEGER  PRIMARY KEY AUTO_INCREMENT,
                     created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     last_modified_date_time TIMESTAMP,
                     fk_user_id           	 INTEGER,
                     FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                     fk_address_id           INTEGER,
                     FOREIGN KEY (fk_address_id) REFERENCES addresses(id) ON DELETE SET NULL ON UPDATE CASCADE,
                     first_name              VARCHAR(50),
                     middle_name             VARCHAR(50),
                     last_name               VARCHAR(50),
                     gender	               	 VARCHAR(10),
                     date_of_birth           TIMESTAMP,
                     mobile                  VARCHAR(25)  NOT NULL,
					 email                   VARCHAR(100) NOT NULL);

-- Table: calendars
DROP TABLE IF EXISTS calendars;
CREATE TABLE calendars(id             		INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id				INTEGER,
						UNIQUE(id));
						
-- Table: calendar_urls
DROP TABLE IF EXISTS calendar_urls;
CREATE TABLE calendar_urls(id             		INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						FOREIGN KEY (fk_calendar_id) REFERENCES calendars(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_calendar_id				INTEGER,
						name					VARCHAR(50),
						team_name				VARCHAR(50),
						url					   	VARCHAR(250),
						format				   	VARCHAR(25),
						description			   	VARCHAR(500),
						enabled			   		INTEGER,
						UNIQUE(name));
						
-- Table: calendar_event
DROP TABLE IF EXISTS calendar_event;
CREATE TABLE calendar_event(id             		INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						FOREIGN KEY (fk_calendar_id) REFERENCES calendars(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_calendar_id				INTEGER,
						start_time				TIMESTAMP,
						end_time			    TIMESTAMP,
						name					VARCHAR(50),
						event_type				VARCHAR(25),
						location				VARCHAR(50),
						summary					VARCHAR(100),
						description			   	VARCHAR(500));
						
-- Table: calendar_event
DROP TABLE IF EXISTS followers;
CREATE TABLE followers(id             			INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id				INTEGER,
						FOREIGN KEY (fk_person_id) REFERENCES persons(id) ON DELETE SET NULL ON UPDATE CASCADE,
                     	fk_person_id            INTEGER,
						summary					VARCHAR(100),
						description			   	VARCHAR(500));

-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;
