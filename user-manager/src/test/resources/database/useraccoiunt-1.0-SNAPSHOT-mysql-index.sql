------------------------------------------------------------------------------------------
-- DB name		 : UserAccount DB
-- DB Component	 : index
-- Release date	 : 01.04.2017 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 01.04.2017	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------
CREATE UNIQUE INDEX UserConnectionRank on UserConnection(userId, providerId, rank);
