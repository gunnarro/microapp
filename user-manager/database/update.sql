-- update 20.05.2016
ALTER TABLE user_diet_menu_item_lnk
ADD COLUMN fk_controlled_by_user_id INTEGER AFTER fk_diet_menu_item_id


ALTER TABLE user_diet_menu_item_lnk
ADD FOREIGN KEY fk_controlled_by_user_id(fk_controlled_by_user_id)
REFERENCES users(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE user_diet_menu_item_lnk
ADD COLUMN caused_conflict INTEGER DEFAULT 0 AFTER fk_controlled_by_user_id


-- update 27.06.2016
ALTER TABLE event_log MODIFY COLUMN last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
UPDATE event_log 
SET last_modified_date_time = created_date_time
WHERE fk_user_id = 1
LIMIT 30;

ALTER TABLE user_diet_menu_item_lnk MODIFY COLUMN last_modified_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP; '1970-01-01 00:00:00'
UPDATE user_diet_menu_item_lnk 
SET last_modified_date_time = created_date_time
WHERE fk_user_id = 1
LIMIT 100;