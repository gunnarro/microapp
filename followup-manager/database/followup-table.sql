-- ----------------------------------------------------------------------------------------
-- DB name		 : followup DB
-- DB Component	 : Table
-- Release date	 : 16.06.2019 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
-- ----------------------------------------------------------------------------------------
-- 16.06.2019	gunnarro 		Initial version 1.0-SNAPSHOT

-- ----------------------------------------------------------------------------------------

-- SET collation_server=utf8_unicode_ci;
-- SET character_set_server=utf8;

-- Turn off fk check
SET FOREIGN_KEY_CHECKS=0;

-- ref: https://github.com/spring-projects/spring-social/blob/master/spring-social-core/src/main/resources/org/springframework/social/connect/jdbc/JdbcUsersConnectionRepository.sql
-- Table: usersConnection
DROP TABLE IF EXISTS UserConnection;
CREATE TABLE UserConnection (
	userId 			VARCHAR(255) NOT NULL,
    providerId 		VARCHAR(255) NOT NULL,
    providerUserId 	VARCHAR(255),
    rank 			INTEGER NOT NULL,
    displayName 	VARCHAR(255),
    profileUrl 		VARCHAR(512),
    imageUrl 		VARCHAR(512),
    accessToken 	VARCHAR(512) NOT NULL,
    secret 			VARCHAR(512),
    refreshToken 	VARCHAR(512),
    expireTime 		BIGINT,
    PRIMARY KEY (userId, providerId, providerUserId))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
	
CREATE UNIQUE INDEX UserConnectionRank on UserConnection(userId, providerId, rank);
	
-- Table: users
DROP TABLE IF EXISTS users;
CREATE TABLE users(id 						INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   username    	        	VARCHAR(100) NOT NULL,
                   password		           	VARCHAR(100) NOT NULL,-- long length because of encryption
                   email    	        	VARCHAR(200),
                   enabled		           	INTEGER DEFAULT 1,
                   UNIQUE(username))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
                   
-- Table: social_users
DROP TABLE IF EXISTS social_users;
CREATE TABLE social_users(id 				INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   username    	        	VARCHAR(25),
                   password		           	VARCHAR(100),-- long length because of encryption
                   email    	        	VARCHAR(200),
                   provider    	        	VARCHAR(200),
                   FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_user_id    	        INTEGER,
                   enabled		           	INTEGER DEFAULT 1,
                   UNIQUE(username))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';                   

-- Table: roles
DROP TABLE IF EXISTS roles;
CREATE TABLE roles(id 						INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   name			           	VARCHAR(24))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
                   
-- Table: permissions
DROP TABLE IF EXISTS permissions;
CREATE TABLE permissions(id 				INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   name    	        		VARCHAR(25),
                   description			    VARCHAR(100))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
 
