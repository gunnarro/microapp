------------------------------------------------------------------------------------------
-- DB name		 : Image Manager DB
-- DB Component	 : Drop
-- Release date	 : 12.07.2016 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 12.07.2016	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

-- drop table data
DELETE FROM image_details WHERE id > 0;

-- drop settings
DELETE FROM users WHERE id > 0;
DELETE  FROM roles WHERE id > 0;
