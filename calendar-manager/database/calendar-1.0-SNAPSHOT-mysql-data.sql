------------------------------------------------------------------------------------------
-- DB name		 : Calendar DB
-- DB Component	 : Reference data
-- Release date	 : 09.06.2016 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
DELETE FROM users WHERE id > 0;
INSERT INTO `users` 
VALUES (1,now(),now(),'admin','$2a$13$GwPQNWAfenYSb06qxu/Nqevmwe31I4FJreraz34ScjbpAUBnO0S4y','gunnar_ronneberg@yahoo.no',1),
(2,now(),now(),'team','$2a$13$8mkWNrSUAJ6fWJTaV3uvY.3jldSleStDGqf3ONsYwmVcGhlPdhuhK','gunnar.ronneberg@gmail.com',1),
(3,now(),now(),'guest','$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi','',1);
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

/*!40000 ALTER TABLE `calendars` DISABLE KEYS */;
DELETE FROM calendars WHERE id > 0;
INSERT INTO `calendars` 
VALUES 
(1,now(),now(),1);
/*!40000 ALTER TABLE `calendars` ENABLE KEYS */;

/*!40000 ALTER TABLE `calendar_urls` DISABLE KEYS */;
DELETE FROM calendar_urls WHERE id > 0;
INSERT INTO `calendar_urls` 
VALUES 
(1,now(),now(),1,'Jenter 13 år 9-er 2.div avd 03', 'sagene', 'http://www.fotball.no/templates/portal/pages/GenerateICalendar.aspx?tournamentid=144524','icalendar','calendar events for j13 9-er football 2015', 1),
(2,now(),now(),1,'Gutter 12 år avd 13', 'lyn', 'http://www.fotball.no/templates/portal/pages/GenerateICalendar.aspx?teamId=8679','icalendar','calendar events for g12 football', 1),
(3,now(),now(),1,'Jenter 14 år 9er 2. div.', 'sagene', 'https://www.fotball.no/footballapi/Calendar/GetCalendar?tournamentId=148973','icalendar','calendar events for j14 9-er football 2016', 1);
/*!40000 ALTER TABLE `calendar_urls` ENABLE KEYS */;


