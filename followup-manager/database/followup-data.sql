-- ----------------------------------------------------------------------------------------
-- DB name		 : followup DB
-- DB Component	 : Reference data
-- Release date	 : 16.06.2019 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
-- ----------------------------------------------------------------------------------------
-- 16.06.2019	gunnarro 		Initial version 1.0-SNAPSHOT

-- ----------------------------------------------------------------------------------------
-- SHOW databases;
-- SHOW GRANTS FOR 'web'@'localhost';
-- GRANT ALL PRIVILEGES ON followup.* TO 'web'@'localhost';
-- UPDATE mysql.user SET password=PASSWORd('change-me') WHERE user='root';
-- UPDATE users SET password='change-me' WHERE username='pappa';
-- DROP USER 'user'@'localhost'

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
DELETE FROM users WHERE id > 0;
INSERT INTO `users` 
VALUES (1,now(),now(),'admin','{bcrypt}$2a$13$GwPQNWAfenYSb06qxu/Nqevmwe31I4FJreraz34ScjbpAUBnO0S4y','gunnar_ronneberg@yahoo.no',1),
(2,now(),now(),'team','{bcrypt}$2a$13$8mkWNrSUAJ6fWJTaV3uvY.3jldSleStDGqf3ONsYwmVcGhlPdhuhK','gunnar.ronneberg@gmail.com',1),
(3,now(),now(),'guest','{bcrypt}$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi','',1),
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

/*!40000 ALTER TABLE `user_follower_lnk` DISABLE KEYS */;
DELETE FROM user_follower_lnk WHERE id > 0;
-- INSERT INTO `user_follower_lnk`
-- VALUES 
-- (1,now(),now(),5,4),
-- (2,now(),now(),5,6),
-- (3,now(),now(),6,4),
-- (4,now(),now(),6,5);
/*!40000 ALTER TABLE `user_follower_lnk` ENABLE KEYS */;


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

/*!40000 ALTER TABLE `children` DISABLE KEYS */;
DELETE FROM children WHERE id > 0;
INSERT INTO `children` 
VALUES 
(1,now(),now(),'emilie', null, 'paulen', '12345678', 'emilie@mail.no'),
(2,now(),now(),'andreas', null, 'paulen','87654321','andreas@mail.no');
/*!40000 ALTER TABLE `children` ENABLE KEYS */;

/*!40000 ALTER TABLE `parents` DISABLE KEYS */;
DELETE FROM parents WHERE id > 0;
INSERT INTO `parents` 
VALUES 
(1,now(),now(),'gunnar', null, 'rønneberg', '45465500', 'gunnar_ronneberg@yahoo.no'),
(2,now(),now(),'siri', 'birgitte', 'paulen','87654321','spaulen@ymail.no');
/*!40000 ALTER TABLE `parents` ENABLE KEYS */;

/*!40000 ALTER TABLE `event_log` DISABLE KEYS */;
DELETE FROM activities WHERE id > 0;
INSERT INTO `activities` 
VALUES 
(1,now(),now(), 'FOTBALL', 'TRAINING', ''),
(2,now(),now(), 'SCOOT', 'TRAINING', ''),
(3,now(),now(), 'GYM', 'TRAINING', ''),
(4,now(),now(), 'WALKING', 'TRAINING', ''),
(5,now(),now(), 'SCOOT', 'TRAINING', ''),
(6,now(),now(), 'HOME WORK', 'SCHOOL', ''),
(7,now(),now(), 'FRIENDS', 'SOCIAL', '' ),
(8,now(),now(), 'GAMING', 'INTERNET', '' ),
(9,now(),now(), 'CHAT', 'INTERNET', '' ),
(10,now(),now(), 'YOUTUBE', 'INTERNET', '' ),
(11,now(),now(), 'SOCIAL NETWORK', 'INTERNET', ''),
(12,now(),now(), 'VIDEO', 'INTERNET', '' );
/*!40000 ALTER TABLE `event_log` ENABLE KEYS */;

