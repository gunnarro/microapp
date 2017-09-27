------------------------------------------------------------------------------------------
-- DB name		 : SportsTeam DB
-- DB Component	 : Reference data
-- Release date	 : 07.09.2014 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
DELETE FROM users WHERE id > 0;
INSERT INTO `users` 
VALUES (1,now(),now(),'admin','$2a$13$GwPQNWAfenYSb06qxu/Nqevmwe31I4FJreraz34ScjbpAUBnO0S4y','gunnar_ronneberg@yahoo.no',1),--uiL2oo3
(2,now(),now(),'team','$2a$13$8mkWNrSUAJ6fWJTaV3uvY.3jldSleStDGqf3ONsYwmVcGhlPdhuhK','gunnar.ronneberg@gmail.com',1),--2003
(3,now(),now(),'guest','$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi','',1);--guest
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
DELETE FROM roles WHERE id > 0;
INSERT INTO `roles` 
VALUES 
(1,now(),now(),'admin','ROLE_ADMIN'),
(2,now(),now(),'team','ROLE_USER'),
(3,now(),now(),'guest','ROLE_GUEST'),
(4,now(),now(),'anonymous','ROLE_ANONYMOUS');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `player_statuses` WRITE;
/*!40000 ALTER TABLE `player_statuses` DISABLE KEYS */;
DELETE FROM player_statuses WHERE id > 0;
INSERT INTO `player_statuses` 
VALUES 
(1,now(),now(),'ACTIVE'),
(2,now(),now(),'PASSIVE'),
(3,now(),now(),'INJURED'),
(4,now(),now(),'QUIT');
/*!40000 ALTER TABLE `player_statuses` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `contact_statuses` WRITE;
/*!40000 ALTER TABLE `contact_statuses` DISABLE KEYS */;
DELETE FROM contact_statuses WHERE id > 0;
INSERT INTO `contact_statuses` 
VALUES 
(1,now(),now(),'ACTIVE'),
(2,now(),now(),'PASSIVE'),
(3,now(),now(),'QUIT');
/*!40000 ALTER TABLE `contact_statuses` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `sport_types` WRITE;
/*!40000 ALTER TABLE `sport_types` DISABLE KEYS */;
DELETE FROM sport_types WHERE id > 0;
INSERT INTO `sport_types` 
VALUES (1,now(),now(),'SOCCER',''),
(2,now(),now(),'BANDY','');
/*!40000 ALTER TABLE `sport_types` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `league_statuses` WRITE;
/*!40000 ALTER TABLE `league_statuses` DISABLE KEYS */;
DELETE FROM league_statuses WHERE id > 0;
INSERT INTO `league_statuses` 
VALUES 
(1,now(),now(),'ACTIVE'),
(2,now(),now(),'TERMINATED');
/*!40000 ALTER TABLE `league_statuses` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `league_rules` WRITE;
/*!40000 ALTER TABLE `league_rules` DISABLE KEYS */;
DELETE FROM league_rules WHERE id > 0;
INSERT INTO `league_rules` 
VALUES
(1,now(),now(),'rule Knøtt',11,11,'male',25,0,7,0,0,0,0,0,0,0,0,0,0,0,2,1,0,'Spillerne må ikke ha fylt 11 år ved årsskiftet i inneværende sesong'),
(2,now(),now(),'rule Lillegutt',12,13,'male',25,0,7,0,0,0,0,0,0,0,0,0,0,0,2,1,0,'Spillerne må ikke ha fylt 13 år ved årsskiftet i inneværende sesong'),
(3,now(),now(),'rule Smågutt',14,15,'male',30,5,11,0,0,0,0,0,0,0,0,0,0,0,2,1,0,'Spillerne må ikke ha fylt 15 år ved årsskiftet i inneværende sesong'),
(4,now(),now(),'rule Gutt',16,17,'male',40,10,11,0,0,0,0,0,0,0,0,0,0,0,2,1,0,'Spillerne må ikke ha fylt 17 år ved årsskiftet i inneværende sesong'),
(5,now(),now(),'rule Junior',18,20,'male',45,10,11,0,0,0,0,0,0,0,0,0,0,0,2,1,0,'Spillerne må ikke ha fylt 20 år ved årsskiftet i inneværende sesong'),
(6,now(),now(),'rule Old boys',35,50,'male',30,5,11,0,0,0,0,0,0,0,0,0,0,0,2,1,0,null),
(7,now(),now(),'rule Veteran',50,70,'male',30,5,7,0,0,0,0,0,0,0,0,0,0,0,2,1,0,null),
(8,now(),now(),'rule Elite',18,36,'male',45,15,11,0,0,0,0,0,0,0,0,0,0,0,2,1,0,null),
(9,now(),now(),'rule 1.divisjon',18,36,'male',45,15,11,0,0,0,0,0,0,0,0,0,0,0,2,1,0,null),
(10,now(),now(),'rule 2.divisjon',18,36,'male',45,15,11,0,0,0,0,0,0,0,0,0,0,0,2,1,0,null),
(11,now(),now(),'rule 3.divisjon',18,36,'male',45,15,11,0,0,0,0,0,0,0,0,0,0,0,2,1,0,null),
(12,now(),now(),'rule Damer',18,36,'female',45,15,11,0,0,0,0,0,0,0,0,0,0,0,2,1,0,null);
/*!40000 ALTER TABLE `league_rules` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `league_categories` WRITE;
/*!40000 ALTER TABLE `league_categories` DISABLE KEYS */;
DELETE FROM league_categories WHERE id > 0;
INSERT INTO `league_categories` 
VALUES 
(1,now(),now(),null,2,1,1,'Knøtt'),
(2,now(),now(),null,2,2,1,'Lillegutt'),
(3,now(),now(),null,2,3,1,'Smågutt'),
(4,now(),now(),null,2,4,1,'Gutt'),
(5,now(),now(),null,2,5,1,'Junior'),
(6,now(),now(),null,2,6,1,'Old boys'),
(7,now(),now(),null,2,7,1,'Veteran'),
(8,now(),now(),null,2,8,1,'Elite'),
(9,now(),now(),null,2,9,1,'1.divisjon'),
(10,now(),now(),null,2,10,1,'2.divisjon'),
(11,now(),now(),null,2,11,1,'3.divisjon'),
(12,now(),now(),null,2,12,1,'Damer');
/*!40000 ALTER TABLE `league_categories` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `leagues` WRITE;
/*!40000 ALTER TABLE `leagues` DISABLE KEYS */;
DELETE FROM leagues WHERE id > 0;
INSERT INTO `leagues` 
VALUES 
(1,now(),now(),null,9,1,'1.divisjon'), 
(2,now(),now(),null,10,1,'2.divisjon'),
(3,now(),now(),null,11,1,'3.divisjon'), 
(4,now(),now(),null,12,1,'Damer'), 
(5,now(),now(),null,8,1,'Elite'), 
(6,now(),now(),null,4,1,'Gutt NM elite'), 
(7,now(),now(),null,4,1,'Gutt NM kval'), 
(8,now(),now(),null,4,1,'Gutt krets Oslo'), 
(9,now(),now(),null,5,1,'Junior NM elite'), 
(10,now(),now(),null,5,1,'Junior NM kval'), 
(11,now(),now(),null,1,1,'Knøtt Akershus 04'), 
(12,now(),now(),null,1,1,'Knøtt Akershus 05'),
(13,now(),now(),null,1,1,'Knøtt Akershus 06 A'),
(14,now(),now(),null,1,1,'Knøtt Akershus 06 B'),
(32,now(),now(),null,1,2,'Knøtt Oslo 03'),
(15,now(),now(),null,1,1,'Knøtt Oslo 04'),
(16,now(),now(),null,1,1,'Knøtt Oslo 05'),
(17,now(),now(),null,1,1,'Knøtt Oslo 06 A'),
(18,now(),now(),null,1,1,'Knøtt Oslo 06 B'),
(19,now(),now(),null,2,1,'Lillegutt Akershus 02'), 
(20,now(),now(),null,2,1,'Lillegutt Akershus 03'), 
(21,now(),now(),null,2,1,'Lillegutt Oslo 02'),
(22,now(),now(),null,2,1,'Lillegutt Oslo 03'),
(23,now(),now(),null,6,1,'Old boys'),
(24,now(),now(),null,3,1,'Smågutt NM seedet'), 
(25,now(),now(),null,3,1,'Smågutt NM useedet A'),
(26,now(),now(),null,3,1,'Smågutt NM useedet B'),
(27,now(),now(),null,3,1,'Smågutt NM useedet C'),
(28,now(),now(),null,3,1,'Smågutt krets Akershus 00'), 
(29,now(),now(),null,3,1,'Smågutt krets Buskerud 01'),
(30,now(),now(),null,3,1,'Smågutt krets Oslo 01'),
(31,now(),now(),null,7,1,'Veteran');
/*!40000 ALTER TABLE `leagues` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `match_status_types` WRITE;
/*!40000 ALTER TABLE `match_status_types` DISABLE KEYS */;
DELETE FROM match_status_types WHERE id > 0;
INSERT INTO `match_status_types` 
VALUES 
(1,now(),now(),'NOT PLAYED',''),
(2,now(),now(),'PLAYED',''),
(3,now(),now(),'CANCELLED',''),
(4,now(),now(),'POSTPONED',''),
(5,now(),now(),'ONGOING','');
/*!40000 ALTER TABLE `match_status_types` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `team_role_types` WRITE;
/*!40000 ALTER TABLE `team_role_types` DISABLE KEYS */;
DELETE FROM team_role_types WHERE id > 0;
INSERT INTO `team_role_types` 
VALUES 
(1,now(),now(),'DEFAULT',''),
(2,now(),now(),'PARENT',''),
(3,now(),now(),'TEAMLEAD',''),
(4,now(),now(),'COACH',''),
(5,now(),now(),'CHAIRMAN',''),
(6,now(),now(),'DEPUTY CHAIRMAN',''),
(7,now(),now(),'BOARD MEMBER','');
/*!40000 ALTER TABLE `team_role_types` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `match_event_types` WRITE;
/*!40000 ALTER TABLE `match_event_types` DISABLE KEYS */;
DELETE FROM match_event_types WHERE id > 0;
INSERT INTO `match_event_types` 
VALUES 
(1,now(),now(),'GOAL',''),
(2,now(),now(),'PENALTY',''),
(3,now(),now(),'FREE KICK','');
/*!40000 ALTER TABLE `match_event_types` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `match_types` WRITE;
/*!40000 ALTER TABLE `match_types` DISABLE KEYS */;
DELETE FROM match_types WHERE id > 0;
INSERT INTO `match_types` 
VALUES 
(1,now(),now(),'LEAGUE',''),
(2,now(),now(),'TRAINING',''),
(3,now(),now(),'CUP',''),
(4,now(),now(),'TOURNAMENT','');
/*!40000 ALTER TABLE `match_types` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `seasons` WRITE;
/*!40000 ALTER TABLE `seasons` DISABLE KEYS */;
DELETE FROM seasons WHERE id > 0;
INSERT INTO `seasons` 
VALUES 
(1,now(),now(),'2013/2014','2013-10-01','2014-03-01'),
(2,now(),now(),'2014/2015','2014-10-01','2015-03-01'),
(3,now(),now(),'2015/2016','2015-10-01','2016-03-01'),
(4,now(),now(),'2016/2017','2016-10-01','2017-03-01');
/*!40000 ALTER TABLE `seasons` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `player_position_types` WRITE;
/*!40000 ALTER TABLE `player_position_types` DISABLE KEYS */;
DELETE FROM player_position_types WHERE id > 0;
INSERT INTO `player_position_types` 
VALUES 
(1,now(),now(),'GOALKEEPER',''),
(2,now(),now(),'DEFENDER',''),
(3,now(),now(),'MIDFIELDER',''),
(4,now(),now(),'FORWARD','');
/*!40000 ALTER TABLE `player_position_types` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
DELETE FROM settings WHERE id > 0;
INSERT INTO `settings` 
VALUES 
(1,now(),now(),'data_file_url_club','https://raw.github.com/gunnarro/bandy/master/assets/uil.xml'),
(2,now(),now(),'data_file_url_team','https://raw.github.com/gunnarro/bandy/master/assets/team-2003.xml'),
(3,now(),now(),'data_file_last_updated','0'),
(4,now(),now(),'data_file_version','0.1'),
(5,now(),now(),'mail_account','na'),
(6,now(),now(),'mail_account_pwd','na');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `task_statuses` WRITE;
/*!40000 ALTER TABLE `task_statuses` DISABLE KEYS */;
DELETE FROM task_statuses WHERE id > 0;
INSERT INTO `task_statuses` 
VALUES 
(1,now(),now(),'OPEN',null,null),
(2,now(),now(),'IN PROGRESS',null,null),
(3,now(),now(),'FINISHED',null,null),
(4,now(),now(),'CLOSED',null,null),
(5,now(),now(),'CANCELLED',null,null);
/*!40000 ALTER TABLE `task_statuses` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `scheduled_tasks` WRITE;
/*!40000 ALTER TABLE `scheduled_tasks` DISABLE KEYS */;
DELETE FROM scheduled_tasks WHERE id > 0;
INSERT INTO `scheduled_tasks` 
VALUES 
(1,now(),now(),'UIL 2003','UPCOMING_ACTIVITIES','task description',1,'* * 8 * * 0',155);
/*!40000 ALTER TABLE `scheduled_tasks` ENABLE KEYS */;
UNLOCK TABLES;

