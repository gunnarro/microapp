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

-- Table: sport_types
DROP TABLE IF EXISTS sport_types;
CREATE TABLE sport_types(id                  		INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time 	TIMESTAMP,
						name                    	VARCHAR(25)     NOT NULL,
						description             	VARCHAR(100),
						UNIQUE(name));
					 
-- Table: league statuses
DROP TABLE IF EXISTS league_statuses;
CREATE TABLE league_statuses(id                     	INTEGER  PRIMARY KEY AUTO_INCREMENT,
							created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time 	TIMESTAMP,
							name             			VARCHAR(25) NOT NULL,
							UNIQUE(name));
                       
-- Table: league_rules
DROP TABLE IF EXISTS league_rules;
CREATE TABLE league_rules(id 								 INTEGER  PRIMARY KEY AUTO_INCREMENT,
                     		 created_date_time               TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     		 last_modified_date_time         TIMESTAMP,
					 		 name			                 VARCHAR(50),
                     		 player_age_min                  INTEGER,
                     		 player_age_max                  INTEGER,
                     		 gender                          VARCHAR(10) NOT NULL,
                     		 match_period_time_minutes       INTEGER,
                     		 match_extra_period_time_minutes INTEGER,
                     		 number_of_players               INTEGER,
                     		 number_of_substitutes           INTEGER,
                     		 penalty						 INTEGER,
                     		 offside						 INTEGER,
                     		 player_exclusion				 INTEGER,
                     		 yellow_card					 INTEGER,
                     		 reed_card						 INTEGER,
                     		 extra_player                    INTEGER,
                     		 field_length					 INTEGER,
                     		 field_width					 INTEGER,
                     		 goal_length					 INTEGER,
                     		 goal_width					 	 INTEGER,
                     		 points_win					 	 INTEGER,
                     		 points_draw					 INTEGER,
                     		 points_loss				 	 INTEGER,
                     		 description					 VARCHAR(500),
                     		 UNIQUE(name));
                     		 
-- Table: league_categories
DROP TABLE IF EXISTS league_categories;
CREATE TABLE league_categories(id 					 INTEGER  PRIMARY KEY AUTO_INCREMENT,
             		 created_date_time          	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     last_modified_date_time   		 TIMESTAMP,
                     terminated_date_time   		 TIMESTAMP,
                     fk_sport_type_id          	 	 INTEGER,
					 FOREIGN KEY (fk_sport_type_id)  REFERENCES sport_types(id) ON DELETE SET NULL ON UPDATE CASCADE,
                     fk_league_rule_id           	 INTEGER,
					 FOREIGN KEY (fk_league_rule_id) REFERENCES league_rules(id) ON DELETE SET NULL ON UPDATE CASCADE,
					 fk_league_status_id           	 INTEGER,
					 FOREIGN KEY (fk_league_status_id) REFERENCES league_statuses(id) ON DELETE SET NULL ON UPDATE CASCADE,
                     name                	 		 VARCHAR(50) NOT NULL,
                     UNIQUE(name,fk_sport_type_id));
                     
-- Table: leagues
DROP TABLE IF EXISTS leagues;
CREATE TABLE leagues(id 							 INTEGER  PRIMARY KEY AUTO_INCREMENT,
             		 created_date_time          	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     last_modified_date_time   		 TIMESTAMP,
                     terminated_date_time   		 TIMESTAMP,
                     fk_league_category_id           INTEGER,
					 FOREIGN KEY (fk_league_category_id) 	 REFERENCES league_categories(id) ON DELETE SET NULL ON UPDATE CASCADE,
                     fk_league_status_id           	 INTEGER,
					 FOREIGN KEY (fk_league_status_id) REFERENCES league_statuses(id) ON DELETE SET NULL ON UPDATE CASCADE,
					 name       		         	 VARCHAR(50) NOT NULL,
                     UNIQUE(name));
                     		 
