------------------------------------------------------------------------------------------
-- DB name		 : Dietmanager DB
-- DB Component	 : drop
-- Release date	 : 09.06.2016
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 31.07.2016	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

-- clear all tables
DELETE FROM event_log WHERE id > 0;


-- drop settings
DELETE FROM users WHERE id > 0;
DELETE FROM roles WHERE id > 0;