/*!40000 ALTER TABLE `image_details` DISABLE KEYS */;
DELETE FROM image_details WHERE id > 0;
INSERT INTO `image_details` 
VALUES 
(1,now(),now(),now(),'20160318_210015.jpg', '/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Kveldsmat, GoMorgen Youghurt byttet ut med 5 fiskekaker', 'type'),
(2,now(),now(),now(),'20160326_174115.jpg', '/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Fiskekaker, fullkorns pasta, tomat og agurk', 'type'),
(3,now(),now(),now(),'20160331_164959.jpg', '/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Kylling, ris, tomat, agurk og rømme', 'type'),
(4,now(),now(),now(),'20160409_174350.jpg', '/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Fiskeboller, fullkorns pasta, brokoli og karry saus', 'type'),
(5,now(),now(),now(),'20160413_163939.jpg', '/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Kylling, fullkorns pasta, tomat, agurk og rømme', 'type'),
(6,now(),now(),now(),'20160428_165753.jpg', '/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, ferdigrett Chilli Con Carne, tilsatt mais', 'type'),
(7,now(),now(),now(),'20160429_171607.jpg', '/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Torsk, poteter, ratatouille', 'type'),
(8,now(),now(),now(),'20160501_173331.jpg', '/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Fiskekaker, ris, tomat, agurk og ketchup', 'type');
/*!40000 ALTER TABLE `image_details` ENABLE KEYS */;
UNLOCK TABLES;