-- Table: clubs
DROP TABLE IF EXISTS clubs;
CREATE TABLE clubs(id 					   INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time TIMESTAMP,
                   fk_address_id           INTEGER,
                   FOREIGN KEY (fk_address_id) REFERENCES addresses(id) ON DELETE SET NULL ON UPDATE CASCADE,
				   club_name               VARCHAR(50)     NOT NULL,
                   club_department_name    VARCHAR(50)     NOT NULL,
                   club_name_abbreviation  VARCHAR(20),
				   club_stadium_name       VARCHAR(50),
                   club_url_home_page      VARCHAR(100),
                   UNIQUE(club_name,club_department_name));

-- Table: teams
DROP TABLE IF EXISTS teams;
CREATE TABLE teams(id                       INTEGER  PRIMARY KEY AUTO_INCREMENT,
                   created_date_time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   last_modified_date_time  TIMESTAMP,
                   fk_club_id               INTEGER NOT NULL,
                   FOREIGN KEY (fk_club_id) REFERENCES clubs(id) ON DELETE CASCADE ON UPDATE CASCADE,
                   fk_league_id             INTEGER,
                   FOREIGN KEY (fk_league_id) REFERENCES leagues(id) ON DELETE SET NULL ON UPDATE CASCADE,
				   team_name                VARCHAR(50)     NOT NULL,
                   team_year_of_birth       INTEGER,
                   team_gender              VARCHAR(10)     NOT NULL,
                   UNIQUE(fk_club_id,team_name,fk_league_id));

-- Table: seasons
DROP TABLE IF EXISTS seasons;
CREATE TABLE seasons(id                      INTEGER  PRIMARY KEY AUTO_INCREMENT,
                     created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     last_modified_date_time TIMESTAMP,
                     period                  VARCHAR(20),
                     start_date              TIMESTAMP  		NOT NULL,
                     end_date                TIMESTAMP  		NOT NULL,
                     UNIQUE(start_date,end_date));

-- Table: tournaments
DROP TABLE IF EXISTS tournaments;
CREATE TABLE tournaments(id                      INTEGER  PRIMARY KEY AUTO_INCREMENT,
						 created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						 last_modified_date_time TIMESTAMP,
						 fk_season_id			 INTEGER,
						 FOREIGN KEY (fk_season_id) REFERENCES seasons(id) ON DELETE SET NULL ON UPDATE CASCADE,
						 start_date              TIMESTAMP,
						 tournament_name         VARCHAR(100)     NOT NULL,
						 organizer_name          VARCHAR(100)     NOT NULL,
						 venue                   VARCHAR(100)     NOT NULL,
						 deadline_date           INTEGER,
						 UNIQUE(fk_season_id,tournament_name));

-- Table: trainings
DROP TABLE IF EXISTS trainings;
CREATE TABLE trainings( id                      	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time 	TIMESTAMP,
						fk_team_id              	INTEGER,
						FOREIGN KEY (fk_team_id) 	REFERENCES teams(id) ON DELETE CASCADE ON UPDATE CASCADE,
						fk_season_id			 	INTEGER,
						FOREIGN KEY (fk_season_id) 	REFERENCES seasons(id) ON DELETE CASCADE ON UPDATE CASCADE,
						start_time              	TIMESTAMP,
						end_time                	TIMESTAMP,
						place                   	VARCHAR(100)     NOT NULL,
						information             	VARCHAR(200),
						UNIQUE(fk_team_id,start_time));

-- Table: contact_statuses
DROP TABLE IF EXISTS contact_statuses;
CREATE TABLE contact_statuses(id                     	INTEGER  PRIMARY KEY AUTO_INCREMENT,
							created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time 	TIMESTAMP,
							name             			VARCHAR(25) NOT NULL,
							UNIQUE(name));
							
