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

-- Turn off fk check
SET FOREIGN_KEY_CHECKS=0;

-- Table: users
DROP TABLE IF EXISTS users;
CREATE TABLE users(id 						INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   username    	        	VARCHAR(100),
                   password		           	VARCHAR(100),-- long length because of encryption
                   email    	        	VARCHAR(200),
                   enabled		           	INTEGER DEFAULT 1,
                   UNIQUE(username));

-- Table: roles
DROP TABLE IF EXISTS roles;
CREATE TABLE roles(id 						INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   username    	        	VARCHAR(25),
                   role			           	VARCHAR(24));
                   
-- Table: user_details_log
DROP TABLE IF EXISTS user_details_log;
CREATE TABLE user_details_log(id	 		INTEGER  PRIMARY KEY AUTO_INCREMENT,
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
-- Table: diet_plans
DROP TABLE IF EXISTS diet_plans;
CREATE TABLE diet_plans(id             			INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						start_date_time      	TIMESTAMP,
						end_date_time 			TIMESTAMP,
						diet_plan_name			VARCHAR(100),
						diet_plan_description	VARCHAR(200),
						diet_plan_active		INTEGER);

-- Table: diet_plan_meals
DROP TABLE IF EXISTS diet_plan_meals;
CREATE TABLE diet_plan_meals(id             	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_diet_plan_id) REFERENCES diet_plans(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_plan_id			INTEGER,
						start_date_time      	TIMESTAMP,
						end_date_time 			TIMESTAMP,
						meal_name				VARCHAR(100),
						meal_period				VARCHAR(200),
						meal_description	    VARCHAR(200),
						meal_default			INTEGER DEFAULT 0,
						meal_order				INTEGER DEFAULT 0);

-- Table: diet_plan_meal_items
DROP TABLE IF EXISTS diet_plan_meal_items;
CREATE TABLE diet_plan_meal_items(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_diet_plan_id) REFERENCES diet_plans(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_plan_id			INTEGER,
						FOREIGN KEY (fk_diet_plan_meal_id) REFERENCES diet_plan_meals(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_plan_meal_id	INTEGER,
						meal_item_name			VARCHAR(100),
						meal_item_description	VARCHAR(500));
						
-- Table: diet_menu
DROP TABLE IF EXISTS diet_menus;
CREATE TABLE diet_menus(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						menu_name			VARCHAR(100),
						menu_description	VARCHAR(500),
						menu_active			INTEGER);

-- Table: diet_menu_items
DROP TABLE IF EXISTS diet_menu_items;
CREATE TABLE diet_menu_items(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_diet_menu_id) REFERENCES diet_menus(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_menu_id			INTEGER,
						menu_item_name			VARCHAR(100),
						menu_item_description	VARCHAR(500),
						menu_item_img_link		VARCHAR(100));

						
-- Table: user_diet_menu_item_lnk
DROP TABLE IF EXISTS user_diet_menu_item_lnk;
CREATE TABLE user_diet_menu_item_lnk( id                      	INTEGER  PRIMARY KEY AUTO_INCREMENT,
									created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
									last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
									fk_user_id           	 	INTEGER NOT NULL,
									FOREIGN KEY (fk_user_id)  	REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
									fk_diet_menu_item_id        INTEGER NOT NULL,
									FOREIGN KEY (fk_diet_menu_item_id) REFERENCES diet_menu_items(id) ON DELETE CASCADE ON UPDATE CASCADE,
									fk_controlled_by_user_id    INTEGER,
									FOREIGN KEY (fk_controlled_by_user_id)  REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
									caused_conflict           	INTEGER DEFAULT 0);
						
-- Table: diet_products
DROP TABLE IF EXISTS diet_products;
CREATE TABLE diet_products(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						product_name			VARCHAR(100),
						product_type			VARCHAR(100),
						product_description		VARCHAR(500));

-- Table: diet_product_equivalent_items
DROP TABLE IF EXISTS diet_product_equivalent_items;
CREATE TABLE diet_product_equivalent_items(id   INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_diet_product_id) REFERENCES diet_products(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_product_id				INTEGER,
						product_equivalent_name			VARCHAR(100),
						product_equivalent_description	VARCHAR(500));

-- Table: food_recipes
DROP TABLE IF EXISTS food_recipes;
CREATE TABLE food_recipes(id            		INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						recipe_name			VARCHAR(100),
						recipe_description	VARCHAR(500));

-- Table: food_recipe_items
DROP TABLE IF EXISTS food_recipe_items;
CREATE TABLE food_recipe_items(id            	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_food_recipe_id) REFERENCES food_recipes(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_food_recipe_id			INTEGER,
						recipe_item_name			VARCHAR(100),
						recipe_item_description	VARCHAR(500));

						
-- Table: body_measurements_log
DROP TABLE IF EXISTS body_measurements_log;
CREATE TABLE body_measurements_log(id            	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						log_date		TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id		INTEGER,
						weight			DECIMAL(6,2),
						height			DECIMAL(6,2),
						comment			VARCHAR(200),
						weight_metric   VARCHAR(5),
						height_metric   VARCHAR(5));
						
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
