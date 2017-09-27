------------------------------------------------------------------------------------------
-- DB name		 : SportsTeam DB
-- DB Component	 : drop
-- Release date	 : 07.09.2014 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

-- clear all lnk tables
DELETE FROM league_team_lnk WHERE id > 0;
DELETE FROM player_match_lnk WHERE id > 0;
DELETE FROM player_cup_lnk WHERE id > 0;
DELETE FROM player_contact_lnk WHERE id > 0;
DELETE FROM contact_role_type_lnk WHERE id > 0; 
DELETE FROM player_training_lnk WHERE id > 0;
DELETE FROM team_tournament_lnk WHERE id > 0;
DELETE FROM team_contact_lnk WHERE id > 0;
DELETE FROM cup_match_lnk WHERE id > 0;
DELETE FROM contact_sub_task_lnk WHERE id > 0;
DELETE FROM team_volunteer_task_lnk WHERE id > 0;


DELETE FROM clubs WHERE id > 0;
DELETE  FROM teams WHERE id > 0;
DELETE FROM players WHERE id > 0;
DELETE FROM contacts WHERE id > 0;
DELETE FROM referees WHERE id > 0;
DELETE FROM persons WHERE id > 0;
DELETE FROM matches WHERE id > 0;
DELETE FROM cups WHERE id > 0;
DELETE FROM trainings WHERE id > 0;
DELETE FROM addresses WHERE id > 0;
DELETE FROM sub_tasks WHERE id > 0;
DELETE FROM volunteer_tasks WHERE id > 0;
DELETE FROM scheduled_tasks WHERE id > 0;

-- drop settings
DELETE FROM users WHERE id > 0;
DELETE  FROM roles WHERE id > 0;
DELETE FROM player_statuses WHERE id > 0;
DELETE FROM contact_statuses WHERE id > 0;
DELETE FROM sport_types WHERE id > 0;
DELETE FROM league_statuses WHERE id > 0;
DELETE FROM league_rules WHERE id > 0;
DELETE FROM league_categories WHERE id > 0;
DELETE FROM leagues WHERE id > 0;
DELETE FROM match_status_types WHERE id > 0;
DELETE FROM team_role_types WHERE id > 0;
DELETE FROM match_event_types WHERE id > 0;
DELETE FROM match_types WHERE id > 0;
DELETE FROM seasons WHERE id > 0;
DELETE FROM player_position_types WHERE id > 0;
DELETE FROM settings WHERE id > 0;
DELETE FROM task_statuses WHERE id > 0;


			// Then clear all activities tables
			database.execSQL(createTableQuery(MatchEventsTable.TABLE_NAME, isDrop));
			database.execSQL(createTableQuery(MatchesTable.TABLE_NAME, isDrop));
			database.execSQL(createTableQuery(TrainingsTable.TABLE_NAME, isDrop));
			database.execSQL(createTableQuery(CupsTable.TABLE_NAME, isDrop));
			database.execSQL(createTableQuery(TournamentsTable.TABLE_NAME, isDrop));
			// Then clear all persons
			database.execSQL(createTableQuery(AddressTable.TABLE_NAME, isDrop));
			database.execSQL(createTableQuery(RefereesTable.TABLE_NAME, isDrop));
			database.execSQL(createTableQuery(ContactsTable.TABLE_NAME, isDrop));
			database.execSQL(createTableQuery(PlayersTable.TABLE_NAME, isDrop));
			// Finally, clear team and club tables
			database.execSQL(createTableQuery(TeamsTable.TABLE_NAME, isDrop));
			database.execSQL(createTableQuery(ClubsTable.TABLE_NAME, isDrop));
