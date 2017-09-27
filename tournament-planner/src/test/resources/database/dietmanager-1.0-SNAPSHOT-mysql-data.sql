------------------------------------------------------------------------------------------
-- DB name		 : Dietmanager DB
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

/*!40000 ALTER TABLE `diet_plans` DISABLE KEYS */;
DELETE FROM diet_plans WHERE id > 0;
INSERT INTO `diet_plans` 
VALUES 
(1,now(),now(),now(),now(),'EP Diet Plan','Kostplan fra uke 16 til x', 1);
/*!40000 ALTER TABLE `diet_plans` ENABLE KEYS */;

/*!40000 ALTER TABLE `diet_plan_meals` DISABLE KEYS */;
DELETE FROM diet_plan_meals WHERE id > 0;
INSERT INTO `diet_plan_meals` 
VALUES 
(1,now(),now(),1,now(),now(),'Frokost','(0700 - 0800, helg 0900 -1030)', ''),
(2,now(),now(),1,now(),now(),'Lunsj','(1130 - 1200, helg 1300 - 1330)', ''),
(3,now(),now(),1,now(),now(),'Mellom måltid','(1500 - 1530)', ''),
(4,now(),now(),1,now(),now(),'Middag','(1630 - 1700, helg 1700 - 1730)', ''),
(5,now(),now(),1,now(),now(),'Kveldsmat','(2030 - 2100, helg 2100 - 2130)', ''),
(6,now(),now(),1,now(),now(),'Regler','Regler og tillegg for alle måltider', '');
/*!40000 ALTER TABLE `diet_plan_meals` ENABLE KEYS */;

/*!40000 ALTER TABLE `diet_plan_meal_items` DISABLE KEYS */;
DELETE FROM diet_plan_meal_items WHERE id > 0;
INSERT INTO `diet_plan_meal_items` 
VALUES 
(1,now(),now(),1,'Frokost','3 brødskiver med margarin og pålegg / Havregrøt(75g) på lettmelk med 1 ts sukker / Havrefras(70g) 3dl lettmelk pluss ett egg'),
(2,now(),now(),1,'Frokost','1 glass lettmelk (2 dl)'),
(3,now(),now(),1,'Frokost','1 frukt'),
(4,now(),now(),1,'Frokost','125 ml Fresubin Energy'),
(5,now(),now(),1,'Frokost','1 multivitamin'),
(6,now(),now(),2,'Lunsj','3 brødskiver med margarin og pålegg / 2 brødskiver med margarin og 1 egg, pluss 1 yoghurt'),
(7,now(),now(),2,'Lunsj','1 glass lettmelk (2 dl)'),
(8,now(),now(),2,'Lunsj','1 frukt'),
(9,now(),now(),2,'Lunsj','125 ml Fresubin Energy'),
(10,now(),now(),3,'Mellom måltid','2 Bixit, 60g nøtteblanding, 6 riskjeks eller 125 ml Fresubin'),
(11,now(),now(),4,'Middag','1 porsjon middag (varier mellom kjøtt og fisk)'),
(12,now(),now(),4,'Middag','1 dessert eller 1 yoghurt (Kan erstattes med 1/3 ekstra middag)'),
(13,now(),now(),4,'Middag','1 juice (2 dl)'),
(14,now(),now(),4,'Middag','125 ml Fresubin Energy'),
(15,now(),now(),5,'Kveldsmat','1 brødskive med margarin og pålegg, pluss 1 Go-Morgen yoghurt'),
(16,now(),now(),5,'Kveldsmat','1 Go-Morgen yoghurt'),
(17,now(),now(),5,'Kveldsmat','1 glass lettmelk (2 dl)'),
(18,now(),now(),5,'Kveldsmat','125 ml Fresubin Energy'),
(19,now(),now(),6,'Regler og tillegg','500 ml vann/saft hver dag'),
(20,now(),now(),6,'Regler og tillegg','Ikke lett produkter'),
(21,now(),now(),6,'Regler og tillegg','Spisetid 30 minutter, det som eventuelt blir igjen skal da erstattes med Fresubin'),
(22,now(),now(),6,'Regler og tillegg','Frokost: 350 ml Fresubin kan erstatte hele måltidet (200 ml innehlder 300 Kcal)'),
(23,now(),now(),6,'Regler og tillegg','Lunsj: 450 ml Fresubin kan erstatte hele måltidet'),
(24,now(),now(),6,'Regler og tillegg','Middag: 500 ml Fresubin kan erstatte hele måltidet'),
(25,now(),now(),6,'Regler og tillegg','Kveldsmat: 350 ml Fresubin kan erstatte hele måltidet');
/*!40000 ALTER TABLE `diet_plan_meal_items` ENABLE KEYS */;

