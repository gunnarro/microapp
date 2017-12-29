# phpMyAdmin SQL Dump
# version 4.0.10.12
# http://www.phpmyadmin.net
# 
# Host: 127.9.127.2:3306
# Generation Time: Sep 29, 2017 at 11:11 AM
# Server version: 5.5.52
# PHP Version: 5.3.3

# DROP DATABASE dietmanager;
# CREATE DATABASE IF NOT EXISTS dietmanager;
USE dietmanager;

SET SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO';
SET time_zone = "+00:00";

-- Turn off fk check
SET FOREIGN_KEY_CHECKS=0;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

# 
# Database: `dietmanager`
# 

########################################### 

# 
# Table structure for table `bmi_percentile_data`
# 
DROP TABLE IF EXISTS `bmi_percentile_data`;
CREATE TABLE IF NOT EXISTS `bmi_percentile_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fk_health_reference_data_id` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `p25` decimal(6,3) DEFAULT NULL,
  `p50` decimal(6,3) DEFAULT NULL,
  `p75` decimal(6,3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_health_reference_data_id` (`fk_health_reference_data_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `body_measurements_log`
# 
DROP TABLE IF EXISTS `body_measurements_log`;
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `diet_menus`
# 
DROP TABLE IF EXISTS `diet_menus`;
CREATE TABLE IF NOT EXISTS `diet_menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `menu_name` varchar(100) DEFAULT NULL,
  `menu_description` varchar(500) DEFAULT NULL,
  `menu_active` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `diet_menu_items`
# 
DROP TABLE IF EXISTS `diet_menu_items`;
CREATE TABLE IF NOT EXISTS `diet_menu_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_diet_menu_id` int(11) DEFAULT NULL,
  `menu_item_name` varchar(100) DEFAULT NULL,
  `menu_item_category` varchar(25) DEFAULT NULL,
  `menu_item_description` varchar(500) DEFAULT NULL,
  `menu_item_energy_kcal` int(11) DEFAULT NULL,
  `menu_item_img_link` varchar(100) DEFAULT NULL,
  `menu_item_enabled` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_diet_menu_id` (`fk_diet_menu_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `diet_plans`
# 
DROP TABLE IF EXISTS `diet_plans`;
CREATE TABLE IF NOT EXISTS `diet_plans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `start_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `diet_plan_name` varchar(100) DEFAULT NULL,
  `diet_plan_description` varchar(200) DEFAULT NULL,
  `diet_plan_active` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `diet_plan_meals`
# 
DROP TABLE IF EXISTS `diet_plan_meals`;
CREATE TABLE IF NOT EXISTS `diet_plan_meals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `start_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `meal_name` varchar(100) DEFAULT NULL,
  `meal_period` varchar(200) DEFAULT NULL,
  `meal_description` varchar(200) DEFAULT NULL,
  `meal_default` int(11) DEFAULT '0',
  `meal_order` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `diet_plan_meal_items`
# 
DROP TABLE IF EXISTS `diet_plan_meal_items`;
CREATE TABLE IF NOT EXISTS `diet_plan_meal_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fk_diet_plan_id` int(11) DEFAULT NULL,
  `fk_diet_plan_meal_id` int(11) DEFAULT NULL,
  `meal_item_name` varchar(100) DEFAULT NULL,
  `meal_item_type` varchar(100) DEFAULT NULL,
  `meal_item_description` varchar(500) DEFAULT NULL,
  `meal_item_energy_kcal` int(11) DEFAULT NULL,
  `meal_item_img_link` varchar(100) DEFAULT NULL,
  `meal_item_enabled` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_diet_plan_id` (`fk_diet_plan_id`),
  KEY `fk_diet_plan_meal_id` (`fk_diet_plan_meal_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `diet_products`
# 
DROP TABLE IF EXISTS `diet_products`;
CREATE TABLE IF NOT EXISTS `diet_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `product_name` varchar(100) DEFAULT NULL,
  `product_type` varchar(100) DEFAULT NULL,
  `product_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `diet_product_equivalent_items`
# 
DROP TABLE IF EXISTS `diet_product_equivalent_items`;
CREATE TABLE IF NOT EXISTS `diet_product_equivalent_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_diet_product_id` int(11) DEFAULT NULL,
  `product_equivalent_name` varchar(100) DEFAULT NULL,
  `product_equivalent_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_diet_product_id` (`fk_diet_product_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `diet_rules`
# 
DROP TABLE IF EXISTS `diet_rules`;
CREATE TABLE IF NOT EXISTS `diet_rules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fk_diet_plan_id` int(11) DEFAULT NULL,
  `rule_name` varchar(100) DEFAULT NULL,
  `rule_description` varchar(200) DEFAULT NULL,
  `rule_active` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_diet_plan_id` (`fk_diet_plan_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `event_log`
# 
DROP TABLE IF EXISTS `event_log`;
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `event_log_comment`
# 
DROP TABLE IF EXISTS `event_log_comment`;
CREATE TABLE IF NOT EXISTS `event_log_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fk_user_id` int(11) DEFAULT NULL,
  `fk_event_log_id` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `content` varchar(4096) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`fk_user_id`),
  KEY `fk_event_log_id` (`fk_event_log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `food_recipes`
# 
DROP TABLE IF EXISTS `food_recipes`;
CREATE TABLE IF NOT EXISTS `food_recipes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `recipe_name` varchar(100) DEFAULT NULL,
  `recipe_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `food_recipe_items`
# 
DROP TABLE IF EXISTS `food_recipe_items`;
CREATE TABLE IF NOT EXISTS `food_recipe_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_food_recipe_id` int(11) DEFAULT NULL,
  `recipe_item_name` varchar(100) DEFAULT NULL,
  `recipe_item_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_food_recipe_id` (`fk_food_recipe_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `growth_reference_data`
# 
DROP TABLE IF EXISTS `growth_reference_data`;
CREATE TABLE IF NOT EXISTS `growth_reference_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fk_health_reference_data_id` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `bmi_p25` decimal(6,3) DEFAULT NULL,
  `bmi_p50` decimal(6,3) DEFAULT NULL,
  `bmi_p75` decimal(6,3) DEFAULT NULL,
  `weight_p25` decimal(6,3) DEFAULT NULL,
  `weight_p50` decimal(6,3) DEFAULT NULL,
  `weight_p75` decimal(6,3) DEFAULT NULL,
  `height_p25` decimal(6,3) DEFAULT NULL,
  `height_p50` decimal(6,3) DEFAULT NULL,
  `height_p75` decimal(6,3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_health_reference_data_id` (`fk_health_reference_data_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `health_reference_data`
# 
DROP TABLE IF EXISTS `health_reference_data`;
CREATE TABLE IF NOT EXISTS `health_reference_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(25) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `permissions`
# 
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE IF NOT EXISTS `permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `name` varchar(25) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `products`
# 
DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `weight_g` int(11) DEFAULT NULL,
  `kcal` int(11) DEFAULT NULL,
  `fat` decimal(6,2) DEFAULT NULL,
  `carbohydrates` decimal(6,2) DEFAULT NULL,
  `fibre` decimal(6,2) DEFAULT NULL,
  `protein` decimal(6,2) DEFAULT NULL,
  `img_link` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `profiles`
# 
DROP TABLE IF EXISTS `profiles`;
CREATE TABLE IF NOT EXISTS `profiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fk_user_id` int(11) DEFAULT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `middlename` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `enabled` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `firstname` (`firstname`,`middlename`,`lastname`,`date_of_birth`),
  KEY `fk_user_id` (`fk_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `roles`
# 
DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `name` varchar(24) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `role_permission_lnk`
# 
DROP TABLE IF EXISTS `role_permission_lnk`;
CREATE TABLE IF NOT EXISTS `role_permission_lnk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fk_role_id` int(11) DEFAULT NULL,
  `fk_permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_role_id` (`fk_role_id`),
  KEY `fk_permission_id` (`fk_permission_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `users`
# 
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `enabled` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `user_details_log`
# 
DROP TABLE IF EXISTS `user_details_log`;
CREATE TABLE IF NOT EXISTS `user_details_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_logged_in_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_logged_in_from_ip_address` varchar(50) DEFAULT NULL,
  `last_logged_in_from_device` varchar(200) DEFAULT NULL,
  `login_attempt_failures` int(11) DEFAULT '0',
  `login_attempt_success` int(11) DEFAULT '0',
  `fk_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fk_user_id` (`fk_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;



########################################### 

# 
# Table structure for table `user_diet_menu_item_lnk`
# 
DROP TABLE IF EXISTS `user_diet_menu_item_lnk`;
CREATE TABLE IF NOT EXISTS `user_diet_menu_item_lnk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '1970-01-01 05:00:00',
  `fk_user_id` int(11) NOT NULL,
  `fk_diet_menu_item_id` int(11) NOT NULL,
  `fk_controlled_by_user_id` int(11) DEFAULT NULL,
  `fk_prepared_by_user_id` int(11) DEFAULT '0',
  `caused_conflict` int(11) DEFAULT '0',
  `fk_log_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`fk_user_id`),
  KEY `fk_diet_menu_item_id` (`fk_diet_menu_item_id`),
  KEY `fk_controlled_by_user_id` (`fk_controlled_by_user_id`),
  KEY `fk_log_id` (`fk_log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `user_follower_lnk`
# 
DROP TABLE IF EXISTS `user_follower_lnk`;
CREATE TABLE IF NOT EXISTS `user_follower_lnk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fk_user_id` int(11) DEFAULT NULL,
  `fk_user_follower_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`fk_user_id`),
  KEY `fk_user_follower_id` (`fk_user_follower_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

########################################### 

# 
# Table structure for table `user_role_lnk`
# 
DROP TABLE IF EXISTS `user_role_lnk`;
CREATE TABLE IF NOT EXISTS `user_role_lnk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_date_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fk_user_id` int(11) DEFAULT NULL,
  `fk_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`fk_user_id`),
  KEY `fk_role_id` (`fk_role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;


-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;

# 
# Constraints for dumped tables
# 

# 
# Constraints for table `bmi_percentile_data`
# 
ALTER TABLE `bmi_percentile_data`
  ADD CONSTRAINT `bmi_percentile_data_ibfk_1` FOREIGN KEY (`fk_health_reference_data_id`) REFERENCES `health_reference_data` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `body_measurements_log`
# 
ALTER TABLE `body_measurements_log`
  ADD CONSTRAINT `body_measurements_log_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `diet_menu_items`
# 
ALTER TABLE `diet_menu_items`
  ADD CONSTRAINT `diet_menu_items_ibfk_1` FOREIGN KEY (`fk_diet_menu_id`) REFERENCES `diet_menus` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `diet_plan_meal_items`
# 
ALTER TABLE `diet_plan_meal_items`
  ADD CONSTRAINT `diet_plan_meal_items_ibfk_1` FOREIGN KEY (`fk_diet_plan_id`) REFERENCES `diet_plans` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `diet_plan_meal_items_ibfk_2` FOREIGN KEY (`fk_diet_plan_meal_id`) REFERENCES `diet_plan_meals` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `diet_product_equivalent_items`
# 
ALTER TABLE `diet_product_equivalent_items`
  ADD CONSTRAINT `diet_product_equivalent_items_ibfk_1` FOREIGN KEY (`fk_diet_product_id`) REFERENCES `diet_products` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `diet_rules`
# 
ALTER TABLE `diet_rules`
  ADD CONSTRAINT `diet_rules_ibfk_1` FOREIGN KEY (`fk_diet_plan_id`) REFERENCES `diet_plans` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `event_log`
# 
ALTER TABLE `event_log`
  ADD CONSTRAINT `event_log_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `event_log_comment`
# 
ALTER TABLE `event_log_comment`
  ADD CONSTRAINT `event_log_comment_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `event_log_comment_ibfk_2` FOREIGN KEY (`fk_event_log_id`) REFERENCES `event_log` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `food_recipe_items`
# 
ALTER TABLE `food_recipe_items`
  ADD CONSTRAINT `food_recipe_items_ibfk_1` FOREIGN KEY (`fk_food_recipe_id`) REFERENCES `food_recipes` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `growth_reference_data`
# 
ALTER TABLE `growth_reference_data`
  ADD CONSTRAINT `growth_reference_data_ibfk_1` FOREIGN KEY (`fk_health_reference_data_id`) REFERENCES `health_reference_data` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `profiles`
# 
ALTER TABLE `profiles`
  ADD CONSTRAINT `profiles_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `role_permission_lnk`
# 
ALTER TABLE `role_permission_lnk`
  ADD CONSTRAINT `role_permission_lnk_ibfk_1` FOREIGN KEY (`fk_role_id`) REFERENCES `roles` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `role_permission_lnk_ibfk_2` FOREIGN KEY (`fk_permission_id`) REFERENCES `permissions` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `user_details_log`
# 
ALTER TABLE `user_details_log`
  ADD CONSTRAINT `user_details_log_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `user_diet_menu_item_lnk`
# 
ALTER TABLE `user_diet_menu_item_lnk`
  ADD CONSTRAINT `user_diet_menu_item_lnk_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_diet_menu_item_lnk_ibfk_2` FOREIGN KEY (`fk_diet_menu_item_id`) REFERENCES `diet_menu_items` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_diet_menu_item_lnk_ibfk_3` FOREIGN KEY (`fk_controlled_by_user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_diet_menu_item_lnk_ibfk_4` FOREIGN KEY (`fk_log_id`) REFERENCES `event_log` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `user_follower_lnk`
# 
ALTER TABLE `user_follower_lnk`
  ADD CONSTRAINT `user_follower_lnk_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `user_follower_lnk_ibfk_2` FOREIGN KEY (`fk_user_follower_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

# 
# Constraints for table `user_role_lnk`
# 
ALTER TABLE `user_role_lnk`
  ADD CONSTRAINT `user_role_lnk_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `user_role_lnk_ibfk_2` FOREIGN KEY (`fk_role_id`) REFERENCES `roles` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
