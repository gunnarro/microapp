------------------------------------------------------------------------------------------
-- DB name		 : UserAccount DB
-- DB Component	 : Drop table and data
-- Release date	 : 01.04.2017 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 01.04.2017	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

-- clear all tables

-- drop settings
DELETE FROM users WHERE id > 0;