-- Table: user_role_lnk
DROP TABLE IF EXISTS user_role_lnk;
CREATE TABLE user_role_lnk(id 				INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_user_id    	        INTEGER,
                   FOREIGN KEY (fk_role_id) REFERENCES roles(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_role_id			INTEGER)
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';                   
                   
-- Table: role_permission_lnk
DROP TABLE IF EXISTS role_permission_lnk;
CREATE TABLE role_permission_lnk(id 				INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (fk_role_id) REFERENCES roles(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_role_id    	        INTEGER,
                   FOREIGN KEY (fk_permission_id) REFERENCES permissions(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_permission_id			INTEGER)
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
                   
-- Table: profiles
DROP TABLE IF EXISTS profiles;
CREATE TABLE profiles(id 					INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_user_id    	        INTEGER,
                   firstname    	        VARCHAR(100),
                   middlename    	        VARCHAR(100),
                   lastname		           	VARCHAR(100),
                   email    	        	VARCHAR(200),
                   date_of_birth    	    date,
                   gender    	        	VARCHAR(1),
                   enabled		           	INTEGER DEFAULT 1,
                   UNIQUE(firstname,middlename,lastname,date_of_birth))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';

-- Table: user_details_log
DROP TABLE IF EXISTS user_details_log;
CREATE TABLE user_details_log(id	 				INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_logged_in_date_time TIMESTAMP,
                   last_logged_in_from_ip_address VARCHAR(50),
                   last_logged_in_from_device 	VARCHAR(200),
                   login_attempt_failures 		INTEGER DEFAULT 0,
                   login_attempt_success 		INTEGER DEFAULT 0,
                   FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
				   fk_user_id				INTEGER,
                   UNIQUE(fk_user_id))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';   
                   
-- Table: user_follower_lnk
DROP TABLE IF EXISTS user_follower_lnk;
CREATE TABLE user_follower_lnk(id 			INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_user_id    	        INTEGER,
                   FOREIGN KEY (fk_user_follower_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_user_follower_id			INTEGER)
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';     
                   

-- Table: event_log
DROP TABLE IF EXISTS event_log;
CREATE TABLE event_log(id            			 INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id				 INTEGER,
						level					 VARCHAR(25),
						title					 VARCHAR(50),
						content   				 VARCHAR(4096))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
						
-- Table: event_log_comment
DROP TABLE IF EXISTS event_log_comment;
CREATE TABLE event_log_comment(id            	 INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id				 INTEGER,
						FOREIGN KEY (fk_event_log_id) REFERENCES event_log(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_event_log_id				 INTEGER,
						title					 VARCHAR(50),
						content   				 VARCHAR(4096))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';

-- Table: event_log
DROP TABLE IF EXISTS event_log_resource;
CREATE TABLE event_log_resource(id            	 INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_event_log_id) REFERENCES event_log(id) ON DELETE SET NULL ON UPDATE CASCADE,
						resource_type			 VARCHAR(25),
						file_path				 VARCHAR(100),
						description   		     VARCHAR(500))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
						
-- Table: childern;
DROP TABLE IF EXISTS children;
CREATE TABLE children(id 					INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   first_name    	        VARCHAR(50),
                   middle_name				VARCHAR(50),
                   last_name		        VARCHAR(50),
                   mobile					VARCHAR(12),
                   email    	        	VARCHAR(100),
                   UNIQUE(first_name,middle_name,last_name))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';	
                   
-- Table: parents
DROP TABLE IF EXISTS parents;
CREATE TABLE parents(id 					INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   first_name    	        VARCHAR(50),
                   middle_name				VARCHAR(50),
                   last_name		        VARCHAR(50),
                   mobile					VARCHAR(12),
                   email    	        	VARCHAR(100),
                   UNIQUE(first_name,middle_name,last_name))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';	
                   
-- Table: togetherness_log
DROP TABLE IF EXISTS togetherness_log;
CREATE TABLE togetherness_log(id            		 INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_parent_id) 	 REFERENCES parents(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_parent_id				 INTEGER,
						FOREIGN KEY (fk_childern_id) REFERENCES childern(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_childern_id				 INTEGER,
						from_date					 DATE,
						from_time					 TIME(4),
						to_date					 	 DATE,
						to_time						 TIME(4),
						description   				 VARCHAR(200))		
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
												
						
-- Table: activities
DROP TABLE IF EXISTS activities;
CREATE TABLE activities(id            		 INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						name			 		 VARCHAR(25),
						category		 		 VARCHAR(25),
						description   			 VARCHAR(4096))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
						

-- Table: activity_log
DROP TABLE IF EXISTS activity_log;
CREATE TABLE activity_log(id            		 INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id				 INTEGER,
						FOREIGN KEY (fk_activity_id) REFERENCES activities(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_activity_id			 INTEGER,
						from_hour				 TIME(4),
						to_hour					 TIME(4),
						rating_intensivity		 INTEGER,
						rating_emotions			 INTEGER,
						description   			 VARCHAR(4096))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
						
-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;
