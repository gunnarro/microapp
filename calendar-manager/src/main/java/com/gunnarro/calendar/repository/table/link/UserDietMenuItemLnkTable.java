package com.gunnarro.calendar.repository.table.link;

public class UserDietMenuItemLnkTable {

    // Database table
    public static final String TABLE_NAME = "user_diet_menu_item_lnk";
    public static final String COLUMN_FK_USER_ID = "fk_user_id";
    public static final String COLUMN_FK_DIET_MENU_ITEM_ID = "fk_diet_menu_item_id";
    
    private static final String[] TABLE_FK_COLUMNS = { COLUMN_FK_USER_ID, COLUMN_FK_DIET_MENU_ITEM_ID };
    
    private UserDietMenuItemLnkTable() {
    }

    public static String[] getFKColumnNames() {
        return TABLE_FK_COLUMNS.clone();
    }
}
