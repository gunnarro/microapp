------------------------------------------------------------------------------------------
-- DB name		 : Calendar DB
-- DB Component	 : drop
-- Release date	 : 09.06.2016
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

-- clear all tables
DELETE FROM calendars WHERE id > 0;


-- drop settings
DELETE FROM users WHERE id > 0;