/*!40000 ALTER TABLE `diet_products` DISABLE KEYS */;
DELETE FROM diet_products WHERE id > 0;
INSERT INTO `diet_products` 
VALUES 
(1,now(),now(),'Yoghurt', 'desert', 'Porsjoner som tilsvarer 1 frukt yoghurt'),
(2,now(),now(),'GO-Morgen Yoghurt', 'desert', 'Porsjoner som tilsvarer 1 go-morgen yoghurt'),
(3,now(),now(),'3 Brødskiver', 'frokost', 'Porsjoner som tilsvarer 3 brødskiver med margarin og pålegg');
/*!40000 ALTER TABLE `diet_products` ENABLE KEYS */;

/*!40000 ALTER TABLE `diet_product_equivalent_items` DISABLE KEYS */;
INSERT INTO `diet_product_equivalent_items` 
VALUES 
(1,now(),now(),1,'brødskive','1 brødskive med pålegg'),
(2,now(),now(),1,'knekkebrød','2 knekkebrød med pålegg'),
(3,now(),now(),1,'sjokolademelk','2 dl OBoy med lett melk'),
(4,now(),now(),1,'middag','1/3 middagsprosjon'),
(5,now(),now(),1,'vaffel','1/2 vaffel med syltetøy'),
(6,now(),now(),1,'is','1,5 dl fløteis'),
(7,now(),now(),1,'bar','1 byggrynslunsj'),
(8,now(),now(),1,'bar','1 rislunsj'),
(9,now(),now(),1,'melk','3 dl lettmelk'),
(10,now(),now(),1,'milsibar','1 Wasa muslibar'),
(11,now(),now(),2,'nøttebalnding','1 pakke småsulten nøtte - og frukt blanding'),
(12,now(),now(),2,'YT','1 YT bar + 5 dl YT restitusjonsdrikk'),
(13,now(),now(),2,'iskaffe','1 iskaffe Energi (Tine)'),
(14,now(),now(),2,'brødskive','2 brødskiver med margarin og pålegg'),
(15,now(),now(),2,'rundstykke','1 rundstykke med margarin og pålegg'),
(16,now(),now(),2,'baguett','1 medium baguett med margaring og pålegg på begge halvdelene'),
(17,now(),now(),2,'sjokolademelk','5 dl sjokolademelk (ikke lett varianten)'),
(18,now(),now(),3,'3 brødskiver','1 Smoothie, pluss 1/2 rundstykke med margarin og pålegg'),
(19,now(),now(),3,'3 brødskiver','Omelett, pluss 1/2 rundstykke med margarin og pålegg');
/*!40000 ALTER TABLE `diet_product_equivalent_items` ENABLE KEYS */;

/*!40000 ALTER TABLE `diet_menus` DISABLE KEYS */;
DELETE FROM diet_menus WHERE id > 0;
INSERT INTO `diet_menus` 
VALUES 
(1,now(),now(),'Menu','Meny', 1);
/*!40000 ALTER TABLE `diet_menus` ENABLE KEYS */;

