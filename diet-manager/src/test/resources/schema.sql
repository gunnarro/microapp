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
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

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
                   
-- Table: diet_plans
DROP TABLE IF EXISTS diet_plans;
CREATE TABLE diet_plans(id             			INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						start_date_time      	TIMESTAMP,
						end_date_time 			TIMESTAMP,
						diet_plan_name			VARCHAR(100),
						diet_plan_description	VARCHAR(200),
						diet_plan_changes		VARCHAR(400),
						diet_plan_active		INTEGER)
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
						
-- Table: diet_rules
DROP TABLE IF EXISTS diet_rules;
CREATE TABLE diet_rules(id             			INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_diet_plan_id) REFERENCES diet_plans(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_plan_id			INTEGER,
						rule_name				VARCHAR(100),
						rule_description		VARCHAR(200),
						rule_active				INTEGER)
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
						
-- Table: diet_plan_meals
DROP TABLE IF EXISTS diet_plan_meals;
CREATE TABLE diet_plan_meals(id             	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						start_date_time      	TIMESTAMP,
						end_date_time 			TIMESTAMP,
						meal_name				VARCHAR(100),
						meal_period				VARCHAR(200),
						meal_description	    VARCHAR(200),
						meal_default			INTEGER DEFAULT 0,
						meal_order				INTEGER DEFAULT 0)
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';

