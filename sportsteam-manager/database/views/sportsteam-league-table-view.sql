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
-- DROP VIEW league_table_view
-- View: league_table_view
CREATE VIEW league_table_view AS
SELECT seasonId,leagueId,matchTypeId,teamName,
       SUM(numberOfWonMatches + numberOfDrawMatches + numberOfLossMatches) AS numberOfPlayedMatches,
       SUM(numberOfWonMatches) AS numberOfWonMatches,
       SUM(numberOfDrawMatches) AS numberOfDrawMatches,
       SUM(numberOfLossMatches) AS numberOfLossMatches,
       SUM(numberOfGoalsScored) AS numberOfGoalsScored,
       SUM(numberOfGoalsAgainst) AS numberOfGoalsAgainst,
       SUM(numberOfWonMatches*2 + numberOfDrawMatches*1) AS scores
 FROM match_result_view
 WHERE matchTypeId = 1
 GROUP BY teamName
 ORDER BY scores, teamName DESC
 