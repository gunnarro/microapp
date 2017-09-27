------------------------------------------------------------------------------------------
-- DB name		 : UserAccount DB
-- DB Component	 : Tables
-- Release date	 : 01.04.2017 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 01.04.2017	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

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
    PRIMARY KEY (userId, providerId, providerUserId));
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
                   UNIQUE(username));
                   
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
                   UNIQUE(username));                   

-- Table: roles
DROP TABLE IF EXISTS roles;
CREATE TABLE roles(id 						INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   name			           	VARCHAR(24));
                   
-- Table: permissions
DROP TABLE IF EXISTS permissions;
CREATE TABLE permissions(id 				INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   name    	        		VARCHAR(25),
                   description			    VARCHAR(100));
 
-- Table: user_role_lnk
DROP TABLE IF EXISTS user_role_lnk;
CREATE TABLE user_role_lnk(id 				INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_user_id    	        INTEGER,
                   FOREIGN KEY (fk_role_id) REFERENCES roles(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_role_id			INTEGER);                   
                   
-- Table: role_permission_lnk
DROP TABLE IF EXISTS role_permission_lnk;
CREATE TABLE role_permission_lnk(id 				INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (fk_role_id) REFERENCES roles(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_role_id    	        INTEGER,
                   FOREIGN KEY (fk_permission_id) REFERENCES permissions(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_permission_id			INTEGER);
                   
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
                   UNIQUE(firstname,middlename,lastname,date_of_birth));

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
                   UNIQUE(fk_user_id));   
                   

-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;
