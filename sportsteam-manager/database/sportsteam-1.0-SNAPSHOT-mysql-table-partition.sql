------------------------------------------------------------------------------------------
-- DB name		 : SportsTeam DB
-- DB Component	 : Tables Partition
-- Release date	 : 04.03.2015 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 05.03.2015	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------
-- Can not use partitions on tables with foriegn keys!
matches
cups
trainings
tournaments
volunteer_tasks

ALTER TABLE matches
    PARTITION BY KEY(fk_season_id)
    PARTITIONS 8;
 
ALTER TABLE matches
    PARTITION BY RANGE (fk_season_id) (
    PARTITION p0 VALUES LESS THAN (1),
    PARTITION p1 VALUES LESS THAN (2),
    PARTITION p2 VALUES LESS THAN (3);