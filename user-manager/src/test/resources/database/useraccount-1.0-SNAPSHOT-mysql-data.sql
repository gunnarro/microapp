------------------------------------------------------------------------------------------
-- DB name		 : UserAccount DB
-- DB Component	 : Data
-- Release date	 : 01.04.2017 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 01.04.2017	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
DELETE FROM users WHERE id > 0;
INSERT INTO `users` 
VALUES (1,now(),now(),'admin','{bcrypt}$2a$13$ezAvKWTwpao4wzFNDVY0jOJ4TqVnhI6J6A.yrFuPMFRxx/Z4De86O','gunnar_ronneberg@yahoo.no',1),
(2,now(),now(),'team','{bcrypt}$2a$13$8mkWNrSUAJ6fWJTaV3uvY.3jldSleStDGqf3ONsYwmVcGhlPdhuhK','gunnar.ronneberg@gmail.com',1),
(3,now(),now(),'guest','{bcrypt}$2a$12$64D4XQ0/lXRTtvvplSMc/eYgKX1f.yYwVsCnjwtE44hyt37K3njXW','',1),
(4,now(),now(),'pepilie','{bcrypt}$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi','',1),
(5,now(),now(),'pappa','{bcrypt}$2a$12$93QA9IyI54.gTU2OjVA.QODwk8shVIBGwGTDde061WnuAAU2LM1Du','',1),
(6,now(),now(),'mamma','{bcrypt}$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi','',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
DELETE FROM roles WHERE id > 0;
INSERT INTO `roles`
VALUES 
(1,now(),now(),'ROLE_ADMIN'),
(2,now(),now(),'ROLE_USER'),
(3,now(),now(),'ROLE_GUEST'),
(4,now(),now(),'ROLE_ANONYMOUS');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

/*!40000 ALTER TABLE `user_role_lnk` DISABLE KEYS */;
DELETE FROM user_role_lnk WHERE id > 0;
INSERT INTO `user_role_lnk`
VALUES 
(1,now(),now(),1,1),
(2,now(),now(),2,2),
(3,now(),now(),3,3),
(4,now(),now(),4,2),
(5,now(),now(),5,2),
(6,now(),now(),6,2);
/*!40000 ALTER TABLE `user_role_lnk` ENABLE KEYS */;

/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
DELETE FROM permissions WHERE id > 0;
INSERT INTO `permissions`
VALUES 
(1,now(),now(),'READ_PRIVILEGE','read permission only for all services'),
(2,now(),now(),'WRITE_PRIVILEGE','write permission for all services'),
(3,now(),now(),'ALL_PRIVILEGE','both read and write permission for all services'),
(4,now(),now(),'BLOGG_READ_PRIVILEGE','read permission for blogg services'),
(5,now(),now(),'BLOGG_WRITE_PRIVILEGE','write permission for blogg services'),
(6,now(),now(),'ACCOUNT_READ_PRIVILEGE','read permission for user account services'),
(7,now(),now(),'ACCOUNT_WRITE_PRIVILEGE','write permission for user account services'),
(8,now(),now(),'DIET_READ_PRIVILEGE','read permission for user dietmanager services'),
(9,now(),now(),'DIET_WRITE_PRIVILEGE','write permission for dietmanager services');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;

/*!40000 ALTER TABLE `role_permission_lnk` DISABLE KEYS */;
DELETE FROM role_permission_lnk WHERE id > 0;
INSERT INTO `role_permission_lnk`
VALUES 
(1,now(),now(),1,1),
(2,now(),now(),1,2),
(3,now(),now(),1,4),
(4,now(),now(),1,5),
(5,now(),now(),1,6),
(6,now(),now(),1,7),
(7,now(),now(),2,1),
(8,now(),now(),2,2),
(9,now(),now(),2,4),
(10,now(),now(),2,5),
(11,now(),now(),3,1);
/*!40000 ALTER TABLE `role_permission_lnk` ENABLE KEYS */;

/*!40000 ALTER TABLE `profiles` DISABLE KEYS */;
DELETE FROM profiles WHERE id > 0;
INSERT INTO `profiles`
VALUES 
(1,now(),now(),5,'Gunnar', '', 'Ronneberg', 'gunnar_ronneberg@yahoo.no', '1966-4-11', 'M', 1),
(2,now(),now(),6,'Siri', 'Birgitte', 'Paulen', 'spaulen@ymail.com', '1966-05-19', 'F', 1),
(3,now(),now(),4,'Emilie', '','Paulen', '', '2002-01-22', 'F', 1);
/*!40000 ALTER TABLE `profiles` ENABLE KEYS */;

