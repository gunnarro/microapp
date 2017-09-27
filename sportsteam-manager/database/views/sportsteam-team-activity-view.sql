------------------------------------------------------------------------------------------
-- DB name		 : SportsTeam DB
-- DB Component	 : Views
-- Release date	 : 07.09.2014 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------
-- DROP VIEW team_activity_view
-- View: team_activity_view 	   
CREATE VIEW team_activity_view AS	   
	SELECT 'match' as type, m.id, m.fk_team_id, m.fk_season_id, m.start_date as start_date, CONCAT(m.home_team_name, " vs ", m.away_team_name) as info, m.venue as place FROM matches m
	UNION SELECT 'training', t.id, t.fk_team_id, t.fk_season_id, t.start_time, 'Training', t.place FROM trainings t
	UNION SELECT 'cup', c.id, 0, fk_season_id, c.start_date, c.cup_name, c.venue FROM cups c
