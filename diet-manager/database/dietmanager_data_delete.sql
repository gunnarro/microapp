# phpMyAdmin SQL Dump
# version 4.0.10.12
# http://www.phpmyadmin.net
#
# Host: 127.9.127.2:3306
# Generation Time: Sep 29, 2017 at 11:03 AM
# Server version: 5.5.52
# PHP Version: 5.3.3

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

DELETE FROM bmi_percentile_data WHERE id > 0;
DELETE FROM body_measurements_log WHERE id > 0;
DELETE FROM diet_menus WHERE id > 0;
DELETE FROM diet_menu_items WHERE id > 0;
# DELETE FROM diet_plans WHERE id > 0;
DELETE FROM diet_plan_meal_items  WHERE id > 0;
DELETE FROM diet_plan_meals WHERE id > 0;
DELETE FROM diet_products WHERE id > 0;
DELETE FROM diet_product_equivalent_items WHERE id > 0;
DELETE FROM diet_rules WHERE id > 0;
DELETE FROM event_log WHERE id > 0;
DELETE FROM event_log_comment WHERE id > 0;
DELETE FROM food_recipes WHERE id > 0;
DELETE FROM food_recipe_items WHERE id > 0;
DELETE FROM growth_reference_data WHERE id > 0;
DELETE FROM health_reference_data WHERE id > 0;
DELETE FROM permissions WHERE id > 0;
DELETE FROM products WHERE id > 0;
DELETE FROM profiles WHERE id > 0;
DELETE FROM roles WHERE id > 0;
DELETE FROM role_permission_lnk WHERE id > 0;
# DELETE FROM UserConnection WHERE id > 0;
DELETE FROM users WHERE id > 0;
DELETE FROM user_diet_menu_item_lnk WHERE id > 0;
DELETE FROM user_follower_lnk WHERE id > 0;
DELETE FROM user_role_lnk WHERE id > 0;
DELETE FROM user_diet_menu_item_lnk WHERE id > 0;
DELETE FROM user_diet_menu_item_lnk WHERE id > 0;
DELETE FROM user_diet_menu_item_lnk WHERE id > 0;
DELETE FROM user_diet_menu_item_lnk WHERE id > 0;
DELETE FROM user_diet_menu_item_lnk WHERE id > 0;
DELETE FROM user_diet_menu_item_lnk WHERE id > 0;
DELETE FROM user_diet_menu_item_lnk WHERE id > 0;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- Turn on fk check						
SET FOREIGN_KEY_CHECKS=1;
