------------------------------------------------------------------------------------------
-- DB name		 : SportsTeam DB
-- DB Component	 : Tables
-- Release date	 : 07.09.2014 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

-- Turn off fk check
SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE calendar_urls
ADD COLUMN enabled INTEGER default 1

-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;
