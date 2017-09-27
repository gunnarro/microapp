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


/*!40000 ALTER TABLE `image_details` DISABLE KEYS */;
DELETE FROM image_details WHERE id > 0;
INSERT INTO `image_details` 
VALUES 
(1,now(),now(),now(),3,'20160318_210015.jpg','title','/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Kveldsmat, GoMorgen Youghurt byttet ut med 5 fiskekaker', 'type'),
(2,now(),now(),now(),3,'20160326_174115.jpg','title','/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Fiskekaker, fullkorns pasta, tomat og agurk', 'type'),
(3,now(),now(),now(),3,'20160331_164959.jpg','title','/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Kylling, ris, tomat, agurk og rømme', 'type'),
(4,now(),now(),now(),3,'20160409_174350.jpg','title','/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Fiskeboller, fullkorns pasta, brokoli og karry saus', 'type'),
(5,now(),now(),now(),3,'20160413_163939.jpg','title','/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Kylling, fullkorns pasta, tomat, agurk og rømme', 'type'),
(6,now(),now(),now(),3,'20160428_165753.jpg','title','/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, ferdigrett Chilli Con Carne, tilsatt mais', 'type'),
(7,now(),now(),now(),3,'20160429_171607.jpg','title','/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Torsk, poteter, ratatouille', 'type'),
(8,now(),now(),now(),3,'20160501_173331.jpg','title','/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads' ,'/web/static/gallery/', 0, 'geo location', 'Middag, Fiskekaker, ris, tomat, agurk og ketchup', 'type');
/*!40000 ALTER TABLE `image_details` ENABLE KEYS */;
