------------------------------------------------------------------------------------------
-- DB name		 : Dietmanager DB
-- DB Component	 : Reference data
-- Release date	 : 29.07.2016 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 29.07.2016	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
DELETE FROM users WHERE id > 0;
INSERT INTO `users` 
VALUES (1,now(),now(),'admin','$2a$13$GwPQNWAfenYSb06qxu/Nqevmwe31I4FJreraz34ScjbpAUBnO0S4y','gunnar_ronneberg@yahoo.no',1),
(2,now(),now(),'team','$2a$13$8mkWNrSUAJ6fWJTaV3uvY.3jldSleStDGqf3ONsYwmVcGhlPdhuhK','gunnar.ronneberg@gmail.com',1),
(3,now(),now(),'guest','$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi','',1),
(4,now(),now(),'pepilie','$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi','',1),
(5,now(),now(),'pappa','$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi','',1),
(6,now(),now(),'mamma','$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi','',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
DELETE FROM roles WHERE id > 0;
INSERT INTO `roles` 
VALUES 
(1,now(),now(),'admin','ROLE_ADMIN'),
(2,now(),now(),'team','ROLE_USER'),
(3,now(),now(),'guest','ROLE_GUEST'),
(4,now(),now(),'anonymous','ROLE_ANONYMOUS');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
