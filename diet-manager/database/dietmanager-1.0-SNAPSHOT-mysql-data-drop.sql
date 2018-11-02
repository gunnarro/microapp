------------------------------------------------------------------------------------------
-- DB name		 : Dietmanager DB
-- DB Component	 : drop
-- Release date	 : 09.06.2016
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------

-- clear all tables
DELETE FROM diet_plan_meal_items WHERE id > 0;
DELETE FROM diet_plan_meals WHERE id > 0;
DELETE FROM diet_plans WHERE id > 0;
DELETE FROM user_diet_menu_item_lnk WHERE id > 0;
DELETE FROM diet_menu_items WHERE id > 0;
DELETE FROM diet_menus WHERE id > 0;
DELETE FROM diet_product_equivalent_items WHERE id > 0;
DELETE FROM diet_products WHERE id > 0;
DELETE FROM food_recipes WHERE id > 0;
DELETE FROM food_recipe_items WHERE id > 0;

-- drop settings
DELETE FROM users WHERE id > 0;
