-- phpMyAdmin SQL Dump
-- version 4.0.10.12
-- http://www.phpmyadmin.net
--
-- Vert: 127.9.127.2:3306
-- Generert den: 11. Sep, 2016 08:46 AM
-- Tjenerversjon: 5.5.50
-- PHP-Versjon: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dietmanager`
--

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `body_measurements_log`
--

CREATE TABLE IF NOT EXISTS `body_measurements_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `log_date` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_user_id` int(11) DEFAULT NULL,
  `weight` decimal(6,2) DEFAULT NULL,
  `height` decimal(6,2) DEFAULT NULL,
  `comment` varchar(200) DEFAULT NULL,
  `weight_metric` varchar(5) DEFAULT NULL,
  `height_metric` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`fk_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=37 ;

--
-- Dataark for tabell `body_measurements_log`
--

INSERT INTO `body_measurements_log` (`id`, `created_date_time`, `last_modified_date_time`, `log_date`, `fk_user_id`, `weight`, `height`, `comment`, `weight_metric`, `height_metric`) VALUES
(1, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-01-14 05:00:00', 4, '35.00', '158.00', '', 'kg', 'cm'),
(2, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-01-29 05:00:00', 4, '36.80', '158.00', '', 'kg', 'cm'),
(3, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-02-05 05:00:00', 4, '37.20', '158.00', '', 'kg', 'cm'),
(4, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-02-09 05:00:00', 4, '37.40', '158.00', '', 'kg', 'cm'),
(5, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-02-16 05:00:00', 4, '39.30', '158.00', '', 'kg', 'cm'),
(6, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-02-23 05:00:00', 4, '40.10', '158.00', '', 'kg', 'cm'),
(7, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-03-01 05:00:00', 4, '40.70', '158.00', '', 'kg', 'cm'),
(8, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-03-08 05:00:00', 4, '41.10', '158.00', '', 'kg', 'cm'),
(9, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-03-15 04:00:00', 4, '41.50', '158.00', '', 'kg', 'cm'),
(10, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-03-22 04:00:00', 4, '41.20', '158.00', '(påskeferie økt aktivitet)', 'kg', 'cm'),
(11, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-03-29 04:00:00', 4, '41.00', '158.00', '(påske ferie økt aktivitet - økt kostplan)', 'kg', 'cm'),
(12, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-04-05 04:00:00', 4, '42.40', '158.00', '(økt kostplan)', 'kg', 'cm'),
(13, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-04-12 04:00:00', 4, '42.60', '158.00', '', 'kg', 'cm'),
(14, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-04-19 04:00:00', 4, '42.60', '158.00', '', 'kg', 'cm'),
(15, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-04-26 04:00:00', 4, '42.80', '158.00', '', 'kg', 'cm'),
(16, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-05-03 04:00:00', 4, '43.10', '158.00', '', 'kg', 'cm'),
(17, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-05-10 04:00:00', 4, '43.50', '158.00', '(andreas 43,1)', 'kg', 'cm'),
(18, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-05-17 04:00:00', 4, '43.80', '158.00', '(andreas 43.0, veid onsdag 18.5)', 'kg', 'cm'),
(19, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-05-24 04:00:00', 4, '43.30', '158.00', '', 'kg', 'cm'),
(20, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-05-31 04:00:00', 4, '43.60', '158.00', '', 'kg', 'cm'),
(21, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-06-07 04:00:00', 4, '43.50', '158.20', '', 'kg', 'cm'),
(22, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-06-14 04:00:00', 4, '44.00', '158.50', '', 'kg', 'cm'),
(23, '2016-06-21 21:25:40', '2016-06-21 21:25:40', '2016-06-21 04:00:00', 4, '44.60', '158.50', '', 'kg', 'cm'),
(24, '2016-06-28 16:03:47', '2016-06-28 16:03:47', '2016-06-28 04:00:00', 4, '44.60', '159.00', '', 'kg', 'cm'),
(25, '2016-07-04 08:03:38', '2016-07-04 08:03:38', '2016-07-04 04:00:00', 4, '44.80', '159.00', '', 'kg', 'cm'),
(26, '2016-07-09 19:31:56', '2016-07-09 19:31:56', '2016-07-09 04:00:00', 4, '44.10', '159.00', 'Ekstra veiing grunnet reise', 'kg', 'cm'),
(27, '2016-07-12 19:20:14', '2016-07-12 19:20:14', '2016-07-12 04:00:00', 4, '44.60', '159.00', 'Veid rett etter lunsj', 'kg', 'cm'),
(28, '2016-07-19 11:53:37', '2016-07-19 11:53:37', '2016-07-19 04:00:00', 4, '44.60', '159.50', 'Veid rett før lunsj', 'kg', 'cm'),
(29, '2016-07-26 11:54:43', '2016-07-26 11:54:43', '2016-07-26 04:00:00', 4, '44.60', '159.50', 'Veid rett før lunsj', 'kg', 'cm'),
(30, '2016-08-02 07:11:27', '2016-08-02 07:11:27', '2016-08-02 04:00:00', 4, '44.60', '160.00', 'Veid før frokost', 'kg', 'cm'),
(31, '2016-08-04 21:38:51', '2016-08-04 21:38:51', '2016-08-04 04:00:00', 4, '44.70', '159.00', 'Ekstra veiing grunnet norway cup. Målt henne til 159 cm', 'kg', 'cm'),
(32, '2016-08-09 07:42:57', '2016-08-09 07:42:57', '2016-08-09 04:00:00', 4, '44.80', '159.50', '', 'kg', 'cm'),
(33, '2016-08-16 08:01:27', '2016-08-16 08:01:27', '2016-08-16 04:00:00', 4, '45.00', '160.00', 'Andreas veier nå 43,9 kg og er ca 153 cm', 'kg', 'cm'),
(34, '2016-08-23 06:03:42', '2016-08-23 06:03:42', '2016-08-23 04:00:00', 5, '45.10', '160.00', 'Andreas veide 43.5 kg', 'kg', 'cm'),
(35, '2016-08-30 06:12:45', '2016-08-30 06:12:45', '2016-08-30 04:00:00', 5, '45.30', '160.00', '', 'kg', 'cm'),
(36, '2016-09-06 06:29:48', '2016-09-06 06:29:48', '2016-09-06 04:00:00', 5, '45.10', '160.00', '', 'kg', 'cm');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `diet_menus`
--

CREATE TABLE IF NOT EXISTS `diet_menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `menu_name` varchar(100) DEFAULT NULL,
  `menu_description` varchar(500) DEFAULT NULL,
  `menu_active` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dataark for tabell `diet_menus`
--

INSERT INTO `diet_menus` (`id`, `created_date_time`, `last_modified_date_time`, `menu_name`, `menu_description`, `menu_active`) VALUES
(1, '2016-05-23 08:23:04', '2016-05-23 08:23:04', 'Menu', 'Middags menu', 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `diet_menu_items`
--

CREATE TABLE IF NOT EXISTS `diet_menu_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_diet_menu_id` int(11) DEFAULT NULL,
  `menu_item_name` varchar(100) DEFAULT NULL,
  `menu_item_description` varchar(500) DEFAULT NULL,
  `menu_item_img_link` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_diet_menu_id` (`fk_diet_menu_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=82 ;

--
-- Dataark for tabell `diet_menu_items`
--

INSERT INTO `diet_menu_items` (`id`, `created_date_time`, `last_modified_date_time`, `fk_diet_menu_id`, `menu_item_name`, `menu_item_description`, `menu_item_img_link`) VALUES
(1, '2016-05-26 07:05:09', '2016-06-03 15:08:00', 1, 'Middag', 'Torsk med poteter, brokkoli og smør/smeltet smør med persille/saus', NULL),
(2, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Kylling/biff i pitabrød/tortillalefse med salat og rømme dressing', NULL),
(3, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Kylling wok med ris og wok grønnsaksblanding', NULL),
(4, '2016-05-26 07:05:09', '2016-08-18 23:39:24', 1, 'Middag', 'Laks/ørret med agurksalat og poteter og rømme tilbehør', 'http://jbossews-gunnarro.rhcloud.com/image-manager/web/static/gallery/guest/20160524_163713.jpg'),
(5, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Fiskeboller med poteter og gulrøtter og hvis saus', NULL),
(6, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Kjøttkaker med poteter/ris, tyttebær, grønnsaksblanding og brun saus', NULL),
(7, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Pasta med kjøttsaus', NULL),
(8, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Tikka Masala med ris', NULL),
(9, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Risotto', NULL),
(10, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Bacalao, ferdiglaget 450g', NULL),
(11, '2016-05-26 07:05:09', '2016-08-18 23:33:30', 1, 'Middag', 'Chili Con Carne, ferdiglaget 400g', 'http://jbossews-gunnarro.rhcloud.com/image-manager/web/static/gallery/guest/20160428_165753.jpg'),
(12, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Amerikansk blanding med kjøttdeig', NULL),
(13, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Meksikansk blanding med kjøttdeig', NULL),
(14, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Fiskesuppe med hvit fisk/ørrett/lask', NULL),
(15, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Pølsegryte med røkt kjøttpølse', NULL),
(16, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Hjemmelagd pizza', NULL),
(17, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Svinekotteletter med poteter/ris og mais, pluss saus', NULL),
(18, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag', 'Biff med poteter/ris/pommes frites og mais, pluss pepper/bernaise saus', NULL),
(19, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag Tilbehør', 'Poteter min. 150g', NULL),
(20, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag Tilbehør', 'Ris min. 100g kokt', NULL),
(21, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag Tilbehør', 'Pasta min 100g, ukokt', NULL),
(22, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag Tilbehør', 'Potetmos min 100g', NULL),
(23, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag Tilbehør', 'Pommes Frites min 100g', NULL),
(24, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag Tilbehør', 'Alle middager skal ha saus. Erstatning til saus kan være rømme eller ketchup', NULL),
(25, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag Porsjon', 'Fisk: Torsk 200g, Sei 200g, fiskeboller 200g, fiskekaker 200g, Ørret 150g, laks 125g', NULL),
(26, '2016-05-26 07:05:09', '2016-05-26 07:05:09', 1, 'Middag Porsjon', 'Kjøtt: Biff 150g, Svin 150g, kylling 150g, kjøttdeig 150g', NULL),
(27, '2016-06-02 07:15:32', '2016-06-02 07:15:32', 1, 'Frokost', 'Havregrøt', NULL),
(28, '2016-06-02 07:15:57', '2016-06-02 07:15:57', 1, 'Frokost', 'Frokostblanding', NULL),
(29, '2016-06-02 07:18:01', '2016-06-02 07:18:01', 1, 'Lunsj', '3 brødskiver med margarin og pålegg', NULL),
(30, '2016-06-02 19:31:45', '2016-06-02 19:31:45', 1, 'Middag Dessert', 'Muslibar', NULL),
(31, '2016-06-02 19:33:08', '2016-06-02 19:33:08', 1, 'Middag', 'Kyllingsalat, 320g kylling, 2 brødskiver og salat', NULL),
(32, '2016-06-03 09:31:31', '2016-06-09 15:04:41', 1, 'Frokost', 'Smoothie pluss brødskive med margarin og pålegg', NULL),
(33, '2016-06-03 15:06:31', '2016-08-18 23:35:51', 1, 'Middag', 'Fiskekaker, ris, brokkoli og ketchup', 'http://jbossews-gunnarro.rhcloud.com/image-manager/web/static/gallery/guest/20160501_173331.jpg'),
(34, '2016-06-04 06:46:30', '2016-06-04 06:46:30', 1, 'Middag Dessert', 'Frukt yoghurt', NULL),
(35, '2016-06-04 06:49:56', '2016-06-04 06:49:56', 1, 'Kveldsmat', '1 brødskive og GoMorgen yoghurt', NULL),
(36, '2016-06-04 18:38:33', '2016-06-04 18:38:33', 1, 'Middag', 'Biff burger med dressing og grove brød', NULL),
(37, '2016-06-04 18:40:09', '2016-06-04 18:40:09', 1, 'Middag Dessert', 'Saftis', NULL),
(38, '2016-06-04 18:41:47', '2016-06-04 18:41:47', 1, 'Middag Dessert', 'Fruktis med vanilje', NULL),
(39, '2016-06-05 15:40:40', '2016-06-06 15:34:08', 1, 'Middag Dessert', 'Korni rug kjeks, 3 stykker', NULL),
(44, '2016-06-06 15:33:26', '2016-06-06 15:33:26', 1, 'Middag Dessert', 'Friggs Riskjeks, 6 stykker', NULL),
(45, '2016-06-07 15:06:57', '2016-08-18 23:28:42', 1, 'Middag', 'Kyllingfilet ovnsbakt med ris og salat, pluss barbecue saus', 'http://jbossews-gunnarro.rhcloud.com/image-manager/web/static/gallery/guest/20160413_163939.jpg'),
(46, '2016-06-08 14:17:48', '2016-06-08 14:17:48', 1, 'Middag', 'Pulled Pork 200g med hamburgerbrød og salat', NULL),
(47, '2016-06-08 14:39:38', '2016-06-08 14:39:38', 1, 'Middag Dessert', 'Kick lakris stang', NULL),
(48, '2016-06-09 15:03:24', '2016-08-18 23:42:41', 1, 'Middag', 'Tomatsuppe fra mere suppe, tilsatt 250g torsk, pluss ett flatbrød med smør', 'http://jbossews-gunnarro.rhcloud.com/image-manager/web/static/gallery/guest/20160609_165513.jpg'),
(49, '2016-06-12 15:55:09', '2016-06-12 15:55:09', 1, 'Middag', 'Torsk, pasta og brokoli med pesto', NULL),
(50, '2016-06-13 16:47:30', '2016-06-13 16:47:30', 1, 'Middag Dessert', 'Nøtteblanding 45g', NULL),
(51, '2016-06-14 20:19:53', '2016-06-14 20:19:53', 1, 'Kveldsmat', '2 brødskiver og 2 egg', NULL),
(52, '2016-06-15 20:05:38', '2016-08-19 20:54:55', 1, 'Kveldsmat', '3 brødskiver med margarin og pålegg', ''),
(53, '2016-06-20 05:28:07', '2016-06-20 05:28:07', 1, 'Middag', 'Svinekjøtt, ris/poteter med grønnsaksblanding', NULL),
(54, '2016-06-23 04:40:33', '2016-06-23 04:40:33', 1, 'Kveldsmat', 'Smoothie pluss brødskive med margarin og pålegg', NULL),
(55, '2016-06-23 04:44:47', '2016-08-14 07:55:25', 1, 'Lunsj', 'Ett stort rundstykke, grov brixs med maragarin og pålegg på begge halvdeler', NULL),
(56, '2016-06-24 07:20:38', '2016-06-24 07:20:38', 1, 'Mellom måltid', '33 ml YT', NULL),
(57, '2016-06-24 07:20:38', '2016-06-24 07:20:38', 1, 'Mellom måltid', '6 Riskjeks', NULL),
(58, '2016-06-24 07:20:38', '2016-06-24 07:20:38', 1, 'Mellom måltid', '60g NøttiFrutti Nøtteblanding', NULL),
(59, '2016-06-24 07:20:38', '2016-06-24 07:20:38', 1, 'Mellom måltid', '2 Bixit', NULL),
(60, '2016-06-24 07:20:38', '2016-06-24 07:20:38', 1, 'Mellom måltid', '1 banan', NULL),
(61, '2016-06-24 19:19:08', '2016-06-24 19:19:08', 1, 'Middag Dessert', 'Sjokolade', NULL),
(62, '2016-06-25 13:39:39', '2016-06-25 13:39:39', 1, 'Mellom måltid', 'Muslibar', NULL),
(63, '2016-06-25 19:21:17', '2016-06-25 19:21:17', 1, 'Middag', 'Sushi', NULL),
(64, '2016-06-27 12:36:57', '2016-08-18 23:52:01', 1, 'Frokost', '2 brødskiver og egen yoghurt blanding med bær og nøtter', 'http://jbossews-gunnarro.rhcloud.com/image-manager/web/static/gallery/guest/20160711_101015.jpg'),
(65, '2016-06-27 12:43:35', '2016-06-27 12:43:35', 1, 'Kveldsmat', '2 rundstykker med margarin og pålegg på alle halvdelene', NULL),
(66, '2016-06-29 15:44:25', '2016-06-29 15:44:25', 1, 'Frokost', '2 rundstykker med margarin og pålegg på alle halvdelene', NULL),
(67, '2016-07-01 20:29:16', '2016-07-01 20:29:16', 1, 'Middag', 'Wraps med biffkjøtt, salat og rømme', NULL),
(68, '2016-07-02 19:53:35', '2016-07-02 19:53:35', 1, 'Middag Dessert', 'Kokosboller, 3 små', NULL),
(69, '2016-07-03 09:24:00', '2016-07-03 09:24:00', 1, 'Frokost', 'Erstattning 325 ml fresubin', NULL),
(70, '2016-07-12 11:54:19', '2016-08-18 23:55:34', 1, 'Kveldsmat', 'Salat med 100g pasta og skinke', 'http://jbossews-gunnarro.rhcloud.com/image-manager/web/static/gallery/guest/20160711_221809.jpg'),
(71, '2016-07-12 19:18:48', '2016-07-12 19:18:48', 1, 'Middag', 'Wraps med kylling, salat og rømme', NULL),
(72, '2016-07-15 00:36:49', '2016-07-15 00:36:49', 1, 'Kveldsmat', '2 brødskiver med pålegg og 3 fiskekaker', NULL),
(73, '2016-07-20 16:16:23', '2016-07-20 16:16:23', 1, 'Middag Dessert', 'Ekstra middag, 1/3 porsjon', NULL),
(74, '2016-07-25 09:02:23', '2016-07-25 09:02:23', 1, 'Frokost', 'Egen frokostblandig, 150g kesam, blåbær, jordbær og nøtter.', NULL),
(75, '2016-07-28 13:51:50', '2016-08-18 23:45:21', 1, 'Frokost', '3 brødskiver med margarin og pålegg', 'http://jbossews-gunnarro.rhcloud.com/image-manager/web/static/gallery/guest/20160709_135040.jpg'),
(76, '2016-08-05 16:55:28', '2016-08-05 16:55:28', 1, 'Middag Dessert', 'Bolle', NULL),
(77, '2016-08-14 08:38:25', '2016-08-14 08:38:25', 1, 'Frokost', '1 Briks med smør og pålegg, pluss 3 små pannekaker, se oppskrift ', NULL),
(78, '2016-08-17 12:04:12', '2016-08-25 05:37:10', 1, 'Middag', 'Svinekjøtt med grønnsaker,brød og rømme', ''),
(79, '2016-08-25 05:34:43', '2016-08-25 05:34:43', 1, 'Mellom måltid', 'Knekkebrød/rundstykke/brødskive eller tilsvarende', ''),
(80, '2016-09-04 08:38:41', '2016-09-04 08:38:41', 1, 'Kveldsmat', '2 brødskiver og egen yoghurt blanding med bær og nøtter', ''),
(81, '2016-09-04 08:39:40', '2016-09-04 08:39:40', 1, 'Middag', 'Kyllingfilet ovnsbakt med pasta og salat, pluss barbecue saus', '');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `diet_plans`
--

CREATE TABLE IF NOT EXISTS `diet_plans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `start_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `diet_plan_name` varchar(100) DEFAULT NULL,
  `diet_plan_description` varchar(200) DEFAULT NULL,
  `diet_plan_active` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dataark for tabell `diet_plans`
--

INSERT INTO `diet_plans` (`id`, `created_date_time`, `last_modified_date_time`, `start_date_time`, `end_date_time`, `diet_plan_name`, `diet_plan_description`, `diet_plan_active`) VALUES
(1, '2016-06-15 19:13:22', '2016-06-15 19:13:22', '2016-06-15 19:13:22', '2016-06-15 19:13:22', 'Pepilie Diet Plan', 'Kostplan fra uke 16 til x', 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `diet_plan_meals`
--

CREATE TABLE IF NOT EXISTS `diet_plan_meals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_diet_plan_id` int(11) DEFAULT NULL,
  `start_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `meal_name` varchar(100) DEFAULT NULL,
  `meal_period` varchar(200) DEFAULT NULL,
  `meal_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_diet_plan_id` (`fk_diet_plan_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dataark for tabell `diet_plan_meals`
--

INSERT INTO `diet_plan_meals` (`id`, `created_date_time`, `last_modified_date_time`, `fk_diet_plan_id`, `start_date_time`, `end_date_time`, `meal_name`, `meal_period`, `meal_description`) VALUES
(1, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 1, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 'Frokost', '(0700 - 0800, helg 0900 -1030)', ''),
(2, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 1, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 'Lunsj', '(1130 - 1200, helg 1300 - 1330)', ''),
(3, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 1, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 'Mellom måltid', '(1500 - 1530 og/eller 1900 - 1930)', ''),
(4, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 1, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 'Middag', '(1630 - 1700, helg 1700 - 1730)', ''),
(5, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 1, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 'Kveldsmat', '(2030 - 2100, helg 2100 - 2130)', ''),
(6, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 1, '2016-06-15 19:14:45', '2016-06-15 19:14:45', 'Regler', 'Regler og tillegg for alle måltider', '');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `diet_plan_meal_items`
--

CREATE TABLE IF NOT EXISTS `diet_plan_meal_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_diet_plan_meal_id` int(11) DEFAULT NULL,
  `meal_item_name` varchar(100) DEFAULT NULL,
  `meal_item_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_diet_plan_meal_id` (`fk_diet_plan_meal_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=22 ;

--
-- Dataark for tabell `diet_plan_meal_items`
--

INSERT INTO `diet_plan_meal_items` (`id`, `created_date_time`, `last_modified_date_time`, `fk_diet_plan_meal_id`, `meal_item_name`, `meal_item_description`) VALUES
(1, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 1, 'Frokost', '3 brødskiver med margarin og pålegg / Havregrøt(75g) på lettmelk med 1 ts sukker / Havrefras(70g) 3dl lettmelk pluss ett egg'),
(2, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 1, 'Frokost', '1 glass lettmelk (2 dl)'),
(3, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 1, 'Frokost', '1 frukt'),
(4, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 1, 'Frokost', '125 ml Fresubin Energy'),
(5, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 1, 'Frokost', '1 multivitamin'),
(6, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 2, 'Lunsj', '3 brødskiver med margarin og pålegg / 2 brødskiver med margarin og 1 egg, pluss 1 yoghurt'),
(7, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 2, 'Lunsj', '1 glass lettmelk (2 dl)'),
(8, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 2, 'Lunsj', '1 frukt'),
(9, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 2, 'Lunsj', '125 ml Fresubin Energy'),
(10, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 3, 'Mellom måltid', '2 Bixit, 60g Nøtteblanding, 6 Riskjeks, Banan eller 33ml YT'),
(11, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 4, 'Middag', '1 porsjon middag (varier mellom kjøtt og fisk)'),
(12, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 4, 'Middag', '1 dessert eller 1 yoghurt (Kan erstattes med 1/3 ekstra middag)'),
(13, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 4, 'Middag', '1 juice (2 dl)'),
(14, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 4, 'Middag', '125 ml Fresubin Energy'),
(15, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 5, 'Kveldsmat', '1 brødskive med margarin og pålegg'),
(16, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 5, 'Kveldsmat', '1 Go-Morgen yoghurt'),
(17, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 5, 'Kveldsmat', '1 glass lettmelk (2 dl)'),
(18, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 5, 'Kveldsmat', '125 ml Fresubin Energy'),
(19, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 6, 'Regler og tillegg', '500 ml vann/saft hver dag'),
(20, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 6, 'Regler og tillegg', 'Ikke lett produkter'),
(21, '2016-05-26 07:03:13', '2016-05-26 07:03:13', 6, 'Regler og tillegg', 'Spisetid 30 minutter, det som eventuelt blir igjen skal da erstattes med Fresubin');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `diet_products`
--

CREATE TABLE IF NOT EXISTS `diet_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `product_name` varchar(100) DEFAULT NULL,
  `product_type` varchar(100) DEFAULT NULL,
  `product_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dataark for tabell `diet_products`
--

INSERT INTO `diet_products` (`id`, `created_date_time`, `last_modified_date_time`, `product_name`, `product_type`, `product_description`) VALUES
(1, '2016-05-23 08:22:07', '2016-05-23 08:22:07', 'Yoghurt', 'desert', 'Porsjoner som tilsvarer 1 frukt yoghurt'),
(2, '2016-05-23 08:22:07', '2016-05-23 08:22:07', 'GO-Morgen Yoghurt', 'desert', 'Porsjoner som tilsvarer 1 go-morgen yoghurt'),
(3, '2016-06-04 07:08:43', '2016-06-04 07:08:43', '3 Brødskiver', 'frokost', 'Porsjoner som tilsvarer 3 brødskiver med margarin og pålegg');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `diet_product_equivalent_items`
--

CREATE TABLE IF NOT EXISTS `diet_product_equivalent_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_diet_product_id` int(11) DEFAULT NULL,
  `product_equivalent_name` varchar(100) DEFAULT NULL,
  `product_equivalent_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_diet_product_id` (`fk_diet_product_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=20 ;

--
-- Dataark for tabell `diet_product_equivalent_items`
--

INSERT INTO `diet_product_equivalent_items` (`id`, `created_date_time`, `last_modified_date_time`, `fk_diet_product_id`, `product_equivalent_name`, `product_equivalent_description`) VALUES
(1, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 1, 'brødskive', '1 brødskive med pålegg'),
(2, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 1, 'knekkebrød', '2 knekkebrød med pålegg'),
(3, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 1, 'sjokolademelk', '2 dl OBoy med lett melk'),
(4, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 1, 'middag', '1/3 middagsprosjon'),
(5, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 1, 'vaffel', '1/2 vaffel med syltetøy'),
(6, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 1, 'is', '1,5 dl fløteis'),
(7, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 1, 'bar', '1 byggrynslunsj'),
(8, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 1, 'bar', '1 rislunsj'),
(9, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 1, 'melk', '3 dl lettmelk'),
(10, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 1, 'milsibar', '1 Wasa muslibar'),
(11, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 2, 'nøttebalnding', '1 pakke småsulten nøtte - og frukt blanding'),
(12, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 2, 'YT', '1 YT bar + 5 dl YT restitusjonsdrikk'),
(13, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 2, 'iskaffe', '1 iskaffe Energi (Tine)'),
(14, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 2, 'brødskive', '2 brødskiver med margarin og pålegg'),
(15, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 2, 'rundstykke', '1 rundstykke med margarin og pålegg'),
(16, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 2, 'baguett', '1 medium baguett med margaring og pålegg på begge halvdelene'),
(17, '2016-05-23 08:22:30', '2016-05-23 08:22:30', 2, 'sjokolademelk', '5 dl sjokolademelk (ikke lett varianten)'),
(18, '2016-06-04 07:09:30', '2016-06-04 07:09:30', 3, '3 brødskiver', '1 Smoothie, pluss 1/2 rundstykke med margarin og pålegg'),
(19, '2016-06-04 07:09:30', '2016-06-04 07:09:30', 3, '3 brødskiver', 'Omelett, pluss 1/2 rundstykke med margarin og pålegg');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `event_log`
--

CREATE TABLE IF NOT EXISTS `event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_user_id` int(11) DEFAULT NULL,
  `level` varchar(25) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `content` varchar(4096) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`fk_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=77 ;

--
-- Dataark for tabell `event_log`
--

INSERT INTO `event_log` (`id`, `created_date_time`, `last_modified_date_time`, `fk_user_id`, `level`, `title`, `content`) VALUES
(1, '2016-06-21 21:29:56', '2016-06-21 21:29:56', 5, 'INFO', 'Generelt', 'Emilie har styrt både lunsj og kveldsmaten selv i dag. Det har gått bra. Hun driver fortsatt å gjemmer unna mat til tider. Men det skaper ingen konflikter. Stort sett går det bra og hun er blid og fornøyd'),
(2, '2016-06-22 06:29:26', '2016-06-22 06:29:26', 5, 'CONFLICT', 'Lunsj på skolen', 'Emilie ble krakilsk da hun ikke fikk spise lunsj på skolen. Hylte, skrek og stanget hode i veggen. Kalte meg slem og løp ut døren. Hun syklet opp til skolen.\r\n\r\nAndreas slo seg også vrang i dag tidlig når han oppdaget at emilie hadde lånt hettegenseren hans. Jeg ble sur fordi han reagerte slik, noe som førte til at han bannet og slang mange stygge ord til meg.\r\nBa meg hoppe ut av vinduet, gå å henge meg, viste meg fingeren. Dette vil få konsekvenser, må tenke litt på hvilke.\r\nHan for ikke spille på pc eller mobil i en uke,  tenker jeg. Samt gi meg en unskylding. '),
(3, '2016-06-22 07:29:06', '2016-06-22 07:29:06', 5, 'INFO', 'Frokost', 'Emilie har puttet mye av forkostblandingen i jakkelommen. Gir henne ekstra fresubin til lunsj'),
(4, '2016-06-23 04:54:05', '2016-06-23 04:54:05', 5, 'INFO', 'Frokost konflikt', 'Gårsdagens konflikt løst seg etterhvert og øvige måltider gikk knirkefritt. Generelt skal det lite til for at en konflikt trigges. Men den er relativt lett å løse ved å inngå kompromier eller ved å la henne få viljen sin. Hun takler ikke brå endringer eller hvis ting ikke blir akkurat slik som hun har tenkt det. Uansett, det viktigste er å få henne til å spise nok mat. Og noen ganger må man da la henne få viljen sin for å unngå konflikter.\r\n'),
(5, '2016-06-25 06:32:07', '2016-06-25 06:32:07', 5, 'INFO', 'God progresjon', 'Emilie veide inn på 44,4 på ullevål i går.\r\nHun har også vokst 1 cm og er nå 159cm høy.\r\nGenerelt sett går det nå bra og hun visere tydelige tegn til at sulten begynner å styre mat mengen. Særlig til kveldsmaten. Da spiser hun ofte ett par ekstra brødskiver og ett ekstra glass melk.\r\nHumøret hennes er stort sett bra og hun er ikke lenger så nærtagen. Videre er hun heller ikke innesluttet så lenge av gangen.'),
(6, '2016-06-25 20:10:41', '2016-06-25 20:10:41', 5, 'INFO', 'Måltider', 'Emilie har styrt mange av måltidene i dag.\r\nHun sier at alt går bra og at hun spiser det hun skal. Men det er grunn til å tro at hun lager måltidene etter sitt ønske og følger ikke alle reglene vi har. Som for eksempel smør på brødskivene. Hun har heller ikke sluttet med å lure unna mat. Konklusjonen er at alle måltidene må fortsatt følges opp og kontrolleres nøye.'),
(7, '2016-06-25 20:57:24', '2016-06-25 20:57:24', 5, 'CONFLICT', 'Kveldsmat', 'Hun nekter å spise brødskivene fordi det er smør på dem. Sier også at hun ikke vil mer opp i vekt. Dette fordi hun nå har begynt å vokse igjen og skjønner da ikke hvorfor hun må ytterlig opp i vekt. Hun skirker og smeller med dørene, tramper i trappa.\r\nJeg sliter også med å beholde roen, sinnet tar overhånd og jeg kastet brødskivene i fanget hennes. Hun sier at hun ikke vil leve mer. Ting roet seg ganske rask og så gikk hun å la seg. Kveldsmaten var ikke ihht. diet planen.'),
(8, '2016-06-26 08:04:07', '2016-06-26 08:04:07', 5, 'INFO', 'Frokost konflikt', 'Hun har store problemer med å spise frokostblandingen. Klager på at det er alt for mye. Men mengden har ikke blitt endret.\r\nEr veldig frustrert og gråter og snakker høyt at hun ikke klarer å spise så mye. Roer seg litt ned etter vel 10 min og setter seg ned for å spise. Plukker ut litt frokostblanding ut av skålen og legger det på bordet. \r\nJeg lar henne bare holde på. Tenker at det er bedre at hun spiser litt enn ingenting. Jeg har ingen virkemiddel når hun blir slik. Må bare å få henne til å spise og håpe på at hun roer seg. Hun er veldig urolig, mye opp og ned mens hun spiser. Klarer ikke å sitte i ro. '),
(9, '2016-06-26 08:04:49', '2016-06-26 08:04:49', 5, 'INFO', 'Generelt', 'Emilie har styrt både lunsj og kveldsmaten selv i dag. Det har gått bra. Hun driver fortsatt å gjemmer unna mat til tider. Men det skaper ingen konflikter. Stort sett går det bra og hun er blid og fornøyd'),
(11, '2016-06-28 16:13:04', '2016-06-28 16:13:04', 5, 'INFO', 'Emilie', 'Hun er blitt mye flinkere til å ta kontakt med vennene sine og de lager avtale om å finne på noe sammen.\r\nDenne uken har hun vært mye ute å lekt med vennene sine. Hun har da også fått lov til å kjøpe seg lunsj på egenhånd.\r\nNoe hun setter vedlig stor pris på. jeg lar henne gjøre det fordi jeg ser at det er som balsam for psyken og humøret hennes.\r\nHun føler seg da ikke annerledes en de andre. '),
(13, '2016-06-29 15:49:20', '2016-07-03 09:00:13', 5, 'CONFLICT', 'Konflikt Middag', 'Her ble det problemer rundt valg av middag.  Emilie ringt og sa hun ønsket fiskesuppe til middag. Når hun kommer hjem viser det seg at jeg har lagd feil fiskesuppe. Jeg sier at hun kan spise denne og her starter konflikten. Det blir mye frem og tilbake. Hun vil ut å kjøpe noe annet, noe jeg ikke godtar. Ber henne om å smake på fiskesuppen først. Noe hun ikke vil. Hun prøver gjentatte ganger å gå ut, så jeg låser døren og tar nøklene. Hun er ganskw så fortivelt og snakker/skriker til meg. Jeg klarer å holde en rolig tone. Hun begynner å bli hysterisk og sparker og prøver å velte kjøleskapet. Jeg tar da grep og legger henne på gulver pg holder henne fast. Hun hyler, sparker og prøver å bite neg. Jeg sier ingenting men holder henne bare stabilt og prøver mitt beste for at det ikke skal være vondt. Etter vel 3 minutter dør anfallet ut og hun slapper av. Jeg slipper taket og bare sitter og styker henne på ryggen. Vil ligger slik i mange minutter. Ting løser seg så, jeg går ut å kjøper den fiskesuppen hun liker. Spør meg selv i ettertid hvorfor jeg ikke gjorde det med en gang. Men det handler om å ha kontrollen og hun skal ikke styre måltidene.'),
(14, '2016-06-30 16:15:02', '2016-06-30 19:22:01', 5, 'INFO', 'Middag', 'Gjemte unna pasta under middagen. Legger pasta''en faktisk under puten på stolen, som ikke er vedlig vanskelig for meg å oppdage. Hun bryr seg ikke så mye om jeg oppdager det eller ei.\r\nDette fordi hun trodde jeg hadde hatt oliven olje i vannet under koking av pasta''en. Noe jeg faktisk hadde glemt denne gangen. Ellers spiste hun opp alt. Dette utløste ingen konflikt.'),
(15, '2016-07-02 16:22:09', '2016-07-02 16:22:09', 5, 'CONFLICT', 'Konflikt i forbindelse med Frokost', 'Det ble problemer når jeg sa at hun måtte ha sukker på havregrøten. Noe som har gått greit tidligere, men som i det siste viser seg å være problematisk for henne. Hun truer med å slå seg vrang. Løste det ved å la henne bestemme mengde sukker. Men slik skal det ikke være. Hun utnytter nå det st jeg ønsker å unngå konflikt og prøver å finne løsninger. Vi mister da de faste rammene rundt måltidene. Og hun prøver hele tiden å ta kontroll over måltidene. Hun foreslår hele tiden nye varianter og det er krevende å påse at kalori inntaket da blir ihht. diet planen.'),
(16, '2016-07-03 08:49:14', '2016-07-03 09:15:28', 5, 'INFO', 'Frokost', 'I dag sov hun til kl. 1000. Hun er ikke sulten, men ønsket å lage seg wraps med laks til. Noe jeg godtar. Jeg spør hvor mye laks hun har hatt i wrapsen. Hun sier 3 skiver, men når jeg sjekker viser det seg at hun har brukt skinke og ikke laks. Hun luver bevist. Og det er nytt. Vi er nå inne i en litt vanskelig periode hva måltider angår. Humøret hennes svinger. Venter litt an og ser om hun vil ha frokost litt senere. Hun pleier å bli sulten og be om mat. Hun er nå frustrert men vil har ingen konflikt akkurat nå. Det løste seg ved st jeg fikk henne til å drikke 325 ml fresubin. Ting har hele tiden gått rolig for seg.'),
(17, '2016-07-03 09:09:00', '2016-07-03 09:22:20', 5, 'INFO', 'Ferie', 'Det er vanskelig å bestemme seg for ferie grunnet emilie. Måltidene er blitt mer utfordrende fordi hun hele tiden prøver å ta styringen, samt hun kommer med nye forslag til mat hele tiden. Når vi er uenige i hva måltidet skal bestå av, nekter hun å spise og prøver å gå ut. Her må det mye overtale til for å gå henne til å ta til seg noe næring.  For ting skal løse seg er det helt essensielt at jeg beholder roen og gir henne litt tid. Det å vise masse omsørg og kjærlighet for henne er også meget viktig når hun har sine tunge øyeblikk. Hun sier at de oppstår uten forvarsel og at hun er vet heller ikke hvorfor eller hva som er grunnen til sine tunge perioder. Hin sier at det er generelt litt vanskelig hele tiden. '),
(18, '2016-07-04 17:03:48', '2016-07-04 20:30:51', 5, 'CONFLICT', 'Problemer', 'Dette har så langt vært en meget tung dag. Det startet etter frokost da jeg kjeftet på emilie fordi hun gjorde knebøy på rommet sitt. Noe hun benekter selv om jeg så henne. Hun blir sur på meg fordi jeg kjefter på henne. Frem til lunsj er vi ikke sammen. Hun ordner og spiser lunsj selv. Det går bra. Middagen lager jeg og det starter bra. Men så slår hun seg vrang å begynner å grise med maten ved å mose den mellom fingerene sine. Det har hun aldri gjort før. Hun er sont på meg og snakker høyt. Sier hun vil bort fra familien og aldri se meg igjen. Sier også at mamma ikke bryr seg en dritt om henne. Hun er forsåvidt rolig nå, men snufser og gråter. Jeg skal prøve å snakke med henne nå. Problemene dabbet sake og sikkert ut mens vi snakket sammen. Blant annen kom det fram at hun synrs jeg aldri sier takk når hun hjelper meg. Hun blir veldog oppskakket og frustrert når jeg kjefter eller blir sint på henne. Her ligger også mye av grunnen til at hun blir lei seg. Når er alt tilbake til det normale igjen.'),
(19, '2016-07-04 20:33:10', '2016-07-04 20:33:10', 5, 'INFO', 'E og A', 'Det siste gangene vi alle har spilt fotball har ikke e og a kranglet. De fungere mye bedre sammwn nå. Slik som de alltid har gjort og det er sabla godt å se. Humøret er tilbake hos dem begge og de fjoller og ler mer nå.'),
(20, '2016-07-04 20:35:13', '2016-07-04 20:35:13', 5, 'CONFLICT', 'Konflikt i forbindelse med Middag', 'Ble litt trøbbel, men det løste seg. Se notat tidligere i dag.'),
(21, '2016-07-05 20:51:49', '2016-07-05 20:51:49', 5, 'INFO', 'Ferie', 'Dro til gjendesheim i dag. Andreas jar sprudlet og fjollet i hele dag. E og A har nå en veldig fin tone og leker fint sammen. Det er flott å se. '),
(22, '2016-07-08 19:47:25', '2016-07-08 20:18:50', 5, 'INFO', 'Ferie oppsummering', 'Selv om vi er på ferie ligger e sin diet som en klam hånd over oss. Det er ikke lett å få en pause fra sykdommen. Måltidene blir mye vanskliger å følge opp når man er på ferie. E prøver hele tiden å rive seg løs fra de faste rammene og vil helst styre alt rundt måltidene selv. Hun lesere også hva alle produktene inneholder av næringsstoffer. Hun nekter å spise hvis hun ikke får det akkurat slik hun ønsker.\r\nSelv om vi har forholdsvis mye aktivitet så roer hun seg ikke ned. Hun står fortsatt mye og setter seg sjeldent ned. Ei har hun spist mer på trass av økt aktivitet. \r\n I morgen når vi kommer hjem skal jeg sjekke vekten. Vil tro den har gått ned.\r\nEllers har disse fire dagene stort sett gått greit. E og A har lekt fint sammen.\r\n'),
(23, '2016-07-09 15:12:03', '2016-07-09 15:16:35', 5, 'CONFLICT', 'Spiste ikke Frokost', 'På fredag 8.7 spiste ikke e frokost. Ville ha knekkebrød men det godtok ikke jeg. Hun drakk 125 ml fresubin. I ettertid er e enig i at det var riktig å nekte henne knekkebrød og si at hun da må bare drikke fresubin. Hun sier sykdommen styrer henne slik st hun da ikke klarer å ta noe annet. Det spesielle denne gangen var at hun ikke spiste noen ting, men jeg må sette grenser, ellers vil alt skli ut. E er enig i at hun ikke må få lov til å hele tiden velge nye ting som ikke står på diet planen. \r\nAlt foregikk rolig.'),
(24, '2016-07-10 08:24:59', '2016-07-10 08:24:59', 5, 'INFO', 'Vekt tap', 'E har gått med 700 g i løpet av de 4 dagene vi var på ferie og veier nå 44.1 kg. Det var ikke helt uventet. Vi er blitt enige om å øke med 25 ml fresibin inntil vi har tatt inn vekttapet. Hun virker motivert for det.\r\nMen slikt svinger hele tiden. Dette viser bare at det er utrolig viktig å ha faste forhold rundt måltidene. Noe vi ikke klarte under ferien. Hun spiste heller ikke mellom måltid de dagene. Samt at fredag 8.7 var det problemer rundt alle måltidene. \r\nVi reiste mye rundt og det er selvfølgelig heller ikke optimalt. Det å være på en fast plass under ferien er nok mye bedre.'),
(25, '2016-07-10 22:05:00', '2016-07-10 22:05:00', 5, 'INFO', 'Måltidene', 'E er nå tilbake til de faste rammene rundt måltidene og hun spiser nå som normalt igjen. Matlysten er tilbake og jeg håper hun henter inn vekttapet raskt. Hun drikker mer melk enn det som er satt opp på diet planen, særlig til kveldsmaten. Ofte spiser hun også litt ekstra. Hun synes også det er lettere å holde seg til planen når hun er hjemme. Hun må hente inn vekttapet før vi eventuelt reiser bort igjen. Og hvis så skjer skal vi da være på samme stedet hele tiden.\r\n'),
(26, '2016-07-11 16:03:49', '2016-07-11 16:03:49', 5, 'INFO', 'Kastet brødskiver', 'Emilie gjemte unna 2 brødskiver under frokosten i dag. Vi ble enige om at neste gang den lysten meldte seg skal hun prøve å si ifra til meg på forhånd.  '),
(27, '2016-07-11 16:14:23', '2016-07-11 16:14:23', 5, 'INFO', 'Oppsummering ferie', 'Det er ikke slik at man legger sykdommen igjen hjemme når man drar på ferie. Den følger henne 24 timer i døgnet enn hvor hun er hen. Hun trenger fortsatt faste rammer å forholde seg til ellers prøver hun titt og ofte å snike seg unna både dessert, frukt og mellom måltider. Selv om jeg aktiviserer henne mer enn normalt blir hun ikke roligere. Hun setter seg ikke automatisk ned av den grunn. Hun blir neste bare mer rastløs. Sliten blir hun aldri. Mye aktivitet gjør at hun spiser mer til kveldsmat.\r\nUtfordringen er å holde faste rammer rundt måltidene, hvis ikke vil sykdommen lettere kunne ta over. Generelt vise emilie progresjon på de fleste områder, men jeg ser også at gamle ljente ting dukker opp igjen. Slik som at hun synrs nå det er problemer å ha sukker i grøten, samt å veie seg. \r\nVi har fortsatt en lang vei å gå.'),
(28, '2016-07-12 11:58:03', '2016-07-12 11:58:03', 5, 'INFO', 'Generelt', 'Hun er blitt litt mer viren hva dessert og mellom måltid angår. Må mase og følge henne nøye opp på dette. Ei liker hun å veie seg på tirsdager her hos neg. Hun er redd for å finne på noe tull hvis hun ser at vekten har gått opp. Her er det viktig å holde fast ved rutinene.'),
(29, '2016-07-12 12:05:24', '2016-07-12 12:05:24', 5, 'INFO', 'Lunsj', 'Man må følge nøye med under måltidene. Hun har fått en lei tendens til å lure unna mat. Til lunsj i dag skrapte hun bort smøret på brødskivene. Jeg bare smørte på nytt,  noe hun godtok.'),
(30, '2016-07-14 06:10:54', '2016-07-14 06:10:54', 5, 'INFO', 'Kontroll', 'Jeg lar nå emolie få lov til å velge og ta på pålegge på brødskivene selv. Dette da hun ikke lenger er opptatt av hvor mye pålegge det er på en brødskive. Hun tar på mye pålegg og gjene salat, paprika og agurk hvor det passer. Hun spiser nå også, leverpostei, hvitost, røklaks, peanuttsmør.\r\nJeg skjærer opp og tar på smør fortsatt.'),
(31, '2016-07-15 13:00:45', '2016-07-15 13:00:45', 5, 'CONFLICT', 'bytte undertøy', 'I dag tidlige ble det bråk fordi jeg ba henne om å bytte undertøy. Hun har nå gått i det samme undetøyet siden søndag. Jeg har mang en gang oppfordret henne til å skifte.\r\nHun rev ned tøykruven på rommet sitt, men etter litt hyl og skrik, byttet hun og så gikk hun ut for å treffe en veninne.'),
(32, '2016-07-15 13:04:36', '2016-07-15 14:35:46', 5, 'CONFLICT', 'bup møte', 'Hun kom hjem kl. 1400 og begynte på spise lunsj. Jeg oppdager at hun har skarpet bort smøret på rundstykket og så starter problemene.\r\nFørst vil hun bare ha fresubin, så vil hun ikke ha noe som helst. Møte på BUP vil hun heller ikke gå  i. Jeg  prøver å ro det hele ned og jobber fortsatt  med saken. Hun er meget opprørt men forholdsvis rolig. Hun er er lite responsiv på mine forslag og står apatisk og ser ut av vinduet. Jeg prøver derfor en ny vir og sier at hun kan prøve å løse opp i dette på egnehånd, det vil si spise opp lunsjen. Fortelelr henen at jeg går ut for å kjøpe fresubin og at hun bare kan ringe meg hvis det er noe. Hun er fortsatt like taus. Jeg drar og når jeg kommer tilbake etter vel 45 minutter står hun utenfor og leker litt med en fotball. Hun har da spist opp lunsjen på egenhånd og er tilbake i normal gange. Hun avtalt med mamme om å gå å spille minigolf. Så da løste floken seg releativt smertefritt for oss begge.'),
(34, '2016-07-15 21:24:11', '2016-07-15 21:24:11', 5, 'CONFLICT', 'Konflikt i forbindelse med Lunsj', 'Problemer med lunsj. Hun spiste opp alt til slutt.'),
(35, '2016-07-15 21:26:31', '2016-07-15 21:26:31', 5, 'INFO', 'Måltider', 'Hun ønsker at jeg skal passe bedre på under måltidene slik at hun ikke blir fristet til å gjemme unna mat. Det vil si at jeg må sitte sammen med henne under hele måltidet. Noe jeg ikke alltid gjør.'),
(36, '2016-07-16 06:57:10', '2016-07-16 06:57:10', 5, 'INFO', 'Lese bok', 'E har nå begynt å lese igjen og vise nå små tegn på at hun ønsker og vill slappe av mer.\r\nI går satt hun i sofaen og leste i vel 30 minutter.'),
(37, '2016-07-16 20:01:26', '2016-07-16 20:01:26', 5, 'INFO', 'Fresubin', 'I dag har det ikke blitt fresibin til lunsj og middag. Dette er ett bevist valg. Hun har spist bra.'),
(38, '2016-07-17 21:18:51', '2016-07-17 21:18:51', 5, 'INFO', 'Kveldsmat', 'Emilie spiste bra, og hadde pitabrød med biff og salat, samt melk og fresubin.\r\n'),
(39, '2016-07-18 12:46:58', '2016-07-18 12:46:58', 5, 'INFO', 'Frokost', 'Til frokost spiste Emilie pannekaker med bringebær,  som hun hadde laget selv av melk, mel og havregryn, dvs.ikke egg.  Hun drakk Fresubin, men ikke melk, da det var melk i pannekakene. Emilie forklarte at dette var ok, siden man også droppet melkeglass når hun spiste frokostblanding? Etter frokost gikk vi en liten tur...'),
(40, '2016-07-19 08:08:53', '2016-07-19 08:10:50', 5, 'INFO', 'Overnattet', 'E overnattet hos m fra søndag til mandag. Alt gikk greit. E benyttet forøvrig anledningen til å velg mat som ikke var på menyen til både kvelds og frokost. Dette er meget uheldig, men ikke uventet. Hun prøver hele tiden og ta kontrollen og styre måltidene slik hun selv ønsker det. Vi skal ikke tillate at så skjer. Vi skal hele tiden ha full kontroll.'),
(41, '2016-07-19 08:48:47', '2016-07-19 08:48:47', 5, 'CONFLICT', 'Konflikt i forbindelse med Frokost', 'Her ble det konflikt grunnet veiing. Hun liker når svært dårlig å veies og slår seg vrang. \r\nSer også at mange gamle tanker som jeg trodde hun var ferdig med dukker opp igjen. Som for eksempel tidspunket for måltidet.\r\nMen alt blir vanskelig når først ett problem har oppstått. Da bare baller det på seg og hun sier at alt er bare dritt. Men hun hyler og skriker mindre en tidligere og det er litt lettere å komme ut av konflikten. Jeg ser hele tiden etter løsninger, uten å måtte gi tapt for det viktigste, som er å få henne til å spise.'),
(42, '2016-07-21 07:47:46', '2016-07-25 08:54:50', 5, 'INFO', 'Treningsøkt', 'I dag tok jeg med Emilie på en løpetur fra maridalen skole og opp til Fagervann. Som er en ganske har løype på vel 3 km en vei.\r\nHun er i veldig bra form og henger lett med meg opp dit. Hun er nå flinkerer til å ta til seg næring etter en treningstur. Hun blir også meget sulten etter en slik hard økt.\r\nJeg tar henne med på slike økter for å vise henne at hun er mye sterkere nå en da hun var på sitt tynneste. Dette slik at hun skal bli mer fortrolig med å gå opp i vekt.\r\n '),
(43, '2016-07-29 08:32:00', '2016-07-29 08:38:29', 5, 'CONFLICT', 'Frokost', 'I dag fikk jeg en liten påminnelse om at Emilie fortsatt sliter med sykdommen hele tiden. Det har nå gått veldig bra over en lang periode og jeg har gradvis gitt henne mer og mer tilit hva måltider angår. Og må være meget påpasselig på at jeg ikke går fort fort frem. For hun sliter voldsomt, selv om hun ikke alltid gir utrykk for det.\r\nI dag ble det meget problematisk å spise frokost, hun oppfører seg ganske rolig så lang. Hun vil ikke snakke med meg og er bare på rommet sitt.\r\nJeg velger å se det hele an og håper på at hun roer seg ned.'),
(44, '2016-08-04 08:29:00', '2016-08-04 08:37:16', 5, 'INFO', 'Norway Cup', 'mandag, tirsdag og onsdag har Emilie spilt 4 kamper for Sagene under Norway Cup. Hvor hver kamp var 2x25 minutter.\r\nDet har gått greit selv om hun på onsdag ble vedlig skuffet over at de ikke gikk videre til 1/16 finalen. Hun har også vært flink til å ta til seg drikke. men her må jeg følge nøye opp, det går ikke helt av seg selv. Hun har også tatt en ekstra  flaske fresubin hver dag. Grunnet dette hadde vi en ekstra veiing i dag, for å sjekke vekten. Hun veide 44.7 kg, så vi har da klart å kompansere for den økte aktiviteten. Økt aktivitet ser også ut til å øke matlysten hennes, hun blir fort sulten og spiser bra om mer en hva som er satt opp på diet planen. Men forbrenningen henens er fortsatt utrolig høy og vi greier ikke å få til vektøkning. Hun har nå ligger på ca 44.6 kg i en månde.'),
(45, '2016-08-09 09:40:00', '2016-08-09 16:18:20', 5, 'INFO', 'Veiing', 'Emilie slo seg litt vrang da tidspunktet for kontroll på Ullevål sykehus ble flyttet fra 1030 til 1300 i dag. Dette grunnet at nytt tidspunkt kræsjer med lunsj,\r\nSå hun er sliter fortsatt litt med å være fleksibel. Hun dro ut for å leke med Sunniva, men kom tilbake kl. 1245 så vi rakk akkurat å stille til kontroll.\r\nDette var siste kontroll på Ullevål sykehus. Fra nå av skal vi kun forholde oss til BUP.'),
(46, '2016-06-17 16:54:00', '2016-08-09 16:56:20', 5, 'INFO', 'pepilie', 'Emilie kjøpt godtepose og spiste litt av den. Sier at hun fortsatt tenker mye på å ikke sitte. Sliter med å kunne slappe av. Vil ikke overnatte hos mamma fordi hun er redd for at det blir for mange fristelser under kveldsmaten og frokosten.'),
(47, '2016-06-08 16:56:00', '2016-08-09 16:57:24', 5, 'INFO', 'pepilie', 'Emilie forteller at hun har veldig lyst til å få ett naturlig forhold til trening, samt å kunne legge seg ned å slappe av.'),
(48, '2016-06-07 16:57:00', '2016-08-09 16:58:38', 5, 'INFO', 'pepilie', ' Hun liker ikke å veie seg og prøver med å putte ting under genseren.\r\nHun prøver å øke vekten inn mot veiing ved å spise ekstra mye dagen før. Vekten har nå stagnert og det er meget vanskelig å få til en økning. Har bedt bup om å sende søknad til capio.\r\n'),
(49, '2016-06-04 16:58:00', '2016-08-09 17:00:50', 5, 'INFO', 'pepilie', 'Hun nektet å spise havregrøten fordi jeg hadde hatt sukker i. Hun tok da heller 350 ml fresubin. Hun har styrt lunsjen selv i dag. Emilie har vært sammen med Sunna i hele dag. Droppet fresubin til lunsj og middag. Spiste 2 ekstra brødskiver til lunsj. Har også spist litt smågodt. Ser ut som om sulten begynner å ta styringen i ny og ne.\r\n'),
(50, '2016-06-03 17:01:00', '2016-08-09 17:02:57', 5, 'CONFLICT', 'pepilie', 'Jeg ber Emilie om å veie seg. Noe hun nekter å gjøre. Går med på det etter hvert, men prrøver da å putte ting under genseren før hun veier seg.\r\nJeg ser selvfølgelig det og da stikker hun bare av. Hun sykler til skolen på trass av at jeg sier hun ikke får lov til det. Har ringt BUP for å søke råd. Jeg har ikke noe virkemiddel mot henne. Ser også at hun gjemmer unna mat, da særlig nøttenblandingen, som er ett mellommåltid. Hun går også veldig ofte på do etter å ha spist, mye hyppigere enn tidligere. Jeg ønsker nå at det søkes om plass på RASP. Hun nekter å følge mine regler og blir hysterisk.\r\n'),
(51, '2016-06-02 17:03:00', '2016-08-09 17:04:17', 5, 'CONFLICT', 'pepilie', 'kl. 2100 startet en konflikt fordi jeg sier hun må spise go morgen youghert til kveldsmat. Hun skriker, tramper i gulvet og smeller med dørene. Hun er hysterisk og gråter. Jeg sier at jeg ringer bvt hvis hun ikke roer seg. Hun roet seg ned etter vel 30 min. Tok 200 ml fresubin og en brødskive.\r\n'),
(52, '2016-05-11 17:04:00', '2016-08-09 17:05:32', 5, 'INFO', 'pepilie', 'Hun fortsetter med å gjemme unna mat, finner stadig på nye metoder. Men er lett å oppdage, samt å få henne til å spise det eller øke fresubin for å kompansere.\r\n'),
(53, '2016-05-04 17:05:00', '2016-08-09 17:06:30', 5, 'INFO', 'pepilie', 'Emilie har de siste 2 ukene begynt å gjemme unna mat. Hun prøver ikke å skjule det og ting løsere seg bra som regel etter at jeg snakker med henne. Forholdet til mat og der å gå ytterlig opp i vekt er fortsatt problematisk for henne. Men hun er nå mer bevist og kjemper bra i mot sin indre demoner. Lar henne få trene og sykkle til skolen mot at hun tar ekstre fresubin eller tilsvarende. Dette har meget god effekt på humøret hennes. Har også innført ett mellommåltid (nøtter eller energibar),  kl. 1500, som hun ofte styrer selv. Og det går nå nesten knirkefritt. Må bare minne henne på det. Hun kan nå også ta initativ til å spise mer til enkelte måltidere. Særlig hvis hun har hatt en aktiv dag. Hun kjenner ofte sulten melde seg, men er forsatt veldig opptatt av mengede og antall kalorier. Liker he&#314;ler ikke å veie seg lenger. Hun øker vel ca 200 g i uken og kanskje dette er en passe økning slik at hun rekker i venne seg til kroppen endringer og akseptere dem.\r\nJeg snakker masse med henne om alt rundt sykdommen og påpeker viktigheten av ett naturlig forhold til mat og egen kropp.\r\nHun har uttykt missnøye med kroppen sin.\r\nAlt i alt, så svinger det frem og tilbake, men stort sett går det greit og vi er på rett vei med fin progresjon.'),
(54, '2016-03-23 17:07:00', '2016-08-09 17:08:30', 5, 'INFO', 'pepilie', 'Emilie forteller meg at påsken 2015 fortalte Siri Birgitte at hun hadde problemer med å velge og spise mat.\r\nSiri Birgitte hadde da beroliget henne og sagt at det går over. Emilie sier at hun var veldig frustrert over dette og i ett svakt øyeblikk åpnet seg opp for Siri Birgitte.\r\nSiden har hun aldri nevnt dette til noen før hun nå sier dette til meg. Må rådeføre meg og revurdre Siri Birgitte sitt samvær med barna.\r\n'),
(55, '2016-01-09 18:09:00', '2016-08-09 17:11:26', 5, 'INFO', 'pepilie', 'Emilie akkutt innlagt på Ullevål Sykehus hvor det konstanteres at hun har anoreksia. Hun har lav puls, 40 pr. min, samt store mangler på vitaminer og mineraler. Antall hvite blodlegemer er også farlig lavt. Hun har også helvetesild. Hun er 158 cm og veier 35 kg. Situasjonen er kritisk og legen sier at det er fare for liv og helse. De kan heller ikke si om hun vil få varig meen.\r\n'),
(56, '2016-01-01 18:11:00', '2016-08-09 17:20:31', 5, 'INFO', 'pepilie', 'Emilie har gradvis over en lang periode redusert sin kropsvekt fra 45 til 39 kg.\r\nHun er en meget aktiv jente som har holdt på med fotball, dans, riding og bandy.\r\nSamt løping sammen med meg.\r\nPå ett tidspunkt startet hun å bli opptatt av hva hun spiste. Og slik jeg husker det, begynte det med å droppe smør på brødskivene. Slik har det jevnt og trutt utviklet seg til ett unormalt forhold til hva som er sunn og usunn mat. Humøret hennes har også gradvis endret seg til å bli veldig nærtagen og følsom for bemerkninger vedrørende hennes kosthold.\r\nHun blir fort sint og begynner lett å gråte.\r\nPå trass av hennes fokus på sunn mat og lovsstil har jeg ikke kunne se at hun på noen måte har redusert mat inntaket. Hun spiser fire måltider dagen, pluss mellom måltider.\r\nHun tilbereder all mat selv og er svært nøye og bruker lang tid på både tilberedongen og spisingen. Hun spiser derimot mye. Ei har jeg noen gang registrert at hun kaster opp eller kvitter seg med maten på annen måte.\r\nEttersom tiden går blir hun veldig restiktiv hva gpdteri angår og spiser dette kun på lørdager. Registrerer også at hun nesten bare drikker vann.\r\nMedio juni 2015 oppsøker vi fastlegen for kontroll. Dette da hun nå har gått ned ca. 6 kg og veier nå 39 kg. Fastlegen tar blodprøver, men viser ellers ingen tegn til bekymring hva vekt angår. Jeg ber om en henvisning til BUP.\r\nBlodprøven viser at alt står normalt til med Emilie hva neringsinnholdet i blodprøven angår. Det bemerkes at hun har ett snev av allergi  mot kannin.\r\nJeg slår meg til ro med dette resultatet og tenker ikke så mye mer på dette. Høsten 2016 flytter Emilie og ANdreas til sin mamma for å bo der en liten periode mens jeg pusser opp i Stavangergata. Ved Jule tider 2016 er jeg fredig og E og A kommer tilbake til meg. E har de blitt vesentlig tynnere og jeg begynner å bli veldig bekymret.\r\n9. januar 2016 oppsøker jeg fastlegen igjen og hun blir veldig stresset grunnet E sitt lave blodtrykk. Hun ber oss oppsøke Ullevål Sykehus umiddelbart. Noe vi selvfølgelig også gjør. '),
(57, '2016-08-14 07:25:00', '2016-08-14 08:09:35', 5, 'INFO', 'Generelt', 'Generelt sett har Emilie blitt litt bedre på alle områder. Det være seg blant annet, valg av mat, takle uforutsette endringer, sitte/ligge å lese i bok, sitte å se på TV/Film sammen med med. Hun har nå mye lettere for å kunne slappe av og hun er stort sett alltid i godt humør. Hun er i ferd med å vende tilbake til sitt normale jeg.\r\nHun er også flin til å oppsøke og leke med venner. I sommer har hun lekt mye med både Åste og Sunniva, som er nye bekjentskaper fra klassen på Nordberg. \r\n\r\nJeg har også gradvis latt henne få lov til å styre måltidene selv. Noe som går ganske så bra. Men jeg må hele tiden sjekke hva hun spiser.\r\nGenerelt sett spiser hun store prosjoner og mye mat. men utfordingen er at hun stort sett velger mager mat og viker unna de produktene som inneholder mye fett.\r\nHun er flink til å spise og det er nå sulten som styrer tidspunkt for måltider og mengde. Hvis hun har mye aktivitet en dag så spiser hun også mer til måltidene.\r\nHun spiser nå nesten aldri mer youhurt til kveldsmat eller frokost. Hun er også begrenset når det kommer til desert. Hun velger da ofte å heller spiser mer middag.\r\nNoe som også gjennspelier seg når hun kontroll veies, dette da fettprosenten hennes holder seg stabilt lav. Hun øker fortsatt mer i muskelmasse  enn fett.\r\nDette har nok sammenhenge med henes valg av mat, samt tildels høy aktivitet. Forbrenningen hennes er uforandret, dvs. fortsatt meget høy. Så vi må følge med på at hun tar til seg nok næring.'),
(58, '2016-08-14 07:39:00', '2016-08-14 07:45:28', 5, 'INFO', 'Capio', 'Slik som situasjonen er nå, er det ikke behov for med bistand fra Capio.\r\njeg øsnker at hun skal start opp ppå skolen som normalt den 23.08.2016. En innleggelse på Capio vil også skape forstyrrelser rundt skolen.\r\nNår så er sagt, er helsen til Emilie mye viktigere en skolen oppstarten. \r\nJeg ønsker kun å dra på møte for å finne ut hva de tilbyr av hjelp og opplegget rundt dette.\r\nDette i tilfelle behovet dukker opp senere. Emilie er fortsatt syk og trenger masse oppfølging, tilbakeslag vil nok også komme.\r\nDerfor er det greit å få bedre innsikt i hva salgs hjelp man kan forvente å motta.'),
(59, '2016-08-14 07:45:00', '2016-08-14 08:10:15', 5, 'INFO', 'Skolestart', 'Når skolen  nå starer opp igjen 23.08.2016 skal Emilie få lov til å styre lunsj selv. \r\nJeg begynner å jobbe 100% igjen fra og med 23.08.2016. Så for vi se hvordan dette går.\r\nJeg vil fortsette med å veie henne her hjemme hver tirsdag morgen.'),
(60, '2016-08-14 07:48:00', '2016-08-14 07:54:42', 5, 'INFO', 'Fotball', 'Emilie skal få lov til å starte opp med fotball igjen. Vi tar  en uke av gangen og ser hvordan dette virker inn på vekten.\r\nVekten er fortsatt det jeg vil styre aktiviteten etter. Med andre ord, hun skal opp i vekt.'),
(61, '2016-08-14 08:10:00', '2016-08-14 08:16:52', 5, 'INFO', 'Samvær', 'Emilie må fortsatt være mest hos meg. Men hun kan nå får lov til å overnatte hos mamma etter eget ønske i ny og ne.\r\nHun kan nå også få lov til å spise alle måltidene hos mamma.\r\nEmilie har nå såpass god kontroll selv at jeg tror det er fint for henne å kunne være litt mer hos sin mamma.\r\nMen dog ikke mer en en dag av gangen. Dette inntil videre.\r\nMamma må fortsatt jobbe med å kunne styre og følge opp måltidene. Hun har en lei tendens til å høre på og følge alt Emilie sier og foreslår av mat valg.\r\nDette på trass av at menyen ligger her, samt mine oppfordringer til å ikke gjøre så.'),
(62, '2016-08-15 08:44:00', '2016-08-15 08:49:06', 5, 'INFO', 'ROS kurs', 'Jeg har nå besluttet å droppe Capio i denne omgang. \r\nØnsker å se det hele an frem til JUlen 2016, Hvis vi fortsatt sliter med vektøkning kan vi da heller søke om plass på RASP, som ligger i Oslo.\r\nHar derfor kansellert møte på Capio i morgen.\r\n\r\nHar meldt Emilie og meg på følgende kurs http://www.nettros.no/aktiviteter/veien-videre/ som jeg tror kan være nyttig for Emilie.\r\nHar også meldt Emilie og meg inn i ROS.'),
(63, '2016-08-16 08:33:00', '2016-08-16 08:47:13', 5, 'INFO', 'Veien videre', 'Opplegget for Emilie blir som følgende:\r\n<ul>\r\n<li>Vi følger fortsatt gjeldende diet plan</li>\r\n<li>Hun veies kun hjemme, en gang i uken, hver tirsdag morgen.</li> \r\n<li>Hun skal spise lunsj på skolen.</li>\r\n<li>Hun skal få sykle til skolen gitt at hun drikker en YT eller Fresubin når hun kommer frem (dette gjelder fra stvgt.)</li> \r\n<li>Skal begynne med individuell terapi på BUP</li>\r\n<li>KOS kurs veien vidrere 14 og 15 september 2016</li>\r\n<li>Hun skal få være med på fotballkamper og en trening i uken for Sagene. Hun må selvfølgelig kompansere for dette ved å ta til seg ekstra næring.</li>\r\n</ul>\r\nProgresjonen vil evalueres fortløpende av meg og BUP. Vektøkning er fortsatt den viktigste parametern vi styrer etter. Samt at hun fortsetter å vokse.\r\nAlle måltider må fortsatt følges nøye opp og påse at hun spiser ihht. gjeldende diet plan.\r\nVed nyttår vil det så gjøres en ny vurdering om det er behov for utvidet hjelp, i form av RASP, CAPIO, eller tilsvarende.\r\nJeg har forøvrig stor tro på at Emilie vil klare dette selv. Hun er på god vei. '),
(64, '2016-08-16 14:52:00', '2016-08-16 14:57:38', 5, 'CONFLICT', 'Sykkeltur', 'Vi har vært på en ganske lang sykkeltur i dag og badet på Frysja 33 etterpå. Emilie ville ikke ta til seg ekstra næring og nektet å dirkke en YT etterpå.\r\nEi har hun drukket Fresubin eller melk til lunsj, eller spist frukt. Noe som gjør meg ganske så forbannet, fant det derfor best at hun nå er hos Siri Birgitte fra nå av og frem til i morgen. Dette slik at jeg skal få en liten pause og unngå å kjefte for mye på henne. Vi må bare fortsette som vanlig igjen i morgen.'),
(65, '2016-08-16 15:22:00', '2016-08-16 15:23:55', 4, 'INFO', 'Sms til pappa', 'At det går ann og bli så sint for en så liten filleting som at jeg ikke drakk hele den Sjokkomelken!  Du skjønner, det er ikke alltid like lett for meg og det har ingenting å si om jeg har løpt en mil eller sittet på rævva hele dagen for plutselig så blir det vanskelig. Og du kan bare slutte og kalle meg sånne stygge ting for det har du ikke grunn til i det hele tatt! Bare fordi jeg glemte fresubinen så trenger du ikke og kalle meg dust! Selvom du kanskje ikke tenker over det så sårer det! Jeg skjønner at du er møkka lei, og du synes kanskje du har det så fælt fordi du ikke får jobbe og har ikke energi og at du kan bli gæren du og! Du har rett, og jeg hadde gjort hva som helst for og la deg og alle dere slippe og oppleve dette, du trenger ikke og snakke så mye om hvor lei du er, for jeg tenker på det hele tiden og det gjør meg vondt langt inn i hjerterota!  Ikke tro jeg ikke tenker på dere og hvor fælt dere og har hatt det! Men uansett hva du tror, så er det jeg som har det værst.. Selvom jeg ikke snakker om det like mye som deg!\r\n\r\nOg du må tenke før du åpner munnen for det er ikke alt det er like gøy og høre! Jeg gikk ikke fordi jeg hadde så lyst til og dra til mamma! Men jeg føler at du ikke vil ha meg hos deg, noe som tydeligvis stemte..'),
(66, '2016-08-16 21:41:00', '2016-08-16 21:51:13', 5, 'INFO', 'Gjemmer mat', 'Emilie holder fortsatt på med å gjemme unna mat i komoden sin. Fant nå ca 3-4 halve rundstykker som har blitt gjemt unna over en liten periode.\r\nHun gjøre nok dette for å prøve å holde vekten sin i sjakk. Hun har hatt marginal økining i sommer. Hun kontrollerer vekten sin ved valg av mager mat samt aktivitet.\r\nMye tyder på at jeg har gitt henne litt for mye ansvar og anledning til å styre måltidene selv. Samtidig er jeg ikke like streng lengre. Må nok gå ett skritt tilbake og ta tilbake kontrollen. Noe som nok vil skape flere konflikter. For hun har store problemer med å gi slipp på kontrollen over måltidene. det er eldig vanskelig å få henne til å spise noe som hun selv ikke har valgt. Hun skal hele tiden ha særbehandling og vil tilrede måltidene selv. jeg må også stramme inn på desert og frukt til. her har vi slurvet i det siste. Selv om fysikken hennes nå bare bilr bedre og bedre må vi  ikke glemme at hun fortsatt er syk og må ikke få for stor ansvar. da vil hun kjøre seg selv i senk igjen.'),
(67, '2016-08-16 08:10:00', '2016-08-17 08:13:35', 6, 'INFO', 'Kveldsmat', 'Emilie spiste 1 rundstykke, fiskeburger (stor) , 2 små yougerter og melk og fresubin , '),
(68, '2016-08-17 10:16:00', '2016-08-17 10:18:00', 5, 'INFO', 'Sms til emilie', 'Hei Emilie,\r\nVil bare fortelle deg at jeg har funnet all maten du har gjemt i komoden din.\r\nVi må også jobbe mye mer med ditt forhold å ta til seg riktig næring. Særlig etter en aktivitet. \r\nDu kan ikke gå rundt å tro at du gjør alt riktig nå, for det er ikke tilfelle. Du må slutte å telle kalorier og få ett bedre forhold til fet mat. Du går svært lite opp i vekt på trass av at du vokser. Og din fettprosent at fortsatt alt for lav. Så tro ikke at du nå er i mål. Jeg har gitt deg en sjanse til å unngå capio. Bruk den fornuftig. Jeg gidder ikke holde på slik. Ja, jeg blir sint når du holder på\r\n slik. Enten du liker det eller ei. Vær ærlig og snakk, slutt å lure unna, da blir jeg heller ikke sint.\r\nVil også du skal skrive ned dine tanker i nye og ne. Særlig etter at du har hatt det tungt. Gjerne skrive dagbok. \r\nVi må videre i prosessen. Tenk på det.\r\nDu må bare legge det bak deg å gå videre. Det er viktig. Vi kan ikke henge oss opp i probelmene. Men finne måter å løse dem på. Tenk positivt. Vi er på rett vei og mye har blitt bedre. Nedturene skal ikke få dominere. De rister vi av oss og bare fortsetter videre.\r\nSykdommen skal ikke få styre oss, vi skal styre den og bli kvitt den.'),
(69, '2016-08-18 08:17:00', '2016-08-18 23:20:54', 5, 'INFO', 'Dagbok', 'Har blitt enig med Emilie om at hun skal begynne å skrive dagbok. Ikke nødvendigvis hver dag, Men spesielt når hun har tunge dager. Jeg tror det er en fin ting for å bevistgjøre, samt få henne til å reflektere over utfordringene sine.'),
(70, '2016-08-18 23:21:00', '2016-08-18 23:24:46', 5, 'INFO', 'Aktivitet', 'Er som regel titt og ofte ute og spiller litt fotball mot Emilie. Det som nå er nytt er at hun ikke løper så mye rundt lenger. Hun viser tegn på å være litt slapp og giddalaus.\r\nVidere kan hun nå legge seg i senga og surfe på nettet. Hun er blitt mye flinkere til å slappe av og lytte til kroppens signaler. Og det er supert.  '),
(71, '2016-08-23 04:00:00', '2016-08-23 19:02:37', 5, 'CONFLICT', 'Vil ikke spise Middag', 'Emilie gjemmer unna deler av middag og deserten. Hun drikker verken juicen eller fresubinen. Hun blir litt frustrert når jeg påpeker dette. Hun gjemmer unna selv når jeg sitter ved siden av henne. Alt går stille og rolig for seg pg hun er nå på rommet sitt. Jeg lar henne bare være der og ser bare hvordan det utvikler seg. \r\nHun skulle spilt kamp i dag, men det har meldt avbud, siden hun ikke spiser.\r\nHun ønsker ikke å snakke og vil bare være litt alene på rommet sitt og det er greit.\r\nEtterhvert gikk hun ut en times tid og drakk fresibin og juice når hun kom tilbake.\r\nSkal be henne skive i dagboken sin. Alt har foregått i ro og mak. '),
(72, '2016-08-25 05:22:00', '2016-08-25 05:32:40', 5, 'INFO', 'Trening', 'Emilie sliter med å gå opp i vekt og har forsatt ett anstrengt forhold til det. Hun liker seg der hun er nå. Det å starte opp med organisert trening nå synes jeg ikke er noen god ide. Selv om hun har veldig lyst til å begynne på løpetrening. Som forøverig er en utsatt idrett hva spiseforstyrrelse angår.\r\nHun har fotballen og der kan hun få være med på både treninger og kamper, gitt at hun viser god progresjon hva vekten angår.\r\nSamt at hun er da villig til å ta til seg ekstra næring etter en treningsøkt eller kamp.\r\nJeg gjør hele tiden fortløpende evalueringer og betraktninger av reglene og justere de ihht. hennes progresjon.'),
(73, '2016-08-31 03:54:00', '2016-08-31 04:02:07', 5, 'INFO', 'Fotball', 'Det forunder meg litt at Emilie vise liten interesse for å være med på fotball treningen og kamper igjen. Dette på trass av at jeg har sagt at hun kan få lov til å starte opp med det igjen.  Jeg har derfor bestemt meg for å  vente til hun tar initiativ selv, fremfor å hele tiden minne henne på om treninger og kamper. Dette gjelder all aktivitet, vi skal ikke stresse dette, men avvente til hun selv spør og tar initativ til begynne igjen.'),
(74, '2016-08-31 04:55:00', '2016-08-31 04:58:13', 5, 'INFO', 'gymtime', 'Har tilbudt Emilie å være med i gymtimene mot at hun da ikke sykler til skolen de dagene.\r\nHun vil tenke på det, virker som om hun da heller vil sykle.'),
(75, '2016-09-08 05:50:00', '2016-09-08 05:59:26', 5, 'INFO', 'Vektreduksjon', 'Ved siste måling har emilie gått litt ned i vekt. Etter skolestart har det blitt mer ansvar på henne hva måltider angår. Jeg har også begynt å jobbe noe som gjør oppfølgingen mer utfordrende. Vi bare fortsetter som før og ser hvordan vekten.\r\nutvikler seg ved neste måling. \r\nHun er flink til å spise og er mer og ner styrt av sulten. Humøret svinger dog litt mer men  \r\nhåper det er grunnet pubertiteten. Hun er også flinkere til å slappe av og sier nå faktisk titt og ofte at hun ikke gidder å gå ut å spille fotball eller gå tur. Hun har også begynt å lese mer i bøker. Ting ser bra ut men vi må forsatt følge nøye opp og ikke tro at kampen er over. Det er fortsatt mye som plager henne psykisk.'),
(76, '2016-09-10 20:59:00', '2016-09-10 21:00:30', 5, 'INFO', 'Trening', 'Tok med Emilie på en liten treningstur rundt voldsløkka i dag. Vi løp intervall, 3 ganger rundt øvre del. Og hun løper nå like fort som meg.');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `food_recipes`
--

CREATE TABLE IF NOT EXISTS `food_recipes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `recipe_name` varchar(100) DEFAULT NULL,
  `recipe_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dataark for tabell `food_recipes`
--

INSERT INTO `food_recipes` (`id`, `created_date_time`, `last_modified_date_time`, `recipe_name`, `recipe_description`) VALUES
(1, '2016-06-04 07:11:19', '2016-06-04 07:11:19', 'Smoothie', 'Oppskrift for Smoothie'),
(2, '2016-06-04 07:11:19', '2016-06-04 07:11:19', 'Omelett', 'Oppskrift for Omelett'),
(3, '2016-08-06 09:07:23', '2016-08-06 09:07:23', 'Pasta salat', 'Oppskrift for Pasta salat'),
(4, '2016-08-06 09:07:23', '2016-08-06 09:07:23', 'Youghurt blanding', 'Oppskrift for Youghurt blanding'),
(5, '2016-08-20 08:27:59', '2016-08-20 08:27:59', 'Pannekaker', 'Oppskrift for pannekaker');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `food_recipe_items`
--

CREATE TABLE IF NOT EXISTS `food_recipe_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_food_recipe_id` int(11) DEFAULT NULL,
  `recipe_item_name` varchar(100) DEFAULT NULL,
  `recipe_item_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_food_recipe_id` (`fk_food_recipe_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=20 ;

--
-- Dataark for tabell `food_recipe_items`
--

INSERT INTO `food_recipe_items` (`id`, `created_date_time`, `last_modified_date_time`, `fk_food_recipe_id`, `recipe_item_name`, `recipe_item_description`) VALUES
(1, '2016-06-04 07:11:42', '2016-06-04 07:11:42', 1, 'Smoothie', '2,5 dl lettmelk'),
(2, '2016-06-04 07:11:42', '2016-06-04 07:11:42', 1, 'Smoothie', '1,5 dl bær'),
(3, '2016-06-04 07:11:42', '2016-06-04 07:11:42', 1, 'Smoothie', '1 ss Cottage Cheese'),
(4, '2016-06-04 07:11:42', '2016-06-04 07:11:42', 1, 'Smoothie', '2 toppede ss havrekli'),
(5, '2016-06-04 07:11:42', '2016-06-04 07:11:42', 1, 'Smoothie', '1 ss chiafrø'),
(6, '2016-06-04 07:11:42', '2016-06-04 07:11:42', 2, 'Omelett', '3 egg'),
(7, '2016-06-04 07:11:42', '2016-06-04 07:11:42', 2, 'Omelett', '3 ss lettmelk'),
(8, '2016-06-04 07:11:42', '2016-06-04 07:11:42', 2, 'Omelett', '1 ss olivenolje'),
(9, '2016-06-04 07:11:42', '2016-06-04 07:11:42', 2, 'Omelett', '2 skiver kokt skinke'),
(10, '2016-08-06 09:09:03', '2016-08-06 09:09:03', 3, 'Pepilies Youghurt blanding', 'Kesam 200 ml'),
(11, '2016-08-06 09:09:03', '2016-08-06 09:09:03', 3, 'Pepilies Youghurt blanding', 'Bålbær, jordbær, og/eller bingebær ca 100g'),
(12, '2016-08-06 09:09:03', '2016-08-06 09:09:03', 3, 'Pepilies Youghurt blanding', 'Nøtter, Peanøtter eller Cashewnøtter, ca 30g'),
(13, '2016-08-06 09:09:03', '2016-08-06 09:09:03', 4, 'Pepilies Pasta salat', '100g Fullkorns pasta'),
(14, '2016-08-06 09:09:03', '2016-08-06 09:09:03', 4, 'Pepilies Pasta salat', '1 pakke skinke, kylling, kalkun eller hamburgerrygg'),
(15, '2016-08-06 09:09:03', '2016-08-06 09:09:03', 4, 'Pepilies Pasta salat', 'Grønnsaker, paprika, agurk, løk, salatblader'),
(16, '2016-08-20 08:28:36', '2016-08-20 08:28:36', 5, 'Pepilies Pannekaker', '100g Hvetemel (ikke grovt)'),
(17, '2016-08-20 08:28:36', '2016-08-20 08:28:36', 5, 'Pepilies Pannekaker', '05 dl melk'),
(18, '2016-08-20 08:28:36', '2016-08-20 08:28:36', 5, 'Pepilies Pannekaker', '1 ss Rømme/Cottage cheese'),
(19, '2016-08-20 08:28:36', '2016-08-20 08:28:36', 5, 'Pepilies Pannekaker', '1 ts kanel');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `username` varchar(25) DEFAULT NULL,
  `role` varchar(24) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dataark for tabell `roles`
--

INSERT INTO `roles` (`id`, `created_date_time`, `last_modified_date_time`, `username`, `role`) VALUES
(1, '2016-07-29 07:30:00', '2016-07-29 07:30:00', 'admin', 'ROLE_ADMIN'),
(3, '2016-07-29 07:30:00', '2016-07-29 07:30:00', 'guest', 'ROLE_GUEST'),
(4, '2016-07-29 07:30:00', '2016-07-29 07:30:00', 'anonymous', 'ROLE_ANONYMOUS'),
(5, '2016-07-29 07:30:00', '2016-07-29 07:30:00', 'pepilie', 'ROLE_USER'),
(6, '2016-07-29 07:30:00', '2016-07-29 07:30:00', 'pappa', 'ROLE_USER'),
(7, '2016-07-29 07:30:00', '2016-07-29 07:30:00', 'mamma', 'ROLE_USER');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `username` varchar(25) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `enabled` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dataark for tabell `users`
--

INSERT INTO `users` (`id`, `created_date_time`, `last_modified_date_time`, `username`, `password`, `email`, `enabled`) VALUES
(1, '2016-08-11 20:48:40', '2016-08-11 20:48:40', 'admin', '$2a$13$GwPQNWAfenYSb06qxu/Nqevmwe31I4FJreraz34ScjbpAUBnO0S4y', 'gunnar_ronneberg@yahoo.no', 1),
(2, '2016-08-11 20:48:40', '2016-08-11 20:48:40', 'team', '$2a$13$8mkWNrSUAJ6fWJTaV3uvY.3jldSleStDGqf3ONsYwmVcGhlPdhuhK', 'gunnar.ronneberg@gmail.com', 0),
(3, '2016-08-11 20:48:40', '2016-08-11 20:48:40', 'guest', '$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi', '', 1),
(4, '2016-08-11 20:48:40', '2016-08-11 20:48:40', 'pepilie', '$2a$12$Ljya1s3.uWu7svzYEgMT3OUpsMLSwYTgybCID.e/ViVc2x8XM038W', '', 1),
(5, '2016-08-11 20:48:40', '2016-08-11 20:48:40', 'pappa', '$2a$12$ZuV6/OziRcAHJtbqr0cvmOMkae.pgjbSfayFpL5WqkhfmIw.4F17m', '', 1),
(6, '2016-08-11 20:48:40', '2016-08-11 20:48:40', 'mamma', '$2a$12$P1tBOtsZNn7ghpLZRIY3Ae7FnIORN9gq.4BY/7vzes04usHVnPhMi', '', 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `user_diet_menu_item_lnk`
--

CREATE TABLE IF NOT EXISTS `user_diet_menu_item_lnk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_user_id` int(11) NOT NULL,
  `fk_diet_menu_item_id` int(11) NOT NULL,
  `fk_controlled_by_user_id` int(11) DEFAULT NULL,
  `caused_conflict` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`fk_user_id`),
  KEY `fk_diet_menu_item_id` (`fk_diet_menu_item_id`),
  KEY `fk_controlled_by_user_id` (`fk_controlled_by_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=475 ;

--
-- Dataark for tabell `user_diet_menu_item_lnk`
--

INSERT INTO `user_diet_menu_item_lnk` (`id`, `created_date_time`, `last_modified_date_time`, `fk_user_id`, `fk_diet_menu_item_id`, `fk_controlled_by_user_id`, `caused_conflict`) VALUES
(298, '2016-08-12 19:39:55', '1970-01-01 05:00:00', 5, 48, 5, 0),
(299, '2016-08-12 19:40:07', '1970-01-01 05:00:00', 5, 30, 5, 0),
(300, '2016-08-12 19:40:22', '1970-01-01 05:00:00', 5, 29, 5, 0),
(301, '2016-08-12 04:00:00', '1970-01-01 05:00:00', 5, 51, 5, 0),
(302, '2016-08-12 04:00:00', '1970-01-01 05:00:00', 5, 32, 5, 0),
(303, '2016-08-13 21:21:25', '1970-01-01 05:00:00', 5, 27, 5, 0),
(304, '2016-08-13 21:21:31', '1970-01-01 05:00:00', 5, 55, 5, 0),
(305, '2016-08-13 21:21:43', '1970-01-01 05:00:00', 5, 45, 5, 0),
(306, '2016-08-13 21:21:53', '1970-01-01 05:00:00', 5, 76, 5, 0),
(307, '2016-08-13 21:22:09', '1970-01-01 05:00:00', 5, 72, 5, 0),
(308, '2016-08-15 04:00:00', '1970-01-01 05:00:00', 5, 28, 5, 0),
(310, '2016-08-14 04:00:00', '1970-01-01 05:00:00', 5, 54, 5, 0),
(311, '2016-08-14 04:00:00', '1970-01-01 05:00:00', 5, 50, 5, 0),
(313, '2016-08-14 04:00:00', '1970-01-01 05:00:00', 5, 55, 6, 0),
(314, '2016-08-14 04:00:00', '1970-01-01 05:00:00', 5, 27, 5, 0),
(315, '2016-08-14 04:00:00', '1970-01-01 05:00:00', 5, 33, 5, 0),
(317, '2016-08-15 04:00:00', '1970-01-01 05:00:00', 5, 1, 5, 0),
(318, '2016-08-15 04:00:00', '1970-01-01 05:00:00', 5, 61, 5, 0),
(319, '2016-08-15 04:00:00', '1970-01-01 05:00:00', 5, 29, 5, 0),
(320, '2016-08-15 04:00:00', '1970-01-01 05:00:00', 5, 54, 5, 0),
(321, '2016-08-16 04:00:00', '1970-01-01 05:00:00', 5, 28, 5, 0),
(322, '2016-08-16 04:00:00', '1970-01-01 05:00:00', 5, 55, 5, 0),
(323, '2016-08-17 08:08:52', '1970-01-01 05:00:00', 6, 27, 6, 0),
(324, '2016-08-16 04:00:00', '1970-01-01 05:00:00', 6, 72, 6, 0),
(328, '2016-08-16 04:00:00', '1970-01-01 05:00:00', 6, 78, 6, 0),
(329, '2016-08-16 04:00:00', '1970-01-01 05:00:00', 6, 50, 6, 0),
(330, '2016-08-17 04:00:00', '1970-01-01 05:00:00', 6, 29, 6, 0),
(331, '2016-08-17 04:00:00', '1970-01-01 05:00:00', 5, 1, 5, 0),
(333, '2016-08-17 04:00:00', '1970-01-01 05:00:00', 5, 54, 5, 0),
(334, '2016-08-18 04:00:00', '1970-01-01 05:00:00', 5, 74, 5, 0),
(335, '2016-08-18 04:00:00', '1970-01-01 05:00:00', 5, 45, 5, 0),
(336, '2016-08-18 04:00:00', '1970-01-01 05:00:00', 5, 29, 5, 0),
(337, '2016-08-18 04:00:00', '1970-01-01 05:00:00', 5, 61, 5, 0),
(338, '2016-08-18 04:00:00', '1970-01-01 05:00:00', 5, 51, 5, 0),
(339, '2016-08-19 20:52:25', '1970-01-01 05:00:00', 5, 28, 5, 0),
(341, '2016-08-19 20:52:50', '1970-01-01 05:00:00', 5, 33, 5, 0),
(342, '2016-08-19 20:53:00', '1970-01-01 05:00:00', 5, 61, 5, 0),
(343, '2016-08-19 20:53:18', '1970-01-01 05:00:00', 5, 52, 5, 0),
(344, '2016-08-19 04:00:00', '1970-01-01 05:00:00', 5, 55, 4, 0),
(345, '2016-08-20 08:12:46', '1970-01-01 05:00:00', 5, 77, 5, 0),
(346, '2016-08-20 21:50:09', '1970-01-01 05:00:00', 5, 52, 5, 0),
(347, '2016-08-20 04:00:00', '1970-01-01 05:00:00', 5, 29, 5, 0),
(348, '2016-08-20 04:00:00', '1970-01-01 05:00:00', 5, 18, 6, 0),
(349, '2016-08-20 04:00:00', '1970-01-01 05:00:00', 5, 61, 6, 0),
(350, '2016-08-21 04:00:00', '1970-01-01 05:00:00', 5, 27, 5, 0),
(351, '2016-08-21 04:00:00', '1970-01-01 05:00:00', 5, 29, 6, 0),
(352, '2016-08-21 04:00:00', '1970-01-01 05:00:00', 5, 48, 5, 0),
(354, '2016-08-21 04:00:00', '1970-01-01 05:00:00', 5, 60, 6, 0),
(355, '2016-08-21 04:00:00', '1970-01-01 05:00:00', 5, 52, 5, 0),
(356, '2016-08-22 20:20:49', '1970-01-01 05:00:00', 5, 32, 5, 0),
(359, '2016-08-22 20:22:07', '1970-01-01 05:00:00', 5, 51, 5, 0),
(361, '2016-08-22 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(362, '2016-08-22 04:00:00', '1970-01-01 05:00:00', 5, 45, 6, 0),
(363, '2016-08-22 04:00:00', '1970-01-01 05:00:00', 5, 73, 6, 0),
(364, '2016-08-22 04:00:00', '1970-01-01 05:00:00', 5, 62, 6, 0),
(365, '2016-08-21 04:00:00', '1970-01-01 05:00:00', 5, 61, 5, 0),
(366, '2016-08-17 04:00:00', '1970-01-01 05:00:00', 5, 30, 5, 0),
(367, '2016-08-23 06:04:30', '1970-01-01 05:00:00', 5, 28, 5, 0),
(368, '2016-08-23 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(369, '2016-08-23 04:00:00', '1970-01-01 05:00:00', 5, 1, 5, 1),
(370, '2016-08-23 04:00:00', '1970-01-01 05:00:00', 5, 65, 5, 0),
(371, '2016-08-24 16:04:17', '1970-01-01 05:00:00', 5, 74, 5, 0),
(372, '2016-08-24 16:04:38', '1970-01-01 05:00:00', 5, 4, 5, 0),
(373, '2016-08-24 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(374, '2016-08-24 04:00:00', '1970-01-01 05:00:00', 5, 70, 5, 0),
(375, '2016-08-24 04:00:00', '1970-01-01 05:00:00', 5, 30, 5, 0),
(376, '2016-08-24 04:00:00', '1970-01-01 05:00:00', 5, 79, 4, 0),
(377, '2016-08-25 04:00:00', '1970-01-01 05:00:00', 5, 27, 4, 0),
(378, '2016-08-25 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(379, '2016-08-25 04:00:00', '1970-01-01 05:00:00', 5, 53, 6, 0),
(380, '2016-08-25 04:00:00', '1970-01-01 05:00:00', 5, 61, 6, 0),
(381, '2016-08-25 04:00:00', '1970-01-01 05:00:00', 5, 52, 5, 0),
(382, '2016-08-26 04:00:00', '1970-01-01 05:00:00', 5, 49, 5, 0),
(385, '2016-08-26 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(386, '2016-08-26 04:00:00', '1970-01-01 05:00:00', 5, 79, 4, 0),
(387, '2016-08-26 04:00:00', '1970-01-01 05:00:00', 5, 51, 5, 0),
(388, '2016-08-26 04:00:00', '1970-01-01 05:00:00', 5, 77, 5, 0),
(389, '2016-08-27 04:00:00', '1970-01-01 05:00:00', 5, 66, 5, 0),
(390, '2016-08-27 04:00:00', '1970-01-01 05:00:00', 5, 29, 5, 0),
(392, '2016-08-27 04:00:00', '1970-01-01 05:00:00', 5, 31, 6, 0),
(393, '2016-08-27 04:00:00', '1970-01-01 05:00:00', 5, 61, 6, 0),
(394, '2016-08-27 04:00:00', '1970-01-01 05:00:00', 5, 79, 4, 0),
(395, '2016-08-27 04:00:00', '1970-01-01 05:00:00', 5, 54, 5, 0),
(396, '2016-08-28 04:00:00', '1970-01-01 05:00:00', 5, 66, 5, 0),
(397, '2016-08-28 04:00:00', '1970-01-01 05:00:00', 5, 29, 5, 0),
(398, '2016-08-28 04:00:00', '1970-01-01 05:00:00', 5, 5, 5, 0),
(399, '2016-08-28 04:00:00', '1970-01-01 05:00:00', 5, 52, 5, 0),
(400, '2016-08-29 04:00:00', '1970-01-01 05:00:00', 5, 28, 5, 0),
(403, '2016-08-29 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(404, '2016-08-29 04:00:00', '1970-01-01 05:00:00', 5, 2, 6, 0),
(405, '2016-08-29 04:00:00', '1970-01-01 05:00:00', 5, 50, 6, 0),
(406, '2016-08-30 04:00:00', '1970-01-01 05:00:00', 5, 32, 5, 0),
(407, '2016-08-31 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(408, '2016-08-30 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(409, '2016-08-30 04:00:00', '1970-01-01 05:00:00', 5, 1, 5, 0),
(410, '2016-08-30 04:00:00', '1970-01-01 05:00:00', 5, 79, 4, 0),
(412, '2016-08-30 04:00:00', '1970-01-01 05:00:00', 5, 61, 5, 0),
(413, '2016-08-30 04:00:00', '1970-01-01 05:00:00', 5, 65, 5, 0),
(415, '2016-08-31 04:00:00', '1970-01-01 05:00:00', 5, 27, 5, 0),
(416, '2016-08-31 04:00:00', '1970-01-01 05:00:00', 5, 79, 4, 0),
(417, '2016-08-31 04:00:00', '1970-01-01 05:00:00', 5, 4, 5, 0),
(418, '2016-08-31 04:00:00', '1970-01-01 05:00:00', 5, 30, 5, 0),
(419, '2016-08-31 04:00:00', '1970-01-01 05:00:00', 5, 52, 5, 0),
(420, '2016-09-01 04:00:00', '1970-01-01 05:00:00', 5, 74, 5, 0),
(422, '2016-09-01 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(423, '2016-09-01 16:38:32', '1970-01-01 05:00:00', 6, 1, 6, 0),
(424, '2016-09-01 16:39:32', '1970-01-01 05:00:00', 6, 79, 6, 0),
(425, '2016-09-01 04:00:00', '1970-01-01 05:00:00', 5, 51, 5, 0),
(426, '2016-09-01 04:00:00', '1970-01-01 05:00:00', 5, 61, 6, 0),
(428, '2016-09-03 04:00:00', '1970-01-01 05:00:00', 5, 77, 5, 0),
(429, '2016-09-02 04:00:00', '1970-01-01 05:00:00', 5, 28, 5, 0),
(432, '2016-09-02 04:00:00', '1970-01-01 05:00:00', 5, 54, 5, 0),
(433, '2016-09-03 04:00:00', '1970-01-01 05:00:00', 5, 29, 5, 0),
(434, '2016-09-02 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(435, '2016-09-03 18:14:19', '1970-01-01 05:00:00', 6, 53, 6, 0),
(436, '2016-09-03 18:14:33', '1970-01-01 05:00:00', 6, 61, 6, 0),
(438, '2016-09-02 04:00:00', '1970-01-01 05:00:00', 5, 45, 6, 0),
(439, '2016-09-04 04:00:00', '1970-01-01 05:00:00', 5, 77, 5, 0),
(440, '2016-09-04 15:34:41', '1970-01-01 05:00:00', 6, 55, 6, 0),
(441, '2016-09-03 04:00:00', '1970-01-01 05:00:00', 5, 80, 5, 0),
(442, '2016-09-05 16:53:04', '1970-01-01 05:00:00', 6, 48, 6, 0),
(443, '2016-09-05 04:00:00', '1970-01-01 05:00:00', 5, 61, 6, 0),
(444, '2016-09-05 04:00:00', '1970-01-01 05:00:00', 5, 32, 5, 0),
(446, '2016-09-05 04:00:00', '1970-01-01 05:00:00', 5, 51, 5, 0),
(448, '2016-09-04 04:00:00', '1970-01-01 05:00:00', 5, 1, 5, 0),
(449, '2016-09-04 04:00:00', '1970-01-01 05:00:00', 5, 72, 5, 0),
(450, '2016-09-05 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(451, '2016-09-06 19:55:11', '1970-01-01 05:00:00', 5, 45, 5, 0),
(452, '2016-09-06 19:55:20', '1970-01-01 05:00:00', 5, 61, 5, 0),
(453, '2016-09-06 19:55:31', '1970-01-01 05:00:00', 5, 72, 5, 0),
(454, '2016-09-06 19:55:38', '1970-01-01 05:00:00', 5, 27, 5, 0),
(455, '2016-09-06 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(456, '2016-09-07 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(457, '2016-09-07 04:00:00', '1970-01-01 05:00:00', 5, 4, 5, 0),
(458, '2016-09-07 04:00:00', '1970-01-01 05:00:00', 5, 30, 5, 0),
(459, '2016-09-07 04:00:00', '1970-01-01 05:00:00', 5, 52, 5, 0),
(460, '2016-09-07 04:00:00', '1970-01-01 05:00:00', 5, 32, 5, 0),
(461, '2016-09-08 04:00:00', '1970-01-01 05:00:00', 5, 29, 4, 0),
(462, '2016-09-08 04:00:00', '1970-01-01 05:00:00', 5, 77, 5, 0),
(463, '2016-09-08 17:14:30', '1970-01-01 05:00:00', 6, 2, 6, 0),
(464, '2016-09-09 04:00:00', '1970-01-01 05:00:00', 5, 28, 5, 0),
(465, '2016-09-09 04:00:00', '1970-01-01 05:00:00', 5, 55, 4, 0),
(466, '2016-09-09 04:00:00', '1970-01-01 05:00:00', 5, 1, 5, 0),
(467, '2016-09-09 04:00:00', '1970-01-01 05:00:00', 5, 30, 5, 0),
(468, '2016-09-09 04:00:00', '1970-01-01 05:00:00', 5, 52, 5, 0),
(469, '2016-09-08 04:00:00', '1970-01-01 05:00:00', 5, 80, 5, 0),
(470, '2016-09-10 04:00:00', '1970-01-01 05:00:00', 5, 66, 5, 0),
(471, '2016-09-10 04:00:00', '1970-01-01 05:00:00', 5, 2, 5, 0),
(472, '2016-09-10 04:00:00', '1970-01-01 05:00:00', 5, 61, 5, 0),
(473, '2016-09-10 04:00:00', '1970-01-01 05:00:00', 5, 54, 5, 0),
(474, '2016-09-10 04:00:00', '1970-01-01 05:00:00', 5, 29, 5, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
