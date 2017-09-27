-- ----------------------------------------------------------------------------------------
-- DB name		 : SportsTeam DB
-- DB Component	 : Views
-- Release date	 : 07.09.2014 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
-- ----------------------------------------------------------------------------------------
-- 21.08.2015	gunnarro 		Initial version 1.0-SNAPSHOT

-- ----------------------------------------------------------------------------------------
-- View: match_result_view_test
CREATE OR REPLACE VIEW match_result_view_test AS
SELECT m.id AS matchId, 
       m.fk_season_id AS seasonId,
	   m.fk_league_id AS leagueId,
	   m.fk_match_type_id AS matchTypeId,
	   m.home_team_name AS teamName,
	   sum(ifnull(m.goals_home_team,0) > ifnull(m.goals_away_team,0)) AS numberOfWonMatches,
	   sum(ifnull(m.goals_home_team,0) = ifnull(m.goals_away_team,-1)) AS numberOfDrawMatches,
	   sum(ifnull(m.goals_home_team,0) < ifnull(m.goals_away_team,0)) AS numberOfLossMatches,
	   sum(ifnull(m.goals_home_team,0)) AS numberOfGoalsScored,
	   sum(ifnull(m.goals_away_team,0)) AS numberOfGoalsAgainst
FROM matches m
WHERE m.fk_match_status_id > 0
GROUP BY m.fk_season_id,m.fk_league_id,m.fk_match_type_id,m.home_team_name
UNION
SELECT m.id AS matchId, 
	   m.fk_season_id AS seasonId,
       m.fk_league_id AS leagueId,
       m.fk_match_type_id AS matchTypeId,
       m.away_team_name AS teamName,
       sum(ifnull(m.goals_away_team,0) > ifnull(m.goals_home_team,0)) AS numberOfWonMatches,
       sum(ifnull(m.goals_away_team,0) = ifnull(m.goals_home_team,-1)) AS numberOfDrawMatches,
       sum(ifnull(m.goals_away_team,0) < ifnull(m.goals_home_team,0)) AS numberOfLossMatches,
       sum(ifnull(m.goals_away_team,0)) AS numberOfGoalsScored,
       sum(ifnull(m.goals_home_team,0)) AS numberOfGoalsAgainst
FROM matches m
WHERE m.fk_match_status_id > 0
GROUP BY m.fk_season_id,m.fk_league_id,m.fk_match_type_id,m.away_team_name
