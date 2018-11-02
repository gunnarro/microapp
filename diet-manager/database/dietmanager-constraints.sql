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
