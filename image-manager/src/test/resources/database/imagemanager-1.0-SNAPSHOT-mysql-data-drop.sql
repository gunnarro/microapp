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

DELETE FROM image_details WHERE id > 0;

-- drop settings
DELETE FROM users WHERE id > 0;
DELETE  FROM roles WHERE id > 0;
