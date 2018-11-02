
-----------------------------------------------------------------------------------------
-- update 20.05.2016
-----------------------------------------------------------------------------------------
ALTER TABLE user_diet_menu_item_lnk
ADD COLUMN fk_controlled_by_user_id INTEGER AFTER fk_diet_menu_item_id


ALTER TABLE user_diet_menu_item_lnk
ADD FOREIGN KEY fk_controlled_by_user_id(fk_controlled_by_user_id)
REFERENCES users(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE user_diet_menu_item_lnk
ADD COLUMN caused_conflict INTEGER DEFAULT 0 AFTER fk_controlled_by_user_id

	
-----------------------------------------------------------------------------------------
-- update 27.06.2016
-----------------------------------------------------------------------------------------
ALTER TABLE event_log MODIFY COLUMN last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
UPDATE event_log 
SET last_modified_date_time = created_date_time
WHERE fk_user_id = 1
LIMIT 30;

ALTER TABLE user_diet_menu_item_lnk MODIFY COLUMN last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP; '1970-01-01 00:00:00'
UPDATE user_diet_menu_item_lnk 
SET last_modified_date_time = created_date_time
WHERE fk_user_id = 1
LIMIT 100;
	
-----------------------------------------------------------------------------------------
-- update 12.08.2016
-----------------------------------------------------------------------------------------
UPDATE event_log 
SET fk_user_id = 5
WHERE id > 0
LIMIT 100;

UPDATE body_measurements_log 
SET fk_user_id = 4
WHERE id > 0
LIMIT 100;

	
-----------------------------------------------------------------------------------------
-- update 18.08.2016
-----------------------------------------------------------------------------------------
ALTER TABLE diet_menu_items
ADD COLUMN menu_item_img_link VARCHAR(100) AFTER menu_item_description;
	
-----------------------------------------------------------------------------------------
-- update 18.09.2016
-----------------------------------------------------------------------------------------
ALTER TABLE diet_menu_items
ADD COLUMN menu_item_category VARCHAR(25) AFTER menu_item_name,
ADD COLUMN menu_item_energy_kcal INTEGER AFTER menu_item_description;
	
-----------------------------------------------------------------------------------------
-- update 24.09.2016
-----------------------------------------------------------------------------------------
ALTER TABLE diet_plan_meal_items
ADD COLUMN meal_item_category VARCHAR(25) AFTER meal_item_name,
ADD COLUMN meal_item_energy_kcal INTEGER AFTER meal_item_description;
	
-----------------------------------------------------------------------------------------
-- update 06.11.2016
-----------------------------------------------------------------------------------------
ALTER TABLE diet_plan_meals
ADD COLUMN meal_default INTEGER AFTER meal_description;
	
-----------------------------------------------------------------------------------------
-- update 20.11.2016
-----------------------------------------------------------------------------------------
ALTER TABLE user_diet_menu_item_lnk
ADD COLUMN fk_prepared_by_user_id INTEGER DEFAULT 0 AFTER fk_controlled_by_user_id;

UPDATE diet_plan_meals 
SET meal_name = 'Dessert'
WHERE meal_name = 'Middag Dessert'
LIMIT 1000;

UPDATE diet_menu_items 
SET menu_item_name = 'Dessert'
WHERE menu_item_name = 'Middag Dessert'
LIMIT 1000;

ALTER TABLE diet_plan_meals
ADD COLUMN meal_order INTEGER DEFAULT 0 AFTER meal_default;

UPDATE diet_plan_meals 
SET meal_order = 1 
WHERE id = 1 
AND meal_name = 'Frokost';

UPDATE diet_plan_meals 
SET meal_order = 2 
WHERE id = 2
AND meal_name = 'Lunsj';

UPDATE diet_plan_meals 
SET meal_order = 3 
WHERE id = 3
AND meal_name = 'Mellom måltid';

UPDATE diet_plan_meals 
SET meal_order = 4 
WHERE id = 4
AND meal_name = 'Middag';

UPDATE diet_plan_meals 
SET meal_order = 5 
WHERE id = 7 
AND meal_name = 'Dessert';

UPDATE diet_plan_meals 
SET meal_order = 6 
WHERE id = 5 
AND meal_name = 'Kveldsmat';

CREATE TABLE diet_rules(id             			INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_diet_plan_id) REFERENCES diet_plans(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_diet_plan_id			INTEGER,
						rule_name				VARCHAR(100),
						rule_description		VARCHAR(200),
						rule_active				INTEGER);

/*!40000 ALTER TABLE `diet_rules` DISABLE KEYS */;
DELETE FROM diet_rules WHERE id > 0;
INSERT INTO `diet_rules` 
VALUES 
(1,now(),now(),1,'lett produkter','Ikke lett produkter', 1),
(2,now(),now(),1,'spisetid','Spisetid 30 minutter', 1),
(3,now(),now(),1,'generelt','Gjennomført måltid ihht. diet', 1);
(3,now(),now(),1,'generelt','Gjennomført måltid ihht. regler', 1);
/*!40000 ALTER TABLE `diet_rules` ENABLE KEYS */;

	
-----------------------------------------------------------------------------------------
-- update 22.01.2016
-----------------------------------------------------------------------------------------
ALTER TABLE users MODIFY username VARCHAR(100);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles(id 						INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   name			           	VARCHAR(24));


CREATE TABLE permissions(id 				INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   name    	        		VARCHAR(25),
                   description			    VARCHAR(100));

CREATE TABLE user_role_lnk(id 				INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_user_id    	        INTEGER,
                   FOREIGN KEY (fk_role_id) REFERENCES roles(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_role_id			INTEGER);                   
                   
CREATE TABLE role_permission_lnk(id 		INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (fk_role_id) REFERENCES roles(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_role_id    	        INTEGER,
                   FOREIGN KEY (fk_permission_id) REFERENCES permissions(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_permission_id			INTEGER);

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
						height_p75						DECIMAL(6,3));		

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

INSERT INTO `roles`
VALUES 
(1,now(),now(),'ROLE_ADMIN'),
(2,now(),now(),'ROLE_USER'),
(3,now(),now(),'ROLE_GUEST'),
(4,now(),now(),'ROLE_ANONYMOUS');

INSERT INTO `permissions`
VALUES 
(1,now(),now(),'READ_PRIVILEGE','read permission only'),
(2,now(),now(),'WRITE_PRIVILEGE','write permission');                   

INSERT INTO `user_role_lnk`
VALUES 
(1,now(),now(),1,1),
(2,now(),now(),2,2),
(3,now(),now(),3,3),
(4,now(),now(),4,2),
(5,now(),now(),5,2),
(6,now(),now(),6,2);

INSERT INTO `role_permission_lnk`
VALUES 
(1,now(),now(),1,1),
(2,now(),now(),1,2),
(3,now(),now(),2,1),
(4,now(),now(),2,2),
(5,now(),now(),3,1);

INSERT INTO `profiles`
VALUES 
(1,now(),now(),5,'Gunnar', '', 'Ronneberg', 'gunnar_ronneberg@yahoo.no', '1966-4-11', 'M', 1),
(2,now(),now(),6,'Siri', 'Birgitte', 'Paulen', 'spaulen@ymail.com', '1966-05-19', 'F', 1),
(3,now(),now(),4,'Emilie', '','Paulen', '', '2002-01-22', 'F', 1);

INSERT INTO `growth_reference_data` 
VALUES 
		(1,now(),now(),1,61,14.306,15.244,16.306,0.0,0.0,0.0,0.0,0.0,0.0),
		(2,now(),now(),1,62,14.301,15.243,16.311,0.0,0.0,0.0,0.0,0.0,0.0),
		(3,now(),now(),1,63,14.298,15.243,16.317,0.0,0.0,0.0,0.0,0.0,0.0),
		(4,now(),now(),1,64,14.294,15.244,16.324,0.0,0.0,0.0,0.0,0.0,0.0),
		(5,now(),now(),1,65,14.292,15.245,16.331,0.0,0.0,0.0,0.0,0.0,0.0),
		(6,now(),now(),1,66,14.29,15.246,16.339,0.0,0.0,0.0,0.0,0.0,0.0),
		(7,now(),now(),1,67,14.288,15.249,16.347,0.0,0.0,0.0,0.0,0.0,0.0),
		(8,now(),now(),1,68,14.287,15.252,16.357,0.0,0.0,0.0,0.0,0.0,0.0),
		(9,now(),now(),1,69,14.287,15.255,16.367,0.0,0.0,0.0,0.0,0.0,0.0),
		(10,now(),now(),1,70,14.287,15.259,16.377,0.0,0.0,0.0,0.0,0.0,0.0),
		(11,now(),now(),1,71,14.288,15.264,16.388,0.0,0.0,0.0,0.0,0.0,0.0),
		(12,now(),now(),1,72,14.29,15.27,16.401,0.0,0.0,0.0,0.0,0.0,0.0),
		(13,now(),now(),1,73,14.292,15.276,16.414,0.0,0.0,0.0,0.0,0.0,0.0),
		(14,now(),now(),1,74,14.295,15.283,16.427,0.0,0.0,0.0,0.0,0.0,0.0),
		(15,now(),now(),1,75,14.299,15.291,16.442,0.0,0.0,0.0,0.0,0.0,0.0),
		(16,now(),now(),1,76,14.303,15.3,16.458,0.0,0.0,0.0,0.0,0.0,0.0),
		(17,now(),now(),1,77,14.309,15.31,16.475,0.0,0.0,0.0,0.0,0.0,0.0),
		(18,now(),now(),1,78,14.315,15.32,16.492,0.0,0.0,0.0,0.0,0.0,0.0),
		(19,now(),now(),1,79,14.322,15.331,16.511,0.0,0.0,0.0,0.0,0.0,0.0),
		(20,now(),now(),1,80,14.33,15.344,16.53,0.0,0.0,0.0,0.0,0.0,0.0),
		(21,now(),now(),1,81,14.339,15.357,16.551,0.0,0.0,0.0,0.0,0.0,0.0),
		(22,now(),now(),1,82,14.349,15.372,16.573,0.0,0.0,0.0,0.0,0.0,0.0),
		(23,now(),now(),1,83,14.36,15.387,16.596,0.0,0.0,0.0,0.0,0.0,0.0),
		(24,now(),now(),1,84,14.371,15.404,16.62,0.0,0.0,0.0,0.0,0.0,0.0),
		(25,now(),now(),1,85,14.384,15.421,16.645,0.0,0.0,0.0,0.0,0.0,0.0),
		(26,now(),now(),1,86,14.398,15.44,16.671,0.0,0.0,0.0,0.0,0.0,0.0),
		(27,now(),now(),1,87,14.412,15.459,16.699,0.0,0.0,0.0,0.0,0.0,0.0),
		(28,now(),now(),1,88,14.428,15.48,16.727,0.0,0.0,0.0,0.0,0.0,0.0),
		(29,now(),now(),1,89,14.444,15.501,16.757,0.0,0.0,0.0,0.0,0.0,0.0),
		(30,now(),now(),1,90,14.462,15.524,16.788,0.0,0.0,0.0,0.0,0.0,0.0),
		(31,now(),now(),1,91,14.48,15.548,16.819,0.0,0.0,0.0,0.0,0.0,0.0),
		(32,now(),now(),1,92,14.499,15.572,16.852,0.0,0.0,0.0,0.0,0.0,0.0),
		(33,now(),now(),1,93,14.52,15.598,16.887,0.0,0.0,0.0,0.0,0.0,0.0),
		(34,now(),now(),1,94,14.541,15.625,16.922,0.0,0.0,0.0,0.0,0.0,0.0),
		(35,now(),now(),1,95,14.563,15.652,16.958,0.0,0.0,0.0,0.0,0.0,0.0),
		(36,now(),now(),1,96,14.586,15.681,16.995,0.0,0.0,0.0,0.0,0.0,0.0),
		(37,now(),now(),1,97,14.61,15.711,17.034,0.0,0.0,0.0,0.0,0.0,0.0),
		(38,now(),now(),1,98,14.635,15.742,17.073,0.0,0.0,0.0,0.0,0.0,0.0),
		(39,now(),now(),1,99,14.661,15.773,17.114,0.0,0.0,0.0,0.0,0.0,0.0),
		(40,now(),now(),1,100,14.687,15.806,17.156,0.0,0.0,0.0,0.0,0.0,0.0),
		(41,now(),now(),1,101,14.715,15.839,17.198,0.0,0.0,0.0,0.0,0.0,0.0),
		(42,now(),now(),1,102,14.743,15.874,17.242,0.0,0.0,0.0,0.0,0.0,0.0),
		(43,now(),now(),1,103,14.772,15.909,17.286,0.0,0.0,0.0,0.0,0.0,0.0),
		(44,now(),now(),1,104,14.802,15.945,17.331,0.0,0.0,0.0,0.0,0.0,0.0),
		(45,now(),now(),1,105,14.832,15.982,17.377,0.0,0.0,0.0,0.0,0.0,0.0),
		(46,now(),now(),1,106,14.864,16.019,17.424,0.0,0.0,0.0,0.0,0.0,0.0),
		(47,now(),now(),1,107,14.895,16.058,17.472,0.0,0.0,0.0,0.0,0.0,0.0),
		(48,now(),now(),1,108,14.928,16.096,17.52,0.0,0.0,0.0,0.0,0.0,0.0),
		(49,now(),now(),1,109,14.96,16.136,17.569,0.0,0.0,0.0,0.0,0.0,0.0),
		(50,now(),now(),1,110,14.994,16.176,17.618,0.0,0.0,0.0,0.0,0.0,0.0),
		(51,now(),now(),1,111,15.028,16.217,17.669,0.0,0.0,0.0,0.0,0.0,0.0),
		(52,now(),now(),1,112,15.063,16.258,17.72,0.0,0.0,0.0,0.0,0.0,0.0),
		(53,now(),now(),1,113,15.098,16.3,17.771,0.0,0.0,0.0,0.0,0.0,0.0),
		(54,now(),now(),1,114,15.134,16.343,17.823,0.0,0.0,0.0,0.0,0.0,0.0),
		(55,now(),now(),1,115,15.17,16.386,17.876,0.0,0.0,0.0,0.0,0.0,0.0),
		(56,now(),now(),1,116,15.207,16.43,17.93,0.0,0.0,0.0,0.0,0.0,0.0),
		(57,now(),now(),1,117,15.245,16.475,17.984,0.0,0.0,0.0,0.0,0.0,0.0),
		(58,now(),now(),1,118,15.283,16.52,18.039,0.0,0.0,0.0,0.0,0.0,0.0),
		(59,now(),now(),1,119,15.323,16.566,18.096,0.0,0.0,0.0,0.0,0.0,0.0),
		(60,now(),now(),1,120,15.362,16.613,18.152,0.0,0.0,0.0,0.0,0.0,0.0),
		(61,now(),now(),1,121,15.403,16.661,18.21,0.0,0.0,0.0,0.0,0.0,0.0),
		(62,now(),now(),1,122,15.445,16.71,18.269,0.0,0.0,0.0,0.0,0.0,0.0),
		(63,now(),now(),1,123,15.487,16.76,18.328,0.0,0.0,0.0,0.0,0.0,0.0),
		(64,now(),now(),1,124,15.53,16.81,18.389,0.0,0.0,0.0,0.0,0.0,0.0),
		(65,now(),now(),1,125,15.574,16.861,18.45,0.0,0.0,0.0,0.0,0.0,0.0),
		(66,now(),now(),1,126,15.619,16.914,18.512,0.0,0.0,0.0,0.0,0.0,0.0),
		(67,now(),now(),1,127,15.664,16.967,18.575,0.0,0.0,0.0,0.0,0.0,0.0),
		(68,now(),now(),1,128,15.71,17.021,18.64,0.0,0.0,0.0,0.0,0.0,0.0),
		(69,now(),now(),1,129,15.758,17.076,18.705,0.0,0.0,0.0,0.0,0.0,0.0),
		(70,now(),now(),1,130,15.806,17.132,18.771,0.0,0.0,0.0,0.0,0.0,0.0),
		(71,now(),now(),1,131,15.854,17.188,18.838,0.0,0.0,0.0,0.0,0.0,0.0),
		(72,now(),now(),1,132,15.904,17.246,18.906,0.0,0.0,0.0,0.0,0.0,0.0),
		(73,now(),now(),1,133,15.955,17.304,18.974,0.0,0.0,0.0,0.0,0.0,0.0),
		(74,now(),now(),1,134,16.006,17.364,19.044,0.0,0.0,0.0,0.0,0.0,0.0),
		(75,now(),now(),1,135,16.058,17.424,19.114,0.0,0.0,0.0,0.0,0.0,0.0),
		(76,now(),now(),1,136,16.11,17.485,19.186,0.0,0.0,0.0,0.0,0.0,0.0),
		(77,now(),now(),1,137,16.164,17.546,19.258,0.0,0.0,0.0,0.0,0.0,0.0),
		(78,now(),now(),1,138,16.218,17.609,19.331,0.0,0.0,0.0,0.0,0.0,0.0),
		(79,now(),now(),1,139,16.273,17.672,19.404,0.0,0.0,0.0,0.0,0.0,0.0),
		(80,now(),now(),1,140,16.328,17.736,19.478,0.0,0.0,0.0,0.0,0.0,0.0),
		(81,now(),now(),1,141,16.384,17.8,19.553,0.0,0.0,0.0,0.0,0.0,0.0),
		(82,now(),now(),1,142,16.441,17.865,19.629,0.0,0.0,0.0,0.0,0.0,0.0),
		(83,now(),now(),1,143,16.498,17.931,19.705,0.0,0.0,0.0,0.0,0.0,0.0),
		(84,now(),now(),1,144,16.555,17.997,19.781,0.0,0.0,0.0,0.0,0.0,0.0),
		(85,now(),now(),1,145,16.613,18.063,19.858,0.0,0.0,0.0,0.0,0.0,0.0),
		(86,now(),now(),1,146,16.671,18.13,19.935,0.0,0.0,0.0,0.0,0.0,0.0),
		(87,now(),now(),1,147,16.73,18.197,20.012,0.0,0.0,0.0,0.0,0.0,0.0),
		(88,now(),now(),1,148,16.788,18.264,20.09,0.0,0.0,0.0,0.0,0.0,0.0),
		(89,now(),now(),1,149,16.847,18.331,20.167,0.0,0.0,0.0,0.0,0.0,0.0),
		(90,now(),now(),1,150,16.906,18.399,20.245,0.0,0.0,0.0,0.0,0.0,0.0),
		(91,now(),now(),1,151,16.965,18.466,20.323,0.0,0.0,0.0,0.0,0.0,0.0),
		(92,now(),now(),1,152,17.024,18.533,20.4,0.0,0.0,0.0,0.0,0.0,0.0),
		(93,now(),now(),1,153,17.082,18.601,20.477,0.0,0.0,0.0,0.0,0.0,0.0),
		(94,now(),now(),1,154,17.141,18.668,20.555,0.0,0.0,0.0,0.0,0.0,0.0),
		(95,now(),now(),1,155,17.2,18.735,20.631,0.0,0.0,0.0,0.0,0.0,0.0),
		(96,now(),now(),1,156,17.258,18.801,20.708,0.0,0.0,0.0,0.0,0.0,0.0),
		(97,now(),now(),1,157,17.316,18.868,20.784,0.0,0.0,0.0,0.0,0.0,0.0),
		(98,now(),now(),1,158,17.373,18.934,20.859,0.0,0.0,0.0,0.0,0.0,0.0),
		(99,now(),now(),1,159,17.431,18.999,20.934,0.0,0.0,0.0,0.0,0.0,0.0),
		(100,now(),now(),1,160,17.488,19.064,21.009,0.0,0.0,0.0,0.0,0.0,0.0),
		(101,now(),now(),1,161,17.544,19.129,21.083,0.0,0.0,0.0,0.0,0.0,0.0),
		(102,now(),now(),1,162,17.6,19.193,21.156,0.0,0.0,0.0,0.0,0.0,0.0),
		(103,now(),now(),1,163,17.656,19.257,21.229,0.0,0.0,0.0,0.0,0.0,0.0),
		(104,now(),now(),1,164,17.711,19.32,21.301,0.0,0.0,0.0,0.0,0.0,0.0),
		(105,now(),now(),1,165,17.765,19.382,21.372,0.0,0.0,0.0,0.0,0.0,0.0),
		(106,now(),now(),1,166,17.819,19.444,21.443,0.0,0.0,0.0,0.0,0.0,0.0),
		(107,now(),now(),1,167,17.872,19.504,21.512,0.0,0.0,0.0,0.0,0.0,0.0),
		(108,now(),now(),1,168,17.925,19.565,21.581,0.0,0.0,0.0,0.0,0.0,0.0),
		(109,now(),now(),1,169,17.977,19.624,21.648,0.0,0.0,0.0,0.0,0.0,0.0),
		(110,now(),now(),1,170,18.028,19.682,21.715,0.0,0.0,0.0,0.0,0.0,0.0),
		(111,now(),now(),1,171,18.078,19.74,21.781,0.0,0.0,0.0,0.0,0.0,0.0),
		(112,now(),now(),1,172,18.127,19.797,21.845,0.0,0.0,0.0,0.0,0.0,0.0),
		(113,now(),now(),1,173,18.176,19.852,21.909,0.0,0.0,0.0,0.0,0.0,0.0),
		(114,now(),now(),1,174,18.223,19.907,21.971,0.0,0.0,0.0,0.0,0.0,0.0),
		(115,now(),now(),1,175,18.27,19.961,22.032,0.0,0.0,0.0,0.0,0.0,0.0),
		(116,now(),now(),1,176,18.316,20.013,22.092,0.0,0.0,0.0,0.0,0.0,0.0),
		(117,now(),now(),1,177,18.361,20.065,22.151,0.0,0.0,0.0,0.0,0.0,0.0),
		(118,now(),now(),1,178,18.404,20.115,22.208,0.0,0.0,0.0,0.0,0.0,0.0),
		(119,now(),now(),1,179,18.447,20.164,22.264,0.0,0.0,0.0,0.0,0.0,0.0),
		(120,now(),now(),1,180,18.489,20.212,22.319,0.0,0.0,0.0,0.0,0.0,0.0),
		(121,now(),now(),1,181,18.529,20.26,22.373,0.0,0.0,0.0,0.0,0.0,0.0),
		(123,now(),now(),1,182,18.569,20.305,22.425,0.0,0.0,0.0,0.0,0.0,0.0),
		(124,now(),now(),1,183,18.608,20.35,22.476,0.0,0.0,0.0,0.0,0.0,0.0),
		(125,now(),now(),1,184,18.645,20.393,22.525,0.0,0.0,0.0,0.0,0.0,0.0),
		(126,now(),now(),1,185,18.682,20.436,22.573,0.0,0.0,0.0,0.0,0.0,0.0),
		(127,now(),now(),1,186,18.717,20.477,22.62,0.0,0.0,0.0,0.0,0.0,0.0),
		(128,now(),now(),1,187,18.752,20.517,22.666,0.0,0.0,0.0,0.0,0.0,0.0),
		(129,now(),now(),1,188,18.785,20.556,22.71,0.0,0.0,0.0,0.0,0.0,0.0),
		(130,now(),now(),1,189,18.818,20.594,22.754,0.0,0.0,0.0,0.0,0.0,0.0),
		(131,now(),now(),1,190,18.849,20.631,22.795,0.0,0.0,0.0,0.0,0.0,0.0),
		(132,now(),now(),1,191,18.88,20.666,22.836,0.0,0.0,0.0,0.0,0.0,0.0),
		(133,now(),now(),1,192,18.909,20.701,22.876,0.0,0.0,0.0,0.0,0.0,0.0),
		(134,now(),now(),1,193,18.938,20.734,22.914,0.0,0.0,0.0,0.0,0.0,0.0),
		(135,now(),now(),1,194,18.966,20.767,22.951,0.0,0.0,0.0,0.0,0.0,0.0),
		(136,now(),now(),1,195,18.992,20.798,22.987,0.0,0.0,0.0,0.0,0.0,0.0),
		(137,now(),now(),1,196,19.018,20.829,23.021,0.0,0.0,0.0,0.0,0.0,0.0),
		(138,now(),now(),1,197,19.043,20.858,23.055,0.0,0.0,0.0,0.0,0.0,0.0),
		(139,now(),now(),1,198,19.067,20.886,23.087,0.0,0.0,0.0,0.0,0.0,0.0),
		(140,now(),now(),1,199,19.09,20.914,23.119,0.0,0.0,0.0,0.0,0.0,0.0),
		(141,now(),now(),1,200,19.112,20.94,23.149,0.0,0.0,0.0,0.0,0.0,0.0),
		(142,now(),now(),1,201,19.133,20.966,23.178,0.0,0.0,0.0,0.0,0.0,0.0),
		(143,now(),now(),1,202,19.154,20.99,23.206,0.0,0.0,0.0,0.0,0.0,0.0),
		(144,now(),now(),1,203,19.173,21.014,23.233,0.0,0.0,0.0,0.0,0.0,0.0),
		(145,now(),now(),1,204,19.193,21.037,23.259,0.0,0.0,0.0,0.0,0.0,0.0),
		(146,now(),now(),1,205,19.211,21.059,23.285,0.0,0.0,0.0,0.0,0.0,0.0),
		(147,now(),now(),1,206,19.228,21.08,23.309,0.0,0.0,0.0,0.0,0.0,0.0),
		(148,now(),now(),1,207,19.245,21.101,23.333,0.0,0.0,0.0,0.0,0.0,0.0),
		(149,now(),now(),1,208,19.262,21.121,23.355,0.0,0.0,0.0,0.0,0.0,0.0),
		(150,now(),now(),1,209,19.277,21.14,23.378,0.0,0.0,0.0,0.0,0.0,0.0),
		(151,now(),now(),1,210,19.292,21.159,23.399,0.0,0.0,0.0,0.0,0.0,0.0),
		(152,now(),now(),1,211,19.307,21.177,23.42,0.0,0.0,0.0,0.0,0.0,0.0),
		(153,now(),now(),1,212,19.321,21.194,23.44,0.0,0.0,0.0,0.0,0.0,0.0),
		(154,now(),now(),1,213,19.335,21.212,23.46,0.0,0.0,0.0,0.0,0.0,0.0),
		(155,now(),now(),1,214,19.348,21.228,23.479,0.0,0.0,0.0,0.0,0.0,0.0),
		(156,now(),now(),1,215,19.361,21.244,23.498,0.0,0.0,0.0,0.0,0.0,0.0),
		(157,now(),now(),1,216,19.374,21.26,23.516,0.0,0.0,0.0,0.0,0.0,0.0),
		(158,now(),now(),1,217,19.386,21.276,23.534,0.0,0.0,0.0,0.0,0.0,0.0),
		(159,now(),now(),1,218,19.398,21.291,23.551,0.0,0.0,0.0,0.0,0.0,0.0),
		(160,now(),now(),1,219,19.41,21.306,23.568,0.0,0.0,0.0,0.0,0.0,0.0),
		(161,now(),now(),1,220,19.421,21.32,23.585,0.0,0.0,0.0,0.0,0.0,0.0),
		(162,now(),now(),1,221,19.432,21.334,23.601,0.0,0.0,0.0,0.0,0.0,0.0),
		(163,now(),now(),1,222,19.443,21.348,23.617,0.0,0.0,0.0,0.0,0.0,0.0),
		(164,now(),now(),1,223,19.454,21.362,23.633,0.0,0.0,0.0,0.0,0.0,0.0),
		(165,now(),now(),1,224,19.464,21.375,23.648,0.0,0.0,0.0,0.0,0.0,0.0),
		(166,now(),now(),1,225,19.475,21.388,23.663,0.0,0.0,0.0,0.0,0.0,0.0),
		(167,now(),now(),1,226,19.485,21.401,23.678,0.0,0.0,0.0,0.0,0.0,0.0),
		(168,now(),now(),1,227,19.495,21.414,23.693,0.0,0.0,0.0,0.0,0.0,0.0),
		(169,now(),now(),1,228,19.504,21.427,23.707,0.0,0.0,0.0,0.0,0.0,0.0);
	
-----------------------------------------------------------------------------------------
-- update 26.02.2016
-----------------------------------------------------------------------------------------
DELETE FROM permissions WHERE id > 0;
INSERT INTO `permissions`
VALUES 
(1,now(),now(),'READ_PRIVILEGE','read permission only for all services'),
(2,now(),now(),'WRITE_PRIVILEGE','write permission for all services'),
(3,now(),now(),'ALL_PRIVILEGE','both read and write permission for all services'),
(4,now(),now(),'BLOGG_READ_PRIVILEGE','read permission for blogg services'),
(5,now(),now(),'BLOGG_WRITE_PRIVILEGE','write permission for blogg services'),
(6,now(),now(),'ACCOUNT_READ_PRIVILEGE','read permission for user account services'),
(7,now(),now(),'ACCOUNT_WRITE_PRIVILEGE','write permission for user account services'),
(8,now(),now(),'DIET_READ_PRIVILEGE','read permission for user dietmanager services'),
(9,now(),now(),'DIET_WRITE_PRIVILEGE','write permission for dietmanager services');

DELETE FROM role_permission_lnk WHERE id > 0;
INSERT INTO `role_permission_lnk`
VALUES 
(1,now(),now(),1,1),
(2,now(),now(),1,2),
(3,now(),now(),1,4),
(4,now(),now(),1,5),
(5,now(),now(),1,6),
(6,now(),now(),1,7),
(7,now(),now(),2,1),
(8,now(),now(),2,2),
(9,now(),now(),2,4),
(10,now(),now(),2,5),
(11,now(),now(),3,1);

ALTER TABLE user_diet_menu_item_lnk
ADD COLUMN fk_log_id INTEGER AFTER caused_conflict;

ALTER TABLE user_diet_menu_item_lnk
ADD FOREIGN KEY fk_log_id(fk_log_id)
REFERENCES event_log(id)
ON DELETE SET NULL
ON UPDATE CASCADE;

ALTER TABLE diet_plan_meal_items
ADD COLUMN fk_diet_plan_id INTEGER AFTER last_modified_date_time;

ALTER TABLE diet_plan_meal_items
ADD FOREIGN KEY fk_diet_plan_id(fk_diet_plan_id)
REFERENCES diet_plans(id)
ON DELETE SET NULL
ON UPDATE CASCADE;

ALTER TABLE diet_plan_meal_items
ADD COLUMN meal_item_type VARCHAR(100) AFTER meal_item_name;

ALTER TABLE diet_plan_meals
DROP COLUMN fk_diet_plan_id;


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
	
-----------------------------------------------------------------------------------------
-- update	11.04.2017
-----------------------------------------------------------------------------------------
ALTER TABLE diet_plan_meal_items
ADD COLUMN meal_item_enabled INTEGER DEFAULT 1 AFTER meal_item_img_link;

ALTER TABLE diet_menu_items
ADD COLUMN menu_item_enabled INTEGER DEFAULT 1 AFTER menu_item_img_link;

UPDATE diet_menu_items 
SET menu_item_enabled = 1
LIMIT 100;

-----------------------------------------------------------------------------------------
-- update	16.04.2017
-----------------------------------------------------------------------------------------

-- Table: user_follower_lnk
CREATE TABLE user_follower_lnk(id 			INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_user_id    	        INTEGER,
                   FOREIGN KEY (fk_user_follower_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
                   fk_user_follower_id			INTEGER);     
                   
INSERT INTO `user_follower_lnk`
VALUES 
 (1,now(),now(),4,5),
 (2,now(),now(),4,6),
 (3,now(),now(),5,4),
 (4,now(),now(),5,6),
 (5,now(),now(),6,4),
 (6,now(),now(),6,5);
 
 -----------------------------------------------------------------------------------------
-- update	01.05.2017
-----------------------------------------------------------------------------------------
 -- Table: event_log_comment
CREATE TABLE event_log_comment(id            	 INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_user_id				 INTEGER,
						FOREIGN KEY (fk_event_log_id) REFERENCES event_log(id) ON DELETE SET NULL ON UPDATE CASCADE,
						fk_event_log_id				 INTEGER,
						title					 VARCHAR(50),
						content   				 VARCHAR(4096));
						
 -----------------------------------------------------------------------------------------
-- update	17.05.2017
-----------------------------------------------------------------------------------------
CREATE TABLE products(id            			INTEGER PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP CURRENT_TIMESTAMP,
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
						img_link		VARCHAR(100));
						
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` 
VALUES 
(null,now(),now(),'brødskive'			,'type'	,'korn'		,'beskrivelse',1,45, 75,0,0,0,0,null),
(null,now(),now(),'lettmelk'			,'type'	,'drikke'	,'beskrivelse',1, 100, 38, 0.7, 4.6, 0, 3.4, null),
(null,now(),now(),'eple'				,'type'	,'frukt'	,'beskrivelse',1,0  ,0,0,0,0,0,null),
(null,now(),now(),'fresubin Energy'	,'type'	,'drikke'	,'beskrivelse',1,100,150,0,0,2,11.2,null),
(null,now(),now(),'multivitamin'		,'type'	,'vitamin'	,'beskrivelse',1,0  ,0,0,0,0,0,null),
(null,now(),now(),'kylling'			,'type'	,'kjøtt'	,'beskrivelse',1,100  ,104,1.0,0,0,0,null),
(null,now(),now(),'biff storfe'		,'type'	,'kjøtt'	,'beskrivelse',1,100  ,105,1.7,0,0,0,null),
(null,now(),now(),'svin'				,'type'	,'kjøtt'	,'beskrivelse',1,100  ,110,1.6,0,0,0,null),
(null,now(),now(),'torsk'				,'type'	,'fisk'		,'beskrivelse',1,100  ,98,0.4,0,0,0,null),
(null,now(),now(),'laks'				,'type'	,'fisk'		,'beskrivelse',1,100  ,224,16,0,0,0,null),
(null,now(),now(),'ørret'				,'type'	,'fisk'		,'beskrivelse',1,100  ,166,10,0,0,0,null),
(null,now(),now(),'sei'				,'type'	,'fisk'		,'beskrivelse',1,100  ,69,0.3,0,0,0,null),
(null,now(),now(),'fiskeboller'		,'type'	,'fisk'		,'beskrivelse',1,100  ,0,0,0,0,0,null),
(null,now(),now(),'fiskekaker'		,'type'	,'fisk'		,'beskrivelse',1,100  ,0,0,0,0,0,null),
(null,now(),now(),'fiskeburger'		,'type'	,'fisk'		,'beskrivelse',1,100  ,0,0,0,0,0,null),
(null,now(),now(),'havregrøt'			,'type'	,'korn'		,'beskrivelse',1,100  ,0,0,0,0,0,null),
(null,now(),now(),'go-morgen yoghurt'	,'type'	,'melk'		,'beskrivelse',1,100  ,0,0,0,0,0,null),
(null,now(),now(),'egg, kokt'			,'type'	,''			,'beskrivelse',1,100  ,143,10.2,0,0,0,null),
(null,now(),now(),'ris kokt'			,'type'	,''			,'beskrivelse',1,100  ,124,0.4,0,0,0,null),
(null,now(),now(),'potet'				,'type'	,''			,'beskrivelse',1,100  ,80,0.1,0,0,0,null),
(null,now(),now(),'pasta fullkorn kokt','type'	,''			,'beskrivelse',1,100  ,135,2.6,0,0,0,null),
(null,now(),now(),'juice eple'			,'type'	,'drikke'	,'beskrivelse',1,100,42,0,0,0,0,null);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

 -----------------------------------------------------------------------------------------
-- update	17.09.2017
-----------------------------------------------------------------------------------------
ALTER TABLE diet_plans
ADD COLUMN diet_plan_changes VARCHAR(400) AFTER diet_plan_description;

INSERT INTO `diet_plans` 
VALUES 
(4,now(),now(),now(),now(),'EP Diet Plan 4','Kostplan fra uke 37 i 2017 til uke x i 2017', 1);

INSERT INTO `diet_plan_meal_items` 
VALUES 
(79,now(),now(),4,1,'Frokost','','3 brødskiver med margarin og pålegg / Havregrøt(75g) på lettmelk med 1 ts sukker / Frokostblanding (90g) 3dl lettmelk',0,null,1),
(80,now(),now(),4,1,'Frokost','','1 glass lettmelk (2 dl)',0,null,1),
(81,now(),now(),4,1,'Frokost','','100 ml Fresubin Energy',0,null,1),
(82,now(),now(),4,1,'Frokost','','1 multivitamin',0,null,1),
(83,now(),now(),4,2,'Lunsj','','3 brødskiver med margarin og pålegg / 2 brødskiver med margarin og 1 egg, pluss 1 frukt yoghurt',0,null,1),
(84,now(),now(),4,2,'Lunsj','','1 glass lettmelk (2 dl)',0,null,1),
(85,now(),now(),4,2,'Lunsj','','1 frukt',0,null,1),
(86,now(),now(),4,2,'Lunsj','','100 ml Fresubin Energy',0,null,1),
(87,now(),now(),4,3,'Mellom måltid','','2 Bixit, 60g nøtteblanding, 6 riskjeks eller 33 ml YT',0,null,1),
(88,now(),now(),4,4,'Middag','','1 porsjon middag (varier mellom kjøtt og fisk)',0,null,1),
(89,now(),now(),4,4,'Middag','','1 dessert eller 1 yoghurt (Kan erstattes med 1/3 ekstra middag)',0,null,1),
(90,now(),now(),4,4,'Middag','','1 juice (2 dl)',0,null,1),
(91,now(),now(),4,4,'Middag','','100 ml Fresubin Energy',0,null,1),
(92,now(),now(),4,5,'Kveldsmat','','1 brødskive med margarin og pålegg, pluss Go-Morgen yoghurt / 3 brødskiver med margarin og pålegg',0,null,1),
(93,now(),now(),4,5,'Kveldsmat','','1 glass lettmelk (2 dl)',0,null,1),
(94,now(),now(),4,5,'Kveldsmat','','100 ml Fresubin Energy',0,null,1),
(95,now(),now(),4,5,'Kveldsmat','','1 frukt',0,null,1),
(96,now(),now(),4,7,'Regler og tillegg','','500 ml vann/saft hver dag',0,null,1),
(97,now(),now(),4,7,'Regler og tillegg','','Drikke fresubin, 200 ml til middag og 200 ml til kveldsmat',0,null,1),
(98,now(),now(),4,7,'Regler og tillegg','','Ikke lett produkter',0,null,1),
(100,now(),now(),4,7,'Regler og tillegg','','Spisetid 30 minutter, det som eventuelt blir igjen skal da erstattes med Fresubin',0,null,1),
(101,now(),now(),4,7,'Regler og tillegg','','Frokost: 350 ml Fresubin kan erstatte hele måltidet (200 ml innehlder 300 Kcal)',0,null,1),
(102,now(),now(),4,7,'Regler og tillegg','','Lunsj: 450 ml Fresubin kan erstatte hele måltidet',0,null,1),
(103,now(),now(),4,7,'Regler og tillegg','','Middag: 500 ml Fresubin kan erstatte hele måltidet',0,null,1),
(104,now(),now(),4,7,'Regler og tillegg','','Kveldsmat: 350 ml Fresubin kan erstatte hele måltidet',0,null,1),
(105,now(),now(),4,7,'Regler og tillegg','','Alle måltider skal tilberedes av foreldre',0,null,1),
(106,now(),now(),4,7,'Regler og tillegg','','Ved aktivitet skal hun drikke 0.5 L sjokolademelk, YT eller fresubin 200


 -----------------------------------------------------------------------------------------
-- update	24.02.2018 convert to spring 5
-----------------------------------------------------------------------------------------
UPDATE users 
SET password = '{bcrypt}$2a$13$GwPQNWAfenYSb06qxu/Nqevmwe31I4FJreraz34ScjbpAUBnO0S4y'
WHERE id = 1;

UPDATE users 
SET password = '{bcrypt}$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi'
WHERE id = 3;

UPDATE users 
SET password = '{bcrypt}$2a$12$Ljya1s3.uWu7svzYEgMT3OUpsMLSwYTgybCID.e/ViVc2x8XM038W'
WHERE id = 4;

UPDATE users 
SET password = '{bcrypt}$2a$12$ZuV6/OziRcAHJtbqr0cvmOMkae.pgjbSfayFpL5WqkhfmIw.4F17m'
WHERE id = 5;

UPDATE users 
SET password = '{bcrypt}$2a$12$P1tBOtsZNn7ghpLZRIY3Ae7FnIORN9gq.4BY/7vzes04usHVnPhMi'
WHERE id = 6;
