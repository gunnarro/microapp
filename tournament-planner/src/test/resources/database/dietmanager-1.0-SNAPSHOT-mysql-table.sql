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
 
-- Table: diet_plans
DROP TABLE IF EXISTS diet_plans;
CREATE TABLE diet_plans(id             			INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						start_date_time      	TIMESTAMP,
						end_date_time 			TIMESTAMP,
						diet_plan_name			VARCHAR(100),
						diet_plan_description	VARCHAR(200),
						diet_plan_active		INTEGER);

-- Table: diet_plan_meals
DROP TABLE IF EXISTS diet_plan_meals;
CREATE TABLE diet_plan_meals(id             	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						FOREIGN KEY (fk_diet_plan_id) REFERENCES diet_plans(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_plan_id			INTEGER,
						start_date_time      	TIMESTAMP,
						end_date_time 			TIMESTAMP,
						meal_name				VARCHAR(100),
						meal_period				VARCHAR(200),
						meal_description	    VARCHAR(200));

-- Table: diet_plan_meal_items
DROP TABLE IF EXISTS diet_plan_meal_items;
CREATE TABLE diet_plan_meal_items(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						FOREIGN KEY (fk_diet_plan_meal_id) REFERENCES diet_plan_meals(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_plan_meal_id	INTEGER,
						meal_item_name			VARCHAR(100),
						meal_item_description	VARCHAR(500));
						
-- Table: diet_menu
DROP TABLE IF EXISTS diet_menus;
CREATE TABLE diet_menus(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						menu_name			VARCHAR(100),
						menu_description	VARCHAR(500),
						menu_active			INTEGER);

-- Table: diet_menu_items
DROP TABLE IF EXISTS diet_menu_items;
CREATE TABLE diet_menu_items(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						FOREIGN KEY (fk_diet_menu_id) REFERENCES diet_menus(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_menu_id			INTEGER,
						menu_item_name			VARCHAR(100),
						menu_item_description	VARCHAR(500));

						
-- Table: user_diet_menu_item_lnk
DROP TABLE IF EXISTS user_diet_menu_item_lnk;
CREATE TABLE user_diet_menu_item_lnk( id                      	INTEGER  PRIMARY KEY AUTO_INCREMENT,
									created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
									last_modified_date_time 	TIMESTAMP,
									fk_user_id           	 	INTEGER NOT NULL,
									FOREIGN KEY (fk_user_id)  	REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
									fk_diet_menu_item_id        INTEGER NOT NULL,
									FOREIGN KEY (fk_diet_menu_item_id) REFERENCES diet_menu_items(id) ON DELETE CASCADE ON UPDATE CASCADE);						
						
-- Table: diet_products
DROP TABLE IF EXISTS diet_products;
CREATE TABLE diet_products(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						product_name			VARCHAR(100),
						product_type			VARCHAR(100),
						product_description		VARCHAR(500));

-- Table: diet_product_equivalent_items
DROP TABLE IF EXISTS diet_product_equivalent_items;
CREATE TABLE diet_product_equivalent_items(id   INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						FOREIGN KEY (fk_diet_product_id) REFERENCES diet_products(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_product_id				INTEGER,
						product_equivalent_name			VARCHAR(100),
						product_equivalent_description	VARCHAR(500));

-- Table: food_recipes
DROP TABLE IF EXISTS food_recipes;
CREATE TABLE food_recipes(id            		INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						recipe_name			VARCHAR(100),
						recipe_description	VARCHAR(500));

-- Table: food_recipe_items
DROP TABLE IF EXISTS food_recipe_items;
CREATE TABLE food_recipe_items(id            	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						FOREIGN KEY (fk_food_recipe_id) REFERENCES food_recipes(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_food_recipe_id			INTEGER,
						recipe_item_name			VARCHAR(100),
						recipe_item_description	VARCHAR(500));
						
-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;