/*!40000 ALTER TABLE `diet_menu_items` DISABLE KEYS */;
DELETE FROM diet_menu_items WHERE id > 0;
INSERT INTO `diet_menu_items` 
VALUES 
(1,now(),now(),1,'Middag','Hvit fisk med poteter, brokkoli og smør/smeltet smør med persille/saus'),
(2,now(),now(),1,'Middag','Kylling/biff i pitabrød/tortillalefse med salat og rømme dressing'),
(3,now(),now(),1,'Middag','Kylling wok med ris og wok grønnsaksblanding'),
(4,now(),now(),1,'Middag','Laks/ørret med agurksalat og poteter og rømme tilbehør'),
(5,now(),now(),1,'Middag','Fiskeboller med poteter og gulrøtter og hvis saus'),
(6,now(),now(),1,'Middag','Kjøttkaker med poteter/ris, tyttebær, grønnsaksblanding og brun saus'),
(7,now(),now(),1,'Middag','Pasta med kjøttsaus'),
(8,now(),now(),1,'Middag','Tikka Masala med ris'),
(9,now(),now(),1,'Middag','Risotto'),
(10,now(),now(),1,'Middag','Bacalao, ferdiglaget 450g'),
(11,now(),now(),1,'Middag','Chili Con Carne, ferdiglaget 400g'),
(12,now(),now(),1,'Middag','Amerikansk blanding med kjøttdeig'),
(13,now(),now(),1,'Middag','Meksikansk blanding med kjøttdeig'),
(14,now(),now(),1,'Middag','Fiskesuppe med hvit fisk/ørrett/lask'),
(15,now(),now(),1,'Middag','Pølsegryte med røkt kjøttpølse'),
(16,now(),now(),1,'Middag','Hjemmelagd pizza'),
(17,now(),now(),1,'Middag','Svinekotteletter med poteter/ris og mais, pluss saus'),
(18,now(),now(),1,'Middag','Biff med poteter/ris/pommes frites og mais, pluss pepper/bernaise saus'),  
(19,now(),now(),1,'Middag Tilbehør','Poteter min. 150g'),
(20,now(),now(),1,'Middag Tilbehør','Ris min. 100g kokt'),
(21,now(),now(),1,'Middag Tilbehør','Pasta min 100g, ukokt'),
(22,now(),now(),1,'Middag Tilbehør','Potetmos min 100g'),
(23,now(),now(),1,'Middag Tilbehør','Pommes Frites min 100g'),
(24,now(),now(),1,'Middag Tilbehør','Alle middager skal ha saus. Erstatning til saus kan være rømme eller ketchup'),
(25,now(),now(),1,'Middag Porsjon','Fisk: Torsk 200g, Sei 200g, fiskeboller 200g, fiskekaker 200g, Ørret 150g, laks 125g'),
(26,now(),now(),1,'Middag Porsjon','Kjøtt: Biff 150g, Svin 150g, kylling 150g, kjøttdeig 150g'),
(27,now(),now(),1,'Middag Dessert','Frukt yoghurt'),
(28,now(),now(),1,'Middag Dessert','Fløte is-pinne'),
(29,now(),now(),1,'Middag Dessert','Nøtteblading 45 g (Tutti Frutti)'),
(30,now(),now(),1,'Middag Dessert','Riskaker 6 stykker (FRIGGS HAMPAFRØ)'),
(31,now(),now(),1,'Middag Dessert','1 Bixit'),
(32,now(),now(),1,'Middag Dessert','Kokosboller, 3 små elle 1 stor'),
(33,now(),now(),1,'Middag Dessert','1 Brownies'),
(34,now(),now(),1,'Middag Dessert','Annet, valgt noe tilsvarende'),
(35,now(),now(),1,'Frokost','Havregrøt'),
(36,now(),now(),1,'Frokost','Frokostblanding'),
(37,now(),now(),1,'Frokost','3 Brødskiver'),
(38,now(),now(),1,'Lunsj','3 Brødskiver'),
(39,now(),now(),1,'Lunsj','Annet, valgt noe tilsvarende'),
(40,now(),now(),1,'Kveldsmat','1 Brødskive og GO-Morgen Yougurt'),
(41,now(),now(),1,'Kveldsmat','1 Brødskive og Smoothie'),
(42,now(),now(),1,'Kveldsmat','1 Brødskive, 2 egg og nøtteblanding 30g'),
(43,now(),now(),1,'Kveldsmat','2 Brødskiver og nøtteblanding 30g'),
(44,now(),now(),1,'Kveldsmat','Annet, valgt noe tilsvarende');
/*!40000 ALTER TABLE `diet_menu_items` ENABLE KEYS */;

/*!40000 ALTER TABLE `food_recipes` DISABLE KEYS */;
DELETE FROM food_recipes WHERE id > 0;
INSERT INTO `food_recipes` 
VALUES 
(1,now(),now(),'Smoothie', 'Oppskrift for Smoothie'),
(2,now(),now(),'Omelett', 'Oppskrift for Omelett');
/*!40000 ALTER TABLE `food_recipes` ENABLE KEYS */;


/*!40000 ALTER TABLE `food_recipe_items` DISABLE KEYS */;
DELETE FROM food_recipe_items WHERE id > 0;
INSERT INTO `food_recipe_items` 
VALUES 
(1,now(),now(),1,'Smoothie','2,5 dl lettmelk'), 
(2,now(),now(),1,'Smoothie','1,5 dl bær'),
(3,now(),now(),1,'Smoothie','1 ss Cottage Cheese'),
(4,now(),now(),1,'Smoothie','2 toppede ss havrekli'),
(5,now(),now(),1,'Smoothie','1 ss chiafrø'),
(6,now(),now(),2,'Omelett','3 egg'),
(7,now(),now(),2,'Omelett','3 ss lettmelk'),
(8,now(),now(),2,'Omelett','1 ss olivenolje'),
(9,now(),now(),2,'Omelett','2 skiver kokt skinke');
/*!40000 ALTER TABLE `food_recipe_items` ENABLE KEYS */;

