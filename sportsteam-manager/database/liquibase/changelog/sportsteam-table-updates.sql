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
ALTER TABLE users
ADD email VARCHAR(200);

ALTER TABLE users
MODIFY COLUMN password VARCHAR(100);