-- Table: contacts
DROP TABLE IF EXISTS contacts;
CREATE TABLE contacts(id                     	INTEGER  PRIMARY KEY AUTO_INCREMENT,
					created_date_time        	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
					last_modified_date_time  	TIMESTAMP,
					fk_user_id               	INTEGER,
					FOREIGN KEY (fk_user_id) 	REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_team_id               	INTEGER,
					FOREIGN KEY (fk_team_id) 	REFERENCES teams(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_address_id            	INTEGER,
					FOREIGN KEY (fk_address_id) REFERENCES addresses(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_status_id                INTEGER,
					FOREIGN KEY (fk_status_id) 	REFERENCES contact_statuses(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_person_id                INTEGER,
					FOREIGN KEY (fk_person_id)  REFERENCES persons(id) ON DELETE SET NULL ON UPDATE CASCADE,
					first_name               	VARCHAR(100)     NOT NULL,
					middle_name              	VARCHAR(100),
					last_name                	VARCHAR(100)     NOT NULL,
					gender                   	VARCHAR(10)      NOT NULL,
					mobile                   	VARCHAR(25)      NOT NULL,
					email                    	VARCHAR(100)     NOT NULL,
					UNIQUE(first_name,last_name));

-- Table: referees
DROP TABLE IF EXISTS referees;
CREATE TABLE referees(id                     		INTEGER  PRIMARY KEY AUTO_INCREMENT,
					created_date_time        		TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
					last_modified_date_time  		TIMESTAMP,
					fk_club_id               		INTEGER,
					FOREIGN KEY (fk_club_id) 		REFERENCES clubs(id) ON DELETE CASCADE ON UPDATE CASCADE,
					fk_address_id            		INTEGER,
					FOREIGN KEY (fk_address_id) 	REFERENCES addresses(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_status_id                	INTEGER,
					FOREIGN KEY (fk_status_id) 		REFERENCES contact_statuses(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_sports_type_id               INTEGER,
					FOREIGN KEY (fk_sports_type_id) REFERENCES sport_types(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_person_id                	INTEGER,
					FOREIGN KEY (fk_person_id)  	REFERENCES persons(id) ON DELETE SET NULL ON UPDATE CASCADE,
					first_name               		VARCHAR(100) NOT NULL,
					middle_name              		VARCHAR(100),
					last_name                		VARCHAR(100) NOT NULL,
					gender                   		VARCHAR(10)  NOT NULL,
					mobile                   		VARCHAR(25)  NOT NULL,
					email                    		VARCHAR(100) NOT NULL,
					UNIQUE(fk_club_id,first_name,last_name));

-- Table: match_status_types
DROP TABLE IF EXISTS match_status_types;
CREATE TABLE match_status_types(id                      INTEGER  PRIMARY KEY AUTO_INCREMENT,
								created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
								last_modified_date_time TIMESTAMP,
								name                    VARCHAR(25)     NOT NULL,
								description             VARCHAR(100),
								UNIQUE(name));

-- Table: match_types
DROP TABLE IF EXISTS match_types;
CREATE TABLE match_types(id                     INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						name                    VARCHAR(25)     NOT NULL,
						description             VARCHAR(100),
						UNIQUE(name));

-- Table: matches
DROP TABLE IF EXISTS matches;
CREATE TABLE matches(id                     INTEGER  PRIMARY KEY AUTO_INCREMENT,
					created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
					last_modified_date_time TIMESTAMP,
					fk_league_id            INTEGER,
					FOREIGN KEY (fk_league_id) REFERENCES leagues(id) ON DELETE CASCADE ON UPDATE CASCADE,
					fk_team_id              INTEGER,
					FOREIGN KEY (fk_team_id) REFERENCES teams(id) ON DELETE CASCADE ON UPDATE CASCADE,
					fk_season_id            INTEGER,
					FOREIGN KEY (fk_season_id) REFERENCES seasons(id) ON DELETE CASCADE ON UPDATE CASCADE,
					fk_match_type_id        INTEGER,
					FOREIGN KEY (fk_match_type_id) REFERENCES match_types(id) ON DELETE CASCADE ON UPDATE CASCADE,
					fk_match_status_id      INTEGER,
					FOREIGN KEY (fk_match_status_id) REFERENCES match_status_types(id) ON DELETE CASCADE ON UPDATE CASCADE,
					fk_referee_id           INTEGER,
					FOREIGN KEY (fk_referee_id) REFERENCES referees(id) ON DELETE CASCADE ON UPDATE CASCADE,
					start_date              TIMESTAMP  		 NOT NULL,
					home_team_name          VARCHAR(100)     NOT NULL,
					away_team_name          VARCHAR(100)     NOT NULL,
					goals_home_team         INTEGER  DEFAULT 0,
					goals_away_team         INTEGER  DEFAULT 0,
					venue                   VARCHAR(100)     NOT NULL,
					information             VARCHAR(200),
					UNIQUE(fk_team_id,start_date));

-- Table: team_role_types
DROP TABLE IF EXISTS team_role_types;
CREATE TABLE team_role_types(id                     INTEGER  PRIMARY KEY AUTO_INCREMENT,
							created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time TIMESTAMP,
							name                    VARCHAR(25)     NOT NULL,
							description             VARCHAR(100),
							UNIQUE(name));

-- Table: contact_statuses
DROP TABLE IF EXISTS contact_statuses;
CREATE TABLE contact_statuses(id                     	INTEGER  PRIMARY KEY AUTO_INCREMENT,
							created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time 	TIMESTAMP,
							name             			VARCHAR(25) NOT NULL,
							UNIQUE(name));
							
-- Table: contacts
DROP TABLE IF EXISTS contacts;
CREATE TABLE contacts(id                     	INTEGER  PRIMARY KEY AUTO_INCREMENT,
					created_date_time        	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
					last_modified_date_time  	TIMESTAMP,
					fk_user_id               	INTEGER,
					FOREIGN KEY (fk_user_id) 	REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_team_id               	INTEGER,
					FOREIGN KEY (fk_team_id) 	REFERENCES teams(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_address_id            	INTEGER,
					FOREIGN KEY (fk_address_id) REFERENCES addresses(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_status_id                INTEGER,
					FOREIGN KEY (fk_status_id) 	REFERENCES contact_statuses(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_person_id                INTEGER,
					FOREIGN KEY (fk_person_id)  REFERENCES persons(id) ON DELETE SET NULL ON UPDATE CASCADE,
					first_name               	VARCHAR(100)     NOT NULL,
					middle_name              	VARCHAR(100),
					last_name                	VARCHAR(100)     NOT NULL,
					gender                   	VARCHAR(10)      NOT NULL,
					mobile                   	VARCHAR(25)      NOT NULL,
					email                    	VARCHAR(100)     NOT NULL,
					UNIQUE(first_name,last_name));

-- Table: contact_role_type_lnk
DROP TABLE IF EXISTS contact_role_type_lnk;
CREATE TABLE contact_role_type_lnk( id                      	 INTEGER  PRIMARY KEY AUTO_INCREMENT,
									created_date_time       	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
									last_modified_date_time 	 TIMESTAMP,
									fk_contact_id           	 INTEGER NOT NULL,
									FOREIGN KEY (fk_contact_id)  REFERENCES contacts(id) ON DELETE CASCADE ON UPDATE CASCADE,
									fk_team_role_type_id         INTEGER NOT NULL,
									FOREIGN KEY (fk_team_role_type_id) REFERENCES team_role_types(id) ON DELETE CASCADE ON UPDATE CASCADE,
									UNIQUE(fk_contact_id,fk_team_role_type_id));

-- Table: cups
DROP TABLE IF EXISTS cups;
CREATE TABLE cups(id                    INTEGER  PRIMARY KEY AUTO_INCREMENT,
				created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
				last_modified_date_time TIMESTAMP,
				fk_season_id            INTEGER,
				FOREIGN KEY (fk_season_id) REFERENCES seasons(id) ON DELETE CASCADE ON UPDATE CASCADE,
				start_date              TIMESTAMP,
				end_date	            TIMESTAMP,
				deadline_date           TIMESTAMP,
				cup_name                VARCHAR(100)     NOT NULL,
				club_name               VARCHAR(100)     NOT NULL,
				venue                   VARCHAR(100)     NOT NULL,
				email					VARCHAR(100),
				home_page				VARCHAR(100),
				UNIQUE(fk_season_id,cup_name));

-- Table: cup_match_lnk
DROP TABLE IF EXISTS cup_match_lnk;
CREATE TABLE cup_match_lnk(id                     	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time 	TIMESTAMP,
						fk_cup_id               	INTEGER  NOT NULL,
						FOREIGN KEY (fk_cup_id) REFERENCES cups(id) ON DELETE CASCADE ON UPDATE CASCADE,		
						fk_match_id             INTEGER NOT NULL,
						FOREIGN KEY (fk_match_id) REFERENCES matches(id) ON DELETE CASCADE ON UPDATE CASCADE,
						UNIQUE(fk_cup_id,fk_match_id));

-- Table: league_team_lnk
DROP TABLE IF EXISTS league_team_lnk;
CREATE TABLE league_team_lnk(id                     	INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       		TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time 		TIMESTAMP,
						fk_league_id            		INTEGER  NOT NULL,
						FOREIGN KEY (fk_league_id) REFERENCES leagues(id) ON DELETE CASCADE ON UPDATE CASCADE,
						fk_team_id              		INTEGER  NOT NULL,
						FOREIGN KEY (fk_team_id) REFERENCES teams(id) ON DELETE CASCADE ON UPDATE CASCADE,
						UNIQUE(fk_league_id,fk_team_id));

-- Table: match_events
DROP TABLE IF EXISTS match_events;
CREATE TABLE match_events(id                     		INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       		TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time 		TIMESTAMP,
						fk_match_id             		INTEGER  NOT NULL,
						FOREIGN KEY (fk_match_id) REFERENCES matches(id) ON DELETE CASCADE ON UPDATE CASCADE,
						played_minutes          		INTEGER,
						team_name               VARCHAR(100),
						player_name             VARCHAR(100),
						match_event_type_name   VARCHAR(100)     NOT NULL,
						key_name	            VARCHAR(100),
						value                   VARCHAR(100)     NOT NULL,
						description             VARCHAR(100));

-- Table: match_event_types
DROP TABLE IF EXISTS match_event_types;
CREATE TABLE match_event_types(id                     	INTEGER  PRIMARY KEY AUTO_INCREMENT,
							created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time 	TIMESTAMP,
							name		                VARCHAR(25)     NOT NULL,
							description 	            VARCHAR(100),
							UNIQUE(name));

-- Table: player statuses
DROP TABLE IF EXISTS player_statuses;
CREATE TABLE player_statuses(id                     	INTEGER  PRIMARY KEY AUTO_INCREMENT,
							created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time 	TIMESTAMP,
							name             			VARCHAR(25) NOT NULL,
							UNIQUE(name));

-- Table: player_position_types
DROP TABLE IF EXISTS player_position_types;
CREATE TABLE player_position_types(id           INTEGER  PRIMARY KEY AUTO_INCREMENT,
					created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
					last_modified_date_time 	TIMESTAMP,
					name                    	VARCHAR(25)     NOT NULL,
					description             	VARCHAR(100),
					UNIQUE(name));
					
-- Table: players
DROP TABLE IF EXISTS players;
CREATE TABLE players(id                     	INTEGER  PRIMARY KEY AUTO_INCREMENT,
					created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
					last_modified_date_time 	TIMESTAMP,
					fk_user_id               	INTEGER,
					FOREIGN KEY (fk_user_id) 	REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_team_id               	INTEGER,
					FOREIGN KEY (fk_team_id) 	REFERENCES teams(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_address_id            	INTEGER,
					FOREIGN KEY (fk_address_id) REFERENCES addresses(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_status_id                INTEGER,
					FOREIGN KEY (fk_status_id) 	REFERENCES player_statuses(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_position_id               INTEGER,
					FOREIGN KEY (fk_position_id) REFERENCES player_position_types(id) ON DELETE SET NULL ON UPDATE CASCADE,
					fk_person_id                INTEGER,
					FOREIGN KEY (fk_person_id)  REFERENCES persons(id) ON DELETE SET NULL ON UPDATE CASCADE,
					first_name              	VARCHAR(100) NOT NULL,
					middle_name             	VARCHAR(100),
					last_name               	VARCHAR(100) NOT NULL,
					gender                  	VARCHAR(10)  NOT NULL,
					date_of_birth           	TIMESTAMP,
					email                   	VARCHAR(100),
					mobile                  	VARCHAR(24),
					jersey_number           	INTEGER,
					school_name             	VARCHAR(100),
					UNIQUE(first_name,last_name));

-- Table: player_contact_lnk
DROP TABLE IF EXISTS player_contact_lnk;
CREATE TABLE player_contact_lnk(id                  	INTEGER  PRIMARY KEY AUTO_INCREMENT,
							created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time 	TIMESTAMP,
							fk_player_id              	INTEGER  NOT NULL,
							FOREIGN KEY (fk_player_id) 	REFERENCES players(id) ON DELETE CASCADE ON UPDATE CASCADE,
							fk_contact_id           	INTEGER  NOT NULL,
							FOREIGN KEY (fk_contact_id) REFERENCES contacts(id) ON DELETE CASCADE ON UPDATE CASCADE,
							UNIQUE(fk_player_id,fk_contact_id));

-- Table: player_cup_lnk
DROP TABLE IF EXISTS player_cup_lnk;
CREATE TABLE player_cup_lnk(id              	INTEGER PRIMARY KEY AUTO_INCREMENT,
					created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
					last_modified_date_time 	TIMESTAMP,
					fk_player_id            	INTEGER  NOT NULL,
					FOREIGN KEY (fk_player_id) 	REFERENCES players(id) ON DELETE CASCADE ON UPDATE CASCADE,
					fk_cup_id               	INTEGER  NOT NULL,
					FOREIGN KEY (fk_cup_id) 	REFERENCES cups(id) ON DELETE CASCADE ON UPDATE CASCADE,
					UNIQUE(fk_player_id,fk_cup_id));

-- Table: player_match_lnk
DROP TABLE IF EXISTS player_match_lnk;
CREATE TABLE player_match_lnk(id              	INTEGER PRIMARY KEY AUTO_INCREMENT,
					created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
					last_modified_date_time 	TIMESTAMP,
					fk_player_id            	INTEGER  NOT NULL,
					FOREIGN KEY (fk_player_id) 	REFERENCES players(id) ON DELETE CASCADE ON UPDATE CASCADE,
					fk_match_id               	INTEGER  NOT NULL,
					FOREIGN KEY (fk_match_id) 	REFERENCES matches(id) ON DELETE CASCADE ON UPDATE CASCADE,
					UNIQUE(fk_player_id,fk_match_id));

-- Table: player_training_lnk
DROP TABLE IF EXISTS player_training_lnk;
CREATE TABLE player_training_lnk(id              	INTEGER PRIMARY KEY AUTO_INCREMENT,
					created_date_time       		TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
					last_modified_date_time 		TIMESTAMP,
					fk_player_id            		INTEGER  NOT NULL,
					FOREIGN KEY (fk_player_id) 		REFERENCES players(id) ON DELETE CASCADE ON UPDATE CASCADE,
					fk_training_id               	INTEGER  NOT NULL,
					FOREIGN KEY (fk_training_id) 	REFERENCES trainings(id) ON DELETE CASCADE ON UPDATE CASCADE,
					UNIQUE(fk_player_id,fk_training_id));

-- Table: settings
DROP TABLE IF EXISTS settings;
CREATE TABLE settings(id                  		INTEGER  PRIMARY KEY AUTO_INCREMENT,
					created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
					last_modified_date_time 	TIMESTAMP,
					key_name                   	VARCHAR(50)     NOT NULL,
					value                   	VARCHAR(200)    NOT NULL,
					UNIQUE(key_name));

-- Table: sport_types
DROP TABLE IF EXISTS sport_types;
CREATE TABLE sport_types(id                  		INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time 	TIMESTAMP,
						name                    	VARCHAR(25)     NOT NULL,
						description             	VARCHAR(100),
						UNIQUE(name));

-- Table: team_contact_lnk
DROP TABLE IF EXISTS team_contact_lnk;
CREATE TABLE team_contact_lnk(id                  		INTEGER  PRIMARY KEY AUTO_INCREMENT,
							created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time 	TIMESTAMP,
							fk_team_id              	INTEGER  NOT NULL,
							FOREIGN KEY (fk_team_id) 	REFERENCES teams(id) ON DELETE CASCADE ON UPDATE CASCADE,
							fk_contact_id           	INTEGER  NOT NULL,
							FOREIGN KEY (fk_contact_id) REFERENCES contacts(id) ON DELETE CASCADE ON UPDATE CASCADE,
							UNIQUE(fk_team_id,fk_contact_id));

-- Table: team_cup_lnk
DROP TABLE IF EXISTS team_cup_lnk;
CREATE TABLE team_cup_lnk(id                  			INTEGER PRIMARY KEY AUTO_INCREMENT,
							created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time 	TIMESTAMP,
							fk_team_id              	INTEGER  NOT NULL,
							FOREIGN KEY (fk_team_id) 	REFERENCES teams(id) ON DELETE CASCADE ON UPDATE CASCADE,
							fk_cup_id        			INTEGER  NOT NULL,
							FOREIGN KEY (fk_cup_id) 	REFERENCES cups(id) ON DELETE CASCADE ON UPDATE CASCADE,
							UNIQUE(fk_team_id,fk_cup_id));
							
-- Table: team_tournament_lnk
DROP TABLE IF EXISTS team_tournament_lnk;
CREATE TABLE team_tournament_lnk(id                  	INTEGER  PRIMARY KEY AUTO_INCREMENT,
							created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time 	TIMESTAMP,
							fk_team_id              	INTEGER  NOT NULL,
							FOREIGN KEY (fk_team_id) REFERENCES teams(id) ON DELETE CASCADE ON UPDATE CASCADE,
							fk_tournament_id        	INTEGER  NOT NULL,
							FOREIGN KEY (fk_tournament_id) REFERENCES tournaments(id) ON DELETE CASCADE ON UPDATE CASCADE,
							UNIQUE(fk_team_id,fk_tournament_id));

-- Table: notifications
DROP TABLE IF EXISTS notifications;
CREATE TABLE notifications(id                  		INTEGER PRIMARY KEY AUTO_INCREMENT,
						created_date_time       	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time 	TIMESTAMP,
						sent_date               	VARCHAR(50) NOT NULL,
						recipients              	VARCHAR(50) NOT NULL,
						subject                 	VARCHAR(50) NOT NULL,
						message                 	VARCHAR(50) NOT NULL,
						status                  	INTEGER);

-- Table: task_statuses
DROP TABLE IF EXISTS task_statuses;
CREATE TABLE task_statuses(id            			INTEGER PRIMARY KEY AUTO_INCREMENT,
							created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time TIMESTAMP,
							name                    VARCHAR(25)     NOT NULL,
							type					VARCHAR(25),
							description             VARCHAR(100),
							UNIQUE(name));
								
-- Table: task_groups
DROP TABLE IF EXISTS task_groups;
CREATE TABLE task_groups(id            			INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						name                    VARCHAR(25)     NOT NULL,
						description             VARCHAR(100),
						UNIQUE(name));

-- Table: volunteer_tasks
DROP TABLE IF EXISTS volunteer_tasks;
CREATE TABLE volunteer_tasks(id                  				INTEGER PRIMARY KEY AUTO_INCREMENT,
						created_date_time       				TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time 				TIMESTAMP,
						start_date 								TIMESTAMP,
						end_date 								TIMESTAMP,
						deadline_date 							TIMESTAMP,
						fk_club_id								INTEGER,
						FOREIGN KEY (fk_club_id) 				REFERENCES clubs(id) ON DELETE CASCADE ON UPDATE CASCADE,
						fk_season_id			 				INTEGER,
						FOREIGN KEY (fk_season_id) 				REFERENCES seasons(id) ON DELETE CASCADE ON UPDATE CASCADE,
						fk_task_status_id        				INTEGER  NOT NULL DEFAULT 1,
						FOREIGN KEY (fk_task_status_id) 		REFERENCES task_statuses(id) ON DELETE CASCADE ON UPDATE CASCADE,
						fk_assigned_to_person_id				INTEGER,
						FOREIGN KEY (fk_assigned_to_person_id) 	REFERENCES contacts(id) ON DELETE CASCADE ON UPDATE CASCADE,
						name               						VARCHAR(50) NOT NULL,
						type              						VARCHAR(25),
						description                 			VARCHAR(100),
						UNIQUE(name));	
						
-- Table: sub_tasks
DROP TABLE IF EXISTS sub_tasks;
CREATE TABLE sub_tasks(id                  						INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       				TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time 				TIMESTAMP,
						start_date 								TIMESTAMP,
						end_date 								TIMESTAMP,
						deadline_date 							TIMESTAMP,
						fk_parent_task_id						INTEGER NOT NULL,
						FOREIGN KEY (fk_parent_task_id) 		REFERENCES volunteer_tasks(id) ON DELETE CASCADE ON UPDATE CASCADE,
						fk_task_status_id        				INTEGER  NOT NULL DEFAULT 1,
						FOREIGN KEY (fk_task_status_id) 		REFERENCES task_statuses(id) ON DELETE CASCADE ON UPDATE CASCADE,
						fk_assigned_to_person_id				INTEGER,
						FOREIGN KEY (fk_assigned_to_person_id) 	REFERENCES contacts(id) ON DELETE CASCADE ON UPDATE CASCADE,
						name               						VARCHAR(50) NOT NULL,
						type              						VARCHAR(25),
						description                 			VARCHAR(100),
						UNIQUE(name));		

-- Table: sub_task_task_group_lnk
DROP TABLE IF EXISTS sub_task_task_group_lnk;
CREATE TABLE sub_task_task_group_lnk(id    		              	INTEGER  PRIMARY KEY AUTO_INCREMENT,
							created_date_time       			TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							last_modified_date_time 			TIMESTAMP,
							fk_sub_task_id              		INTEGER  NOT NULL,
							FOREIGN KEY (fk_sub_task_id) 		REFERENCES sub_tasks(id) ON DELETE CASCADE ON UPDATE CASCADE,
							fk_task_group_id		        	INTEGER  NOT NULL,
							FOREIGN KEY (fk_task_group_id) 		REFERENCES task_groups(id) ON DELETE CASCADE ON UPDATE CASCADE,
							UNIQUE(fk_sub_task_id,fk_task_group_id));
								
-- Table: scheduled_tasks
DROP TABLE IF EXISTS scheduled_tasks;
CREATE TABLE scheduled_tasks(id                  				INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       				TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time 				TIMESTAMP,
						task_name								VARCHAR(50),
						task_type								VARCHAR(50),
						task_description						VARCHAR(100),
						task_enabled							INTEGER,
						cron_expression 						VARCHAR(50),
						team_id									INTEGER,
						UNIQUE(task_name,task_type));	
						
-- Table: scheduled_task_log
DROP TABLE IF EXISTS scheduled_task_log;
CREATE TABLE scheduled_task_log(id                  			INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time       				TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						team_id									INTEGER,
						task_name								VARCHAR(50),
						task_action								VARCHAR(50),
						log_msg									VARCHAR(500));

-- Table: followers
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
												
-- Table: image_details
DROP TABLE IF EXISTS image_details;
CREATE TABLE image_details(id            INTEGER  PRIMARY KEY AUTO_INCREMENT,
						created_date_time      	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
						last_modified_date_time TIMESTAMP,
						image_date_time			TIMESTAMP,
						image_name				VARCHAR(50),
						image_path				VARCHAR(100),
						image_mapped_absolute_path	VARCHAR(100),
						image_size_byte			INTEGER,
						image_geo_location		VARCHAR(100),
						image_description	VARCHAR(500),
						image_type	VARCHAR(25));

-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;