-- Table: diet_plan_meal_items
DROP TABLE IF EXISTS diet_plan_meal_items;
CREATE TABLE diet_plan_meal_items(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_diet_plan_id) REFERENCES diet_plans(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_plan_id			INTEGER,
						FOREIGN KEY (fk_diet_plan_meal_id) REFERENCES diet_plan_meals(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_plan_meal_id	INTEGER,
						meal_item_name			VARCHAR(100),
						meal_item_type			VARCHAR(100),
						meal_item_description	VARCHAR(500),
						meal_item_energy_kcal	INTEGER,
						meal_item_img_link		VARCHAR(100),
						meal_item_enabled		INTEGER DEFAULT 1)
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
											
-- Table: products
DROP TABLE IF EXISTS products;
CREATE TABLE products(id            			INTEGER PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						name			VARCHAR(100),
						type			VARCHAR(100),
						category		VARCHAR(100),
						description		VARCHAR(500),
						amount			VARCHAR(10),
						weight_g		INTEGER,
						kcal			INTEGER,
						fat				DECIMAL(6,2),
						carbohydrates	DECIMAL(6,2),
						fibre			DECIMAL(6,2),
						protein			DECIMAL(6,2),
						img_link		VARCHAR(100))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';

-- Table: diet_menu
DROP TABLE IF EXISTS diet_menus;
CREATE TABLE diet_menus(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						menu_name			VARCHAR(100),
						menu_description	VARCHAR(500),
						menu_active			INTEGER)
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';

-- Table: diet_menu_items
DROP TABLE IF EXISTS diet_menu_items;
CREATE TABLE diet_menu_items(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_diet_menu_id) REFERENCES diet_menus(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_menu_id			INTEGER,
						menu_item_name			VARCHAR(100),
						menu_item_category		VARCHAR(25),
						menu_item_description	VARCHAR(500),
						menu_item_energy_kcal	INTEGER,
						menu_item_img_link		VARCHAR(100),
						menu_item_enabled		INTEGER DEFAULT 1)
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
						
-- Table: user_diet_menu_item_lnk
DROP TABLE IF EXISTS user_diet_menu_item_lnk;
CREATE TABLE user_diet_menu_item_lnk( id                      	INTEGER  PRIMARY KEY AUTO_INCREMENT,
									created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
									last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
									fk_user_id           	 	INTEGER NOT NULL,
									FOREIGN KEY (fk_user_id)  	REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
									fk_diet_menu_item_id        INTEGER NOT NULL,
									FOREIGN KEY (fk_diet_menu_item_id) REFERENCES diet_menu_items(id) ON DELETE CASCADE ON UPDATE CASCADE,
									fk_controlled_by_user_id    INTEGER,
									FOREIGN KEY (fk_controlled_by_user_id)  REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
									fk_prepared_by_user_id    	INTEGER,
									FOREIGN KEY (fk_controlled_by_user_id)  REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
									caused_conflict           	INTEGER DEFAULT 0,
									fk_log_id    				INTEGER,
									FOREIGN KEY (fk_log_id)  REFERENCES event_log(id) ON DELETE CASCADE ON UPDATE CASCADE)
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
									
						
-- Table: diet_products
DROP TABLE IF EXISTS diet_products;
CREATE TABLE diet_products(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						product_name			VARCHAR(100),
						product_type			VARCHAR(100),
						product_description		VARCHAR(500))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';

-- Table: diet_product_equivalent_items
DROP TABLE IF EXISTS diet_product_equivalent_items;
CREATE TABLE diet_product_equivalent_items(id   INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_diet_product_id) REFERENCES diet_products(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_product_id				INTEGER,
						product_equivalent_name			VARCHAR(100),
						product_equivalent_description	VARCHAR(500))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';

-- Table: food_recipes
DROP TABLE IF EXISTS food_recipes;
CREATE TABLE food_recipes(id            		INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						recipe_name			VARCHAR(100),
						recipe_description	VARCHAR(500))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';

-- Table: food_recipe_items
DROP TABLE IF EXISTS food_recipe_items;
CREATE TABLE food_recipe_items(id            	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_food_recipe_id) REFERENCES food_recipes(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_food_recipe_id			INTEGER,
						recipe_item_name			VARCHAR(100),
						recipe_item_description	VARCHAR(500))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';

-- Table: body_measurements_log
DROP TABLE IF EXISTS body_measurements_log;
CREATE TABLE body_measurements_log(id            	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						log_date		TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id		INTEGER,
						weight			DECIMAL(6,2),
						height			DECIMAL(6,2),
						comment			VARCHAR(200),
						weight_metric   VARCHAR(5),
						height_metric   VARCHAR(5))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';		
						
-- TABLE: health_reference_data
DROP TABLE IF EXISTS health_reference_data;
CREATE TABLE health_reference_data(id            	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						name						VARCHAR(100),
						type						VARCHAR(25),
						gender						VARCHAR(10),
						description   				VARCHAR(200))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';	
						
-- TABLE: bmi_percentile
DROP TABLE IF EXISTS growth_reference_data;
CREATE TABLE growth_reference_data(id            	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						fk_health_reference_data_id	INTEGER,
						FOREIGN KEY (fk_health_reference_data_id) REFERENCES health_reference_data(id) ON DELETE SET NULL ON UPDATE CASCADE,
						month							INTEGER,
						bmi_p25							DECIMAL(6,3),
						bmi_p50							DECIMAL(6,3),
						bmi_p75							DECIMAL(6,3),
						weight_p25						DECIMAL(6,3),
						weight_p50						DECIMAL(6,3),
						weight_p75						DECIMAL(6,3),
						height_p25						DECIMAL(6,3),
						height_p50						DECIMAL(6,3),
						height_p75						DECIMAL(6,3))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';		
						
						
-- Table: childern;
DROP TABLE IF EXISTS childern;
CREATE TABLE childern(id 					INTEGER  PRIMARY KEY AUTO_INCREMENT,
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
                   
-- Table: samvar_log
DROP TABLE IF EXISTS samvar_log;
CREATE TABLE samvar_log(id            			 	 INTEGER  PRIMARY KEY AUTO_INCREMENT,
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
						description   				 VARCHAR(200));		
						
						
-- Table: activity_log
DROP TABLE IF EXISTS activity_log;
CREATE TABLE activity_log(id            		 INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id				 INTEGER,
						name					 VARCHAR(25),
						from_hour				 INTEGER,
						to_hour					 INTEGER,
						rating_intensivity		 INTEGER,
						rating_emotions			 INTEGER,
						description   			 VARCHAR(4096))
CHARACTER SET 'utf8' 
COLLATE 'utf8_unicode_ci';
						
-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;
